package strings;

import static java.lang.Math.min;

public class StringDistance {

    /**
     * This method is used to calculate the distance between two given strings.
     * @param first this is the first string
     * @param second this is the second string
     * @return int this is the distance between the two strings
     */
    public static int calculateDistance(String first, String second) {
        int lenFirst = first.length();
        int lenSecond = second.length();

        int[][] table = new int[lenFirst + 1][lenSecond + 1];

        for (int i = 0; i <= lenFirst; i++) {
            table[i][0] = i;
        }

        for (int j = 0; j <= lenSecond; j++) {
            table[0][j] = j;
        }

        for (int i = 0; i < lenFirst; i++) {
            char characterFromFirst = first.charAt(i);

            for (int j = 0; j < lenSecond; j++) {
                char characterFromSecond = second.charAt(j);

                if (characterFromFirst == characterFromSecond) {
                    table[i + 1][j + 1] = table[i][j];
                } else {
                    int replace = table[i][j] + 1;
                    int insert = table[i][j + 1] + 1;
                    int delete = table[i + 1][j] + 1;

                    int min = min(delete, min(replace, insert));
                    table[i + 1][j + 1] = min;
                }
            }
        }

//        // Output Table
//        System.out.print("|   |");
//        for (char chara : second.toCharArray()) {
//            System.out.print("| " + chara + " |");
//        }
//        System.out.println();
//        for (int i = 0; i < lenFirst; i++) {
//            System.out.print("| " + first.charAt(i) + " |");
//            for (int j = 0; j < lenSecond; j++) {
//                System.out.print("| " + table[i][j] + " |");
//            }
//            System.out.println();
//        }

        return table[lenFirst][lenSecond];
    }

}
