package edu.iastate.cs228.hw2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y
 * coordinates are respectively the medians of the x and y coordinates of the
 * original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 * @author Carter Snook
 */
public class PointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy
	 * the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("The points must not be null or empty.");
		}

		// Copy the points.
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i]);
		}

		sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		File file = new File(inputFileName);
		Scanner scanner = new Scanner(file);

		ArrayList<Point> points = new ArrayList<Point>();

		while (scanner.hasNextInt()) {
			// NOTE: scanner.nextInt() will already throw the
			// InputMismatchException for us
			points.add(new Point(scanner.nextInt(), scanner.nextInt()));
		}

		scanner.close();

		this.points = points.toArray(new Point[points.size()]);
		sortingAlgorithm = algo;
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate.
	 * b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 * c) Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the two
		// rounds of sorting, have aSorter do the following:
		AbstractSorter aSorter;

		switch (sortingAlgorithm) {
			case InsertionSort:
				aSorter = new InsertionSorter(points);
				break;
			case SelectionSort:
				aSorter = new SelectionSorter(points);
				break;
			case MergeSort:
				aSorter = new MergeSorter(points);
				break;
			default:
				// NOTE: Doing this in default is required because otherwise we
				// will get compile errors because Java does not understand the
				// exhaustiveness of enums in switch statements.
				aSorter = new QuickSorter(points);
		}

		long totalTime = 0;

		// Run 2 rounds of the sorting algorithm.
		for (int i = 0; i < 2; i++) {
			// a) call setComparator() with an argument 0 or 1.
			aSorter.setComparator(i);

			long start = System.nanoTime();

			{
				// b) call sort().
				aSorter.sort();
			}

			totalTime += System.nanoTime() - start;
		}

		// c) use a new Point object to store the coordinates of the
		// medianCoordinatePoint
		// d) set the medianCoordinatePoint reference to the object with the correct
		// coordinates.
		medianCoordinatePoint = new Point(aSorter.getMedian());

		// e) sum up the times spent on the two sorting rounds and set the instance
		// variable scanTime.
		scanTime = totalTime;
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		// The output sorting algorithm names are equal to the enum names.
		String algo = sortingAlgorithm.toString();

		return algo + "\t" + points.length + "\t" + scanTime;
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space
	 * in between.
	 */
	@Override
	public String toString() {
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	/**
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile(String outputFileName) throws FileNotFoundException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
			writer.write(toString());
			writer.close();
		} catch (IOException e) {
			if (e.getClass() != FileNotFoundException.class) {
				System.err.printf("warning: exception class differs from FileNotFoundException (%s)\n",
						e.getClass().getName());
			}

			// We are only supposed to throw FileNotFoundException but
			// FileWriter throws exception with more information so we need to
			// normalize it as a FileNotFoundException.
			throw new FileNotFoundException(outputFileName);
		}
	}

}
