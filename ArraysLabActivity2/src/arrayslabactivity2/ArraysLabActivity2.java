/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arrayslabactivity2;

import static arrayslabactivity2.ArraysLabActivity2.ArrayDelete.deleteAtPosition;
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
public class ArraysLabActivity2 {

    /**
     * @param args the command line arguments
     */
    public class ArrayDelete {

        public static int[] deleteAtPosition(int[] arr, int position) {

            int[] newArr = new int[arr.length - 1];
            for (int i = 0; i < position; i++) {
                newArr[i] = arr[i];
            }
            for (int i = position; i < newArr.length; i++) {
                newArr[i] = arr[i + 1];
            }
            return newArr;
        }
    }

    public static void main(String[] args) {

        int[] arr = {10, 20, 30, 40, 50};
        int[] updated = deleteAtPosition(arr, 2); //the element of the array in 2 will be deleted 
        for (int x : updated) {
            System.out.print(x + " ");

        }
    }

}
