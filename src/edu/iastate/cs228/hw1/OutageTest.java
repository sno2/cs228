package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class OutageTest {
    TownCell cell;

    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }

        // Prevent "Additional rules" collision.
        TownCell.nCensus[TownCell.EMPTY] = 2;

        cell = new Outage(null, 0, 0);
    }

    @Test
    void behavior4() {
        // An Outage cell becomes an Empty cell, meaning internet access is
        // restored on the billing cycle after an outage.
        TownCell next = cell.next(null);
        assertEquals(State.EMPTY, next.who());
    }
}
