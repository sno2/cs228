package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class TownTest {
    private static State C = State.CASUAL;
    private static State E = State.EMPTY;
    private static State O = State.OUTAGE;
    private static State R = State.RESELLER;
    private static State S = State.STREAMER;

    Town createTownFromStates(State[][] states) {
        Town town = new Town(states.length, states[0].length);

        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                switch (states[i][j]) {
                    case CASUAL:
                        town.grid[i][j] = new Casual(town, i, j);
                        break;
                    case EMPTY:
                        town.grid[i][j] = new Empty(town, i, j);
                        break;
                    case OUTAGE:
                        town.grid[i][j] = new Outage(town, i, j);
                        break;
                    case RESELLER:
                        town.grid[i][j] = new Reseller(town, i, j);
                        break;
                    case STREAMER:
                        town.grid[i][j] = new Streamer(town, i, j);
                        break;
                }
            }
        }

        return town;
    }

    void assertTownGridEquals(State[][] expectedGrid, Town town) {
        ArrayList<String> errors = new ArrayList<String>();

        for (int i = 0; i < expectedGrid.length; i++) {
            for (int j = 0; j < expectedGrid[i].length; j++) {
                if (town.grid[i][j].who() != expectedGrid[i][j]) {
                    errors.add(
                            "Expected " + expectedGrid[i][j].name() + ", got " + town.grid[i][j].who().name()
                                    + " at (" + i + ", " + j + ")");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new Error(errors.toString());
        }
    }

    @Test
    void bug0() throws FileNotFoundException {
        State[][] startGrid = {
                { R, C, O, E },
                { R, S, C, C },
                { R, S, C, E },
                { R, C, R, E },
        };

        State[][] expectedGrid = {
                { E, R, E, R },
                { E, R, S, C },
                { E, R, O, R },
                { E, R, E, R },
        };

        Town town = createTownFromStates(startGrid);

        town = ISPBusiness.updatePlain(town);

        assertTownGridEquals(expectedGrid, town);
    }

    @Test
    void bug1() throws FileNotFoundException {
        State[][] startGrid = {
                { R, C, C, C },
                { E, C, C, C },
                { E, C, S, C },
                { R, C, R, R },
        };

        State[][] expectedGrid = {
                { E, R, R, R },
                { R, O, R, R },
                { R, O, R, R },
                { E, R, E, E },
        };

        Town town = createTownFromStates(startGrid);
        town = ISPBusiness.updatePlain(town);

        assertTownGridEquals(expectedGrid, town);
    }

    @Test
    void checkTownRandomInit() {
        State[][] expectedGrid = {
                { O, R, O, R },
                { E, E, C, O },
                { E, S, O, S },
                { E, O, R, R },
        };

        Town town = new Town(4, 4);
        town.randomInit(10);

        assertTownGridEquals(expectedGrid, town);
    }

    @Test
    void checkTownLength() {
        Town town = new Town(4, 8);
        assertEquals(town.getLength(), 4);
    }

    @Test
    void checkTownWidth() {
        Town town = new Town(4, 8);
        assertEquals(town.getWidth(), 8);
    }

    @Test
    void checkTownToString() {
        Town town = new Town(4, 4);

        town.randomInit(10);
        String expected = "O R O R\nE E C O\nE S O S\nE O R R";
        assertEquals(expected, town.toString());

        town.randomInit(5);
        expected = "C C S S\nE R S E\nC E E O\nC R O R";
        assertEquals(expected, town.toString());

        town = new Town(6, 4);
        town.randomInit(8);
        expected = "S E R E\nC O E E\nO R S R\nC C O O\nC S O R\nO E O R";
        assertEquals(expected, town.toString());
    }

    @Test
    void checkTownFromFile() throws FileNotFoundException {
        State[][] expectedGrid = {
                { O, R, O, R },
                { E, E, C, O },
                { E, S, O, S },
                { E, O, R, R },
        };

        // NOTE: Make sure that you compile and run the tests in the folder with
        // the "bug-0.txt" file in the root.
        Town town = new Town("ISP4x4.txt");

        assertTownGridEquals(expectedGrid, town);
    }
}