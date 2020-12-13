package compression;

import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static util.Utility.binaryToDecimal;
import static util.Utility.decimalToBinary;
import static util.Utility.flip;
import static util.Utility.padToSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW {

    private Map<String, Integer> baseDictionary;
    private int baseCodeword;
    private int baseCodewordLength;

    public LZW(String text) {
        this.baseDictionary = new HashMap<>();
        setupBaseDict(text);
    }

    public Map<String, Integer> getBaseDictionary() {
        return baseDictionary;
    }

    public void setupBaseDictionary(String text) {
        setupBaseDict(text);
    }

    /**
     * This method is used to encode text, using the base dictionary setup upon class creation.
     * @param text this is the raw text
     * @return string this is the encoded text
     */
    public String encode(String text) {
        Map<String, Integer> dict = new HashMap<>(baseDictionary);
        int codeword = baseCodeword;
        int codewordLength = baseCodewordLength;

        StringBuilder encodedText = new StringBuilder();
        int position = 0;
        int step = 0;

        System.out.println("Steps of LZW Compression: ");

        while (position < text.length()) {
            String word = valueOf(text.charAt(position));
            String longestWordInDictionary = word;
            int length = 0;

            while (dict.containsKey(word)) {
                longestWordInDictionary = word;
                length++;
                if (position + length < text.length() + 1) {
                    word = text.substring(position, position + length);
                } else {
                    word = "----";
                }
            }

            String longestWordCode = decimalToBinary(dict.get(longestWordInDictionary), codewordLength);
            StringBuilder code = new StringBuilder(longestWordCode);
            encodedText.append(padToSize(code, codewordLength));

            if (codeword >= Math.pow(2, codewordLength)) {
                codewordLength++;
            }

            if (!word.equals("----")) {
                dict.put(word, codeword);
                System.out.println((step + 1) + "\t" + (position + 1) + "\t" + longestWordInDictionary + "    \t" + code + "      \t" + word + "          \t" + dict.get(word));
            } else {
                System.out.println((step + 1) + "\t" + (position + 1) + "\t" + longestWordInDictionary + "    \t" + code + "      \t" + word + "          \t" + "----");
            }

            codeword++;
            position += length - 1;
            step++;
        }

        return encodedText.toString();
    }

    /**
     * This method is used to decode text, using the base dictionary setup upon class creation.
     * @param encodedText this is the encoded text
     * @return string this is the decoded text
     */
    public String decode(String encodedText) {
        Map<Integer, String> dict = flip(baseDictionary);

        int codeword = baseCodeword;
        int codewordLength = baseCodewordLength;

        StringBuilder decodedText = new StringBuilder();
        int position = 0;
        int step = 0;

        System.out.println("Steps of LZW Decompression:");

        String word = encodedText.substring(position, position + codewordLength);
        int code = binaryToDecimal(word);
        decodedText.append(dict.get(code));
        position += codewordLength;

        System.out.println(step + "\t" + position + "    \t" + "----" + "          \t" + word + "          \t" + dict.get(code) + "        \t" + "----" + "      \t" + "----");

        while (position < encodedText.length()) {
            step++;
            String oldString = dict.get(code);

            if (codeword >= Math.pow(2, codewordLength)) {
                codewordLength++;
            }

            if (position + codewordLength < encodedText.length() + 1) {
                word = encodedText.substring(position, position + codewordLength);
                code = binaryToDecimal(word);
            }

            String currentString;
            if (dict.containsKey(code)) {
                currentString = dict.get(code);
                decodedText.append(currentString);
            } else {
                currentString = oldString + oldString.charAt(0);
            }

            String newString = oldString + currentString.charAt(0);
            dict.put(codeword, newString);

            System.out.println(step + "\t" + position + "    \t" + oldString + "           \t" + word + "          \t" + currentString + "        \t" + newString + "      \t" + codeword);

            codeword++;
            position += codewordLength;
        }

        return decodedText.toString();
    }

    /**
     * This method is used to setup the base dictionary from unique characters.
     * @param text this is the raw text used to get the base dictionary
     */
    private void setupBaseDict(String text) {
        List<Character> uniqueCharacters = getUniqueCharacters(text);
        baseCodewordLength = getCodewordLength(uniqueCharacters);
        baseCodeword = 0;

        for (char letter : uniqueCharacters) {
            baseDictionary.put(valueOf(letter), baseCodeword);
            baseCodeword++;
        }
    }

    /**
     * This method is used to get the codeword length, given a list of unique characters.
     * @param characters this is the list of unique characters
     * @return int this is the codeword length
     */
    private int getCodewordLength(List<Character> characters) {
        int codewordLength = 1;
        int noOfCodewords = (int) Math.pow(2, codewordLength);

        while (noOfCodewords < characters.size()) {
            codewordLength++;
            noOfCodewords = (int) Math.pow(2, codewordLength);
        }

        return codewordLength;
    }

    /**
     * This method is used to get a list of unique characters.
     * @param text this is the raw text
     * @return list this is the list of unique characters
     */
    private List<Character> getUniqueCharacters(String text) {
        List<Character> uniqueCharacters = new ArrayList<>();

        for (char letter : text.toCharArray()) {
            if (!uniqueCharacters.contains(letter)) {
                uniqueCharacters.add(letter);
            }
        }

        sort(uniqueCharacters);

        return uniqueCharacters;
    }

}
