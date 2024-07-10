package org.example.demo1;

import java.util.Random;

public class ASCIIDigits {
    private static final String[][] DIGITS = {
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    " 888    888 ",
                    " 888    888 ",
                    " 888    888 ",
                    " 888    888 ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  "
            },
            {
                    "    d8888   ",
                    "   d8P888   ",
                    "  d8P 888   ",
                    "    8888    ",
                    "    8888    ",
                    "    8888    ",
                    "    8888    ",
                    "  88888888  "
            },
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    "      .d88P ",
                    "    .d88P   ",
                    "  .d88P     ",
                    " d88P       ",
                    " d88P  .d88b",
                    " 8888888P\"  "
            },
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    "      .d88P ",
                    "     8888\"  ",
                    "      \"Y88b.",
                    "         888",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  "
            },
            {
                    "     d8888  ",
                    "    d8P888  ",
                    "   d8P 888  ",
                    "  d88  888  ",
                    " d88   888  ",
                    "88888888888 ",
                    "        888 ",
                    "        888 "
            },
            {
                    " 8888888888 ",
                    " 888        ",
                    " 888        ",
                    " 8888888b.  ",
                    "     \"Y88b. ",
                    "       \"888 ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  "
            },
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    " 888        ",
                    " 888d888b.  ",
                    " 888P  \"88b ",
                    " 888    888 ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  "
            },
            {
                    " 8888888888 ",
                    "        d88P",
                    "       d88P ",
                    "      d88P  ",
                    "     d88P   ",
                    "    d88P    ",
                    "   d88P     ",
                    "  d88P      "
            },
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    " 888    888 ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  ",
                    " .d88P.     ",
                    " 888888b.   ",
                    " 888  888   "
            },
            {
                    "  .d8888b.  ",
                    " d88P  Y88b ",
                    " 888    888 ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  ",
                    "       888  ",
                    " Y88b  d88P ",
                    "  \"Y8888P\"  "
            }
    };

    public static String[] getDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }
        return DIGITS[digit];
    }

    static String generateCaptcha() {
        Random rand = new Random();
        int checkNumber = rand.nextInt(100000, 100000000);
        String checkString = String.format("%04d", checkNumber);

        String[] asciiArt = new String[8];
        for (int i = 0; i < 8; i++) {
            asciiArt[i] = " ";
        }

        for (char ch : checkString.toCharArray()) {
            int digit = ch - '0';
            for (int i = 0; i < 8; i++) {
                asciiArt[i] += DIGITS[digit][i] + "             ";
            }
        }
        displayCaptcha(asciiArt);
        return checkString;
    }

    private static void displayCaptcha(String[] asciiArt) {
        for (String line : asciiArt) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        generateCaptcha();
    }
}
