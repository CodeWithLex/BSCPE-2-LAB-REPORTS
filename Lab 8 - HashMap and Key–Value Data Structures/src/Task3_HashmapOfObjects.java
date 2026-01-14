
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class StudentMap {

    static class Student {
        String id, name;
        double grade;

        Student(String i, String n, double g) {
            id = i;
            name = n;
            grade = g;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
        }
    }

    public static void main(String[] args) {
        // Create a HashMap to store students with ID as key
        HashMap<String, Student> students = new HashMap<>();

        // Add a student
        students.put("2025-001", new Student("2025-001", "Alice", 89.5));
        students.put("2025-002", new Student("2025-002", "Bob", 85.0));

        // Display all students
        System.out.println("=== Original Students ===");
        for (Student s : students.values()) {
            System.out.println(s);
        }

        // Update Alice's grade safely
        if (students.containsKey("2025-001")) {
            students.get("2025-001").grade = 92.0;
        }

        // Display after update
        System.out.println("\n=== After Updating Alice's Grade ===");
        for (Student s : students.values()) {
            System.out.println(s);
        }

        // Remove a student by ID
        students.remove("2025-002");

        // Display after removal
        System.out.println("\n=== After Removing Bob ===");
        for (Student s : students.values()) {
            System.out.println(s);
        }
    }
}
