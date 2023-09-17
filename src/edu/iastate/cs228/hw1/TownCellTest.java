package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class TownCellTest {
    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }
    }

    @Test
    void checkWho() {
        TownCell reseller = new Reseller(null, 0, 0);
        assertEquals(reseller.who(), State.RESELLER);

        TownCell empty = new Empty(null, 0, 0);
        assertEquals(empty.who(), State.EMPTY);

        TownCell casual = new Casual(null, 0, 0);
        assertEquals(casual.who(), State.CASUAL);

        TownCell outage = new Outage(null, 0, 0);
        assertEquals(outage.who(), State.OUTAGE);

        TownCell streamer = new Streamer(null, 0, 0);
        assertEquals(streamer.who(), State.STREAMER);
    }

    @Test
    void behavior6A() {
        // Any cell that (1) is not a Reseller or Outage and (2) and has (Number
        // of Empty + Number of Outage neighbors less than or equal to 1)
        // converts to Reseller.
        TownCell cell = new Streamer(null, 0, 0);
        TownCell next = cell.next(null);
        assertEquals(State.RESELLER, next.who());
    }

    @Test
    void behavior6B() {
        // If none of the above rules apply, any cell with 5 or more casual
        // neighbors becomes a Streamer.
        TownCell cell = new Casual(null, 0, 0);
        TownCell.nCensus[TownCell.CASUAL] = 5;
        TownCell.nCensus[TownCell.EMPTY] = 2;
        TownCell next = cell.next(null);
        assertEquals(State.STREAMER, next.who());
    }

    @Test
    void behavior7() {
        // If none of the rules apply, then the cell state remains unchanged for
        // the next iteration.
        TownCell cell = new Casual(null, 0, 0);
        TownCell.nCensus[TownCell.EMPTY] = 2;
        TownCell next = cell.next(null);
        assertEquals(State.CASUAL, next.who());
    }
}
