package pl.edu.agh.student.nanostarwars.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

public class Star extends GameElement {
	
	private int value;
	
	private List<Missle> missles = null;
	
	public Star(Bitmap bitmap, Vec position, int team) {
		super(bitmap, position, team);
		
		missles = new LinkedList<Missle>();
		value =0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		//foreach po liście missles w dobrą stronę i dodawanie/odejmowanie w zależności od tego jakie pociski trafiają
		
	}
	
	
	public void consumeMissle(Missle m) {
		missles.add(m);
	}
}
