/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package student.id.lookup;
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
public class StudentIDLookup {

    public static String findStudentID(String[] studentIDs, String targetID) {
        for (int i = 0; i < studentIDs.length; i++) {
            if (studentIDs[i].equals(targetID)) {
                return "Student ID " + targetID + " found at position " + i;
            }
        }
        return "Student ID " + targetID + " not found in the system";
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] studentIDs = {"2021-001", "2021-045", "2021-078", "2021-112"};
        String searchID = "2021-078"; //NAA SA LIST
        String searchID2 = "2021-07"; //MALI
        String result = findStudentID(studentIDs, searchID);
        String result2 = findStudentID(studentIDs, searchID2);
        System.out.println(result + "\n" + result2);
        
    }

}
