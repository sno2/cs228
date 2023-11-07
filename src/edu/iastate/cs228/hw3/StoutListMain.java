package edu.iastate.cs228.hw3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ListIterator;

/**
 * A class to test the StoutList class behavior with the examples.
 * 
 * @author Joshua Dwight
 */
public class StoutListMain {
    public static void main(String args[]) {
        StoutList<Integer> lst = new StoutList<>();
        lst.add(1);
        lst.add(5);
        lst.add(2);
        lst.add(3);
        lst.add(4);
        lst.add(6);
        lst.sortReverse();

        System.out.println(lst.toString());

        // StoutList<String> stoutList = new StoutList<>(4);

        // stoutList.add("A");
        // stoutList.add("B");
        // stoutList.add("C");
        // stoutList.add("C");
        // stoutList.add("C");
        // stoutList.add("D");
        // stoutList.add("E");

        // stoutList.remove(2);
        // stoutList.remove(2);

        // // [(A, B, C, C), (C, D, E, -))]
        // // [(A, B, -, -), (C, D, E, -))]

        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 3 - An Example List\n");

        // stoutList.add("V"); // add to end
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 4 - After add(V)\n");

        // stoutList.add("W"); // add to end and make new node
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 5 - After add(W)\n");

        // stoutList.add(2, "X"); // add at position 2
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 6 - After add(2, X)\n");

        // stoutList.add(2, "Y"); // add at position 2
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 7 - After add(2, Y)\n");

        // stoutList.add(2, "Z"); // add at position 2, perform split operation and move
        // Y and X to n'
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 8 - After add(2, Z)\n");

        // System.out.println();
        // System.out.println();

        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 9 - Example list after all add() operations\n");

        // System.out.println();
        // System.out.println();

        // // [(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]
        // stoutList.remove(9); // remove W, get rid of empty node
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 10 - After removing W\n");

        // stoutList.remove(3); // remove Y and perform a mini merge
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 11 - After removing Y (mini-merge)\n");

        // stoutList.remove(3); // remove X and perform another mini merge
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 12 - After removing X (mini-merge)\n");

        // stoutList.remove(5); // remove E, no merge should occur since it was in last
        // // node and last node
        // // // doesn't need M/2 elements
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 13 - After removing E (no merge with predecessor
        // node)\n");

        // stoutList.remove(3); // remove C, full merge should occur to ensure all nodes
        // // that aren't last node
        // // have at least M/2 elements
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Figure 14 - After removing C (full merge with successor
        // node)\n");

        // System.out.println();
        // System.out.println();

        // System.out.println(stoutList.toStringInternal());
        // System.out.println("Example list after all remove() operations\n");

        // System.out.println();
        // System.out.println();

        // stoutList.sort(); // insertionSort test
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("End result sorted in normal order\n");

        // stoutList.sortReverse(); // bubbleSort test
        // System.out.println(stoutList.toStringInternal());
        // System.out.println("End result sorted in reverse order");
    }
}
