package compression;

import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static util.Utility.decimalToBinary;
import static util.Utility.padToSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW {

    private Map<String, String> dictionary;

    public LZW() {
        this.dictionary = new HashMap<>();
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public String encode(String text) {
        System.out.println("Original Text: " + text);
        System.out.println("Steps of LZW Compression: ");

        List<Character> uniqueCharacters = getUniqueCharacters(text);
        int codewordLength = getCodewordLength(uniqueCharacters);
        int codeword = 0;

        for (char letter : uniqueCharacters) {
            dictionary.put(valueOf(letter), decimalToBinary(codeword, codewordLength));
            codeword++;
        }

        StringBuilder encodedText = new StringBuilder();
        int position = 0;
        int step = 0;

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

//    public String decode(String encryptedText) {
//
//    }

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

}
