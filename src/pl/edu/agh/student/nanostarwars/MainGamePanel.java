package pl.edu.agh.student.nanostarwars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.student.nanostarwars.model.Player;
import pl.edu.agh.student.nanostarwars.model.Star;
import pl.edu.agh.student.nanostarwars.model.Vec;
import android.content.Context;
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

	public MainGamePanel(Context context) {
		super(context);
		thread = new MainThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);

		players = new ArrayList<Player>();
		stars = new ArrayList<Star>();
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
			Vec position = new Vec(randomize.nextInt(getWidth()-2*size)+size,
					randomize.nextInt(getHeight()-2*size)+size);
			boolean safeDistance = true;
			for (Star otherStar : stars) {
				if (Vec.distance(position, otherStar.getPosition()) < size + otherStar.getSize() + 10) {
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
		for (Star star : stars) {
			star.draw(canvas);
		}
	}

	// events when touching the screen

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
			for (Star star : stars) {
				// check all the bounds of the ball
				if (Vec.distance(star.getPosition(), new Vec(X,Y)) < star.getSize()+50) {
					this.selectedStar = star;
					break;
				}
				double dist= Vec.distance(star.getPosition(), new Vec(X,Y));
				double dist2= star.getSize()+50;
			}
			break;
		case MotionEvent.ACTION_MOVE: // touch drag with the ball

			// move the balls the same as the finger

			this.selectedStar.getPosition().setX(X);

			this.selectedStar.getPosition().setY(Y);

			break;

		case MotionEvent.ACTION_UP:

			this.selectedStar.getPosition().setX(X);

			this.selectedStar.getPosition().setY(Y);
			// touch drop - just do things here after dropping

			break;

		}

		// redraw the canvas

		invalidate();

		return true;
	}
	
}
