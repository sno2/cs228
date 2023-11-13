package edu.iastate.cs228.hw4;

public class MsgTree {
    public char payloadChar;
    public MsgTree left;
    public MsgTree right;

    /*
     * Can use a static char idx to the tree string for recursive
     * solution, but it is not strictly necessary
     */
    private static int staticCharIdx = 0;

    // Constructor building the tree from a string
    public MsgTree(String encodingString) {
    }

    // Constructor for a single node with null children
    public MsgTree(char payloadChar) {
    }

    // method to print characters and their binary codes
    public static void printCodes(MsgTree root, String code) {
    }
}