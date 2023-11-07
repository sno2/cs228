package edu.iastate.cs228.examples;

import java.util.Stack;

public class PostfixMath {
    public static void main(String[] args) {
        String s = "12 3 4 * +";
        System.out.println(evaluate(s)); // 24
    }

    public static int evaluate(String s) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Add all numbers to stack.
            if (ch >= '0' && ch <= '9') {
                int accumulator = 0;
                do {
                    accumulator *= 10;
                    accumulator += ch - '0';
                    ch = s.charAt(++i);
                } while (ch >= '0' && ch <= '9');
                stack.add(accumulator);
            }

            // Parse operators
            switch (ch) {
                case '+':
                    stack.add(stack.pop() + stack.pop());
                    break;
                case '-':
                    stack.add(stack.pop() - stack.pop());
                    break;
                case '*':
                    stack.add(stack.pop() * stack.pop());
                    break;
                case '/':
                    stack.add(stack.pop() / stack.pop());
                    break;
                case '%':
                    stack.add(stack.pop() % stack.pop());
                    break;
                case ' ':
                    break;
                default:
                    System.err.println("Warning: Unknown character: '" + ch + "'");
                    break;
            }
        }

        return stack.pop();
    }
}
