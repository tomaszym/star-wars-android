package pl.edu.agh.student.nanostarwars.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star extends GameElement {
	
	/**
	 * How many points does the star have?
	 */
	private int points;
	/**
	 * Where it is
	 */
	private Vec position;
	/**
	 * Who does it belong to
	 */
	private Player player;
	/**
	 * What's this?
	 */
	private int spawnCounter;
	/**
	 * Returns size of the star for display purposes.
	 * @return
	 */
	public int getSize() {
		/**
		 * wtf floor zwraca double? :D
		 */
		return (int) Math.max(Math.floor(Math.sqrt(points)), 50);
	}
	
	public Star(Bitmap bitmap, Vec position, Player player, int size) {
		super(bitmap, position, player);
		
		this.position = position;
		this.points = 0;
		this.spawnCounter = 0;
	}

	@Override
	public synchronized void update() {
		this.spawnCounter++;
//		if(spawnCounter >= 1000/size){
//			missiles.add(new Missile(this.player));
//			this.spawnCounter = 0;
//		}
		if(spawnCounter > 2) {
			points++;
			spawnCounter = 0;
		}
	}
	
	public void draw(Canvas canvas) {
		if(player == null){
			Paint bgPaint = new Paint();
			bgPaint.setColor(Color.GRAY);
			canvas.drawCircle(this.position.x(), this.position.y(), this.getSize(), bgPaint);
			Paint txtPaint = new Paint();
			txtPaint.setColor(Color.WHITE);
			txtPaint.setTextSize((int)(this.getSize()/1.5));
			canvas.drawText(Integer.toString(this.getSize()),
							this.position.x()-txtPaint.measureText(Integer.toString(this.getSize()))/2,
							this.position.y()+this.getSize()/6,
							txtPaint);
		}
		else{
			canvas.drawCircle(this.position.x(), this.position.y(), this.getSize(), player.getColor());
		}
	}
	
	public void setPosition(int x, int y){
		this.position.setX(x);
		this.position.setY(y);
	}


	public void drawArrow(Canvas canvas, Bitmap arrowBitmap, Vec position) {
		
	}


	public synchronized Missile sendMissile(Star star) {
		int pointsToBeSent = points/2;
		points -= pointsToBeSent;
		
		return new Missile(this.player, pointsToBeSent, star);
	}
}
