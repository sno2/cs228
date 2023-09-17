package edu.iastate.cs228.hw1;

/**
 * An outage town cell.
 * 
 * @author Carter Snook
 */
public class Outage extends TownCell {
    public Outage(Town p, int r, int c) {
        super(p, r, c);
    }

    /** {@link TownCell.who} */
    @Override
    public State who() {
        return State.OUTAGE;
    }

    /** {@link TownCell.nextInternal} */
    protected TownCell nextInternal(Town tNew) {
        // An Outage cell becomes an Empty cell, meaning internet access is
        // restored on the billing cycle after an outage.
        return new Empty(tNew, row, col);
    }
}
