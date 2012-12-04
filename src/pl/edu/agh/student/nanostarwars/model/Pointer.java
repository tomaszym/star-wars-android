package pl.edu.agh.student.nanostarwars.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Pointer {

	private Bitmap arrowBitmap;
	private Vec sourcePosition;
	private boolean visibility;
	
	public boolean isVisible() {
		return visibility;
	}
	
	public void show() {
		this.visibility = true;
	}
	public void hide() {
		this.visibility = false;
	}
	
	public Vec getSourcePosition() {
		return sourcePosition;
	}

	public void setSourcePosition(Vec sourcePosition) {
		this.sourcePosition = sourcePosition;
	}

	public Vec getDestPosition() {
		return destPosition;
	}

	public void setDestPosition(Vec destPosition) {
		this.destPosition = destPosition;
	}

	public Bitmap getArrowBitmap() {
		return arrowBitmap;
	}

	private Vec destPosition;

	public Pointer(Bitmap arrowBitmap, Vec sourcePosition, Vec destPosition) {
		this.arrowBitmap = arrowBitmap;
		this.sourcePosition = sourcePosition;
		this.destPosition = destPosition;
		this.visibility = false;
	}

	public void draw(Canvas canvas) {
		Matrix matrix = new Matrix();
		float scale = (float)Vec.distance(destPosition, sourcePosition)/arrowBitmap.getHeight();
		matrix.setScale(1, scale);
		float scaledHeight = scale*arrowBitmap.getHeight();
		matrix.postRotate((float)Math.toDegrees(Vec.rotation(sourcePosition,destPosition))+90,arrowBitmap.getWidth()/2,scaledHeight);
		matrix.postTranslate(sourcePosition.x()-arrowBitmap.getWidth()/2, sourcePosition.y()-scaledHeight);
		canvas.drawBitmap(arrowBitmap, matrix, null);
	}
}
