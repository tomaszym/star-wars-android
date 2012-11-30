package pl.edu.agh.student.nanostarwars.model;

public class Vec {

	private int x;
	private int y;

	public Vec(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int y() {
		return x;
	}

	public int x() {
		return y;
	}

	public boolean equals(Object thatObj) {
		if (this == thatObj)
			return true;
		if (!(this.getClass().equals(thatObj.getClass())))
			return false;
		Vec thatVec = (Vec) thatObj;
		
		return testEquals(thatVec);
	}
	
	protected boolean testEquals(Vec that) {
		return this.x() == that.x() && this.y() == that.y();
	}
}
