
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Task1_HashMapBasics {
   public static void main(String[] args) {
HashMap<String, Double> grades = new HashMap<>();
grades.put("Alice", 89.5);
grades.put("Ben", 91.0);
grades.put("Carla", 85.5);


System.out.println(grades.get("Ben"));
System.out.println(grades.containsKey("Alice"));
grades.remove("Carla");
} 
}
