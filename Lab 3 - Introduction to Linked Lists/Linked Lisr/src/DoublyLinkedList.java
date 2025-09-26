/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class DoublyNode {

    int data;
    DoublyNode next;
    DoublyNode prev;

    public DoublyNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class DoublyLinkedList {

    private DoublyNode head;
    private DoublyNode tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insertAtHead(int value) {
        DoublyNode newNode = new DoublyNode(value);

        if (head == null) {

            head = tail = newNode;
        } else {

            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        System.out.println("Inserted " + value + " at head");
    }

    public void insertAtTail(int value) {
        DoublyNode newNode = new DoublyNode(value);

        if (tail == null) {

            head = tail = newNode;
        } else {

            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        System.out.println("Inserted " + value + " at tail");
    }

    public void deleteByValue(int value) {
        if (head == null) {
            System.out.println("List is empty. Cannot delete " + value);
            return;
        }

        DoublyNode current = head;

        while (current != null && current.data != value) {
            current = current.next;
        }

        if (current == null) {
            System.out.println(value + " not found in the list");
            return;
        }

        if (current == head && current == tail) {
            head = tail = null;
        } else if (current == head) {
            head = head.next;
            head.prev = null;
        } else if (current == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        System.out.println("Deleted " + value + " from doubly linked list");
    }

    public void traverseForward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        System.out.print("Forward: ");
        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println(" -> NULL");
    }

    public void traverseBackward() {
        if (tail == null) {
            System.out.println("List is empty");
            return;
        }

        System.out.print("Backward: ");
        DoublyNode current = tail;
        while (current != null) {
            System.out.print(current.data);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        System.out.println(" -> NULL");
    }
}
