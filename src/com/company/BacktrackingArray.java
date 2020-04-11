package com.company;

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;
    private Integer minimum;
    private Integer maximum;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        size = 0;
        minimum = null;
        maximum = null;

    }

    @Override
    public Integer get(int index) {
        return (Integer) arr[index];
    }

    @Override
    public Integer search(int x) {
        for (int i = 0; i < size; i = i + 1) {
            if (arr[i] == x)
                return (Integer) i;
        }
        return (Integer) (-1);
    }

    @Override
    public void insert(Integer x) {
        if (size == arr.length)
            throw new IllegalArgumentException();
        stack.push(x);
        stack.push(size);
        stack.push(size);
        stack.push(minimum);
        stack.push(maximum);
        arr[size] = x;
        if (minimum == null || x < arr[minimum])
            minimum = (Integer) size;
        if (maximum == null || x > arr[maximum])
            maximum = (Integer) size;
        size = size + 1;
    }

    @Override
    public void delete(Integer index) {
        stack.push(arr[index]);
        stack.push(index);
        stack.push(size);
        stack.push(minimum);
        stack.push(maximum);
        size = size - 1;
        arr[index] = arr[size];
        if (size > 0) {
            if (index.equals(minimum)) {
                minimum = (Integer) 0;
                for (int i = 1; i < size; i++) {
                    if (arr[i] < arr[minimum])
                        minimum = (Integer) i;
                }
            }
            if (index.equals(maximum)) {
                maximum = (Integer) 0;
                for (int i = 1; i < size; i++) {
                    if (arr[i] > arr[maximum])
                        maximum = (Integer) i;
                }
            }
            if (maximum == size)
                maximum = index;
            if (minimum == size)
                minimum = index;
        } else {
            maximum = null;
            minimum = null;
        }
    }


    @Override
    public Integer minimum() {
        return minimum;
    }

    @Override
    public Integer maximum() {
        return maximum;
    }

    @Override
    public Integer successor(Integer index) {
        Integer output = -1;
        for (int i = 0; i < size; i++)
            if (arr[i] > arr[index]) {
                if (output.equals(-1) || arr[i] < arr[output])
                    output = (Integer) i;
            }
        return output;
    }

    @Override
    public Integer predecessor(Integer index) {
        Integer output = -1;
        for (int i = 0; i < size; i++)
            if (arr[i] < arr[index]) {
                if (output.equals(-1) || arr[i] > arr[output])
                    output = (Integer) i;
            }
        return output;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            maximum = (Integer) stack.pop();
            minimum = (Integer) stack.pop();
            int temp = (Integer) stack.pop();
            if (size < temp)
                arr[(Integer) stack.pop()] = (Integer) stack.pop();
            size = temp;
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