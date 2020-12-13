package compression;

import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static util.Utility.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW {

    private Map<String, String> baseDictionary;
    private int baseCodeword;
    private int baseCodewordLength;

    public LZW(String text) {
        this.baseDictionary = new HashMap<>();
        setupBaseDictionary(text);
    }

    public Map<String, String> getBaseDictionary() {
        return baseDictionary;
    }

    public String encode(String text) {
        Map<String, String> dictionary = new HashMap<>(baseDictionary);
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

            while (dictionary.containsKey(word)) {
                longestWordInDictionary = word;
                length++;
                if (position + length < text.length() + 1) {
                    word = text.substring(position, position + length);
                } else {
                    word = "----";
                }
            }

            StringBuilder code = new StringBuilder(dictionary.get(longestWordInDictionary));
            encodedText.append(padToSize(code, codewordLength));

            if (codeword >= Math.pow(2, codewordLength)) {
                codewordLength++;
            }

            if (!word.equals("----")) {
                dictionary.put(word, decimalToBinary(codeword, codewordLength));
                System.out.println((step + 1) + "\t" + (position + 1) + "\t" + longestWordInDictionary + "    \t" + code + "      \t" + word + "          \t" + dictionary.get(word));
            } else {
                System.out.println((step + 1) + "\t" + (position + 1) + "\t" + longestWordInDictionary + "    \t" + code + "      \t" + word + "          \t" + "----");
            }

            codeword++;
            position += length - 1;
            step++;
        }

        return encodedText.toString();
    }

    public String decode(String encodedText) {
        Map<Integer, String> dictionary = exchangeBinaryForDecimalCodes(flip(baseDictionary));

        int codeword = baseCodeword;
        int codewordLength = baseCodewordLength;

        StringBuilder decodedText = new StringBuilder();
        int position = 0;
        int step = 0;

        System.out.println("Steps of LZW Decompression:");

        String word = encodedText.substring(position, position + codewordLength);
        int code = binaryToDecimal(word);
        decodedText.append(dictionary.get(code));
        position += codewordLength;

        System.out.println(step + "\t" + position + "    \t" + "----" + "          \t" + word + "          \t" + dictionary.get(code) + "        \t" + "----" + "      \t" + "----");

        while (position < encodedText.length()) {
            step++;
            String oldString = dictionary.get(code);

            if (codeword >= Math.pow(2, codewordLength)) {
                codewordLength++;
            }

            if (position + codewordLength < encodedText.length() + 1) {
                word = encodedText.substring(position, position + codewordLength);
                code = binaryToDecimal(word);
            }

            String currentString = dictionary.get(code);
            decodedText.append(currentString);

            String newString = oldString + currentString.charAt(0);
            String newCode = decimalToBinary(codeword, codewordLength);
            dictionary.put(binaryToDecimal(newCode), newString);

            System.out.println(step + "\t" + position + "    \t" + oldString + "           \t" + word + "          \t" + currentString + "        \t" + newString + "      \t" + newCode);

            codeword++;
            position += codewordLength;
        }

        return decodedText.toString();
    }

    private void setupBaseDictionary(String text) {
        List<Character> uniqueCharacters = getUniqueCharacters(text);
        baseCodewordLength = getCodewordLength(uniqueCharacters);
        baseCodeword = 0;

        for (char letter : uniqueCharacters) {
            baseDictionary.put(valueOf(letter), decimalToBinary(baseCodeword, baseCodewordLength));
            baseCodeword++;
        }
    }

    private int getCodewordLength(List<Character> characters) {
        int codewordLength = 1;
        int noOfCodewords = (int) Math.pow(2, codewordLength);

        while (noOfCodewords < characters.size()) {
            codewordLength++;
            noOfCodewords = (int) Math.pow(2, codewordLength);
        }

        return codewordLength;
    }

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

    private Map<Integer, String> exchangeBinaryForDecimalCodes(Map<String, String> binaryKeys) {
        Map<Integer, String> decimalKeys = new HashMap<>();

        for (Map.Entry<String, String> entry : binaryKeys.entrySet()) {
            int code = binaryToDecimal(entry.getKey());
            decimalKeys.put(code, entry.getValue());
        }

        return decimalKeys;
    }

}
