package pl.edu.agh.student.nanostarwars;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.student.nanostarwars.model.Missile;
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
	private EnemyThread enemyThread;
	private Random randomize = null;
	public Status gameStatus;
	
	public enum Status {RUNNING, VICTORY, DEFEAT};

	private int speed = 10;
	/**
	 * Important assumption: 
	 * players.get(0) is the HUMAN PLAYER
	 */
	private List<Player> players = null;
	private List<Star> stars = null;
	private List<Missile> missiles = null;
	Star selectedStar;
	Pointer userPointer;

	public MainGamePanel(Context context) {
		super(context);
		thread = new MainThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);
		
		this.gameStatus = Status.RUNNING;
		this.randomize = new Random();
		this.players = new ArrayList<Player>();
		this.stars = new ArrayList<Star>();
		this.missiles = new ArrayList<Missile>();
		this.userPointer = new Pointer(BitmapFactory.decodeResource(getResources(), R.drawable.arrow),null,null);
		

		enemyThread = new EnemyThread(players, stars, missiles);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		
		Player human = new Player(0, Color.RED);
		Player pc = new Player(1, Color.BLUE);
		
		synchronized(players) {
			players.add(human);
			players.add(pc);
		}
		
		generateMap(6,2);
		thread.setRunning(true);
		enemyThread.setRunning(true);
		thread.start();
		enemyThread.start();
	}

	public void generateMap(int neutralCount, int playersCount) {
		while (stars.size() < neutralCount) {
			int size = randomize.nextInt(20) + 10 + randomize.nextInt(20);
			Vec position = new Vec(randomize.nextInt(getWidth() - 2 * size)
					+ size, randomize.nextInt(getHeight() - 2 * size) + size);
			
			if (safeDistance(position, size)) {
				stars.add(new Star(null, position, null, size));
			}
		}
		
		for(int i=0; i<playersCount; i++) {
			int size = 30;
			Vec position;
			do{
				position = new Vec(randomize.nextInt(getWidth() - 2 * size)
						+ size, randomize.nextInt(getHeight() - 2 * size) + size);
			}while(!safeDistance(position, size));
			synchronized(players) {
				stars.add(new Star(null,position, players.get(i), size));	
			}
		}
	}

	private boolean safeDistance(Vec position, int size) {
		boolean safeDistance = true;
		synchronized(stars) {
			for (Star otherStar : stars) {
				if (Vec.distance(position, otherStar.getPosition()) < size
						+ otherStar.getSize() + 10) {
					safeDistance = false;
					break;
				}
			}
		}
		return safeDistance;
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
		synchronized(stars) {
			for (Star star : stars) {
				star.draw(canvas);
			}	
		}
		
		if (userPointer.isVisible()){
			userPointer.draw(canvas);
		}
		
		synchronized(missiles) {
			for (Missile m: missiles) {
				m.draw(canvas);
			}			
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
				if (Vec.distance(star.getPosition(), touchPosition) < star.getSize() + 50 && star.hasPlayer() && star.getPlayer().getNumber()==0) {
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
			if (selectedStar != null) {
				for (Star star : stars) {
					if (star != selectedStar && (Vec.distance(star.getPosition(), touchPosition) < star.getSize() + 50)) {
						synchronized(missiles) {
							missiles.add(selectedStar.sendMissile(star));
						}
						selectedStar = null;
						break;
					}
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
		int playerStars = 0;
		int enemyStars = 0;
		synchronized(stars) {
			for(Star star : stars){
				star.update();
				if(star.hasPlayer()){
					if(star.getPlayer().getNumber() == 0){
						playerStars++;
					}else{
						enemyStars++;
					}
				}
			}
		}
		if(enemyStars == 0){
			gameStatus = Status.VICTORY;
		}
		else if(playerStars == 0){
			gameStatus = Status.DEFEAT;
		}

		List<Missile> blownUp = new LinkedList<Missile>();
		synchronized(missiles) {		
			for(Missile m: missiles) {
				m.update();
			}
			for(Missile m: missiles) {
				synchronized(stars) {
					m.update();	
				}
				if(m.isHit())
					blownUp.add(m);
			}
		}
		missiles.removeAll(blownUp);
	}

}
