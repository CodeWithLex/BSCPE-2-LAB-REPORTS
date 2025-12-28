/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class BinarySearchTreeMain {
     public static void main(String[] args) {

        BinarySearchTree bst = new BinarySearchTree();

        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int v : values) {
            bst.insert(v);
        }

        System.out.print("Inorder Traversal (Sorted): ");
        bst.inorder(bst.root);

        System.out.println("\nSearch 60: " + bst.search(60));
        System.out.println("Search 25: " + bst.search(25));
    }
}
