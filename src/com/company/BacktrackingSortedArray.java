package com.company;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    int size;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        size = 0;
    }

    @Override
    public Integer get(int index) {
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        return binarySearch(arr, x, 0, arr.length - 1);
    }

    private static int binarySearch(int[] arr, int x, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (x == arr[mid]) return mid;
            if (x < arr[mid]) return binarySearch(arr, x, left, mid - 1);
            if (x > arr[mid]) return binarySearch(arr, x, mid + 1, right);
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        int index = insertBinarySearch(arr, x, 0, size - 1);
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = x;
        stack.push(x);
        stack.push(index);
        stack.push(size);
        size = size + 1;
    }

    private static int insertBinarySearch(int[] arr, int x, int left, int right) {
        if (right == -1)
            return 0;
        while (left != right) {
            int mid = (left + right) / 2;
            if (x == arr[mid]) return mid;
            if (x < arr[mid]){
                if (left==mid)
                    return mid;
                return insertBinarySearch(arr, x, left, mid - 1);
            }
            if (x > arr[mid]) return insertBinarySearch(arr, x, mid + 1, right);
        }
        if (x>arr[left])
            return left+1;
        else
            return left;
    }


    @Override
    public void delete(Integer index) {
        stack.push(arr[index]);
        stack.push(index);
        stack.push(size);
        for (int i = index; i < size; i++) {
            arr[i] = arr[i + 1];
        }
        size = size--;
    }

    @Override
    public Integer minimum() {
        if (size == 0) {
            return -1;
        }
        return 0;
    }

    @Override
    public Integer maximum() {
        if (size == 0) {
            return -1;
        }
        return size - 1;
    }

    @Override
    public Integer successor(Integer index) {
        if (index < size - 1)
            return index + 1;
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index != 0)
            return index - 1;
        return -1;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            int temp = (int) stack.pop();
            int index = (int) stack.pop();
            if (size < temp) {
                for (int i = size; i > index; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[index] = (int) stack.pop();
            } else {
                for (int i = index; i < size; i++) {
                    arr[i] = arr[i + 1];
                    stack.pop();
                }
            }
        }
        System.out.println("backtracking performed");
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        String output = "";
        for (int i = 0; i < size; i++) {
            output = output + arr[i] + " ";
        }
        if (output.length() > 0)
            output = output.substring(0, output.length() - 1);
        System.out.println(output);
    }
}