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
        else if (prev.key > z.key) {
            prev.left = z;
            z.parent = prev;
        }
        else {
            prev.right = z;
            z.parent = prev;
        }
    }

    public void delete(Node x) {
        if (x.left == null & x.right == null) { // 0 children
            if (x.parent.right == x) x.parent.right = null;
            else x.parent.left = null;
        }
        else if (x.left == null) { // 1 right children
            x.right.parent = x.parent;
            x.parent.left = x.right;
        }
        else if (x.right == null) { // 1 left right
            x.left.parent = x.parent;
            x.parent.right = x.left;
        }
        else { // this node has two children
            Node succ = successor(x);
            x.value = succ.value;
            x.key = succ.key;
            delete(succ);
        }
    }

    public Node minimum() {
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    public Node maximum() {
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    public Node successor(Node x) {
        Node curr = x;
        // If x has a right child, the successor is the node with
        // minimum key in the subtree of the right child
        if (x.right != null) {
            while (curr.left != null)
                curr = curr.left;
            return curr;
        }
        // Otherwise, the successor is the lowest ancestor of x
        // whose left child is also an ancestor of x (if no such
        // ancestor exists, the successor is NULL)
        else {
            Node y = x.parent;
            while (y != null && curr == y.right) {
                curr = y;
                y = y.parent;
            }
            return y;
        }
    }

    public Node predecessor(Node x) {
        // Symmetric to successor method
        Node curr = x;
        if (x.left != null) {
            while (curr.right != null)
                curr = curr.right;
            return curr;
        }
        else {
            Node y = x.parent;
            while (y != null && curr == y.left) {
                curr = y;
                y = y.parent;
            }
            return y;
        }
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
        print();
    }

    @Override
    public void print() {
        Node node = root;
        while (node != null) {

            // If left child is null, print the current node data. Move to
            // right child.
            if (node.left == null) {
                System.out.print(node.value + " ");
                node = node.right;
            } else {

                // Find inorder predecessor
                Node current = node.left;
                while (current.right != null && current.right != node) {
                    current = current.right;
                }

                // If the right child of inorder predecessor
                // already points to this node
                if (current.right == node) {
                    current.right = null;
                    node = node.right;
                }

                // If right child doesn't point to this node, then print
                // this node and make right child point to this node
                else {
                    System.out.print(node.value + " ");
                    current.right = node;
                    node = node.left;
                }
            }
        }
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

        // Copy builder
        private Node (Node other) {
            left = other.left;
            right = other.right;
            parent = other.parent;
            key = other.key;
            value = other.value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

}
