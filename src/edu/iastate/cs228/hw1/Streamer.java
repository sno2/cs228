package edu.iastate.cs228.hw1;

/**
 * A streamer town cell.
 * 
 * @author Carter Snook
 */
public class Streamer extends TownCell {
    public Streamer(Town p, int r, int c) {
        super(p, r, c);
    }

    /** {@link TownCell.who} */
    @Override
    public State who() {
        return State.STREAMER;
    }

    /** {@link TownCell.nextInternal} */
    protected TownCell nextInternal(Town tNew) {
        // If there is any reseller in the neighborhood, the reseller causes
        // outage for the streamer as well.
        if (nCensus[RESELLER] > 0) {
            return new Outage(tNew, row, col);
        }

        // Otherwise, if there is any Outage in the neighborhood, then the
        // streamer leaves and the cell becomes Empty.
        else if (nCensus[OUTAGE] > 0) {
            return new Empty(tNew, row, col);
        }

        return new Streamer(tNew, row, col);
    }
}
