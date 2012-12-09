package pl.edu.agh.student.nanostarwars.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.Log;

public class Star extends GameElement {
	
	/**
	 * How many points does the star have?
	 */
	private int points;
	
	/**
	 * Size of the star. Bigger star = quicker points growth
	 */
	int size;
	public int getSize() {return size;}
	
	/**
	 * How many updates to point incrementation
	 */
	private int spawnCounter;
	
	public int getPoints() {return points;}
	
	public Star(Bitmap bitmap, Vec position, Player player, int size) {
		super(bitmap, position, player);
		
		this.size = size;
		this.points = 0;
		this.spawnCounter = 0;
		if(player == null) {
			this.points = size/10*5;
		}
	}

	public boolean hasPlayer() {
		return this.player != null;
	}
	
	@Override
	public synchronized void update() {
		if(this.player != null) {
			this.spawnCounter++;
			if(spawnCounter >= 1000/size){
				points++;
				this.spawnCounter = 0;
			}
		}
	}
	
	public void draw(Canvas canvas) {
		if(player == null){
			Paint bgPaint = new Paint();
			bgPaint.setColor(Color.WHITE);
//			canvas.drawCircle(this.position.x(), this.position.y(), this.getSize(), bgPaint);
			canvas.drawCircle(this.position.x(), this.position.y(), 12, bgPaint);
			canvas.drawBitmap(this.bitmap, this.position.x() - 25, this.position.y() - 25, null);
			drawPointsOnStar(canvas);
		}
		else{
//			canvas.drawCircle(this.position.x(), this.position.y(), this.getSize(), player.getColor());	

			canvas.drawCircle(this.position.x(), this.position.y(), 12, player.getColor());
			canvas.drawBitmap(this.bitmap, this.position.x() - 25, this.position.y() - 25, null);
			drawPointsOnStar(canvas);
		}
	}

	private void drawPointsOnStar(Canvas canvas) {
		Paint txtPaint = new Paint();
		txtPaint.setColor(Color.BLACK);
		txtPaint.setShadowLayer(1, 0, 0, Color.WHITE);
		txtPaint.setTextSize((int)(this.getSize()));
		canvas.drawText(Integer.toString(this.getPoints()),
						this.position.x()-txtPaint.measureText(Integer.toString(this.getSize()+15))/2,
						this.position.y()+this.getSize()/2,
						txtPaint);
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
		
		return new Missile(this.player, pointsToBeSent, star, this );
	}
	
	/**
	 * Fired when missile reaches its target
	 * @param m
	 */
	public synchronized void hitBy(Missile m, Vibrator vib) {
		boolean vibrationNeeded = false;
		
		if(m.getOwner().equals(this.player)) { // it's your cell
			this.points += m.getPoints();
		} else { //enemy hit
			this.points -= m.getPoints();
			if(points < 0) {// owner changed
				this.points = -this.points;
				if(this.player != null)
					if(this.player.getNumber() == 0)
						vibrationNeeded = true;
				this.player = m.getOwner();
				
				if(vibrationNeeded) {
					vib.vibrate(200);
				}
			}
		}
	}
}
