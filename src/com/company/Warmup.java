package com.company;

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        for (int i = 0; i < arr.length; i++) {
            while (fd > 0) {
                myStack.push(arr[i]);
                if (arr[i] == x) return i;
                fd--;
                i++;
            }
            while (bk > 0) {
                myStack.pop();
                bk--;
                i--;
            }
            myStack.push(arr[i]);
            if (arr[i] == x) return i;
        }
        return -1;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int low = 0;
        int te;
        int high = arr.length;
        int middle = 0;
        while (low < high) {
            int isCon = isConsistent(arr);
            if (isCon > 0) {
                while (isCon >= 0) {
                    middle = (int) myStack.pop();
                    isCon--;
                }
            }
            else middle = (low + high) / 2;
            myStack.push(middle);
            if (arr[middle] == x) return middle;
            if (arr[middle] < x) low = middle + 1;
            else high = middle - 1;
        }
        return -1;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}
