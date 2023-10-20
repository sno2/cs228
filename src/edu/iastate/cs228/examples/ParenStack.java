package edu.iastate.cs228.examples;

import java.util.Stack;

public class ParenStack {
    public static void main(String[] args) {
        String s = "((([())]))";
        System.out.println(isBalanced(s));
    }

    static int ASCII_SIZE = 0x80;

    static boolean isBalanced(String s) {
        Stack<Integer> stack = new Stack<Integer>();

        int[] charMap = new int[ASCII_SIZE];

        charMap['('] = 1;
        charMap[')'] = -1;

        charMap['['] = 2;
        charMap[']'] = -2;

        charMap['{'] = 3;
        charMap['}'] = -3;

        int[] chars = s.chars().toArray();

        for (int c : chars) {
            // Skip non-ASCII characters
            if (c >= ASCII_SIZE) {
                continue;
            }

            int id = charMap[c];

            // Skip unknown characters
            if (id == 0) {
                continue;
            }

            // Open
            else if (id > 0) {
                stack.add(id);
            }

            // Close
            else if (stack.isEmpty() || stack.pop() != -id) {
                return false;
            }
        }

        // If there are any openings left, it is not balanced.
        return stack.isEmpty();
    }

}
