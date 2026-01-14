
import java.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Tast1_Array_Based_Sorting {
    static void bubbleSort(int[] arr) {
        System.out.println("Before Bubble Sort: " + Arrays.toString(arr));
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("After Bubble Sort:  " + Arrays.toString(arr));
    }

    // Selection Sort
    static void selectionSort(int[] arr) {
        System.out.println("Before Selection Sort: " + Arrays.toString(arr));
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        System.out.println("After Selection Sort:  " + Arrays.toString(arr));
    }

    // Insertion Sort
    static void insertionSort(int[] arr) {
        System.out.println("Before Insertion Sort: " + Arrays.toString(arr));

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        System.out.println("After Insertion Sort:  " + Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] arr1 = {7, 3, 9, 2, 5};
        bubbleSort(arr1.clone());

        int[] arr2 = {7, 3, 9, 2, 5};
        selectionSort(arr2.clone());

        int[] arr3 = {7, 3, 9, 2, 5};
        insertionSort(arr3.clone());
    }
}
