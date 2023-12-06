package edu.iastate.cs228.hw4;

import java.io.File;
import java.util.Scanner;

/**
 * @author Carter Snook
 */
public class Main {
    public static void main(String[] args) {
        System.out.print("Please enter filename to decode: ");

        Scanner scanner = new Scanner(System.in);

        String filename = scanner.nextLine();

        try {
            File file = new File(filename);
            scanner.close();
            scanner = new Scanner(file);
        } catch (Exception err) {
            System.err.println("File error: " + err.getMessage());
            System.exit(1);
        }

        String schema = scanner.nextLine() + "\n";
        String encodedMessage = "";

        while (true) {
            encodedMessage = scanner.nextLine();

            if (encodedMessage.startsWith("0") || encodedMessage.startsWith("1")) {
                break;
            }

            schema += encodedMessage + "\n";
        }

        schema = schema.substring(0, schema.length() - 1);

        MsgTree root = new MsgTree(schema);

        MsgTree.printCodes(root, encodedMessage);

        System.out.println("MESSAGE:");
        MsgTree.decode(root, encodedMessage);

        scanner.close();
    }
}
