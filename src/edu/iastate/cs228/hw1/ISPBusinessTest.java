package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * @author Carter Snook
 */
class ISPBusinessTest {
    @Test
    void checkUpdateTown() {
        // NOTE: Specific behaviors are checked in Town, TownCell subclasses,
        // and in the E2E test below.

        Town town = new Town(2, 2);

        for (int i = 0; i < town.getLength(); i++) {
            for (int j = 0; j < town.getWidth(); j++) {
                town.grid[i][j] = new Empty(town, i, j);
            }
        }

        town = ISPBusiness.updatePlain(town);

        for (int i = 0; i < town.getLength(); i++) {
            for (int j = 0; j < town.getWidth(); j++) {
                assertEquals(town.grid[i][j].who(), State.CASUAL);
            }
        }
    }

    @Test
    void checkGetProfit() {
        Town town = new Town(4, 4);

        for (int i = 0; i < town.getLength(); i++) {
            for (int j = 0; j < town.getWidth(); j++) {
                town.grid[i][j] = new Casual(town, i, j);
            }
        }

        assertEquals(ISPBusiness.getProfit(town), 4 * 4);

        town.grid[0][0] = new Empty(town, 0, 0);

        assertEquals(ISPBusiness.getProfit(town), 4 * 4 - 1);

        for (int i = 1; i < town.getWidth(); i++) {
            town.grid[0][i] = new Empty(town, 0, i);
        }

        assertEquals(ISPBusiness.getProfit(town), 4 * 4 - town.getWidth());
    }

    // E2E Test
    @Test
    void behavior() {
        System.setIn(new ByteArrayInputStream("2\n4 4 10\n".getBytes()));

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdout));

        String[] args = {};
        ISPBusiness.main(args);

        String output = stdout.toString();

        String startOutput = "How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed\nProvide rows, cols and seed integer separated by spaces: \n";

        assertEquals(
                startOutput + "27.60%\n",
                output.replaceAll("\r\n?", "\n"));
    }
}
