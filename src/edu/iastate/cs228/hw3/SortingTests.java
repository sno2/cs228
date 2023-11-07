package edu.iastate.cs228.hw3;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

class SortingTests {
    @Test
    void SortTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                sortTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void sortTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        list.sort();
        ListIterator<T> iter = list.listIterator();
        T lastElement = iter.next();
        for (int i = 0; i < dataArray.length - 1; i++) {
            T currentElement = iter.next();
            assertTrue(lastElement.compareTo(currentElement) <= 0, "Values were not sorted properly with sort() value "
                    + lastElement + " and " + currentElement + " are backwards.");
            lastElement = currentElement;
        }
    }

    @Test
    void SortReverseTest() {
        for (int nodeSize : TestValues.NODE_SIZES) {
            for (Object[] array : TestValues.ALL_ARRAYS) {
                sortReverseTester(nodeSize, array);
            }
        }
    }

    private <T extends Comparable<? super T>> void sortReverseTester(int nodeSize, Object[] data) {
        StoutList<T> list = new StoutList<>(nodeSize);
        @SuppressWarnings("unchecked")
        T[] dataArray = (T[]) data;

        for (T e : dataArray) {
            list.add(e);
        }
        list.sortReverse();
        ListIterator<T> iter = list.listIterator();
        T lastElement = iter.next();
        for (int i = 0; i < dataArray.length - 1; i++) {
            T currentElement = iter.next();
            assertTrue(lastElement.compareTo(currentElement) >= 0,
                    "Values were not sorted properly with sortReverse() value " + lastElement + " and " + currentElement
                            + " are backwards.");
            lastElement = currentElement;
        }
    }
}
