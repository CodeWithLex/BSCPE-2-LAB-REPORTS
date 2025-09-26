/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*

 Course: Data Structures and Algorithms in Java
 Lab No. 2: Linear Search – Applications in Real Life
 Student Name: Lex Edrick Asherjesse C. Matondo
 Student ID: 0-0207
 Date Submitted: September 26, 2025

*/
/**
 *
 * @author User
 */
public class LinkedListTester {

    public static void main(String[] args) {
        System.out.println("TESTING SINGLY LINKED LIST");
        SinglyLinkedList sll = new SinglyLinkedList();

        System.out.println("\n1. Testing insertions:");
        sll.insertAtHead(10);
        sll.insertAtTail(20);
        sll.insertAtHead(5);
        sll.insertAtTail(30);
        sll.traverseForward();

        System.out.println("\n2. Testing deletions:");
        sll.deleteByValue(20);
        sll.traverseForward();
        sll.deleteByValue(5);
        sll.traverseForward();
        sll.deleteByValue(99);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("TESTING DOUBLY LINKED LIST");
        DoublyLinkedList dll = new DoublyLinkedList();

        System.out.println("\n1. Testing insertions:");
        dll.insertAtHead(15);
        dll.insertAtTail(25);
        dll.insertAtHead(10);
        dll.insertAtTail(35);
        dll.insertAtHead(5);

        System.out.println("\n2. Testing traversals:");
        dll.traverseForward();
        dll.traverseBackward();

        System.out.println("\n3. Testing deletions:");
        dll.deleteByValue(15);
        dll.traverseForward();
        dll.traverseBackward();

        dll.deleteByValue(5);  // Delete head
        dll.traverseForward();
        dll.traverseBackward();

        dll.deleteByValue(35); // Delete tail
        dll.traverseForward();
        dll.traverseBackward();

        System.out.println("\nTESTING COMPLETE");
    }
}

