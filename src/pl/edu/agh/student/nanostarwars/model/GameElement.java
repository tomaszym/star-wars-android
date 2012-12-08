package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameElement {

	protected Vec position;
	
	protected Bitmap bitmap;
	
	protected Player player;
	
	public GameElement(Bitmap bitmap, Vec position, Player player) {
		this.bitmap = bitmap;
		this.position = position;
		this.player = player;
	}

	public GameElement() {
		this.bitmap = null;
		this.position = null;
		this.player = null;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, position.x(), position.y(), null);
	}

	
	abstract public void update();

	public Vec getPosition() {
		return position;
	}
}
