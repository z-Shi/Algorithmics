package util;

import java.util.HashMap;
import java.util.Map;

public class Utility {

    /**
     * This method is used to convert a given binary word to its decimal representation.
     * @param word this is the binary word itself
     * @return int this is the decimal value
     */
    public static int binaryToDecimal(String word) {
        int decimalVal = 0;
        int base = 1;

        for (int i = word.length() - 1; i >= 0; i--) {
            if (word.charAt(i) == '1') {
                decimalVal += base;
            }
            base = base * 2;
        }

        return decimalVal;
    }

    /**
     * This method is used to convert a decimal number to its binary equivalent.
     * @param value this is the decimal value itself
     * @param paddedLength target length of binary response
     * @return string this is the binary value, padded with zeros if necessary
     */
    public static String decimalToBinary(int value, int paddedLength) {
        StringBuilder binaryVal = new StringBuilder();

        while (value > 0) {
            int remainder = value % 2;
            value = value / 2;
            binaryVal.insert(0, remainder);
        }

        padToSize(binaryVal, paddedLength);

        return binaryVal.toString();
    }

    /**
     * This method is used to pad a binary string to a given length
     * @param binary this is the binary value
     * @param paddedLength this is the given length
     * @return string this is the padded binary value
     */
    public static String padToSize(StringBuilder binary, int paddedLength) {
        while (binary.length() < paddedLength) {
            binary.insert(0, 0);
        }

        return binary.toString();
    }

    /**
     * This method is used to cut a binary string to a given length
     * @param binary this is the binary value
     * @param length this is the given length
     * @return string this is the shortened binary value
     */
    public static String cutToSize(StringBuilder binary, int length) {
        return binary.delete(binary.length() - length + 1, binary.length()).toString();
    }

    /**
     * This method is used to return a flipped version of a map, where the
     * keys are swapped with values
     * @param original this is the original map
     * @return map this is the flipped map
     */
    public static Map<String, String> flip(Map<String, String> original) {
        Map<String, String> flipped = new HashMap<>();

        for (Map.Entry<String, String> entry : original.entrySet()) {
            flipped.put(entry.getValue(), entry.getKey());
        }

        return flipped;
    }

}
