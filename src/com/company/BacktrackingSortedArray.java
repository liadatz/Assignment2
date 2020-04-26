package com.company;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    int size;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
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
        return binarySearch(arr, x, 0, arr.length - 1);
    }//call to private function with the array,x, start index, last index

    private static int binarySearch(int[] arr, int x, int left, int right) {
        //while we did not find x and left <=right we keep searching for x
        if (left <= right) {
            int mid = (left + right) / 2;//the middle between left to right
            if (x == arr[mid]) return mid;
            if (x < arr[mid]) return binarySearch(arr, x, left, mid - 1);//search x between left to mid-1
            if (x > arr[mid]) return binarySearch(arr, x, mid + 1, right);//search x between mid+1 to right
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        int index = insertBinarySearch(arr, x, 0, size - 1);//find the index to insert x
        for (int i = size; i > index; i--) {//move all the cells from size to index one step right
            arr[i] = arr[i - 1];
        }
        arr[index] = x;//insert x to the appropriate cell
        /*push 3 variables to the stuck for use it later in the backtracking function
         we push element x, push the index which x put in, push the size of the array
         before the insertion operation
         */
        stack.push(x);
        stack.push(index);
        stack.push(size);
        size = size + 1;//update size
    }

    private static int insertBinarySearch(int[] arr, int x, int left, int right) {
        //the function search the appropriate index for insert x
        if (right == -1)//the array empty
            return 0;
        while (left != right) {
            int mid = (left + right) / 2;// the middle between left and right
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
        /* push 3 variables to the stuck for use it later in the backtracking function
        we push the element to deletion, push the index to deletion, push the size of the array before the
        deletion operation*/
        stack.push(arr[index]);
        stack.push(index);
        stack.push(size);
        //move all the cells from index to size one step left
        for (int i = index; i < size; i++) {
            arr[i] = arr[i + 1];
        }
        size--;//update size
    }

    @Override
    public Integer minimum() {// in sorted array the minimum index is 0
        if (size == 0) {//if the array empty return -1
            return -1;
        }
        return 0;
    }

    @Override
    public Integer maximum() {// in sorted array the maximum index is size-1
        if (size == 0) {//if the array empty return -1
            return -1;
        }
        return size - 1;
    }

    @Override
    public Integer successor(Integer index) {
        if (index < size - 1)//if the index is not the last the index of the successor is index+1
            return index + 1;
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index != 0)//if the index is not the first the index of the predecssor is index-1
            return index - 1;
        return -1;
    }

    @Override
    public void backtrack() {
        //the function canceled the last operation that was taken (insertion/deletion)
        if (!stack.isEmpty()) {
            int temp = (int) stack.pop();//the size of the array before the last operation
            int index = (int) stack.pop();//the index in which the operation was performed
            if (size < temp) {//the last operation was deletion
                for (int i = size; i > index; i--) {//move all the cells from size to index one step right
                    arr[i] = arr[i - 1];
                }
                arr[index] = (int) stack.pop();//insert element
            } else {//the last operation was insertion
                for (int i = index; i < size; i++) {//move all the cells from index to size one step left
                    arr[i] = arr[i + 1];
                }
                stack.pop();
            }
            size = temp;//update size
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