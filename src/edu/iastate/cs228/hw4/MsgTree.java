package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * A binary tree used to reprsent a message schema.
 * 
 * @author Carter Snook
 */
public class MsgTree {
    /**
     * The character payload of the node.
     */
    public char payloadChar;

    /**
     * The left branch of the node.
     */
    public MsgTree left;

    /**
     * The right branch of the node.
     */
    public MsgTree right;

    // Constructor building the tree from a string
    public MsgTree(String encodingString) {
        // The root node has no payload.
        payloadChar = '\0';

        // Stack to keep track of the open nodes, or internal nodes that have
        // not had their branches filled yet. It is also expected for this to
        // still contain items after the function has ran because we only pop
        // nodes when required by the binary tree.
        Stack<MsgTree> open = new Stack<>();

        // Loop through the encoding string.
        for (int i = 0; i < encodingString.length(); i++) {

            // Get the current character.
            char c = encodingString.charAt(i);

            // If the character is a '^', then we need to create a new node.
            if (c == '^') {
                // If the stack is empty, then we need to add the root node to
                // the stack.
                if (open.isEmpty()) {
                    open.add(this);
                    continue;
                }

                MsgTree lastNode = open.lastElement();

                if (lastNode.left == null) {
                    MsgTree node = new MsgTree('\0');
                    lastNode.left = node;
                    open.add(node);
                } else {
                    MsgTree node = new MsgTree('\0');
                    lastNode.right = node;
                    open.pop();
                    open.add(node);
                }
            }

            // Otherwise, we need to set the payload to one of the current
            // node's branches.
            else {
                MsgTree lastNode = open.lastElement();

                if (lastNode.left == null) {
                    lastNode.left = new MsgTree(c);
                } else {
                    lastNode.right = new MsgTree(c);
                    open.pop();
                }
            }
        }
    }

    /**
     * Constructor for a node with a payload and no branches.
     */
    public MsgTree(char payloadChar) {
        this.payloadChar = payloadChar;
    }

    /**
     * Returns true if the node is a leaf.
     */
    private boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Prints the payload characters and their encoded values. Also, prints out
     * the decoded message.
     * 
     * @param root           The root node of the tree.
     * @param encodedMessage The encoded message.
     */
    public static void printCodes(MsgTree root, String encodedMessage) {
        System.out.println("character\tcode");
        System.out.println("-------------------------");
        printCodesRec(root, "");
    }

    /**
     * Recursively prints the payload characters and their encoded values using
     * preorder traversal.
     */
    private static void printCodesRec(MsgTree node, String accumulator) {
        if (node.isLeaf()) {
            System.out.println(node.payloadChar + "\t" + accumulator);
            return;
        }

        if (node.left != null) {
            printCodesRec(node.left, accumulator + "0");
        }

        if (node.right != null) {
            printCodesRec(node.right, accumulator + "1");
        }
    }

    /**
     * Decodes the message using the tree.
     * 
     * @param root    The tree to use for decoding.
     * @param message The message to decode.
     */
    public static void decode(MsgTree root, String encodedMessage) {
        for (int i = 0; i < encodedMessage.length();) {
            // The following steps decode one character from the bit string:

            // 1. Start at root
            MsgTree current = root;

            // 2. Repeat until at leaf
            while (!current.isLeaf()) {
                // a. Scan one bit
                char bit = encodedMessage.charAt(i++);

                // b. Go to left child if 0; else go to right child
                if (bit == '0') {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            // 3. Print leaf payload
            System.out.print(current.payloadChar);
        }
    }

}