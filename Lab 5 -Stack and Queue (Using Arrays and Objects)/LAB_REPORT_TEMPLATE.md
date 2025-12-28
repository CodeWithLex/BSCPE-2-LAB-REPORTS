# Lab 5: Stack and Queue (Using Arrays and Objects)

**Name:** __________________________  
**Section:** ________________________  
**Date Submitted:** ________________  

---

## 1. Objectives

By completing this lab, I have:
- ✅ Understood the LIFO (Last In First Out) behavior of stacks
- ✅ Understood the FIFO (First In First Out) behavior of queues
- ✅ Implemented stack data structure using arrays
- ✅ Implemented queue data structure using arrays
- ✅ Implemented both using Object-Oriented Programming principles
- ✅ Performed push, pop, enqueue, and dequeue operations
- ✅ Applied stack and queue concepts to solve practical problems
- ✅ Analyzed time complexity of operations

---

## 2. Discussion (Concept Summary)

### Stack (LIFO - Last In First Out)

A **Stack** is a linear data structure where elements are added and removed from the same end (the "top"). The last element added is the first one to be removed.

**Key Operations:**
- **push(x):** Insert element at the top - O(1)
- **pop():** Remove element from the top - O(1)
- **peek():** View top element without removing - O(1)
- **isEmpty():** Check if stack is empty - O(1)
- **isFull():** Check if stack is full (array implementation) - O(1)

**Real-world Applications:**
- Function call stack in programs
- Undo/Redo feature in text editors
- Browser navigation (Back button)
- Parentheses matching in compilers
- Expression evaluation (infix to postfix)

**Example:**
```
Stack: [10, 20, 30, 40, 50]  ← top
push(60) → [10, 20, 30, 40, 50, 60]  ← top
pop() → returns 60, stack becomes [10, 20, 30, 40, 50]  ← top
peek() → returns 50 (without removing)
```

---

### Queue (FIFO - First In First Out)

A **Queue** is a linear data structure where elements are added at one end (rear) and removed from the other end (front). The first element added is the first one to be removed.

**Key Operations:**
- **enqueue(x):** Insert element at the rear - O(1)
- **dequeue():** Remove element from the front - O(1)
- **peek():** View front element without removing - O(1)
- **isEmpty():** Check if queue is empty - O(1)
- **isFull():** Check if queue is full (array implementation) - O(1)

**Real-world Applications:**
- CPU scheduling
- Printer job queue
- Call center waiting lines
- Message buffering in communication
- Breadth-First Search (BFS) in graphs

**Example:**
```
Queue: front → [10, 20, 30, 40, 50] ← rear
enqueue(60) → front → [10, 20, 30, 40, 50, 60] ← rear
dequeue() → returns 10, queue becomes front → [20, 30, 40, 50, 60] ← rear
peek() → returns 20 (without removing)
```

---

### Array vs Object-Oriented Implementation

| Feature | Array Implementation | OOP Implementation |
|---------|---------------------|-------------------|
| **Structure** | Uses primitive arrays | Uses classes with encapsulation |
| **Flexibility** | Fixed size | More modular, maintainable |
| **Readability** | Procedural approach | Structured, organized |
| **Reusability** | Limited - code duplication | High - can be reused across programs |
| **Encapsulation** | No data hiding | Data is protected (private) |
| **Maintainability** | Harder to modify | Easier to extend and modify |

**Why OOP is Better for Production Code:**
- Encapsulation protects data integrity
- Abstraction hides complex implementation details
- Reusability reduces code duplication
- Modularity makes code easier to test and debug
- Follows industry best practices

---

## 3. Pseudocode / Implementation

### Task 1: Stack Using Array - Pseudocode

```
CLASS StackArray:
    DECLARE maxSize, top, stackArray[]
    
    CONSTRUCTOR(size):
        maxSize = size
        stackArray = new array[maxSize]
        top = -1
    
    METHOD push(value):
        IF top == maxSize - 1 THEN
            PRINT "Stack Overflow"
        ELSE
            top = top + 1
            stackArray[top] = value
        END IF
    
    METHOD pop():
        IF top == -1 THEN
            PRINT "Stack Underflow"
            RETURN -1
        ELSE
            value = stackArray[top]
            top = top - 1
            RETURN value
        END IF
    
    METHOD peek():
        IF top == -1 THEN
            RETURN -1
        ELSE
            RETURN stackArray[top]
        END IF
    
    METHOD isEmpty():
        RETURN top == -1
    
    METHOD isFull():
        RETURN top == maxSize - 1
END CLASS
```

### Task 2: Queue Using Array - Pseudocode

```
CLASS QueueArray:
    DECLARE maxSize, front, rear, queueArray[], currentSize
    
    CONSTRUCTOR(size):
        maxSize = size
        queueArray = new array[maxSize]
        front = 0
        rear = -1
        currentSize = 0
    
    METHOD enqueue(value):
        IF currentSize == maxSize THEN
            PRINT "Queue Full"
        ELSE
            rear = rear + 1
            queueArray[rear] = value
            currentSize = currentSize + 1
        END IF
    
    METHOD dequeue():
        IF currentSize == 0 THEN
            PRINT "Queue Empty"
            RETURN -1
        ELSE
            value = queueArray[front]
            front = front + 1
            currentSize = currentSize - 1
            
            IF currentSize == 0 THEN
                front = 0
                rear = -1
            END IF
            
            RETURN value
        END IF
    
    METHOD peek():
        IF currentSize == 0 THEN
            RETURN -1
        ELSE
            RETURN queueArray[front]
        END IF
    
    METHOD isEmpty():
        RETURN currentSize == 0
    
    METHOD isFull():
        RETURN currentSize == maxSize
END CLASS
```

### Task 3: Stack and Queue Using OOP

See `Task3_StackQueueOOP.java` for full implementation with:
- Separate Stack and Queue classes
- Encapsulated data members (private)
- Public methods for operations
- Demonstration of both LIFO and FIFO behavior
- Side-by-side comparison

**Key OOP Concepts Applied:**
1. **Encapsulation:** Data members are private, accessed via public methods
2. **Abstraction:** Users don't need to know internal implementation
3. **Modularity:** Each class has a single, well-defined purpose
4. **Reusability:** Classes can be imported and used in other programs

---

## 4. Program Output (Screenshots and Logs)

### Task 1: Stack Using Array Output

```
=== TASK 1: STACK USING ARRAY ===

Is stack empty? true

--- Pushing Elements ---
Pushed: 10
Pushed: 20
Pushed: 30
Pushed: 40
Pushed: 50

Stack elements (top to bottom): 50 40 30 20 10 
Stack size: 5

--- Testing Stack Overflow ---
Stack Overflow! Cannot push 60

--- Peek Operation ---
Top element: 50

--- Popping Elements ---
Popped: 50
Popped: 40

Stack elements (top to bottom): 30 20 10 
Stack size: 3

Popped: 30
Popped: 20
Popped: 10

--- Testing Stack Underflow ---
Stack Underflow! Stack is empty.

Is stack empty? true
```

*[INSERT SCREENSHOT HERE]*

---

### Task 2: Queue Using Array Output

```
=== TASK 2: QUEUE USING ARRAY ===

Is queue empty? true

--- Enqueuing Elements ---
Enqueued: 10
Enqueued: 20
Enqueued: 30
Enqueued: 40
Enqueued: 50

Queue elements (front to rear): 10 20 30 40 50 
Queue size: 5

--- Testing Queue Full ---
Queue Full! Cannot enqueue 60

--- Peek Operation ---
Front element: 10

--- Dequeuing Elements ---
Dequeued: 10
Dequeued: 20

Queue elements (front to rear): 30 40 50 
Queue size: 3

--- Enqueuing After Dequeue ---
Enqueued: 60
Enqueued: 70
Queue elements (front to rear): 30 40 50 60 70
```

*[INSERT SCREENSHOT HERE]*

---

### Task 3: Stack and Queue Using OOP Output

```
=== TASK 3: STACK AND QUEUE USING OBJECTS (OOP) ===

========== STACK OPERATIONS ==========

--- Pushing to Stack ---
Stack: Pushed 5
Stack: Pushed 10
Stack: Pushed 15
Stack: Pushed 20

Stack (top to bottom): 20 15 10 5 
Stack size: 4
Top element: 20

--- Popping from Stack ---
Stack: Popped 20
Stack: Popped 15

Stack (top to bottom): 10 5 

========== QUEUE OPERATIONS ==========

--- Enqueuing to Queue ---
Queue: Enqueued 100
Queue: Enqueued 200
Queue: Enqueued 300
Queue: Enqueued 400

Queue (front to rear): 100 200 300 400 
Queue size: 4
Front element: 100

--- Dequeuing from Queue ---
Queue: Dequeued 100
Queue: Dequeued 200

Queue (front to rear): 300 400
```

*[INSERT SCREENSHOT HERE]*

---

### Problem 1: Undo/Redo Simulation

```
=== PROBLEM 1: UNDO/REDO SIMULATION ===

=== AUTOMATED DEMO ===

Starting automated demo...

1. Typing 'Hello '
   Text: "Hello "

2. Typing 'World'
   Text: "Hello World"

3. Typing '!'
   Text: "Hello World!"

4. Undo last action
   Text: "Hello World"

5. Undo again
   Text: "Hello "

6. Redo last undo
   Text: "Hello World"

7. Redo again
   Text: "Hello World!"

Final text: "Hello World!"

✓ Stack perfectly handles UNDO/REDO with LIFO behavior!
```

*[INSERT SCREENSHOT HERE]*

---

### Problem 2: Customer Queue Management

```
=== PROBLEM 2: CUSTOMER QUEUE MANAGEMENT SYSTEM ===

=== AUTOMATED DEMO: SERVICE COUNTER ===

--- Customers Arriving ---

✓ Customer added: Ticket#1:Alice
✓ Customer added: Ticket#2:Bob
✓ Customer added: Ticket#3:Charlie
✓ Customer added: Ticket#4:Diana
✓ Customer added: Ticket#5:Eve

--- Current Queue ---
Customers waiting: 5
Queue (Front → Rear): Ticket#1:Alice → Ticket#2:Bob → Ticket#3:Charlie → Ticket#4:Diana → Ticket#5:Eve 
Next to be served: Ticket#1:Alice

--- Serving Customers ---

✓ Served: Ticket#1:Alice
✓ Served: Ticket#2:Bob
✓ Served: Ticket#3:Charlie

✓ All customers served! Queue demonstrates perfect FIFO behavior.
```

*[INSERT SCREENSHOT HERE]*

---

### Problem 3: Palindrome Checker

```
=== PROBLEM 3: PALINDROME CHECKER ===
Using Stack (LIFO) and Queue (FIFO)

Testing: "racecar"

Processing string: "racecar"
Adding characters: r a c e c a r 

Comparing characters:
Position | From Stack (reversed) | From Queue (forward) | Match?
---------|----------------------|----------------------|-------
   1      |         r            |         r            |  ✓    
   2      |         a            |         a            |  ✓    
   3      |         c            |         c            |  ✓    
   4      |         e            |         e            |  ✓    
   5      |         c            |         c            |  ✓    
   6      |         a            |         a            |  ✓    
   7      |         r            |         r            |  ✓    

Result: "racecar" is a PALINDROME! ✓
```

*[INSERT SCREENSHOT HERE]*

---

## 5. Complexity Analysis Table

| Operation | Stack | Queue | Time Complexity | Space Complexity | Explanation |
|-----------|-------|-------|-----------------|------------------|-------------|
| **Push/Enqueue** | O(1) | O(1) | Constant time | O(1) | Direct array access at index |
| **Pop/Dequeue** | O(1) | O(1) | Constant time | O(1) | Direct array access at index |
| **Peek** | O(1) | O(1) | Constant time | O(1) | Direct array access at index |
| **isEmpty** | O(1) | O(1) | Constant time | O(1) | Simple comparison operation |
| **isFull** | O(1) | O(1) | Constant time | O(1) | Simple comparison operation |
| **Display** | O(n) | O(n) | Linear time | O(1) | Must traverse all n elements |
| **Search** | O(n) | O(n) | Linear time | O(1) | May need to check all elements |
| **Overall Space** | O(n) | O(n) | - | O(n) | Array of size n |

### Key Observations:

1. **Main operations are O(1)** - This makes stacks and queues very efficient for their intended use cases
2. **Array-based implementation** - Fixed size means no dynamic memory allocation overhead
3. **Trade-off** - Fixed size can lead to overflow, but provides predictable performance
4. **Linear queue limitation** - Wasted space after dequeuing (circular queue solves this)

---

## 6. Critical Thinking Answers

### Challenge Question:
**Why is a circular queue more efficient than a linear queue when using arrays?**

**Answer:**

In a **linear queue**, when elements are dequeued from the front, those positions become permanently unusable in the current queue state. This creates several problems:

**Problems with Linear Queue:**
1. **Wasted Memory Space:**
   - Front positions remain empty after dequeue operations
   - These spaces cannot be reused until the entire queue is reset
   - Example: After dequeuing 3 elements from a 10-element queue, 3 spaces are wasted

2. **False "Queue Full" Condition:**
   - The rear pointer reaches the end of the array
   - Queue appears "full" even though empty spaces exist at the front
   - Example: Array[10] with elements at indices 7,8,9 appears full, but indices 0-6 are empty

3. **Inefficient Memory Utilization:**
   - Actual capacity decreases with each dequeue operation
   - Must shift all elements or reset the queue to reuse space
   - Shifting is O(n) operation, defeating the purpose of O(1) operations

**Circular Queue Solution:**

A **circular queue** treats the array as circular, wrapping around when reaching the end:

```
Linear Queue:           Circular Queue:
[_][_][_][40][50]      [60][70][_][40][50]
       ↑   ↑                ↑       ↑   ↑
     front rear           rear    front
(Spaces wasted)         (Spaces reused!)
```

**Advantages:**
1. **Space Reuse:** `rear = (rear + 1) % maxSize` allows wrapping to position 0
2. **True Capacity:** All array positions can be utilized
3. **No Shifting:** O(1) operations maintained
4. **Efficient:** No wasted memory, full capacity always available

**Mathematical Formula:**
```
Next position = (current_position + 1) % maxSize

Example with maxSize = 5:
position 4 → (4 + 1) % 5 = 0 (wraps around)
position 2 → (2 + 1) % 5 = 3 (normal increment)
```

**Conclusion:**  
Circular queues are more efficient because they eliminate wasted space and maximize array utilization by allowing the rear pointer to wrap around and reuse positions freed by dequeue operations, all while maintaining O(1) time complexity for operations.

---

## 7. Reflection (Learning Insights)

### What did you learn about Stack and Queue behavior?

**Stack (LIFO):**
- I learned that stacks naturally reverse order - the last element added is the first to come out
- Stacks are perfect for scenarios requiring backtracking or reversal (undo/redo, browser history)
- The LIFO principle makes stacks ideal for nested or recursive operations
- Understanding stacks helped me visualize how function calls work in programming

**Queue (FIFO):**
- I learned that queues maintain fairness - first come, first served
- Queues model real-world waiting scenarios perfectly (customer service, task scheduling)
- The FIFO principle ensures order is preserved, which is critical for many algorithms
- Queues are essential for breadth-first processing of data

**Key Difference:**
- Stack: Order of removal is **reversed** from order of insertion
- Queue: Order of removal **matches** order of insertion

---

### Which was more challenging to implement: Stack or Queue? Why?

**Queue was more challenging** for the following reasons:

1. **Two Pointers vs One:**
   - Stack only needs `top` pointer
   - Queue needs both `front` and `rear` pointers
   - Managing two pointers requires more careful coordination

2. **Space Management:**
   - Stack grows/shrinks from one end (simple)
   - Queue has the "wasted space" problem after dequeuing
   - Had to implement reset logic: `if (isEmpty()) { front = 0; rear = -1; }`

3. **Edge Cases:**
   - Queue has more edge cases to handle:
     - When to reset pointers after emptying
     - Detecting full vs empty state with two pointers
     - Maintaining `currentSize` for accurate state

4. **Circular Queue Complexity:**
   - Understanding the modulo operation for wrap-around
   - Distinguishing between full and empty states (both have `front == rear`)
   - Required more mathematical thinking

**Stack Implementation:**
- More intuitive (like stacking plates)
- Single direction of growth
- Simpler state management

**However**, implementing both taught me:
- The importance of understanding data structure constraints
- How to handle edge cases systematically
- The trade-offs between simplicity and efficiency

---

### How could these structures be used in real-world systems?

**Stack Applications in Real Systems:**

1. **Text Editors (Undo/Redo):**
   - Every action is pushed onto an undo stack
   - Ctrl+Z pops from undo stack and pushes to redo stack
   - Allows users to navigate through edit history
   - **Implemented in:** Microsoft Word, VS Code, Photoshop

2. **Web Browsers (Navigation History):**
   - Each visited page is pushed onto a back stack
   - Back button pops from back stack, pushes to forward stack
   - Enables backward/forward navigation
   - **Implemented in:** Chrome, Firefox, Safari

3. **Function Call Stack:**
   - Program execution pushes function calls onto stack
   - Function returns by popping from stack
   - Enables recursive function calls and proper return flow
   - **Implemented in:** Every programming language runtime

4. **Expression Evaluation:**
   - Converting infix to postfix notation
   - Evaluating mathematical expressions
   - Parentheses matching in compilers
   - **Implemented in:** Calculators, compilers, interpreters

5. **Game Development:**
   - Undo moves in chess/puzzle games
   - Saving/loading game states
   - Managing screen transitions

**Queue Applications in Real Systems:**

1. **Operating System (CPU Scheduling):**
   - Processes waiting for CPU time are enqueued
   - CPU serves processes in FIFO order (Round Robin)
   - Ensures fair distribution of resources
   - **Implemented in:** Windows, Linux, macOS task schedulers

2. **Printer Spooler:**
   - Print jobs enqueued as they arrive
   - Printer processes jobs in order received
   - Prevents job starvation and ensures fairness
   - **Implemented in:** All printer management systems

3. **Call Centers (Customer Service):**
   - Incoming calls enqueued in waiting queue
   - Next available agent serves first customer in queue
   - Displays "You are #3 in queue" messages
   - **Implemented in:** IVR systems, helpdesk software

4. **Message Queues (Asynchronous Processing):**
   - Messages/tasks enqueued by producers
   - Workers dequeue and process messages
   - Enables decoupling and scalability
   - **Implemented in:** RabbitMQ, Apache Kafka, AWS SQS

5. **Breadth-First Search (BFS):**
   - Graph/tree traversal algorithm
   - Nodes enqueued level by level
   - Used in shortest path algorithms, social networks
   - **Implemented in:** Google Maps, LinkedIn "People You May Know"

6. **Network Packet Management:**
   - Data packets enqueued at routers
   - Transmitted in FIFO order (with priority queues for QoS)
   - **Implemented in:** Network routers, switches

**Combined Applications:**

- **Web Servers:** Queue for incoming requests + Stack for processing state
- **Video Games:** Queue for events + Stack for game states
- **Databases:** Queue for transactions + Stack for undo logs

---

### Personal Insights

**What I found interesting:**
- How simple array-based structures power complex real-world systems
- The elegance of LIFO vs FIFO - two opposite behaviors from similar structures
- How the palindrome checker cleverly uses both structures together

**Skills Developed:**
- Better understanding of pointer/index management
- Improved edge case handling and error checking
- Appreciation for O(1) time complexity in data structure operations
- Stronger grasp of OOP principles (encapsulation, abstraction)

**Future Applications:**
- I can now implement these structures in any project requiring ordering
- Understanding these basics will help with more complex structures (linked lists, trees)
- These concepts apply to system design and algorithm optimization

**Challenges Overcome:**
- Initially confused about when to use Stack vs Queue
- Struggled with circular queue wrap-around logic
- Practice with edge cases improved my debugging skills

**Conclusion:**
This lab reinforced that **choosing the right data structure** is crucial for efficient problem-solving. Stacks and queues are fundamental building blocks that, despite their simplicity, are indispensable in modern software systems. The hands-on implementation deepened my understanding far beyond theoretical knowledge.

---

## 📚 References

1. Course lecture notes - Engr. Jamie Eduardo Rosal, MSCpE
2. Lab 5 instructions and pseudocode
3. Java Documentation: https://docs.oracle.com/javase/tutorial/
4. Data Structures and Algorithms in Java (Textbook)

---

## ✅ Submission Checklist

- [x] All objectives completed
- [x] Discussion section with concept summary
- [x] Pseudocode for all implementations
- [x] Program outputs with screenshots
- [x] Complexity analysis table with explanations
- [x] Critical thinking answer (circular queue)
- [x] Comprehensive reflection
- [x] All 6 Java files tested and working
- [x] Code follows best practices and is well-commented
- [x] README.md file for project documentation

---

**End of Lab Report**

*Submitted by: [Your Name]*  
*Date: [Submission Date]*  
*Course: Data Structures and Algorithms*  
*Instructor: Engr. Jamie Eduardo Rosal, MSCpE*
