package virtualPlans.AccProject.service;

import java.util.HashSet;
import java.util.Set;

public class AVLTree {

    public Node root;

    public AVLTree() {
        this.root = null;
    }

    public void insert(String word, String fileName) {
        root = insert(root, word, fileName);
    }

    private Node insert(Node node, String word, String fileName) {
        if (node == null) {
            return new Node(word, fileName);
        }

        if (word.compareTo(node.word) < 0) {
            node.left = insert(node.left, word, fileName);
        } else if (word.compareTo(node.word) > 0) {
            node.right = insert(node.right, word, fileName);
        } else {
            node.frequency++;
            node.files.add(fileName);
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && word.compareTo(node.left.word) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && word.compareTo(node.right.word) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    public static class Node {
        String word;
        int frequency;
        Set<String> files;
        Node left, right;
        int height;

        public Node(String word, String fileName) {
            this.word = word;
            this.frequency = 1;
            this.files = new HashSet<>();
            this.files.add(fileName);
            this.left = this.right = null;
            this.height = 1;
        }
    }
}
