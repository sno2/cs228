package edu.iastate.cs228.hw2;

/**
 * This class implements selection sort.
 * 
 * @author Carter Snook
 */
public class SelectionSorter extends AbstractSorter {
	/**
	 * Constructor takes an array of points. It invokes the superclass
	 * constructor, and also set the instance variables algorithm in the
	 * superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "selection sort";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		// Iterate through [0, pointsCount - 1)
		for (int i = 0; i < points.length - 1; i++) {
			// Set the smallest index to the first index.
			int smallestIndex = i;

			// Iterate through [i + 1, pointsCount)
			for (int j = i + 1; j < points.length; j++) {
				// points[j] < points[smallestIndex]
				if (pointComparator.compare(points[j], points[smallestIndex]) < 0) {
					smallestIndex = j;
				}
			}

			// Swap points[i] with points[smallestIndex]
			swap(i, smallestIndex);
		}
	}
}
