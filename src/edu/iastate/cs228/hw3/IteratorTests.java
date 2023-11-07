package edu.iastate.cs228.hw3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

/**
 * @author Unknown
 */
class IteratorTests {

    @Test
    void readIteratorTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                readIteratorTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void readIteratorTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }

        int i = 0;
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            assertEquals(dataArray[i], iter.next(), "Iterating through iterator() did not return correct values.");
            i++;
        }
    }

    @Test
    void readListIteratorTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                readListIteratorTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void readListIteratorTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }

        int i = 0;
        ListIterator<T> iter = list.listIterator();
        while (iter.hasNext()) {
            assertEquals(dataArray[i], iter.next(), "Iterating through listIterator() did not return correct values.");
            i++;
        }
    }

    @Test
    void HasNextTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                hasNextTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void hasNextTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        for (int i = 0; i < dataArray.length; i++) {
            assertTrue(list.listIterator(i).hasNext(), "Expected to iterator to say it has next element.");
        }
        assertFalse(list.listIterator(dataArray.length).hasNext(),
                "Expected to iterator to say it does NOT have next element.");
    }

    @Test
    void NextTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                nextTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void nextTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        for (int i = 0; i < dataArray.length; i++) {
            assertEquals(dataArray[i], list.listIterator(i).next(), "Expected to iterator to say it has next element.");
        }
    }

    @Test
    void HasPreviousTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                hasPreviousTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void hasPreviousTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        for (int i = 0; i < dataArray.length; i++) {
            assertTrue(list.listIterator(i + 1).hasPrevious(), "Expected to iterator to say it has previous element.");
        }
        assertFalse(list.listIterator().hasPrevious(),
                "Expected to iterator to say it does NOT have previous element.");
    }

    @Test
    void PreviousTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                previousTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void previousTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        for (int i = 0; i < dataArray.length; i++) {
            assertEquals(dataArray[i], list.listIterator(i + 1).previous(),
                    "Expected to iterator to say it has next element.");
        }
    }

    @Test
    void AddTest1() {
        String expected = "[(2.2, -, -, -, -, -)]";
        StoutList<Double> lst = new StoutList<>(6);
        ListIterator<Double> iter;
        iter = lst.listIterator();
        iter.add(2.2);
        assertEquals(expected, lst.toStringInternal(), "Add test 1 (new node) fail");
    }

    @Test
    void AddTest2a() {
        String expected = "[(a, b, c, d), (e, f, -, -)]";
        StoutList<Character> lst = new StoutList<>();
        ListIterator<Character> iter;
        iter = lst.listIterator();
        iter.add('a');
        iter.add('b');
        iter.add('e');
        iter.add('f');
        iter = lst.listIterator(2);
        iter.add('c');
        iter.add('d');
        assertEquals(expected, lst.toStringInternal(), "Add test 2a (previous node not full) fail");
    }

    @Test
    void AddTest2b() {
        String expected = "[(ab, cd), (ef, gh), (ij, -)]";
        StoutList<String> lst = new StoutList<>(2);
        ListIterator<String> iter;
        iter = lst.listIterator();
        iter.add("ab");
        iter.add("cd");
        iter.add("ef");
        iter.add("gh");
        iter.add("ij");
        assertEquals(expected, lst.toStringInternal(), "Add test 2b (tail node and previous full) fail");
    }

    @Test
    void AddTest3() {
        String expected = "[(0, 1, 2, 3, -, -)]";
        StoutList<Integer> lst = new StoutList<>(6);
        ListIterator<Integer> iter;
        iter = lst.listIterator();
        iter.add(0);
        iter.add(2);
        iter.add(3);
        iter = lst.listIterator(1);
        iter.add(1);
        assertEquals(expected, lst.toStringInternal(), "Add test 3 (current node not full) fail");
    }

    @Test
    void AddTest4a() {
        String expected = "[(a, b, c, -), (d, e, -, -)]";
        StoutList<Character> lst = new StoutList<>();
        ListIterator<Character> iter;
        iter = lst.listIterator();
        iter.add('a');
        iter.add('c');
        iter.add('d');
        iter.add('e');
        iter = lst.listIterator(1);
        iter.add('b');
        assertEquals(expected, lst.toStringInternal(), "Add test 4a (split node add former) fail");
    }

    @Test
    void AddTest4b() {
        String expected = "[(0, 1, -, -), (2, 3, 4, -)]";
        StoutList<Integer> lst = new StoutList<>();
        ListIterator<Integer> iter;
        iter = lst.listIterator();
        iter.add(0);
        iter.add(1);
        iter.add(2);
        iter.add(4);
        iter = lst.listIterator(3);
        iter.add(3);
        assertEquals(expected, lst.toStringInternal(), "Add test 4b (split node add later) fail");
    }

    @Test
    void AddNullTest() {
        StoutList<String> lst = new StoutList<>();
        ListIterator<String> iter;
        iter = lst.listIterator();
        assertThrowsExactly(NullPointerException.class, () -> iter.add(null),
                "Should throw null exception when trying to add null");
    }

    @Test
    void RemoveTest1() {
        String expected = "[]";
        StoutList<Double> lst = new StoutList<>(8);
        ListIterator<Double> iter;
        iter = lst.listIterator();
        iter.add(576.63);
        iter = lst.listIterator();
        iter.next();
        iter.remove();
        assertEquals(expected, lst.toStringInternal(), "Remove test 1 (delete node) fail");
    }

    @Test
    void RemoveTest2() {
        String expected = "[(0, 2, 3, -), (4, 5, -, -)]";
        StoutList<Integer> lst = new StoutList<>();
        ListIterator<Integer> iter;
        iter = lst.listIterator();
        iter.add(0);
        iter.add(1);
        iter.add(2);
        iter.add(3);
        iter.add(4);
        iter.add(5);
        iter = lst.listIterator(1);
        iter.next();
        iter.remove();
        assertEquals(expected, lst.toStringInternal(), "Remove test 2 (default delete) fail");
    }

    @Test
    void RemoveTest3a() {
        String expected = "[(apple, banana, -, -), (cranberry, date, eggplant, -)]";
        StoutList<String> lst = new StoutList<>();
        ListIterator<String> iter;
        iter = lst.listIterator();
        iter.add("apple");
        iter.add("zucchini");
        iter.add("corn");
        iter.add("potato");
        iter.add("banana");
        iter.add("cranberry");
        iter.add("date");
        iter.add("eggplant");
        iter = lst.listIterator(4);
        iter.previous();
        iter.remove();
        iter.previous();
        iter.remove();
        iter.previous();
        iter.remove();
        assertEquals(expected, lst.toStringInternal(), "Remove test 3a (mini-merge) fail");
    }

    @Test
    void RemoveTest3b() {
        String expected = "[(a, b, c, d, e, -)]";
        StoutList<Character> lst = new StoutList<>(6);
        ListIterator<Character> iter;
        iter = lst.listIterator();
        iter.add('a');
        iter.add('b');
        iter.add('1');
        iter.add('2');
        iter.add('3');
        iter.add('4');
        iter.add('c');
        iter.add('d');
        iter.add('e');
        iter = lst.listIterator(6);
        iter.previous();
        iter.remove();
        iter.previous();
        iter.remove();
        iter.previous();
        iter.remove();
        iter.previous();
        iter.remove();
        assertEquals(expected, lst.toStringInternal(), "Remove test 3b (full-merge) fail");
    }

    @Test
    void RemoveNoDirectionTest() {
        StoutList<String> lst = new StoutList<>();
        ListIterator<String> iter = lst.listIterator();
        iter.add("Hello");
        assertThrowsExactly(IllegalStateException.class, () -> iter.remove(),
                "Should throw IllegalStateException exception when trying to remove with no direction");
    }

    @Test
    void SetTest() {
        String expected = "[(2048.34, -42.67, 11.53, 54.6, 75.32, -26.77, 0.46, 39.81, 17.95, -54.29, -5.14, | 545.567), (30.88, -72.09, -14.62, 5.41, 20.19, -95.76, 0.002, 49.08, -, -, -, -)]";
        StoutList<Double> lst = new StoutList<>(12);
        ListIterator<Double> iter;
        iter = lst.listIterator();
        for (Double e : TestValues.FLOATS) {
            iter.add(e);
        }
        iter = lst.listIterator(19);
        iter.previous();
        iter.set(0.002);
        iter = lst.listIterator(3);
        iter.next();
        iter.set(54.6);
        iter = lst.listIterator(0);
        iter.next();
        iter.set(2048.34);
        iter = lst.listIterator(12);
        iter.previous();
        iter.set(545.567);
        assertEquals(expected, lst.toStringInternal(iter), "Set test did not result in expeced list");
    }

    @Test
    void SetNullTest() {
        StoutList<String> lst = new StoutList<>();
        ListIterator<String> iter;
        iter = lst.listIterator();
        assertThrowsExactly(NullPointerException.class, () -> iter.add(null),
                "Should throw null exception when trying to set null");
    }

    @Test
    void SetNoDirectionTest() {
        StoutList<String> lst = new StoutList<>();
        ListIterator<String> iter = lst.listIterator();
        iter.add("Hello");
        assertThrowsExactly(IllegalStateException.class, () -> iter.set("Bye"),
                "Should throw IllegalStateException exception when trying to set with no direction");
    }

    @Test
    void NextIndexTest() {
        StoutList<Integer> lst = new StoutList<>(12);
        ListIterator<Integer> iter;
        iter = lst.listIterator();
        for (Integer e : TestValues.INTEGERS) {
            iter.add(e);
        }
        iter = lst.listIterator(7);
        assertEquals(7, iter.nextIndex(), "nextIndex() gave incorrect result.");
        iter.next();
        assertEquals(8, iter.nextIndex(), "nextIndex() gave incorrect result after incrementing with next().");
    }

    @Test
    void PreviousIndexTest() {
        StoutList<Character> lst = new StoutList<>(12);
        ListIterator<Character> iter;
        iter = lst.listIterator();
        for (Character e : TestValues.CHARACTERS) {
            iter.add(e);
        }
        iter = lst.listIterator(7);
        assertEquals(6, iter.previousIndex(), "previousIndex() gave incorrect result.");
        iter.previous();
        assertEquals(5, iter.previousIndex(),
                "previousIndex() gave incorrect result after decrementing with previous().");
    }

}
