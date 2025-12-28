# Lab 5: Stack and Queue (Using Arrays and Objects)
**Instructor:** Engr. Jamie Eduardo Rosal, MSCpE

## 📚 Project Overview
This lab implements Stack and Queue data structures using both array-based and object-oriented approaches, demonstrating LIFO and FIFO behaviors.

## 📁 File Structure

### Core Tasks
1. **Task1_StackArray.java** - Stack implementation using arrays
2. **Task2_QueueArray.java** - Queue implementation using arrays  
3. **Task3_StackQueueOOP.java** - Stack and Queue using OOP principles

### Practice Problems
4. **Problem1_UndoRedo.java** - Undo/Redo simulation using Stack
5. **Problem2_CustomerQueue.java** - Customer queue management system
6. **Problem3_PalindromeChecker.java** - Palindrome checker using Stack & Queue

## 🎯 Learning Objectives Covered

✅ **LIFO (Last In First Out)** - Stack behavior  
✅ **FIFO (First In First Out)** - Queue behavior  
✅ **Array implementation** of both data structures  
✅ **Object-Oriented implementation** with encapsulation  
✅ **Push, Pop, Enqueue, Dequeue** operations  
✅ **Practical applications** in real-world scenarios  
✅ **Complexity analysis** (Time: O(1) for main operations)

## 🚀 How to Run Each File

### Task 1: Stack Using Array
```bash
cd "c:\Users\User\Documents\NetBeansProjects\Lab 5 -Stack and Queue (Using Arrays and Objects)\src\lab\pkg5\stack\and\queue\using\arrays\and\objects"
javac Task1_StackArray.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task1_StackArray
```

**What it demonstrates:**
- Push elements to stack
- Pop elements from stack
- Peek at top element
- Handle stack overflow and underflow
- Display stack contents

### Task 2: Queue Using Array
```bash
javac Task2_QueueArray.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task2_QueueArray
```

**What it demonstrates:**
- Enqueue elements to rear
- Dequeue elements from front
- Peek at front element
- Handle queue full and empty states
- Display queue contents

### Task 3: Stack and Queue Using Objects (OOP)
```bash
javac Task3_StackQueueOOP.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task3_StackQueueOOP
```

**What it demonstrates:**
- Encapsulation of data and methods
- Reusable Stack and Queue classes
- Data abstraction
- Modular design
- Side-by-side comparison of LIFO vs FIFO

### Problem 1: Undo/Redo Simulation
```bash
javac Problem1_UndoRedo.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem1_UndoRedo
```

**Interactive Commands:**
- `type <text>` - Add text to editor
- `undo` - Undo last action
- `redo` - Redo last undone action
- `show` - Display current text
- `history` - Show action history
- `exit` - Exit program

**Or run automated demo automatically**

### Problem 2: Customer Queue Management
```bash
javac Problem2_CustomerQueue.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem2_CustomerQueue
```

**Interactive Commands:**
- `add <name>` - Add customer to queue
- `serve` - Serve next customer
- `next` - View next customer
- `show` - Display queue
- `count` - Show waiting customers
- `demo` - Run automated demo
- `exit` - Exit program

### Problem 3: Palindrome Checker
```bash
javac Problem3_PalindromeChecker.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem3_PalindromeChecker
```

**Interactive Commands:**
- `check <string>` - Check if palindrome
- `demo` - Run automated demo with examples
- `exit` - Exit program

**Example palindromes:** racecar, madam, noon, level

## 📊 Complexity Analysis

| Operation | Stack | Queue | Time Complexity |
|-----------|-------|-------|-----------------|
| Push/Enqueue | O(1) | O(1) | Constant time |
| Pop/Dequeue | O(1) | O(1) | Constant time |
| Peek | O(1) | O(1) | Constant time |
| Search | O(n) | O(n) | Linear time |
| isEmpty/isFull | O(1) | O(1) | Constant time |

## 🎓 Key Concepts Demonstrated

### Stack (LIFO)
- **Real-world analogy:** Stack of plates
- **Applications:** 
  - Function call stack
  - Undo/Redo features
  - Parentheses matching
  - Browser back button

### Queue (FIFO)
- **Real-world analogy:** Line at ticket counter
- **Applications:**
  - CPU scheduling
  - Printer job queue
  - Call center waiting lines
  - Message buffering

### Array vs OOP Implementation

| Feature | Array Implementation | OOP Implementation |
|---------|---------------------|-------------------|
| Structure | Primitive arrays | Classes with encapsulation |
| Flexibility | Fixed size | More modular |
| Readability | Procedural | Structured |
| Reusability | Limited | High |
| Maintainability | Harder | Easier |

## 🤔 Challenge Question Answer

**Q: Why is a circular queue more efficient than a linear queue when using arrays?**

**A:** In a linear queue, when elements are dequeued, the front pointer moves forward, leaving empty spaces at the beginning of the array that cannot be reused. This leads to:
- Wasted memory space
- False "queue full" condition (rear reaches end even when spaces exist at front)
- Need to shift all elements or reset pointers

A circular queue solves this by:
- Wrapping around: When rear reaches the end, it wraps to the beginning
- Reusing space: Empty spaces from dequeued elements can be reused
- Efficient use of memory: No wasted spaces
- True capacity: Can use all array positions

**Formula:** `next_position = (current_position + 1) % maxSize`

## 💡 Student Learning Insights

### What makes Stack special?
- Last element added is first to be removed
- Perfect for reversing order
- Used when you need to backtrack
- Natural fit for recursive operations

### What makes Queue special?
- First element added is first to be removed
- Maintains order of arrival
- Fair processing (first come, first served)
- Models real-world waiting scenarios

### OOP Benefits
- **Encapsulation:** Internal details hidden from user
- **Abstraction:** Simple interface, complex implementation
- **Reusability:** Write once, use many times
- **Modularity:** Easy to maintain and extend

## 📝 Output Examples

### Task 1 Output Preview:
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
```

### Task 2 Output Preview:
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
```

### Task 3 Output Preview:
```
=== TASK 3: STACK AND QUEUE USING OBJECTS (OOP) ===

========== STACK OPERATIONS ==========

--- Pushing to Stack ---
Stack: Pushed 5
Stack: Pushed 10
Stack: Pushed 15
Stack: Pushed 20

Stack (top to bottom): 20 15 10 5
```

## 🎯 Testing Checklist

- [x] Stack push operation
- [x] Stack pop operation
- [x] Stack overflow handling
- [x] Stack underflow handling
- [x] Queue enqueue operation
- [x] Queue dequeue operation
- [x] Queue full handling
- [x] Queue empty handling
- [x] Peek operations
- [x] Display methods
- [x] OOP encapsulation
- [x] Undo/Redo functionality
- [x] Customer queue simulation
- [x] Palindrome checking

## 📤 Submission Checklist

- [ ] All 6 Java files compile without errors
- [ ] Screenshot of Task 1 output
- [ ] Screenshot of Task 2 output
- [ ] Screenshot of Task 3 output
- [ ] Screenshot of Problem 1 (Undo/Redo)
- [ ] Screenshot of Problem 2 (Customer Queue)
- [ ] Screenshot of Problem 3 (Palindrome Checker)
- [ ] Complexity analysis table
- [ ] Answer to challenge question
- [ ] Reflection on learning experience

## 🔧 Troubleshooting

**Issue:** `ClassNotFoundException`  
**Solution:** Make sure you're in the correct directory and using the full class name

**Issue:** Stack Overflow error  
**Solution:** This is expected behavior when stack is full - demonstrates error handling

**Issue:** Queue Empty error  
**Solution:** This is expected behavior when queue is empty - demonstrates error handling

## 📚 Additional Resources

- Stack visualization: [VisuAlgo](https://visualgo.net/en/list)
- Queue visualization: [VisuAlgo](https://visualgo.net/en/list)
- Java documentation: [Oracle Java Docs](https://docs.oracle.com/javase/tutorial/)

## 👨‍🎓 Author Notes

Each file is designed with a **student-friendly approach**:
- Clear comments explaining each operation
- Descriptive variable names
- Step-by-step demonstrations
- Both automated and interactive modes
- Comprehensive error handling
- Visual output formatting

**Good luck with your lab!** 🚀

---
*Lab 5: Stack and Queue Implementation*  
*Course: Data Structures and Algorithms*  
*Instructor: Engr. Jamie Eduardo Rosal, MSCpE*
