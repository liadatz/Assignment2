package com.company;

import static com.company.Warmup.consistentBinSearch;

public class Main {

    public static void main(String[] args) {

        int[] arr = {1, 1, 2, 14, 15, 16, 23, 99, 100, 100, 100, 132, 193, 196, 197};
        Stack myStack = new Stack();
        //test 1a
        System.out.println(consistentBinSearch(arr,14,myStack));

        //test 1b
        System.out.println(consistentBinSearch(arr, 13, myStack));

    }
}
