package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Paint;

public class Player {

	/**
	 * 0 = human player, >0 computers
	 */
	protected int number;
	protected Paint color;
	
	public int getNumber() {
		return number;
	}

	public Paint getColor() {
		return color;
	}

	public Player(int number, Paint color) {
		this.number = number;
		this.color = color;
	}

	public Player(int number, int color) {
		this.number = number;
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        this.color = mPaint;
	}
	public boolean equals(Object thatObj) {
		if (thatObj == null)
			return false;
		if (this == thatObj)
			return true;
		if (!(this.getClass().equals(thatObj.getClass())))
			return false;
		Player thatPlayer = (Player) thatObj;
		
		return testEquals(thatPlayer);
	}
	
	protected boolean testEquals(Player that) {
		return this.getNumber() == that.getNumber();
	}
}
