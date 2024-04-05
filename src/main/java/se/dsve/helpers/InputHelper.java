package se.dsve.helpers;

import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;

    public InputHelper() {
        scanner = new Scanner(System.in);
    }

    private String readString() {
        return scanner.nextLine();
    }

    private int readInt() {
        int number = 0;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                valid = true;
            } else {
                System.out.println("Det är inte ett giltigt heltal. Försök igen.");
            }
            scanner.nextLine();
        }
        return number;
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public void closeScanner() {
    }

    public String promptUserAndGetString(String s) {
        System.out.print(s);
        return readString();
    }

    public int promptUserAndGetInt(String s) {
        System.out.print(s);
        return readInt();
    }
}
