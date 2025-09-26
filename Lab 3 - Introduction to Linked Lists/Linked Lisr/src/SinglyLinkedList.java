/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class Node {
    int data;
    Node next;
    
    // Constructor
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}
public class SinglyLinkedList {
    private Node head;
    
    // Constructor
    public SinglyLinkedList() {
        this.head = null;
    }
    
    // Insert at head - adds new node at the beginning
    public void insertAtHead(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        System.out.println("Inserted " + value + " at head");
    }
    
    // Insert at tail - adds new node at the end
    public void insertAtTail(int value) {
        Node newNode = new Node(value);
        
        // If list is empty, make new node the head
        if (head == null) {
            head = newNode;
            System.out.println("Inserted " + value + " as first element");
            return;
        }
        
        // Traverse to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        
        // Link the last node to new node
        current.next = newNode;
        System.out.println("Inserted " + value + " at tail");
    }
    
    // Delete by value - removes first occurrence of the value
    public void deleteByValue(int value) {
        // Check if list is empty
        if (head == null) {
            System.out.println("List is empty. Cannot delete " + value);
            return;
        }
        
        // If head node contains the value to delete
        if (head.data == value) {
            head = head.next;
            System.out.println("Deleted " + value + " from head");
            return;
        }
        
        // Search for the node to delete
        Node current = head;
        while (current.next != null && current.next.data != value) {
            current = current.next;
        }
        
        // If value found, delete it
        if (current.next != null) {
            current.next = current.next.next;
            System.out.println("Deleted " + value + " from list");
        } else {
            System.out.println(value + " not found in the list");
        }
    }
    
    // Traverse forward - prints all elements from head to tail
    public void traverseForward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        System.out.print("Singly Linked List: ");
        Node current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println(" -> NULL");
    }
}
