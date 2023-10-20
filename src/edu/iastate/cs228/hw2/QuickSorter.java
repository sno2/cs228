package edu.iastate.cs228.hw2;

/**
 * This class implements the quicksort sorting algorithm.
 * 
 * @author Carter Snook
 */
public class QuickSorter extends AbstractSorter {
	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public QuickSorter(Point[] pts) {
		super(pts);
		algorithm = "quicksort";
	}

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.
	 */
	@Override
	public void sort() {
		quickSortRec(0, points.length - 1);
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param start The start index.
	 * @param end   The end index.
	 */
	private void quickSortRec(int start, int end) {
		if (start >= end) {
			return;
		}

		int determinant = partition(start, end);
		quickSortRec(start, determinant);
		quickSortRec(determinant + 1, end);
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param start The start index.
	 * @param end   The end index.
	 * @return The determinant index.
	 */
	private int partition(int start, int end) {
		// Pick middle element as pivot.
		int midpoint = start + (end - start) / 2;
		Point pivot = points[midpoint];

		while (true) {
			while (pointComparator.compare(points[start], pivot) < 0) {
				start += 1;
			}

			while (pointComparator.compare(pivot, points[end]) < 0) {
				end -= 1;
			}

			// If zero or one elements remain, then all numbers are partitioned.
			if (start >= end) {
				break;
			}

			// Swap numbers[start] and numbers[end]
			swap(start, end);

			// Update start and end
			start += 1;
			end -= 1;
		}
		return end;
	}
}
