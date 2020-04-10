package com.company;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        Node curr = root;
        while (curr != null && curr.key != x) {
            if (curr.key > x) {
                curr = curr.left;
            } else
                curr = curr.right;
        }
        return curr;
    }

    public void insert(BacktrackingBST.Node z) {
        stack.push(z);
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (curr.key > z.key)
                curr = curr.left;
            else
                curr = curr.right;
        }
        if (prev == null)
            root = z;
        else if (prev.key > z.key)
            prev.left = z;
        else
            prev.right = z;
    }

    public void delete(Node x) {
        recursiveDelete(x,root);
    }

    private static void recursiveDelete (Node toRemove, Node current) {
        if (current.key > toRemove.key) {
            if (current.left != null)
                recursiveDelete(toRemove, current.left);
        } else if (current.key < toRemove.key) {
            if (current.right != null)
                recursiveDelete(toRemove, current.right);
        } else { //need to remove the data in this node
            if (current.left == null | current.right == null) { // 0/1 children
                if (current.left == null)         // (base cases)
                    current.parent = current.right;
                else
                    current.parent = current.left;
            } else { // this node has two children
                current = findMin(current.right);
                recursiveDelete(current, current.right);
            }
        }
    }
    private static Node findMin(Node node){
        Node currNode = node;
        while (currNode.left != null){
            currNode = currNode.left;
        }
        return currNode;
    }

    public Node minimum() {
        // TODO: implement your code here
        return null;
    }

    public Node maximum() {
        // TODO: implement your code here
        return null;
    }

    public Node successor(Node x) {
        // TODO: implement your code here
        return null;
    }

    public Node predecessor(Node x) {
        // TODO: implement your code here
        return null;
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // TODO: implement your code here
    }

    public void printPreOrder() {
        // TODO: implement your code here
    }

    @Override
    public void print() {
        // TODO: implement your code here
    }

    public static class Node {
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

}
