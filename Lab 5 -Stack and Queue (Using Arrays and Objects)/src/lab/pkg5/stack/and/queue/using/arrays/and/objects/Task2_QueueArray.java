package lab.pkg5.stack.and.queue.using.arrays.and.objects;

// Task 2: Queue Using Array (FIFO)
public class Task2_QueueArray {
    
    public static void main(String[] args) {
        System.out.println("=== TASK 2: QUEUE ===\n");
        
        // Queue variables
        int[] queue = new int[5];
        int front = 0;
        int rear = -1;
        
        // Enqueue
        queue[++rear] = 10;
        queue[++rear] = 20;
        queue[++rear] = 30;
        System.out.println("Enqueued: 10, 20, 30");
        
        // Display
        System.out.print("Queue: ");
        for (int i = front; i <= rear; i++) 
            System.out.print(queue[i] + " ");
        System.out.println();
        
        // Dequeue
        System.out.println("Dequeued: " + queue[front++]);
        System.out.println("Dequeued: " + queue[front++]);
        
        // Display
        System.out.print("Queue: ");
        for (int i = front; i <= rear; i++) 
            System.out.print(queue[i] + " ");
        System.out.println("\n\nFIFO: First In, First Out");
    }
}
