package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * A grid for implementations of TownCell.
 * 
 * @author Carter Snook
 */
public class Town {
	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed.
	 * This constructor does not populate each cell of the grid (but should assign a
	 * 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in
	 * this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		// NOTE: Unlike what the JavaDoc above implies, file is not a resource:
		// it simply wraps a string file name.
		File file = new File(inputFileName);
		Scanner scanner = new Scanner(file);

		length = scanner.nextInt();
		width = scanner.nextInt();

		grid = new TownCell[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				String c = scanner.next();

				switch (c.charAt(0)) {
					case 'C':
						grid[i][j] = new Casual(this, i, j);
						break;
					case 'S':
						grid[i][j] = new Streamer(this, i, j);
						break;
					case 'R':
						grid[i][j] = new Reseller(this, i, j);
						break;
					case 'O':
						grid[i][j] = new Outage(this, i, j);
						break;
					case 'E':
						grid[i][j] = new Empty(this, i, j);
						break;
					default:
						throw new IllegalStateException("failed to parse file");
				}

			}
		}

		scanner.close();
	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int idx = rand.nextInt(TownCell.NUM_CELL_TYPE);
				TownCell cell;

				switch (idx) {
					case TownCell.RESELLER:
						cell = new Reseller(this, i, j);
						break;
					case TownCell.EMPTY:
						cell = new Empty(this, i, j);
						break;
					case TownCell.CASUAL:
						cell = new Casual(this, i, j);
						break;
					case TownCell.OUTAGE:
						cell = new Outage(this, i, j);
						break;
					case TownCell.STREAMER:
						cell = new Streamer(this, i, j);
						break;
					default:
						// The default case will never be reached because the
						// index must be one of the town cell types. This is
						// only required to make the compiler know that cell is
						// always assigned to.
						throw new IllegalStateException("unreachable");
				}

				grid[i][j] = cell;
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line
	 * between the rows.
	 */
	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < length; i++) {
			// We only want to separate the rows with newlines.
			if (i != 0) {
				s += '\n';
			}

			for (int j = 0; j < width; j++) {
				TownCell cell = grid[i][j];

				// Add a space for separator but exclude the first one because a
				// space is not necessary after a newline.
				if (j != 0) {
					s += ' ';
				}

				switch (cell.who()) {
					case CASUAL:
						s += 'C';
						break;
					case EMPTY:
						s += 'E';
						break;
					case OUTAGE:
						s += 'O';
						break;
					case RESELLER:
						s += 'R';
						break;
					case STREAMER:
						s += 'S';
						break;
				}

			}
		}

		return s;
	}
}
