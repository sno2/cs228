package edu.iastate.cs228.hw2;

/**
 * This class implements insertion sort.
 * 
 * @author Carter Snook
 */
public class InsertionSorter extends AbstractSorter {
	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = "insertion sort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		for (int i = 1; i < points.length; i++) {
			for (int j = i; j > 0 && pointComparator.compare(points[j], points[j - 1]) < 0; j--) {
				// Swap points[j] with points[j - 1]
				swap(j, j - 1);
			}
		}
	}
}
