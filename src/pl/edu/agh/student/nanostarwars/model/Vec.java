package pl.edu.agh.student.nanostarwars.model;

public class Vec {

	private int x;

	private int y;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Vec(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int y() {
		return y;
	}

	public int x() {
		return x;
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
	
	public static double distance(Vec a, Vec b){
		return Math.sqrt((a.x()-b.x())*(a.x()-b.x())+(a.y()-b.y())*(a.y()-b.y()));
	}

	public static double rotation(Vec sourcePosition, Vec destPosition) {
		int delta_x = destPosition.x() - sourcePosition.x();
		int delta_y = destPosition.y() - sourcePosition.y();
		double theta_radians = Math.atan2(delta_y, delta_x);
		return theta_radians;
	}
}
