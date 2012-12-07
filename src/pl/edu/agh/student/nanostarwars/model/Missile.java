package pl.edu.agh.student.nanostarwars.model;


public class Missile {

	/**
	 * Who's fired the missile
	 */
	private Player owner;
	
	/**
	 * How big it is
	 */
	private int points;
	
	/**
	 * What is it fired at
	 */
	private Star target;
	
	public Player getOwner() {
		return owner;
	}

	public Missile(Player owner, int points, Star target) {
		this.owner = owner;
		this.points = points;
		this.target = target;
	}

}
