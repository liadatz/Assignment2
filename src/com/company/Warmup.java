package com.company;

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        for (int i = 0; i < arr.length; i++) {
            int fdCopy = fd;
            int bkCopy = bk;
            while (fd > 0) { // Search x for 'fd' number of searches
                myStack.push(arr[i]);
                if (arr[i] == x) return i;
                fd--;
                i++;
            }
            fd = fdCopy; // Return fd to his originally value
            while (bk > 0) { // Backtracking 'bk' step
                myStack.pop();
                bk--;
                i--;
            }
            bk = bkCopy; // Return bk to his originally value
            myStack.push(arr[i]);
            if (arr[i] == x) return i;
        }
        return -1;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        return binSearch(arr, x, myStack, 0, arr.length - 1);
    }

    private static int binSearch(int[] arr, int x, Stack myStack, int left, int right) {
        int isCon = isConsistent(arr);
        if (isCon > 0) { // If array is is not consistent, undo steps in order to become consistent
            int index = 0;
            while (isCon > 0) {
                index = (int) myStack.pop();
                isCon = isCon - 1;
            } // Update left or right parameters to previous values
            if (index < left) return binSearch(arr, x, myStack, index - (right - index), right);
            if (index > right) return binSearch(arr, x, myStack, left, index + (index - left));
            }
        else {
            if (left <= right) { // Recursive binary search
                int mid = (left + right) / 2;
                myStack.push(mid);
                if (x == arr[mid]) return mid;
                if (x < arr[mid]) return binSearch(arr, x, myStack, left, mid - 1);
                if (x > arr[mid]) return binSearch(arr, x, myStack, mid + 1, right);
            }
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
