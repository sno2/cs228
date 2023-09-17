package edu.iastate.cs228.hw1;

/**
 * A reseller town cell.
 * 
 * @author Carter Snook
 */
public class Reseller extends TownCell {
    public Reseller(Town p, int r, int c) {
        super(p, r, c);
    }

    /** {@link TownCell.who} */
    @Override
    public State who() {
        return State.RESELLER;
    }

    /** {@link TownCell.nextInternal} */
    protected TownCell nextInternal(Town tNew) {
        if (
        // If there are 3 or fewer casual users in the neighborhood, then
        // Reseller finds it unprofitable to maintain the business and leaves,
        // making the cell Empty.
        nCensus[CASUAL] <= 3 ||

        // Also, if there are 3 or more empty cells in the neighborhood, then
        // the Reseller leaves, making the cell Empty. Resellers do not like
        // unpopulated regions.
                nCensus[EMPTY] >= 3) {
            return new Empty(tNew, row, col);
        }

        return new Reseller(tNew, row, col);
    }
}
