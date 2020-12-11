package util;

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

        while (binaryVal.length() < paddedLength) {
            binaryVal.insert(0, 0);
        }

        return binaryVal.toString();
    }

}
