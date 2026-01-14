
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class Student {
    int id;
    String name;
    double grade;

    Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}

public class Task2_StudentArrayList {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();

        // Add students
        students.add(new Student(1, "Alice", 85.5));
        students.add(new Student(2, "Bob", 90.0));
        students.add(new Student(3, "Charlie", 78.0));

        System.out.println("=== Original List ===");
        displayStudents(students);

        // Update a student's grade using set()
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id == 2) { // Update Bob's grade
                Student updated = new Student(students.get(i).id, students.get(i).name, 95.0);
                students.set(i, updated);
                break;
            }
        }

        System.out.println("\n=== After Updating Bob's Grade ===");
        displayStudents(students);

        // Remove a student by ID
        students.removeIf(s -> s.id == 3); // Remove Charlie
        System.out.println("\n=== After Removing Student with ID 3 ===");
        displayStudents(students);

        // Remove a student by Name
        students.removeIf(s -> s.name.equalsIgnoreCase("Alice"));
        System.out.println("\n=== After Removing Student Named Alice ===");
        displayStudents(students);
    }

    // Helper method to display students
    private static void displayStudents(ArrayList<Student> students) {
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
