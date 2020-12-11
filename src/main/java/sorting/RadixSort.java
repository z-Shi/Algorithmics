package sorting;

import java.util.ArrayList;
import java.util.List;

import static util.Utility.binaryToDecimal;
import static util.Utility.decimalToBinary;

public class RadixSort {

    public RadixSort() {

    }

    /**
     * This method allows a list of integers to be sorted, by converting each value to its binary equivalent.
     * It is a basic implementation of the Radix Sort, based on pseudocode provided in class.
     * We are given the length of these binary words - which is expected to be >= the length of the largest
     * value in the sequence, or it won't work correctly.
     * We are also given the length of the subsequence, this is used to determine the number of buckets,
     * and therefore the number of iterations.
     * @param rawSequence this is the unsorted list of integers
     * @param itemLength this is the length of the largest integer when converted to binary
     * @param lengthOfSubseq this is the length of the subsequence used for sorting
     * @return list<int> this is the sorted list of integers
     */
    public List<Integer> sort(List<Integer> rawSequence, int itemLength, int lengthOfSubseq) {
        List<String> workingSequence = getBinaryVersion(rawSequence, itemLength);

        int numIterations = itemLength / lengthOfSubseq;
        int numBuckets = (int) Math.pow(2, lengthOfSubseq);

        List<List<String>> buckets = new ArrayList<>(numBuckets);

        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int i = 1; i <= numIterations; i++) {
            buckets.forEach(List::clear);

            for (String word : workingSequence) {
                int k = bits(word, lengthOfSubseq, (i - 1) * lengthOfSubseq);
                buckets.get(k).add(word);
            }

            workingSequence.clear();

            buckets.forEach(workingSequence::addAll);

            System.out.println("Iteration: " + i + " | Sequence: " + workingSequence.toString());
        }

        return getDecimalVersion(workingSequence);
    }

    /**
     * Helper method to return the value stored at our requested bit locations.
     * @param item this is the word itself
     * @param lengthOfSubseq this is the length of our subsequence
     * @param pos this is the end position of our subsequence
     * @return int this is the decimal value of our requested subsequence
     */
    private int bits(String item, int lengthOfSubseq, int pos) {
        String subseq = item.substring(item.length() - pos - lengthOfSubseq, item.length() - pos);
        return binaryToDecimal(subseq);
    }

    /**
     * Helper method to return a list of binary words, given the list of integers and padded length for binary values.
     * @param input this is the list of integers
     * @param paddedLength this is the padded length for binary values
     * @return list<string> this is the list of binary words
     */
    private List<String> getBinaryVersion(List<Integer> input, int paddedLength) {
        List<String> output = new ArrayList<>();
        input.forEach(value -> output.add(decimalToBinary(value, paddedLength)));
        return output;
    }

    /**
     * Helper method to return a list of integers, given the list of binary words
     * @param input this is the list of binary words
     * @return list<int> this is the list of integers
     */
    private List<Integer> getDecimalVersion(List<String> input) {
        List<Integer> output = new ArrayList<>();
        input.forEach(word -> output.add(binaryToDecimal(word)));
        return output;
    }

}
