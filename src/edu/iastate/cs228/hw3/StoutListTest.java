package edu.iastate.cs228.hw3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import org.junit.Test;

/**
 * @author Carter Snook
 */
public class StoutListTest {
    @Test
    public void step1() {
        StoutList<Integer> list = new StoutList<Integer>();

        assertEquals("[]", list.toStringInternal());

        list.add(0);
        assertEquals("[(0, -, -, -)]", list.toStringInternal());

        list.add(1);
        assertEquals("[(0, 1, -, -)]", list.toStringInternal());

        list.add(2);
        assertEquals("[(0, 1, 2, -)]", list.toStringInternal());

        list.add(3);
        assertEquals("[(0, 1, 2, 3)]", list.toStringInternal());

        list.add(4);
        assertEquals("[(0, 1, 2, 3), (4, -, -, -)]", list.toStringInternal());

        for (int i = 5; i < 9; i++) {
            list.add(i);
        }

        assertEquals("[(0, 1, 2, 3), (4, 5, 6, 7), (8, -, -, -)]", list.toStringInternal());
    }

    @Test
    public void step2__emptyCase() {
        StoutList<Integer> list = new StoutList<Integer>();

        Iterator<Integer> iter = list.iterator();

        assertEquals(false, iter.hasNext());
        assertThrows(IllegalStateException.class, () -> iter.next());
        assertThrows(UnsupportedOperationException.class, () -> iter.remove());
    }

    @Test
    public void step2__nodeSizeMultipleCase() {
        int multiple = 10;

        StoutList<Integer> list = new StoutList<Integer>();

        for (int i = 0; i < 4 * multiple; i++) {
            list.add(i);
        }

        Iterator<Integer> iter = list.iterator();

        for (Integer i = 0; i < 4 * multiple; i++) {
            assertEquals(true, iter.hasNext());
            assertEquals(i, iter.next());
        }

        assertEquals(false, iter.hasNext());
        assertThrows(IllegalStateException.class, () -> iter.next());
        assertThrows(UnsupportedOperationException.class, () -> iter.remove());
    }

    @Test
    public void step2__normalCase() {
        int multiple = 10;
        int offset = new Random().nextInt(0, 4);
        int length = 4 * multiple + offset;

        StoutList<Integer> list = new StoutList<Integer>();

        for (int i = 0; i < length; i++) {
            list.add(i);
        }

        Iterator<Integer> iter = list.iterator();

        for (Integer i = 0; i < length; i++) {
            assertEquals(true, iter.hasNext());
            assertEquals(i, iter.next());
        }

        assertEquals(false, iter.hasNext());
        assertThrows(IllegalStateException.class, () -> iter.next());
        assertThrows(UnsupportedOperationException.class, () -> iter.remove());
    }

    @Test
    public void section5Example() {
        StoutList<Character> list = new StoutList<>();

        list.add('A');
        list.add('B');
        list.add('C');
        list.add('C');
        list.add('C');
        list.add('D');
        list.add('E');

        list.remove(2);
        list.remove(2);

        assertEquals("[(A, B, -, -), (C, D, E, -)]", list.toStringInternal());
        assertEquals(5, list.size());

        list.add('V');
        assertEquals("[(A, B, -, -), (C, D, E, V)]", list.toStringInternal());
        assertEquals(6, list.size());

        list.add('W');
        assertEquals("[(A, B, -, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal());
        assertEquals(7, list.size());

        list.add(2, 'X');
        assertEquals("[(A, B, X, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal());
        assertEquals(8, list.size());

        list.add(2, 'Y');
        assertEquals("[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]", list.toStringInternal());
        assertEquals(9, list.size());

        list.add(2, 'Z');
        assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal());
        assertEquals(10, list.size());

        list.remove(9);
        assertEquals("Figure 10 - after removing W", "[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]",
                list.toStringInternal());
        assertEquals(9, list.size());

        list.remove(3);
        assertEquals("Figure 11 - after removing Y (mini-merge)", "[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]",
                list.toStringInternal());
        assertEquals(8, list.size());

        list.remove(3);
        assertEquals("Figure 12 - after removing X (mini-merge)", "[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]",
                list.toStringInternal());
        assertEquals(7, list.size());

        list.remove(5);
        assertEquals("Figure 13 - after removing E (no merge with predecessor node)",
                "[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]",
                list.toStringInternal());
        assertEquals(6, list.size());

        list.remove(3);
        assertEquals("Figure 14 - after removing C (full merge with successor node)",
                "[(A, B, Z, -), (D, V, -, -)]",
                list.toStringInternal());
        assertEquals(5, list.size());
    }

    @Test
    public void listIteratorSet() {
        StoutList<Character> list = new StoutList<>();
        list.add('A');

        ListIterator<Character> iter = list.listIterator();

        assertEquals((Character) 'A', iter.next());

        iter.set('X');
        // assertEquals((Character) 'X', list.get(0));

        assertEquals((Character) 'X', iter.previous());

        iter.set('Y');
        assertEquals((Character) 'Y', list.get(0));
    }

}
