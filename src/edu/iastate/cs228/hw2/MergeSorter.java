package edu.iastate.cs228.hw2;

/**
 * This class implements the mergesort algorithm.
 * 
 * @author Carter Snook
 */
public class MergeSorter extends AbstractSorter {
	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "mergesort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 */
	@Override
	public void sort() {
		mergeSortRec(points, 0, points.length - 1);
	}

	/**
	 * The recursive mergesort implementation. This avoids copies by slicing
	 * into the existing array by passing around start and end indices.
	 * 
	 * @param pts   The point array.
	 * @param start The inclusive start index.
	 * @param end   The exclusive end index.
	 */
	private void mergeSortRec(Point[] pts, int start, int end) {
		// Exit if invalid range
		if (start >= end) {
			return;
		}

		// Find the midpoint in the partition
		int mid = start + (end - start) / 2;

		// Recursively sort left and right partitions
		mergeSortRec(pts, start, mid);
		mergeSortRec(pts, mid + 1, end);

		// Merge left and right partition in sorted order
		merge(pts, start, mid, end);
	}

	/** Implements the Merge() algorithm for the MergeSort(). */
	private void merge(Point[] pts, int leftFirst, int leftLast, int rightLast) {
		// Size of merged partition
		int mergedSize = rightLast - leftFirst + 1;

		// Position to insert merged number
		int mergePos = 0;

		// Position of elements in left partition
		int leftPos = leftFirst;

		// Position of elements in right partition
		int rightPos = leftLast + 1;

		// Dynamically allocates temporary array
		Point[] mergedPoints = new Point[mergedSize];

		while (leftPos <= leftLast && rightPos <= rightLast) {
			if (pointComparator.compare(points[leftPos], points[rightPos]) < 0) {
				mergedPoints[mergePos] = pts[leftPos];
				leftPos++;
			} else {
				mergedPoints[mergePos] = pts[rightPos];
				rightPos++;
			}
			mergePos++;
		}

		// If left partition not empty, add remaining elements
		while (leftPos <= leftLast) {
			mergedPoints[mergePos] = points[leftPos];
			leftPos++;
			mergePos++;
		}

		// If right partition not empty, add remaining elements
		while (rightPos <= rightLast) {
			mergedPoints[mergePos] = points[rightPos];
			rightPos++;
			mergePos++;
		}

		// Copy mergedNumbers back to numbers
		for (mergePos = 0; mergePos < mergedSize; mergePos++) {
			points[leftFirst + mergePos] = mergedPoints[mergePos];
		}
	}
}
