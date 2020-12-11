package sorting;

import java.util.ArrayList;
import java.util.List;

public class SortingMain {

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();

        values.add(132);
        values.add(145);
        values.add(29);
        values.add(7);

        RadixSort radixSort = new RadixSort();
        List<Integer> binarySorted = radixSort.sortBinary(values, 8, 2);
        List<Integer> decimalSorted = radixSort.sortDecimal(values);

        System.out.println("Unsorted List: " + values.toString());
        System.out.println("Sorted List (Binary): " + binarySorted.toString());
        System.out.println("Sorted List (Decimal): " + decimalSorted.toString());
    }

}
