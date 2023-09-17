package edu.iastate.cs228.hw1;

import java.util.Scanner;

/**
 * The ISPBusiness class performs simulation over a grid plain with cells
 * occupied by different TownCell types.
 * 
 * @author Carter Snook
 */
public class ISPBusiness {
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld The old Town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				TownCell cell = tOld.grid[i][j];

				// Update the census for the cell.
				cell.census(TownCell.nCensus);

				// Set each cell to the computed next value.
				tNew.grid[i][j] = cell.next(tNew);
			}
		}

		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 */
	public static int getProfit(Town town) {
		int profit = 0;

		for (TownCell[] row : town.grid) {
			for (TownCell cell : row) {
				if (cell.who() == State.CASUAL) {
					profit += 1;
				}
			}
		}

		return profit;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid
	 * via an input file (option: 1) or wants to generate it randomly (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and
	 * if user choice is to populate it randomly, then populate the grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle).
	 * Print the final profit in terms of %. You should print the profit percentage
	 * with two digits after the decimal point: Example if profit is 35.5600004,
	 * your output
	 * should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions
	 * in it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		try {
			Town town;

			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			int populationMode = scanner.nextInt();

			if (populationMode == 1) {
				// Option 1: File population mode
				System.out.println("Please enter file path:");
				String filePath = scanner.next();
				town = new Town(filePath);
			} else if (populationMode == 2) {
				// Option 2: Random population mode
				System.out.println("Provide rows, cols and seed integer separated by spaces: ");
				int rows = scanner.nextInt();
				int cols = scanner.nextInt();
				int seed = scanner.nextInt();

				town = new Town(rows, cols);
				town.randomInit(seed);
			} else {
				throw new IllegalStateException("invalid population mode");
			}

			int profit = getProfit(town);

			// Go through 11 months. Note that the initial state is counted as
			// the first month.
			for (int i = 0; i < 11; i++) {
				town = updatePlain(town);
				int newProfit = getProfit(town);
				profit += newProfit;
			}

			double maximumProfit = 12 * town.getLength() * town.getWidth();
			double profitUtilization = (double) profit / maximumProfit;

			System.out.printf("%.2f%%\n", profitUtilization * 100);
		} catch (Exception err) {
			// We try to give as much information to the user as possible as to
			// the cause of the issue.
			System.err.println("error: " + (err.getMessage() != null ? err.getMessage() : err.toString()));
			scanner.close();
			System.exit(1);
		}

		scanner.close();
	}
}
