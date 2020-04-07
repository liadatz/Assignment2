package com.company;

public class Main {

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 14, 15, 16, 23, 99, 100, 100, 100, 132, 193, 196, 197};
        Stack myStack = new Stack();
        System.out.println(Warmup.consistentBinSearch(arr,14,myStack));
    }
}
