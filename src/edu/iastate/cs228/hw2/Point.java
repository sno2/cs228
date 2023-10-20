package edu.iastate.cs228.hw2;

/**
 * A 2D point.
 * 
 * @author Carter Snook
 */
public class Point implements Comparable<Point> {
	private int x;
	private int y;

	public static boolean xORy; // compare x coordinates if xORy == true and y coordinates otherwise
								// To set its value, use Point.xORy = true or false.

	// Default constructor.
	public Point() {
		// x and y are implicitly 0.
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		x = p.getX();
		y = p.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		Point.xORy = xORy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return the comparison of either the x-values or y-values
	 */
	public int compareTo(Point q) {
		if (xORy) {
			return Integer.compare(y, q.y);
		}

		return Integer.compare(x, q.x);
	}

	/** Output a point in the standard form (x, y). */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
