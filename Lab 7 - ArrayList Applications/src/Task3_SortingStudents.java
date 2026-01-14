
import java.util.ArrayList;
import java.util.Collections;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
class Student2 {
    int id;
    String name;
    double grade;

    Student2(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}

public class Task3_SortingStudents {
    public static void main(String[] args) {
        ArrayList<Student2> students = new ArrayList<>();

        // Add students
        students.add(new Student2(1, "Alice", 85.5));
        students.add(new Student2(2, "Bob", 90.0));
        students.add(new Student2(3, "Charlie", 78.0));
        students.add(new Student2(4, "David", 92.0));

        System.out.println("Original List:");
        for (Student2 s : students) {
            System.out.println(s);
        }

        // Sort by grade descending
        Collections.sort(students, (a, b) -> Double.compare(b.grade, a.grade));
        System.out.println("\nSorted by Grade (Descending):");
        for (Student2 s : students) {
            System.out.println(s);
        }

        // Sort by name alphabetical
        Collections.sort(students, (a, b) -> a.name.compareToIgnoreCase(b.name));
        System.out.println("\nSorted by Name (Alphabetical):");
        for (Student2 s : students) {
            System.out.println(s);
        }
    }
}
