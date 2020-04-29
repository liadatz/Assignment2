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
        internalInsert(z, true);
    }

    private void internalInsert(BacktrackingBST.Node z, boolean shouldClearRedoStack) {
        if (shouldClearRedoStack) {
            redoStack.clear();
        }
        stack.push(z);
        stack.push("insert");
        Node curr = root;
        Node prev = null;
        while (curr != null) { // Find designated place for Node z
            prev = curr;
            if (curr.key > z.key)
                curr = curr.left;
            else
                curr = curr.right;
        }
        if (prev == null) // Meaning loop never initialize so tree must be empty
            root = z;
        else if (prev.key > z.key) { // Node prev will be parent of Node z, check if he should be left or right child
            prev.left = z;
            z.parent = prev;
        } else {
            prev.right = z;
            z.parent = prev;
        }
    }

    public void delete(Node x) {
        internalDelete(x, true);
    }

    // String meaning:
    // r - stand for root, n - not root
    // 00 - no children, 01 - 1 right child, 10 - 1 left child, 11 - 2 children
    // Update x's children pointers in order to delete Node x
    // Check if x was left or right child of parent (if exists) and update parent pointer to x's child or successor
    private void internalDelete (Node x,boolean shouldClearRedoStack) {
        if (shouldClearRedoStack) {
            redoStack.clear();
        }
        stack.push(x);
        if (x.left == null & x.right == null) { // 0 children
            if (x == root) {
                root = null;
                stack.push("r00");
            } else {
                if (x.parent.right == x) x.parent.right = null;
                else x.parent.left = null;
                stack.push("n00");
            }
        } else if (x.left == null) { // 1 right child
            if (x == root) {
                root = x.right;
                x.right.parent = null;
                stack.push("r01");
            } else {
                x.right.parent = x.parent;
                if (x.parent.right == x) x.parent.right = x.right;
                else x.parent.left = x.right;
                stack.push("n01");
            }
        } else if (x.right == null) { // 1 left child
            if (x == root) {
                root = x.left;
                x.left.parent = null;
                stack.push("r10");
            } else {
                x.left.parent = x.parent;
                if (x.parent.right == x) x.parent.right = x.left;
                else x.parent.left = x.left;
                stack.push("n10");
            }
        } else { // This node has two children
            // A copy of the successor was made in order to prevent pointer's changes in Stack when deleting successor
            Node successor = successor(x);
            Node successorCopy = new Node(successor);
            if (x == root) {
                root = successorCopy;
                stack.push("r11");
            } else {
                stack.push("n11");
                if (x.parent.right == x) x.parent.right = successorCopy;
                else x.parent.left = successorCopy;
            }
            x.left.parent = successorCopy;
            x.right.parent = successorCopy;
            successorCopy.right = x.right;
            successorCopy.left = x.left;
            successorCopy.parent = x.parent;
            delete(successor);
        }
    }

    // Minimum node located in the far left Node of the tree
    public Node minimum() {
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    // Maximum node located in the far right Node of the tree
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
            curr = x.right;
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
            curr = x.left;
            while (curr.right != null)
                curr = curr.right;
            return curr;
        } else {
            Node y = x.parent;
            while (y != null && curr == y.left) {
                curr = y;
                y = y.parent;
            }
            return y;
        }
    }

    // Each action and node backtrack pushed in redoStack
    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            String action = (String) stack.pop();
            Node node = (Node) stack.pop();
            if (action.equals("insert")) { // Undo insert ~ delete the last node (node with 0 children)
                if (node == root) {
                    root = null;
                } else {
                    if (node.parent.right == node) node.parent.right = null;
                    else node.parent.left = null;
                }
                redoStack.push(node);
                redoStack.push("insert");
            } else { // Undo delete
                // Restore node to his original location by update children's pointers back to node (if exists)
                // Check if node originally was left or right child of parent (if exists)
                // and update node's parent pointer back to node
                // if node originally was root, update root pointer back to node
                redoStack.push(node);
                redoStack.push("delete");
                if (action.charAt(1) == '0' & action.charAt(2) == '0') { // 0 children
                    if (action.charAt(0) == 'r') root = node;
                    else {
                        if (node.key >= node.parent.key) node.parent.right = node;
                        else node.parent.left = node;
                    }
                } else if (action.equals("n10")) { // Node wasn't root with 1 left child
                    node.left.parent = node;
                    if (node.parent.left == node.left) node.parent.left = node;
                    else node.parent.right = node;
                } else if (action.equals("r10")) { // Node was root with 1 left child
                    node.left.parent = node;
                    root = node;
                } else if (action.equals("n01")) { // Node wasn't root with 1 right child
                    node.right.parent = node;
                    if (node.parent.left == node.right) node.parent.left = node;
                    else node.parent.right = node;
                } else if (action.equals("r01")) { // node was root with 1 right child
                    node.right.parent = node;
                    root = node;
                }
                // If another action is in stack check if its Deletion of a node with 2 children, if not put action back in stack
                // When deleting node with 2 children, 2 deletions occur- one for the Node with 2 children (A.K.A. x in Delete method)
                // And another for his successor.
                if (!stack.isEmpty()) {
                    String action2 = (String) stack.pop();
                    if (action2.equals("n11") | action2.equals("r11")) {
                        Node node2 = (Node) stack.pop();
                        node2.left.parent = node2;
                        node2.right.parent = node2;
                        redoStack.push(node2);
                        redoStack.push("delete");
                        if (action2.charAt(0) == 'r') root = node2;
                        else {
                            if (node2.key > node2.parent.key) node2.parent.right = node2;
                            else node2.parent.left = node2;
                        }
                    } else stack.push(action2);
                }
            }
            System.out.println("backtracking performed");
        }
    }

    // In order to redo each action that backtracked use action method but without clearing RedoStack
    @Override
    public void retrack() {
        if (!redoStack.isEmpty()) {
            String action = (String) redoStack.pop();
            Node node = (Node) redoStack.pop();
            if (action.equals("insert")) {
                internalInsert(node, false);
            } else {
                internalDelete(node, false);
            }
        }
    }

    public void printPreOrder() {
        print();
    }

    @Override
    public void print() {
        Node node = root;
        while (node != null) {
            // If left child is null, print the current node data and Move to right child
            if (node.left == null) {
                System.out.print(node.key + " ");
                node = node.right;
            } else {
                // Find inorder predecessor
                Node current = node.left;
                while (current.right != null && current.right != node) {
                    current = current.right;
                }
                // If the right child of inorder predecessor already points to this node
                if (current.right == node) {
                    current.right = null;
                    node = node.right;
                }
                // If right child doesn't point to this node, then print this node and make right child point to this node
                else {
                    System.out.print(node.key + " ");
                    current.right = node;
                    node = node.left;
                }
            }
        }
    }

    // TODO remove
    public void treeFormPrint(){
        if (root != null) treeFormPrint(root, "");
        else System.out.println("Empty tree");
    }

    // TODO remove
    private void treeFormPrint(Node node, String acc){
        String signSpace = acc + "            ";
        if (node.right != null) {
            treeFormPrint(node.right, acc+"               ");
            if (node.right.parent == node)
                System.out.println(signSpace + "/");
            else System.out.println(signSpace + "$");
        }
        System.out.println(acc + "| key: " + node.key);
        System.out.println(acc + "| par: " + node.parent);
        if (node.left != null) {
            if (node.left.parent == node)
                System.out.println(signSpace + "\\");
            else System.out.println(signSpace + "$");
            treeFormPrint(node.left, acc+"               ");
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

        // Copy builder for Deletion node with 2 children
        private Node(Node other) {
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

        // TODO remove
        public String toString() {
            return key + "";
        }
    }
}
