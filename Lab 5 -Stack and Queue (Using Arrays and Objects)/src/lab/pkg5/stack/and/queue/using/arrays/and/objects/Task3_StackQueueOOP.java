package lab.pkg5.stack.and.queue.using.arrays.and.objects;

// Task 3: Stack and Queue Using Objects

// Stack Class
class Stack {
    int[] stack = new int[5];
    int top = -1;
    
    void push(int value) {
        stack[++top] = value;
    }
    
    void pop() {
        System.out.println("Popped: " + stack[top--]);
    }
    
    void display() {
        System.out.print("Stack: ");
        for (int i = 0; i <= top; i++) 
            System.out.print(stack[i] + " ");
        System.out.println();
    }
}

// Queue Class
class Queue {
    int[] queue = new int[5];
    int front = 0, rear = -1;
    
    void enqueue(int value) {
        queue[++rear] = value;
    }
    
    void dequeue() {
        System.out.println("Dequeued: " + queue[front++]);
    }
    
    void display() {
        System.out.print("Queue: ");
        for (int i = front; i <= rear; i++) 
            System.out.print(queue[i] + " ");
        System.out.println();
    }
}

// Main
public class Task3_StackQueueOOP {
    public static void main(String[] args) {
        System.out.println("=== TASK 3: USING OBJECTS ===\n");
        
        // Stack
        Stack s = new Stack();
        s.push(10);
        s.push(20);
        s.push(30);
        s.display();
        s.pop();
        s.display();
        
        System.out.println();
        
        // Queue
        Queue q = new Queue();
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        q.display();
        q.dequeue();
        q.display();
    }
}
