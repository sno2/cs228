package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class ResellerTest {
    TownCell cell;

    @BeforeEach
    void setup() {
        for (int i = 0; i < TownCell.nCensus.length; i++) {
            TownCell.nCensus[i] = 0;
        }

        // Prevent "Additional rules" collision.
        TownCell.nCensus[TownCell.EMPTY] = 2;

        cell = new Reseller(null, 0, 0);
    }

    @Test
    void behavior3A() {
        // If there are 3 or fewer casual users in the neighborhood, then
        // Reseller finds it unprofitable to maintain the business and leaves,
        // making the cell Empty.
        TownCell next = cell.next(null);
        assertEquals(State.EMPTY, next.who());
    }

    @Test
    void behavior3B() {
        // Also, if there are 3 or more empty cells in the neighborhood, then
        // the Reseller leaves, making the cell Empty. Resellers do not like
        // unpopulated regions.
        TownCell.nCensus[TownCell.EMPTY] = 3;
        TownCell next = cell.next(null);
        assertEquals(State.EMPTY, next.who());
    }
}
