
import java.util.HashMap;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Task4_ProductInventoryMiniApp {

    // HashMap to store product inventory: Key = Product Code, Value = Quantity
    static HashMap<String, Integer> inventory = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== Product Inventory Mini App ===");
            System.out.println("1. Add New Product");
            System.out.println("2. Update Stock Level");
            System.out.println("3. Display All Products");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateStock();
                case 3 -> displayProducts();
                case 0 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    // Add a new product
    public static void addProduct() {
        System.out.print("Enter Product Code: ");
        String code = sc.nextLine().toUpperCase();

        if (inventory.containsKey(code)) {
            System.out.println("Product already exists! Use Update Stock option.");
        } else {
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine(); // consume newline
            inventory.put(code, qty);
            System.out.println("Product added successfully.");
        }
    }

    // Update stock level for an existing product
    public static void updateStock() {
        System.out.print("Enter Product Code to Update: ");
        String code = sc.nextLine().toUpperCase();

        if (inventory.containsKey(code)) {
            System.out.print("Enter additional quantity to add: ");
            int qty = sc.nextInt();
            sc.nextLine(); // consume newline
            inventory.put(code, inventory.get(code) + qty);
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Product not found! Please add it first.");
        }
    }

    // Display all products
    public static void displayProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            System.out.println("\n--- Product Inventory ---");
            for (String code : inventory.keySet()) {
                System.out.println("Product Code: " + code + " | Quantity: " + inventory.get(code));
            }
        }
    }
}
