package pl.edu.agh.student.nanostarwars;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.util.Log;

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
		Log.i("EnemyThread", "begins the run!");
		while(running) {
			
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(players) {
				Log.d("EnemyThread", "players locked");
				synchronized(stars) {
					Log.d("EnemyThread", "stars locked");
					
					Player agressor = players.get(1+rand.nextInt(players.size()-1));
					Iterator<Star> agressiveStars = getStars(agressor);
					Log.d("EnemyThread", "agressors picked");
					
					Star victimStar = getWeakestStar(agressor);
					
					Log.d("EnemyThread", "victim picked");
					if(victimStar == null){
						Log.d("EnemyThread", "no weakest star");
					}
					else{
						while(agressiveStars.hasNext()) {
							Log.d("EnemyThread", "FIRE!!");
							Star agressiveStar = agressiveStars.next();
							missiles.add(agressiveStar.sendMissile(victimStar));
							Log.d("EnemyThread", agressiveStar.getPoints() + " > " + victimStar.getPoints());
						}
					}
				}
			} 
			
			
		}
		
	}

	private Iterator<Star> getStars(Player p) {
		List<Star> list = new LinkedList<Star>();
		synchronized(players) {
			synchronized(stars){
				for(Star s: stars) {
					if(p.equals(s.getPlayer())) {
						list.add(s);
					}
				}				
			}
		}
		return list.iterator();
	}
	
	private Iterator<Star> getTargets(Player p) {
		List<Star> list = new LinkedList<Star>();
		synchronized(players) {
			synchronized(stars){
				for(Star s: stars) {
					if(!p.equals(s.getPlayer())) {
						list.add(s);
					}
				}				
			}
		}
		return list.iterator();
	}
	
	private Star getWeakestStar(Player p) {
		Iterator<Star> targetIterator = getTargets(p);
		Iterator<Star> starsIterator = getStars(p);
		Star weakest = null;
		Star tmp = null;
		int availablePoints = 0;
		if(targetIterator.hasNext())
			weakest = targetIterator.next();
		else
			return null;
		synchronized(players) {
			synchronized(stars) {
				while(starsIterator.hasNext()) {
					tmp = starsIterator.next();
					availablePoints += tmp.getPoints()/2;
				}
				while(targetIterator.hasNext()) {
					tmp = targetIterator.next();
					if(tmp.getPoints() < weakest.getPoints())
						weakest = tmp;
				}
			}
		}
		if(availablePoints > weakest.getPoints()){
			return weakest;
		}else{
			return null;
		}
	}
	
	
	public void setRunning(boolean running) { this.running = running;}
}
