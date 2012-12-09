package pl.edu.agh.student.nanostarwars;

import pl.edu.agh.student.nanostarwars.MainGamePanel.Status;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Looper;
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
		Looper.prepare();
		Canvas canvas;
		while (gamePanel.gameStatus == Status.RUNNING) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gamePanel.update();
					this.gamePanel.render(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		if (gamePanel.gameStatus == Status.VICTORY) {
			victory();
		} else if (gamePanel.gameStatus == Status.DEFEAT) {
			defeat();
		}
		Looper.loop();

	}

	private void victory() {
		AlertDialog alertDialog = new AlertDialog.Builder(
				this.gamePanel.getContext()).create();
		alertDialog.setTitle("Wygrana...");
		alertDialog.setMessage("Zniszczyłeś wroga!");
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yay!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}

	private void defeat() {
		AlertDialog alertDialog = new AlertDialog.Builder(
				this.gamePanel.getContext()).create();
		alertDialog.setTitle("Porażka...");
		alertDialog.setMessage("Zostałeś pokonany.");
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yay!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
}
