package strings;

import static strings.StringDistance.calculateDistance;

public class StringMain {

    public static void main(String[] args) {
        String first = "abadcdb";
        String second = "acbacacb";
        int distance = calculateDistance(first, second);
        System.out.println("Distance between '" + first + "' and '" + second + "' is: " + distance);
    }

}
