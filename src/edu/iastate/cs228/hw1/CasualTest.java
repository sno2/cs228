package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class CasualTest {
    TownCell cell;

    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }

        // Prevent "Additional rules" collision.
        TownCell.nCensus[TownCell.EMPTY] = 2;

        cell = new Casual(null, 0, 0);
    }

    @Test
    void behavior1A() {
        // If there is any reseller in its neighborhood, then the reseller
        // causes outage in the casual user cell. Thus, the state of the cell
        // changes from C (Casual) to O (Outage).
        TownCell.nCensus[TownCell.RESELLER] = 1;
        TownCell next = cell.next(null);
        assertEquals(State.OUTAGE, next.who());
    }

    @Test
    void behavior1B() {
        // Otherwise, if there is any neighbor who is a streamer, then the
        // casual user also becomes a streamer, in the hopes of making it big on
        // the internet.
        TownCell.nCensus[TownCell.STREAMER] = 1;
        TownCell next = cell.next(null);
        assertEquals(State.STREAMER, next.who());
    }
}
