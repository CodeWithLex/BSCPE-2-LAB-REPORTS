/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class BinarySearchTree {
    TreeNode root;

    void insert(int value) {
        root = insertRecursive(root, value);
    }
  TreeNode insertRecursive(TreeNode node, int value) {
        if (node == null) return new TreeNode(value);

        if (value < node.data)
            node.left = insertRecursive(node.left, value);
        else if (value > node.data)
            node.right = insertRecursive(node.right, value);

        return node;
    }

    boolean search(int value) {
        return searchRecursive(root, value);
    }

    boolean searchRecursive(TreeNode node, int value) {
        if (node == null) return false;
        if (node.data == value) return true;

        return value < node.data
                ? searchRecursive(node.left, value)
                : searchRecursive(node.right, value);
    }

    // Inorder traversal prints sorted values
    void inorder(TreeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }
}