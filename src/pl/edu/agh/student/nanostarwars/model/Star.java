package pl.edu.agh.student.nanostarwars.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star extends GameElement {
	
	private int size;
	private Vec position;
	private Player player;
	private int spawnCounter;
	private List<Missile> missiles = null;
	
	public Star(Bitmap bitmap, Vec position, Player player, int size) {
		super(bitmap, position, player);
		
		missiles = new LinkedList<Missile>();
		this.position = position;
		this.size = size;
		this.spawnCounter = 0;
	}
	

	public int getSize() {
		return this.size;
	}

	@Override
	public synchronized void update() {
		this.spawnCounter++;
		if(spawnCounter >= 1000/size){
			missiles.add(new Missile(this.player));
			this.spawnCounter = 0;
		}
	}
	
	public void draw(Canvas canvas) {
		if(player == null){
			Paint bgPaint = new Paint();
			bgPaint.setColor(Color.GRAY);
			canvas.drawCircle(this.position.x(), this.position.y(), this.size, bgPaint);
			Paint txtPaint = new Paint();
			txtPaint.setColor(Color.WHITE);
			txtPaint.setTextSize((int)(this.size/1.5));
			canvas.drawText(Integer.toString(this.missiles.size()), this.position.x()-txtPaint.measureText(Integer.toString(this.missiles.size()))/2, this.position.y()+this.size/6, txtPaint);
		}
		else{
			canvas.drawCircle(this.position.x(), this.position.y(), this.size, player.getColor());
		}
	}
	
	public void setPosition(int x, int y){
		this.position.setX(x);
		this.position.setY(y);
	}


	public void drawArrow(Canvas canvas, Bitmap arrowBitmap, Vec position) {
		
	}


	public synchronized void sendMissiles(Star star) {
		star.missiles.addAll(this.missiles);
	}
}
