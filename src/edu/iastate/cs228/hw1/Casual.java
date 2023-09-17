package edu.iastate.cs228.hw1;

/**
 * A casual town cell.
 * 
 * @author Carter Snook
 */
public class Casual extends TownCell {
    public Casual(Town p, int r, int c) {
        super(p, r, c);
    }

    /** {@link TownCell.who} */
    @Override
    public State who() {
        return State.CASUAL;
    }

    /** {@link TownCell.nextInternal} */
    protected TownCell nextInternal(Town tNew) {
        // If there is any reseller in its neighborhood, then the reseller
        // causes outage in the casual user cell. Thus, the state of the cell
        // changes from C (Casual) to O (Outage).
        if (nCensus[RESELLER] > 0) {
            return new Outage(tNew, row, col);
        }

        // Otherwise, if there is any neighbor who is a streamer, then the
        // casual user also becomes a streamer, in the hopes of making it big on
        // the internet.
        if (nCensus[STREAMER] > 0) {
            return new Streamer(tNew, row, col);
        }

        // Otherwise, return a clone of the current cell attached to the new
        // town.
        return new Casual(tNew, row, col);
    }
}
