package pl.edu.agh.student.nanostarwars;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning(boolean running) {

		this.running = running;
	}

	public void run() {
		Canvas canvas;
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
