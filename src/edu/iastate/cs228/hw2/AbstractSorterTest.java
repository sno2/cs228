package edu.iastate.cs228.hw2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class AbstractSorterTest {
    @Test
    public void test1() {
        Point[] input = { new Point(3, 4), new Point(1, 2), new Point(5, 6) };
        Point[] expected = { new Point(1, 2), new Point(3, 4), new Point(5, 6) };
        testAlgorithms(0, input, expected);
    }

    @Test
    public void test2() {
        Point[] input = {
                new Point(221, 185), new Point(105, 245),
                new Point(157, 139), new Point(104, 119),
                new Point(60, 37), new Point(142, 128)
        };
        Point[] expected = {
                new Point(60, 37), new Point(104, 119),
                new Point(142, 128), new Point(157, 139),
                new Point(221, 185), new Point(105, 245)
        };
        testAlgorithms(1, input, expected);
    }

    @Test
    public void test3() {
        Point[] input = {
                new Point(221, 185), new Point(105, 245),
                new Point(157, 139), new Point(104, 119),
                new Point(60, 37), new Point(142, 128)
        };
        Point[] expected = {
                new Point(60, 37), new Point(104, 119),
                new Point(142, 128), new Point(157, 139),
                new Point(221, 185), new Point(105, 245),
        };
        testAlgorithms(1, input, expected);
    }

    @Test
    public void testBehavior() {
        Point[] input = {
                new Point(-10, 0), new Point(-7, 0), new Point(-7, 0), new Point(-6, 0), new Point(-3, 0),
                new Point(-2, 0), new Point(-1, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(3, 0),
                new Point(5, 0), new Point(5, 0), new Point(7, 0), new Point(8, 0), new Point(10, 0), new Point(10, 0),
        };

        AbstractSorter sorter = new QuickSorter(input);
        sorter.setComparator(0);
        sorter.sort();
        assertEquals(new Point(0, 0), sorter.getMedian());
    }

    static int getDeterminantCoordinate(int mode, Point p) {
        return mode == 0 ? p.getX() : p.getY();
    }

    @Test
    public void testRandom() {
        Random rand = new Random(0);

        for (int i = 0; i < 100_000; i++) {
            Point[] points = CompareSorters.generateRandomPoints(rand.nextInt(50) + 1, rand);

            // 0=compare by x
            // 1=compare by y
            for (int mode = 0; mode < 2; mode++) {
                AbstractSorter sorter = new SelectionSorter(points);
                sorter.setComparator(mode);
                sorter.sort();
                int median = getDeterminantCoordinate(mode, sorter.getMedian());

                sorter = new InsertionSorter(points);
                sorter.setComparator(mode);
                sorter.sort();
                assertEquals(median, getDeterminantCoordinate(mode, sorter.getMedian()));

                sorter = new MergeSorter(points);
                sorter.setComparator(mode);
                sorter.sort();
                assertEquals(median, getDeterminantCoordinate(mode, sorter.getMedian()));

                sorter = new QuickSorter(points);
                sorter.setComparator(mode);
                sorter.sort();
                assertEquals(median, getDeterminantCoordinate(mode, sorter.getMedian()));
            }
        }
    }

    void testAlgorithms(int order, Point[] input, Point[] expected) {
        Algorithm[] algos = Algorithm.values();

        for (Algorithm algo : algos) {
            AbstractSorter sorter;

            Point[] clone = input.clone();

            switch (algo) {
                case SelectionSort:
                    sorter = new SelectionSorter(clone);
                    break;
                case InsertionSort:
                    sorter = new InsertionSorter(clone);
                    break;
                case MergeSort:
                    sorter = new MergeSorter(clone);
                    break;
                case QuickSort:
                    sorter = new QuickSorter(clone);
                    break;
                default:
                    // unreachable
                    return;
            }

            sorter.setComparator(order);
            sorter.sort();

            Point[] output = new Point[input.length];
            sorter.getPoints(output);

            assertArrayEquals(String.format("%s", algo.name()), expected, output);
        }
    }
}
