package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameElement {

	protected Vec position;
	
	protected Bitmap bitmap;

	/**
	 * 0 = human player, >0 computers
	 */
	protected int team;
	
	public GameElement(Bitmap bitmap, Vec position, int team) {
		this.bitmap = bitmap;
		this.position = position;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, position.x(), position.y(), null);
	}

	
	abstract public void update();

	public Vec getPosition() {
		return position;
	}
}
