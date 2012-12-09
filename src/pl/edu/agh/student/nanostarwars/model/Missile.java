package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;


public class Missile {

	/**
	 * Who's fired the missile
	 */
	private final Player owner;
	
	/**
	 * How big it is
	 */
	private final int points;
	
	public int getPoints() {
		return points;
	}

	/**
	 * What is it fired at
	 */
	private final Star target;
	private final Star cannon;
	
	private final int speed = 1;
	
	private final double distance; 
	
	private final int diffY;
	private final int diffX;
	/**
	 * is it in the air or hit?
	 */
	private boolean hit;
	
	public boolean isHit() {
		return hit;
	}

	private Vec position;
	
	public Vec getPosition() {
		return position;
	}

	/**
	 * How far has it gotten already
	 * Target hit if >= distance
	 */
	private double distanceMoved;
	
	public Player getOwner() {
		return owner;
	}

	public Missile(Player owner, int points, Star target, Star cannon) {
		this.owner = owner;
		this.points = points;
		this.target = target;
		this.distance = Vec.distance(target.getPosition(), cannon.getPosition());
		this.diffX = cannon.getPosition().x()-target.getPosition().x();
		this.diffY = cannon.getPosition().y()-target.getPosition().y();
		this.distanceMoved = 0;
		this.cannon = cannon;
		this.hit = false;
		this.position = cannon.getPosition();
	}
	
	/**
	 * Moves missle and checks if hit
	 */
	public void update() {
		this.position = newPosition();
		if(this.position.equals(target.getPosition()))
		{
			target.hitBy(this);
			this.hit = true;
		}	
	}
	
	private Vec newPosition() {
		distanceMoved += speed;
		return new Vec(this.cannon.getPosition().x() - Math.round(distanceMoved/distance*diffX),
					   this.cannon.getPosition().y() - Math.round(distanceMoved/distance*diffY) );
	}

	
	public void draw(Canvas canvas) {
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.WHITE);
		canvas.drawCircle(this.position.x(), this.position.y(), 15, bgPaint);
	}
}
