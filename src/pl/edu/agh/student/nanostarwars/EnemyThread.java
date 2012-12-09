package pl.edu.agh.student.nanostarwars;

import java.util.List;
import java.util.Random;

import pl.edu.agh.student.nanostarwars.model.Missile;
import pl.edu.agh.student.nanostarwars.model.Player;
import pl.edu.agh.student.nanostarwars.model.Star;

public class EnemyThread extends Thread {
	
	private List<Player> players = null;
	private List<Star> stars = null;
	private List<Missile> missiles = null;
	
	private boolean running = false;
	private Random rand = null;

	public EnemyThread(List<Player> players, List<Star> stars, List<Missile> missiles) {
		super();
		this.players = players;
		this.stars = stars;
		this.missiles = missiles;
		this.rand = new Random();
	}
	
	public void run() {
		
		while(running) {
			
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(players) {
				Player agressor = players.get(1+rand.nextInt(players.size()-1));
			} 
			
			
		}
		
	}
	
	public void setRunning(boolean running) { this.running = running;}
}
