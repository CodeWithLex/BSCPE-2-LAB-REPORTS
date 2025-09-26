/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package attendance.checking;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class AttendanceChecking {
public static String findBooks(String[] bookCodes, String targetCode) {
        int count = 0;
        ArrayList<Integer> positions = new ArrayList<>();
        
        for (int i = 0; i < bookCodes.length; i++) {
            if (bookCodes[i].equals(targetCode)) {
                count++;
                positions.add(i);
            }
        }
        
        if (count == 0) {
            return "Book code " + targetCode + " not found";
        } else {
            return "Book code " + targetCode + " found " + count + 
                   " times at positions: " + positions.toString();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] bookCodes = {"EMATH121", "EMATH111", "COM1", "ENGR1", "CPE111"};
        String searchCode = "EMATH121";
        String searchCode2 = "SHESH123";
        
        String result = findBooks(bookCodes, searchCode);
        String result2 = findBooks(bookCodes, searchCode2);
        System.out.println(result + "\n" + result2);
    }
    
}
