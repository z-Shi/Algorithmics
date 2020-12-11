package sorting;

import java.util.ArrayList;
import java.util.List;

public class SortingMain {

    public static void main(String[] args) {
        List<String> values = new ArrayList<>();
        values.add("10000100"); // 132
        values.add("10010001"); // 145
        values.add("00011101"); // 29
        values.add("00000111"); // 7
        RadixSort radixSort = new RadixSort();
        List<String> sorted = radixSort.sort(values, 8);
        System.out.println(sorted.toString());
    }

}
