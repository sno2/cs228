package edu.iastate.cs228.hw1;

/**
 * @author Carter Snook
 */
public abstract class TownCell {
	protected Town plain;
	protected int row;
	protected int col;

	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;

	public static final int NUM_CELL_TYPE = 5;

	// Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}

	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 * 
	 * @param counts of all customers
	 */
	public void census(int[] nCensus) {
		// Zero the counts of all customers.
		for (int i = 0; i < nCensus.length; i++) {
			nCensus[i] = 0;
		}

		// Iterate the neighbors above.
		if (row != 0) {
			for (int i = Math.max(0, col - 1); i < Math.min(col + 2, plain.getWidth()); i++) {
				State id = plain.grid[row - 1][i].who();
				nCensus[id.ordinal()] += 1;
			}
		}

		// Check the left neighbor.
		if (col != 0) {
			State id = plain.grid[row][col - 1].who();
			nCensus[id.ordinal()] += 1;
		}

		// Check the right neighbor.
		if (col + 1 < plain.getWidth()) {
			State id = plain.grid[row][col + 1].who();
			// The ordinal values are equal to the indices.
			nCensus[id.ordinal()] += 1;
		}

		// Check the neighbors below.
		if (row + 1 < plain.getLength()) {
			for (int i = Math.max(0, col - 1); i < Math.min(col + 2, plain.getWidth()); i++) {
				State id = plain.grid[row + 1][i].who();
				nCensus[id.ordinal()] += 1;
			}
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * The internal method for getting the next cell type. This allows for less
	 * code duplication from checking rules 6a and 6b in every single next()
	 * implementation.
	 * 
	 * @implNote The returned TownCell must be attached to Town argument, not
	 *           the Town from the previous state.
	 * @param tNew The new Town.
	 * @return TownCell
	 */
	protected abstract TownCell nextInternal(Town tNew);

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew The town in the next cycle.
	 * @return TownCell
	 */
	public TownCell next(Town tNew) {
		State id = who();

		// Any cell that (1) is not a Reseller or Outage and (2) and has (Number
		// of Empty + Number of Outage neighbors less than or equal to 1)
		// converts to Reseller.
		// NOTE: For some reason, 6a has a higher precedence than any of the
		// other rules.
		if (id != State.RESELLER && id != State.OUTAGE && nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {
			return new Reseller(tNew, row, col);
		}

		TownCell maybeNext = nextInternal(tNew);

		// If none of the above rules apply, any cell with 5 or more casual
		// neighbors becomes a Streamer.
		if (who() == maybeNext.who() && nCensus[CASUAL] >= 5) {
			// NOTE: If the next cell has the same identity as the current, then
			// none of the above rules applied.
			return new Streamer(tNew, row, col);
		}

		return maybeNext;
	}
}
