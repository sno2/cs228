package edu.iastate.cs228.hw3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StoutListTests {

    @Test
    void GeneralFormatTest1() {
        String expected;
        StoutList<Character> lst = new StoutList<>();
        for (Character e : TestValues.CHARACTERS) {
            lst.add(e);
        }
        expected = "[(A, 2, b, C), (4, E, F, 3), (g, H, i, J), (b, 4, L, 4), (N, o, P, 5)]";
        assertEquals(expected, lst.toStringInternal(), "List does not match expected format");
    }

    @Test
    void GeneralFormatTest2() {
        String expected;
        StoutList<Integer> lst = new StoutList<>(26);
        for (Integer e : TestValues.INTEGERS) {
            lst.add(e);
        }
        expected = "[(23, -75, 42, 0, 91, -10, -50, 13, 66, -35, 79, -99, 7, 54, -25, -68, 31, 18, -82, 61, -, -, -, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "List does not match expected format");
    }

    @Test
    void ExampleUsageTest() {
        String expected;
        StoutList<Character> lst = new StoutList<>();
        lst.add('A');
        lst.add('B');
        lst.add('C');
        lst.add('E');
        lst.add(3, 'D');
        expected = "[(A, B, -, -), (C, D, E, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.add('V');
        expected = "[(A, B, -, -), (C, D, E, V)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.add('W');
        expected = "[(A, B, -, -), (C, D, E, V), (W, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.add(2, 'X');
        expected = "[(A, B, X, -), (C, D, E, V), (W, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.add(2, 'Y');
        expected = "[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.add(2, 'Z');
        expected = "[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.remove(9);
        expected = "[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.remove(3);
        expected = "[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.remove(3);
        expected = "[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.remove(5);
        expected = "[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");

        lst.remove(3);
        expected = "[(A, B, Z, -), (D, V, -, -)]";
        assertEquals(expected, lst.toStringInternal(), "Fail when following example usage from pdf");
    }

    @Test
    void sizeTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                sizeTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void sizeTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (int i = 0; i < dataArray.length; i++) {
            list.add(dataArray[i]);
            assertEquals(i + 1, list.size(),
                    String.format("For node size of %d, after adding type %s to list, size gave incorrect result",
                            nodeSize, dataArray[i].getClass().toString()));
        }
    }

    @Test
    void AddTest1() {
        String expected = "[(2.2, -, -, -, -, -)]";
        StoutList<Double> lst = new StoutList<>(6);
        lst.add(2.2);
        assertEquals(expected, lst.toStringInternal(), "Add test 1 (new node) fail");
    }

    @Test
    void AddTest2a() {
        String expected = "[(a, b, c, d), (e, f, -, -)]";
        StoutList<Character> lst = new StoutList<>();
        lst.add('a');
        lst.add('b');
        lst.add('e');
        lst.add('f');
        lst.add(2, 'c');
        lst.add(3, 'd');
        assertEquals(expected, lst.toStringInternal(), "Add test 2a (previous node not full) fail");
    }

    @Test
    void AddTest2b() {
        String expected = "[(ab, cd), (ef, gh), (ij, -)]";
        StoutList<String> lst = new StoutList<>(2);
        lst.add("ab");
        lst.add("cd");
        lst.add("ef");
        lst.add("gh");
        lst.add(4, "ij");
        assertEquals(expected, lst.toStringInternal(), "Add test 2b (tail node and previous full) fail");
    }

    @Test
    void AddTest3() {
        String expected = "[(0, 1, 2, 3, -, -)]";
        StoutList<Integer> lst = new StoutList<>(6);
        lst.add(0);
        lst.add(2);
        lst.add(3);
        lst.add(1, 1);
        assertEquals(expected, lst.toStringInternal(), "Add test 3 (current node not full) fail");
    }

    @Test
    void AddTest4a() {
        String expected = "[(a, b, c, -), (d, e, -, -)]";
        StoutList<Character> lst = new StoutList<>();
        lst.add('a');
        lst.add('c');
        lst.add('d');
        lst.add('e');
        lst.add(1, 'b');
        assertEquals(expected, lst.toStringInternal(), "Add test 4a (split node add former) fail");
    }

    @Test
    void AddTest4b() {
        String expected = "[(0, 1, -, -), (2, 3, 4, -)]";
        StoutList<Integer> lst = new StoutList<>();
        lst.add(0);
        lst.add(1);
        lst.add(2);
        lst.add(4);
        lst.add(3, 3);
        assertEquals(expected, lst.toStringInternal(), "Add test 4b (split node add later) fail");
    }

    @Test
    void AddNullTest() {
        StoutList<String> lst = new StoutList<>();
        assertThrowsExactly(NullPointerException.class, () -> lst.add(null),
                "Should throw null exception when trying to add null");
    }

    @Test
    void RemoveTest1() {
        String expectedLst = "[]";
        Double expectedItem = 576.63;
        StoutList<Double> lst = new StoutList<>(8);
        lst.add(576.63);
        assertEquals(expectedItem, lst.remove(0), "Remove test 1 returned wrong element");
        assertEquals(expectedLst, lst.toStringInternal(), "Remove test 1 (delete node) fail");
    }

    @Test
    void RemoveTest2() {
        String expectedLst = "[(0, 1, 3, -), (4, 5, -, -)]";
        Integer expectedItem = 2;
        StoutList<Integer> lst = new StoutList<>();
        lst.add(0);
        lst.add(1);
        lst.add(2);
        lst.add(3);
        lst.add(4);
        lst.add(5);
        assertEquals(expectedItem, lst.remove(2), "Remove test 2 returned wrong element");
        assertEquals(expectedLst, lst.toStringInternal(), "Remove test 2 (default delete) fail");
    }

    @Test
    void RemoveTest3a() {
        String expectedLst = "[(apple, banana, -, -), (cranberry, date, eggplant, -)]";
        String expectedItem = "zucchini";
        StoutList<String> lst = new StoutList<>();
        lst.add("apple");
        lst.add("zucchini");
        lst.add("corn");
        lst.add("potato");
        lst.add("banana");
        lst.add("cranberry");
        lst.add("date");
        lst.add("eggplant");
        lst.remove(2);
        lst.remove(2);
        assertEquals(expectedItem, lst.remove(1), "Remove test 3a returned wrong element");
        assertEquals(expectedLst, lst.toStringInternal(), "Remove test 3a (mini-merge) fail");
    }

    @Test
    void RemoveTest3b() {
        String expectedLst = "[(a, b, c, d, e, -)]";
        Character expectedItem = '1';
        StoutList<Character> lst = new StoutList<>(6);
        lst.add('a');
        lst.add('b');
        lst.add('1');
        lst.add('2');
        lst.add('3');
        lst.add('4');
        lst.add('c');
        lst.add('d');
        lst.add('e');
        lst.remove(3);
        lst.remove(3);
        lst.remove(3);
        assertEquals(expectedItem, lst.remove(2), "Remove test 3b returned wrong element");
        assertEquals(expectedLst, lst.toStringInternal(), "Remove test 3b (full-merge) fail");
    }

}