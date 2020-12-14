package strings;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class StringSearch {

    private final int NO_OF_CHARS = 256;

    /**
     * This method uses a brute force strategy to find whether a given pattern is in a given text, if found it will
     * return the starting position of this pattern - this will be the first occurrence. If not found, -1.
     * @param text this is the given text
     * @param pattern this is the given pattern
     * @return int this is the starting position if found, -1 if not found
     */
    public int bruteForce(String text, String pattern) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int startingPosition = 0;
        int textPosition = 0;
        int patternPosition = 0;

        while ((startingPosition <= textLength - patternLength) && (patternPosition < patternLength)) {
            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                textPosition++;
                patternPosition++;
            } else {
                patternPosition = 0;
                startingPosition++;
                textPosition = startingPosition;
            }
        }

        if (patternPosition == patternLength) {
            return startingPosition;
        }

        return -1;
    }

    /**
     * This method uses the knuth-morris-pratt algorithm to find whether a given pattern is in a given text, if found
     * it will return the starting position - this will be for the first occurrence. If not found, it will
     * return -1.
     * @param text this is the given text
     * @param pattern this is the given pattern
     * @return int this is the first starting position if found, if not found, -1
     */
    public int kmp(String text, String pattern) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int textPosition = 0;
        int patternPosition = 0;

        int[] borderTable = setupBorderTable(pattern);

        while (textPosition < textLength) {
            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                textPosition++;
                patternPosition++;

                if (patternPosition == patternLength) {
                    return textPosition - patternPosition;
                }
            } else {
                if (borderTable[patternPosition] > 0) {
                    patternPosition = borderTable[patternPosition];
                } else {
                    if (patternPosition == 0) {
                        textPosition++;
                    } else {
                        patternPosition = 0;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * This method uses the boyer-moore algorithm to find whether a given pattern is in a given text, if found
     * it will return the starting position - if found, else it will return -1.
     * @param text this is the given text
     * @param pattern this is the given pattern
     * @return int this is the starting position of the pattern, else it will be -1
     */
    public int bm(String text, String pattern) {
        int patternLength = pattern.length();
        int textLength = text.length();
        int startingPosition = 0;
        int textPosition = patternLength - 1;
        int patternPosition = patternLength - 1;

        int[] lastOccurrenceTable = new int[NO_OF_CHARS];
        setupLastOccurrenceTable(pattern, lastOccurrenceTable);

        while ((startingPosition <= textLength - patternLength) && (patternPosition >= 0)) {
            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                textPosition--;
                patternPosition--;
            } else {
                startingPosition += max(1, patternPosition - lastOccurrenceTable[text.charAt(textPosition)]);
                textPosition += patternLength - min(patternPosition, 1 + lastOccurrenceTable[text.charAt(textPosition)]);
                patternPosition = patternLength - 1;
            }
        }

        if (patternPosition < 0) {
            return startingPosition;
        }

        return -1;
    }

    /**
     * This method is used to setup the border table, given a set pattern.
     * @param pattern this is the given pattern
     * @return int[] this is the border table
     */
    private int[] setupBorderTable(String pattern) {
        int[] borderTable = new int[pattern.length()];

        borderTable[1] = 0;
        for (int i = 1; i < pattern.length() - 1; i++) {
            int j = borderTable[i];

            while ((j > 0) && (pattern.charAt(i) != pattern.charAt(j))) {
                j = borderTable[j];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                borderTable[i + 1] = j + 1;
            } else {
                borderTable[i + 1] = 0;
            }
        }

        return borderTable;
    }

    /**
     * This is a method used to setup the last occurrence table for storing the last occurrence of each character.
     * If it doesn't occur in the pattern, it would store -1
     * @param pattern this is the given pattern
     * @param lastOccurrenceTable this is the last occurrence table
     */
    private void setupLastOccurrenceTable(String pattern, int[] lastOccurrenceTable) {
        for (int i = 0; i < NO_OF_CHARS; i++) {
            lastOccurrenceTable[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            lastOccurrenceTable[(int) pattern.charAt(i)] = i;
        }
    }

}
