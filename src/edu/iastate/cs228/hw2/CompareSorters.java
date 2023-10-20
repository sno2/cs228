package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

/**
 * This class executes four sorting algorithms: selection sort, insertion sort,
 * mergesort, and quicksort, over randomly generated integers as well integers
 * from a file input. It compares the execution times of these algorithms on the
 * same input.
 *
 * @author Carter Snook
 */
public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points
	 * with respect to their median coordinate point four times, each time using
	 * a different sorting algorithm.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		try {
			// We separate the main implementation from the main function to
			// make sure that we handle all of the errors without having
			// too large of indentation that is hard to read.
			mainImpl();
		} catch (FileNotFoundException e) {
			// Due to the inclusion in the method signature, we should just be
			// throwing these errors.
			throw e;
		} catch (InputMismatchException e) {
			// I don't think that we are supposed to be throwing this error
			// because it is not included in the @throws signature.
			System.err.println("error: invalid input");
			System.exit(1);
		} catch (Exception e) {
			String message = e.getMessage() != null ? e.getMessage() : e.toString();
			System.err.println("error: " + message);
			System.exit(1);
		}
	}

	/**
	 * The main implementation of the algorithm. This is separated to ensure we
	 * catch all proper errors.
	 */
	private static void mainImpl() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();

		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");

		// Conducts multiple rounds of comparison of four sorting algorithms. Within
		// each round, set up scanning as follows:
		for (int trialCount = 1;;) {
			System.out.print("Trial " + trialCount + ": ");

			// 1=random, 2=file, 3=exit
			int mode = scanner.nextInt();

			// a) If asked to scan random points, calls generateRandomPoints()
			// to initialize an array of random points.
			// NOTE: This is done later for cleaner code.

			// b) Reassigns to the array scanners[] (declared below) the
			// references to four new PointScanner objects, which are created
			// using four different values of the Algorithm type: SelectionSort,
			// InsertionSort, MergeSort and QuickSort. For each input of points,
			// do the following.

			// a) Initialize the array scanners[].
			PointScanner[] pointScanners = new PointScanner[4];

			// 1=random
			if (mode == 1) {
				System.out.print("Enter number of random points: ");
				int pointsCount = scanner.nextInt();
				Point[] randomPoints = generateRandomPoints(pointsCount, rand);

				// NOTE: Writing these out manually reads better than using a
				// loop over the Algorithm variants and casting to indices with
				// Enum.ordinal().

				pointScanners[0] = new PointScanner(randomPoints, Algorithm.SelectionSort);
				pointScanners[1] = new PointScanner(randomPoints, Algorithm.InsertionSort);
				pointScanners[2] = new PointScanner(randomPoints, Algorithm.MergeSort);
				pointScanners[3] = new PointScanner(randomPoints, Algorithm.QuickSort);
			}

			// 2=file
			else if (mode == 2) {
				System.out.print("File name: ");
				String fileName = scanner.next();

				pointScanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				pointScanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				pointScanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				pointScanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
			}

			// 3=exit
			else if (mode == 3) {
				// NOTE: We break instead of return so that we can still clean
				// up resources.
				break;
			}

			// unspecified
			else {
				System.out.println("\nInvalid mode. Try again.\n");
				continue;
			}

			// b) Iterate through the array scanners[], and have every scanner
			// call the scan() method in the PointScanner class.
			for (PointScanner pointScanner : pointScanners) {
				pointScanner.scan();

				// Debugging writing the median point to the file:
				// pointScanner.writeMCPToFile("foo.txt");
			}

			// c) After all four scans are done for the input, print out the statistics
			// table from section 2.

			System.out.println();
			System.out.println("algorithm\tsize\ttime (ns)");
			System.out.println("----------------------------------");
			for (PointScanner pointScanner : pointScanners) {
				System.out.println(pointScanner.stats());
			}
			System.out.println("----------------------------------\n");

			// NOTE: We manually increment the counter so that we are able to
			// maintain the count if we are given invalid input.
			trialCount += 1;
		}

		scanner.close();
	}

	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be
	 * generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException("The number of points to generate must be greater than 0.");
		}

		Point[] points = new Point[numPts];

		for (int i = 0; i < numPts; i++) {
			// Load the random point's data in the range of [-50, 50].
			points[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}

		return points;
	}
}
