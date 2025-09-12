/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arrayslabactivity1;

import static arrayslabactivity1.ArraysLabActivity1.ArrayInsert.insertAtPosition;
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
public class ArraysLabActivity1 {

    /**
     * @param args the command line arguments
     */
    public class ArrayInsert {

        public static int[] insertAtPosition(int[] arr, int position, int value) {
            int[] newArr = new int[arr.length + 1];

            for (int i = 0; i < position; i++) {
                newArr[i] = arr[i];
            }
            newArr[position] = value;
            for (int i = position; i < arr.length; i++) {
                newArr[i + 1] = arr[i];
            }
            return newArr;
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40};
        int[] updated = insertAtPosition(arr, 1, 77); //here mag change sa values of the insertAtPosition method
        for (int x : updated) {
            System.out.print(x + " ");
        }

    }

}
