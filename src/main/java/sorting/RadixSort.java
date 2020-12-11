package sorting;

import java.util.ArrayList;
import java.util.List;

public class RadixSort {
    private int bits(String item, int lengthOfSubseq, int pos) {
        String subseq = item.substring(item.length() - pos - lengthOfSubseq, item.length() - pos);
        return Integer.parseInt(subseq, 2);
    }

    public List<String> sort(List<String> sequence, int itemLength) {
        int numIterations = itemLength / 2;
        int lengthOfSubseq = 2;
        int numBuckets = (int) Math.pow(2, lengthOfSubseq);
        List<List<String>> buckets = new ArrayList<>(numBuckets);

        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int i = 1; i <= numIterations; i++) {
            for (int j = 0; j < numBuckets; j++) {
                buckets.get(j).clear();
            }

            for (String item : sequence) {
                int k = bits(item, lengthOfSubseq, (i - 1) * lengthOfSubseq);
                buckets.get(k).add(item);
            }

            sequence.clear();

            for (int j = 0; j < numBuckets; j++) {
                sequence.addAll(buckets.get(j));
            }
        }

        return sequence;
    }

}
