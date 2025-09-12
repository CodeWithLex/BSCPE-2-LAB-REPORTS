/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arrayslabactivity3;
/*

 Course: Data Structures and Algorithms in Java
 Lab No. 1: Arrays & Complexity Basics
 Student Name: Lex Edrick Asherjesse C. Matondo
 Student ID: 0-0207
 Date Submitted: September 12, 2025

*/

/**
 *
 * @author User
 */
public class ArraysLabActivity3 {

    /**
     * @param args the command line arguments
     */
    public static void findMaxMin(int[] arr) {
       
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);
    }

    public static void main(String[] args) {
        int[] arr = {12, 45, 23, 50, 78};
        findMaxMin(arr);

    }

}
