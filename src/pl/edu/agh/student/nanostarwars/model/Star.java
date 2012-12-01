package pl.edu.agh.student.nanostarwars.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star extends GameElement {
	
	private int size;
	private Vec position;
	private Player player;
	
	private List<Missle> missles = null;
	
	public Star(Bitmap bitmap, Vec position, Player player, int size) {
		super(bitmap, position, player);
		
		missles = new LinkedList<Missle>();
		this.position = position;
		this.size = size;
	}
	

	public int getSize() {
		return this.size;
	}

	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		
		//foreach po liście missles w dobrą stronę i dodawanie/odejmowanie w zależności od tego jakie pociski trafiają
		
	}
	
	
	public void consumeMissle(Missle m) {
		missles.add(m);
	}
	
	public void draw(Canvas canvas) {
		if(player == null){
			Paint bgPaint = new Paint();
			bgPaint.setColor(Color.GRAY);
			canvas.drawCircle(this.position.x(), this.position.y(), this.size, bgPaint);
			Paint txtPaint = new Paint();
			txtPaint.setColor(Color.WHITE);
			canvas.drawText(Integer.toString(this.position.x())+'/'+Integer.toString(this.position.y()), this.position.x()-size*2, this.position.y(), txtPaint);
		}
		else{
			canvas.drawCircle(this.position.x(), this.position.y(), this.size, player.getColor());
		}
	}
}
