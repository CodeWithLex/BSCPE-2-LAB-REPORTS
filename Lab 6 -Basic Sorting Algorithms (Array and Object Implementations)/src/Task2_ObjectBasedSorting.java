/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Task2_ObjectBasedSorting {
    static class Student {
        String name;
        int grade;

        Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    static void bubbleSortByGrade(Student[] students) {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                if (students[j].grade > students[j + 1].grade) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    static void selectionSortByName(Student[] students) {
        for (int i = 0; i < students.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < students.length; j++) {
                if (students[j].name.compareTo(students[min].name) < 0) {
                    min = j;
                }
            }
            Student temp = students[i];
            students[i] = students[min];
            students[min] = temp;
        }
    }

    static void display(Student[] students) {
        for (Student s : students) {
            System.out.println(s.name + " - " + s.grade);
        }
    }

    public static void main(String[] args) {
        Student[] students = {
            new Student("Carla", 95),
            new Student("Anna", 90),
            new Student("Ben", 85)
        };

        System.out.println("Before Sorting:");
        display(students);

        bubbleSortByGrade(students);
        System.out.println("\nAfter Sorting by Grade:");
        display(students);

        selectionSortByName(students);
        System.out.println("\nAfter Sorting by Name:");
        display(students);
    }
}
