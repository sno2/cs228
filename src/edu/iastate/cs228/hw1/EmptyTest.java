package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class EmptyTest {
    TownCell cell;

    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }

        // Prevent "Additional rules" collision.
        TownCell.nCensus[TownCell.EMPTY] = 2;

        cell = new Empty(null, 0, 0);
    }

    @Test
    void behavior5() {
        // If the cell was empty, then a Casual user takes it and it becomes a C.
        TownCell next = cell.next(null);
        assertEquals(State.CASUAL, next.who());
    }
}
