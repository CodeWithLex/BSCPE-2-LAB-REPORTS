
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Task2_HashMapInteraction {
    public static void main(String[] args) {
HashMap<String, Double> grades = new HashMap<>();
grades.put("Alice", 89.5);
grades.put("Ben", 91.0);


for (Map.Entry<String, Double> entry : grades.entrySet())
System.out.println(entry.getKey() + ": " + entry.getValue());
}
}
