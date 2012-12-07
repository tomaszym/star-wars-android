package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Paint;

/**
 * Dzięki, że nie zrobiłeś enumów! :D
 */
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
}
