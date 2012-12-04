package pl.edu.agh.student.nanostarwars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.student.nanostarwars.model.Player;
import pl.edu.agh.student.nanostarwars.model.Pointer;
import pl.edu.agh.student.nanostarwars.model.Star;
import pl.edu.agh.student.nanostarwars.model.Vec;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainThread thread;

	private List<Player> players = null;
	private List<Star> stars = null;
	Star selectedStar;
	Pointer userPointer;

	public MainGamePanel(Context context) {
		super(context);
		thread = new MainThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);

		this.players = new ArrayList<Player>();
		this.stars = new ArrayList<Star>();
		this.userPointer = new Pointer(BitmapFactory.decodeResource(getResources(), R.drawable.arrow),null,null);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Player player = new Player(0, Color.RED);
		players.add(player);
		generateMap(6);
		thread.setRunning(true);
		thread.start();
	}

	public void generateMap(int starsCount) {
		while (stars.size() < starsCount) {
			Random randomize = new Random();
			int size = randomize.nextInt(30) + 20;
			Vec position = new Vec(randomize.nextInt(getWidth() - 2 * size)
					+ size, randomize.nextInt(getHeight() - 2 * size) + size);
			boolean safeDistance = true;
			for (Star otherStar : stars) {
				if (Vec.distance(position, otherStar.getPosition()) < size
						+ otherStar.getSize() + 10) {
					safeDistance = false;
				}
			}
			if (safeDistance) {
				stars.add(new Star(null, position, null, size));
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	protected void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		for (Star star : stars) {
			star.draw(canvas);
		}
		if (userPointer.isVisible()){
			userPointer.draw(canvas);
		}
	}

	// events when touching the screen

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventAction = event.getAction();
		Vec touchPosition = new Vec((int) event.getX(), (int) event.getY());
		switch (eventAction) {
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
			for (Star star : stars) {
				if (Vec.distance(star.getPosition(), touchPosition) < star
						.getSize() + 50) {
					selectedStar = star;
					userPointer.setSourcePosition(star.getPosition());
					break;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (selectedStar != null) {
				userPointer.setDestPosition(touchPosition);
				userPointer.show();
			}

			break;

		case MotionEvent.ACTION_UP: // touch drop - just do things here after dropping
			for (Star star : stars) {
				if (star != selectedStar && (Vec.distance(star.getPosition(), touchPosition) < star.getSize() + 50)) {
					selectedStar.sendMissiles(star);
					selectedStar = null;
					break;
				}
			}
			selectedStar = null;
			userPointer.hide();
			break;

		}

		// redraw the canvas

		invalidate();

		return true;
	}
	
	public void update() {
		for(Star star : stars){
			star.update();
		}
	}

}
