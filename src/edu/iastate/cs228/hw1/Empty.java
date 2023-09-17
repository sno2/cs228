package edu.iastate.cs228.hw1;

/**
 * An empty town cell.
 * 
 * @author Carter Snook
 */
public class Empty extends TownCell {
    public Empty(Town p, int r, int c) {
        super(p, r, c);
    }

    /** {@link TownCell.who} */
    @Override
    public State who() {
        return State.EMPTY;
    }

    /** {@link TownCell.nextInternal} */
    protected TownCell nextInternal(Town tNew) {
        // If the cell was empty, then a Casual user takes it and it becomes a C.
        return new Casual(tNew, row, col);
    }
}
