package edu.ccrm.util;

import java.util.Arrays;

public class ArrayDemos {
    public static void run() {
        String[] codes = {"CS201","MA101","CS101"};
        // Operator precedence demo in comments:
        // 5 + 2 * 3 == 11 (multiplication before addition)
        // Bitwise example: (1 << 3) == 8
        Arrays.sort(codes); // sorts lexicographically
        int idx = Arrays.binarySearch(codes, "CS101");
        System.out.println("Arrays demo: " + String.join(",", codes) + " | index of CS101=" + idx);
    }
}
