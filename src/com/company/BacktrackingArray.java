package com.company;

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.size = 0;//pointer to the next empty cell
    }

    @Override
    public Integer get(int index) {
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        for (int i = 0; i < size; i = i + 1) {//search x by create a loop over all the array
            if (arr[i] == x)
                return i;
        }
        return -1;//return -1 in case x dose not exist in the array
    }

    @Override
    public void insert(Integer x) {
        if (size == arr.length)//if array is full ans there is no space to inter x
            throw new IllegalArgumentException();
        /*push 3 variables to the stuck for use it later in the backtracking function
         we push element x, push the index which x put in, push the size of the array
         before the insertion operation
         */
        stack.push(x);
        stack.push(size);
        stack.push(size);
        //insert x to the next empty cell and update the size
        arr[size] = x;
        size = size + 1;
    }

    @Override
    public void delete(Integer index) {
        /* push 3 variables to the stuck for use it later in the backtracking function
        we push the element to deletion, push the index to deletion, push the size of the array before the
        deletion operation*/
        stack.push(arr[index]);
        stack.push(index);
        stack.push(size);
        //update the size and delete the element arr[index] by overriding it with the last element in the array
        size = size - 1;
        arr[index] = arr[size];
    }


    @Override
    public Integer minimum() {
        /*loop over all the array and search for the index of the minimum element in the array
        we initialize the minimum to 0, and if we find other index i2 that minimum>arr[i2] we
        initialize minimum to i2
         */
        int minimum = 0;
        for (int i = 1; i < size; i++) {
            if (arr[i] < arr[minimum])
                minimum = i;
        }
        return minimum;
    }

    @Override
    public Integer maximum() {
        /*loop over all the array and search for the index of the maximum element in the array
        we initialize the maximum to 0, and if we find other index i2 that maximum<arr[i2] we
        initialize maximum to i2
         */
        int maximum = 0;
        for (int i = 1; i < size; i++) {
            if (arr[i] > arr[maximum])
                maximum = i;
        }
        return maximum;
    }

    @Override
    public Integer successor(Integer index) {
        /*if there is no successor to arr[index]  return -1
        else, we return the index of the successor
         */

        /*loop over the array and search for the index of the successor
        we initialize the output in -1, and when we find variable bigger than arr[index]
         if the variable is smaller than output we change output to variable
         we keep search until the end of the array
         */
        Integer output = -1;
        for (int i = 0; i < size; i++)
            if (arr[i] > arr[index]) {
                if (output.equals(-1) || arr[i] < arr[output])
                    output = i;
            }
        return output;
    }

    @Override
    public Integer predecessor(Integer index) {
        /*if there is no predecessor to arr[index]  return -1
        else, we return the index of the predecessor
         */

        /*loop over the array and search for the index of the predecessor
        we initialize the output in -1, and when we find variable smaller than arr[index]
        if the variable is bigger than output we change output to variable.
        we keep search until the end of the array
         */
        Integer output = -1;
        for (int i = 0; i < size; i++)
            if (arr[i] < arr[index]) {
                if (output.equals(-1) || arr[i] > arr[output])
                    output = i;
            }
        return output;
    }

    @Override
    public void backtrack() {
        //the function canceled the last operation that was taken (insertion/deletion)
        if (!stack.isEmpty()) {
            int tempSize = (Integer) stack.pop();//the size of the array before the last operation
            int tempIndex = (Integer) stack.pop();//the index in which the operation was performed
            if (size < tempSize) {//the last operation was deletion
                arr[size] = arr[tempIndex];
                arr[tempIndex] = (Integer) stack.pop();
            } else//the last operation was insertion
                stack.pop();
            size = tempSize;//update size
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
        //loop over the array and add spaces between the variable
        for (int i = 0; i < size; i++) {
            output = output + arr[i] + " ";
        }
        if (output.length() > 0)
            output = output.substring(0, output.length() - 1);//remove the last space
        System.out.print(output);
    }
}