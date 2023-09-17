package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class StreamerTest {
    TownCell cell;

    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }

        // Prevent "Additional rules" collision.
        TownCell.nCensus[TownCell.EMPTY] = 2;

        cell = new Streamer(null, 0, 0);
    }

    @Test
    void behavior2A() {
        // If there is any reseller in the neighborhood, the reseller causes
        // outage for the streamer as well.
        TownCell.nCensus[TownCell.RESELLER] = 1;
        TownCell next = cell.next(null);
        assertEquals(State.OUTAGE, next.who());
    }

    @Test
    void behavior2B() {
        // Otherwise, if there is any Outage in the neighborhood, then the
        // streamer leaves and the cell becomes Empty.
        TownCell.nCensus[TownCell.OUTAGE] = 1;
        TownCell next = cell.next(null);
        assertEquals(State.EMPTY, next.who());
    }
}
