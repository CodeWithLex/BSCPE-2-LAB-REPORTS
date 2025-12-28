/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class PerformanceComparison {
     public static void main(String[] args) {

        int[] array = {50, 30, 70, 20, 40, 60, 80};
        int target = 60;

        int comparisons = 0;
        boolean found = false;

        // Linear search in array
        for (int i = 0; i < array.length; i++) {
            comparisons++;
            if (array[i] == target) {
                found = true;
                break;
            }
        }

        System.out.println("Array Search:");
        System.out.println("Found: " + found);
        System.out.println("Comparisons: " + comparisons);

        // BST search comparison
        BinarySearchTree bst = new BinarySearchTree();
        for (int v : array) bst.insert(v);

        System.out.println("\nBST Search:");
        System.out.println("Found: " + bst.search(target));
        System.out.println("Comparisons: ~log(n) ≈ " + (int)(Math.log(array.length) / Math.log(2)));
    }
}
