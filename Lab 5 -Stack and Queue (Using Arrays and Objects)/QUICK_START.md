# 🚀 Quick Start Guide - Lab 5

## 📁 Files Overview

| File | Description | Run Command |
|------|-------------|-------------|
| **Task1_StackArray.java** | Stack using array (LIFO) | `java Task1_StackArray` |
| **Task2_QueueArray.java** | Queue using array (FIFO) | `java Task2_QueueArray` |
| **Task3_StackQueueOOP.java** | Both using OOP | `java Task3_StackQueueOOP` |
| **Problem1_UndoRedo.java** | Undo/Redo simulation | `java Problem1_UndoRedo` |
| **Problem2_CustomerQueue.java** | Customer queue system | `java Problem2_CustomerQueue` |
| **Problem3_PalindromeChecker.java** | Palindrome checker | `java Problem3_PalindromeChecker` |

## ⚡ Quick Compile & Run All

### Option 1: Run each individually
```powershell
cd "c:\Users\User\Documents\NetBeansProjects\Lab 5 -Stack and Queue (Using Arrays and Objects)\src\lab\pkg5\stack\and\queue\using\arrays\and\objects"

# Task 1
javac Task1_StackArray.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task1_StackArray

# Task 2
javac Task2_QueueArray.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task2_QueueArray

# Task 3
javac Task3_StackQueueOOP.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task3_StackQueueOOP

# Problem 1
javac Problem1_UndoRedo.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem1_UndoRedo

# Problem 2
javac Problem2_CustomerQueue.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem2_CustomerQueue

# Problem 3
javac Problem3_PalindromeChecker.java
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Problem3_PalindromeChecker
```

### Option 2: Compile all at once
```powershell
cd "c:\Users\User\Documents\NetBeansProjects\Lab 5 -Stack and Queue (Using Arrays and Objects)\src\lab\pkg5\stack\and\queue\using\arrays\and\objects"

javac Task1_StackArray.java Task2_QueueArray.java Task3_StackQueueOOP.java Problem1_UndoRedo.java Problem2_CustomerQueue.java Problem3_PalindromeChecker.java
```

## 📊 What Each File Does

### ✅ Task 1: Stack Using Array
- Demonstrates LIFO (Last In First Out)
- Push, Pop, Peek operations
- Handles overflow and underflow
- **Output:** Shows stack operations step-by-step

### ✅ Task 2: Queue Using Array  
- Demonstrates FIFO (First In First Out)
- Enqueue, Dequeue, Peek operations
- Handles full and empty states
- **Output:** Shows queue operations step-by-step

### ✅ Task 3: Stack & Queue OOP
- Both structures using classes
- Shows encapsulation benefits
- Side-by-side LIFO vs FIFO comparison
- **Output:** Demonstrates OOP principles

### 🎯 Problem 1: Undo/Redo
- Text editor simulation
- Uses stack for undo/redo functionality
- **Interactive** + Automated demo
- **Commands:** type, undo, redo, show, history, exit

### 🎯 Problem 2: Customer Queue
- Service counter simulation
- Uses queue for customer management
- **Interactive** + Automated demo
- **Commands:** add, serve, next, show, count, demo, exit

### 🎯 Problem 3: Palindrome Checker
- Uses BOTH stack and queue
- Checks if word reads same forwards/backwards
- **Interactive** + Automated demo
- **Commands:** check, demo, exit

## 🎓 Key Learning Points

### Stack (LIFO)
```
Operation: PUSH 1, 2, 3, 4, 5
Stack:     [1, 2, 3, 4, 5] ← top
           
Operation: POP, POP
Result:    Returns 5, then 4
Stack:     [1, 2, 3] ← top

Last In → First Out ✓
```

### Queue (FIFO)
```
Operation: ENQUEUE 1, 2, 3, 4, 5
Queue:     [1, 2, 3, 4, 5]
            ↑           ↑
          front       rear
           
Operation: DEQUEUE, DEQUEUE
Result:    Returns 1, then 2
Queue:     [3, 4, 5]
            ↑       ↑
          front   rear

First In → First Out ✓
```

## 📸 Screenshot Checklist

For your lab report, capture:
- [ ] Task 1 - Full output showing push/pop operations
- [ ] Task 2 - Full output showing enqueue/dequeue operations  
- [ ] Task 3 - Side-by-side stack and queue demo
- [ ] Problem 1 - Undo/Redo demonstration
- [ ] Problem 2 - Customer queue simulation
- [ ] Problem 3 - Palindrome checking (show both palindrome and non-palindrome)

## 💡 Tips for Success

1. **Run automated demos first** - Each file has automatic demonstrations
2. **Try interactive modes** - Problems 1, 2, and 3 have interactive commands
3. **Understand the output** - Don't just run it, analyze what's happening
4. **Test edge cases:**
   - Stack: Overflow and underflow
   - Queue: Full and empty states
5. **Compare LIFO vs FIFO** - Task 3 shows this perfectly

## 🐛 Common Issues & Solutions

**Issue:** "Cannot find symbol" error
**Solution:** Make sure you're in the correct directory and compile first

**Issue:** "NoClassDefFoundError"  
**Solution:** Use the full package name when running:
```
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task1_StackArray
```

**Issue:** Program exits immediately
**Solution:** This is expected for automated demos. For interactive mode, use the commands.

## 📝 Quick Answers Reference

### Complexity Table
| Operation | Time | Space |
|-----------|------|-------|
| Push/Enqueue | O(1) | O(1) |
| Pop/Dequeue | O(1) | O(1) |
| Peek | O(1) | O(1) |
| Search | O(n) | O(1) |

### Challenge Question Answer
**Why is circular queue more efficient?**
- **Linear Queue:** Wastes space at front after dequeuing
- **Circular Queue:** Wraps around using modulo: `(position + 1) % maxSize`
- **Result:** 100% space utilization vs partial utilization

## 🎯 Evaluation Criteria (100 points)

- Stack Implementation: 25 points
- Queue Implementation: 25 points
- OOP Design: 25 points
- Critical Thinking: 25 points

**To get full marks:**
- All operations must work correctly
- Code must be clean and well-commented
- Must handle edge cases (overflow/underflow)
- Answer challenge question thoroughly
- Complete reflection section

## 📚 Next Steps

After completing this lab:
1. ✅ Run all 6 files and capture screenshots
2. ✅ Fill in the LAB_REPORT_TEMPLATE.md
3. ✅ Answer the challenge question
4. ✅ Write your reflection
5. ✅ Submit according to instructor's guidelines

## 🆘 Need Help?

**Check these files:**
- `README.md` - Comprehensive documentation
- `LAB_REPORT_TEMPLATE.md` - Complete report template with answers
- Comments in each `.java` file - Detailed explanations

**Still stuck?** Review the:
- Pseudocode in Task 1 and Task 2
- Step-by-step comments in the code
- Output examples in the README

---

**Good luck! 🚀**

*Remember: Understanding is more important than just running the code.*  
*Take time to analyze how LIFO and FIFO principles work!*
