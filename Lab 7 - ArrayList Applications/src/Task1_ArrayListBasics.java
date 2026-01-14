
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Task1_ArrayListBasics {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();

        // Adding elements
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);

        System.out.println("Original list: " + numbers);

        // get() example
        System.out.println("Element at index 2: " + numbers.get(2));

        // set() example (update index 1)
        numbers.set(1, 25);
        System.out.println("After set operation: " + numbers);

        // contains() example
        System.out.println("Contains 30? " + numbers.contains(30));

        // remove() example (remove element at index 0)
        numbers.remove(0);
        System.out.println("After remove operation: " + numbers);
    }
}
