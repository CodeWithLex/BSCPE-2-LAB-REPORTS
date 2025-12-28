package lab.pkg5.stack.and.queue.using.arrays.and.objects;

// Task 1: Stack Using Array (LIFO)
public class Task1_StackArray {
    
    public static void main(String[] args) {
        System.out.println("=== TASK 1: STACK ===\n");
        
        // Stack variables
        int[] stack = new int[5];
        int top = -1;
        
        // Push
        stack[++top] = 10;
        stack[++top] = 20;
        stack[++top] = 30;
        System.out.println("Pushed: 10, 20, 30");
        
        // Display
        System.out.print("Stack: ");
        for (int i = 0; i <= top; i++) 
            System.out.print(stack[i] + " ");
        System.out.println();
        
        // Pop
        System.out.println("Popped: " + stack[top--]);
        System.out.println("Popped: " + stack[top--]);
        
        // Display
        System.out.print("Stack: ");
        for (int i = 0; i <= top; i++) 
            System.out.print(stack[i] + " ");
        System.out.println("\n\nLIFO: Last In, First Out");
    }
}
