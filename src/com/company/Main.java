package com.company;

public class Main {

    public static void main(String[] args) {

        int[] arr = {17, 62, 19, 10, 1, 78, 20, 20, 20, 10};
        int[] arr2 = {12, 6, 1, 29, 17, 33, 30, 46};
        Stack stack1 = new Stack();
        Stack stack2 = new Stack();
        BacktrackingBST bst = new BacktrackingBST(stack1,stack2);
        for (int i = 0; i < arr2.length; i++) {
            BacktrackingBST.Node z = new BacktrackingBST.Node(arr2[i],arr2[i]);
            bst.insert(z);

        }
        bst.print();

    }
}
