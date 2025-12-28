# Lab 5 Summary: Stack and Queue

## 📚 Overview
This lab implements **Stack (LIFO)** and **Queue (FIFO)** using arrays and OOP.

---

## 🎯 Three Main Tasks

### Task 1: Stack Using Array
```java
int[] stack = new int[5];
int top = -1;

// Push: stack[++top] = value
// Pop: value = stack[top--]
// Display: loop from top to 0
```
**Principle:** LIFO - Last In, First Out

### Task 2: Queue Using Array
```java
int[] queue = new int[5];
int front = 0, rear = -1;

// Enqueue: queue[++rear] = value
// Dequeue: value = queue[front++]
// Display: loop from front to rear
```
**Principle:** FIFO - First In, First Out

### Task 3: Stack & Queue Using OOP
```java
class Stack {
    int[] stack; int top, maxSize;
    push(value) { stack[++top] = value; }
    pop() { return stack[top--]; }
}

class Queue {
    int[] queue; int front, rear, maxSize;
    enqueue(value) { queue[++rear] = value; }
    dequeue() { return queue[front++]; }
}
```
**Benefit:** Encapsulation, reusability, modularity

---

## 📊 Quick Comparison

| Feature | Stack | Queue |
|---------|-------|-------|
| **Order** | LIFO | FIFO |
| **Add** | Push at top | Enqueue at rear |
| **Remove** | Pop from top | Dequeue from front |
| **Pointers** | `top` | `front` & `rear` |
| **Example** | Plate stack | Waiting line |

---

## 🔄 Visual Behavior

**Stack:**
```
Push 10, 20, 30 → [10, 20, 30] ← top
Pop → 30
Pop → 20
Result: [10] ← top
```

**Queue:**
```
Enqueue 10, 20, 30 → [10, 20, 30]
                      ↑        ↑
                    front    rear
Dequeue → 10
Dequeue → 20
Result: [30]
        ↑
      front & rear
```

---

## ⚡ Complexity

| Operation | Time | Space |
|-----------|------|-------|
| Push/Enqueue | O(1) | O(1) |
| Pop/Dequeue | O(1) | O(1) |
| Display | O(n) | O(1) |

---

## 💡 Key Concepts

**Stack (LIFO):**
- Last element added is first removed
- One pointer: `top`
- Use cases: Undo/Redo, Browser back, Function calls

**Queue (FIFO):**
- First element added is first removed
- Two pointers: `front` and `rear`
- Use cases: Printer queue, CPU scheduling, Customer service

**Array vs OOP:**
- Array: Simple, procedural, fixed size
- OOP: Encapsulated, reusable, maintainable

---

## 🎓 Challenge Question Answer

**Q: Why is circular queue more efficient than linear queue?**

**A:** Linear queue wastes space at front after dequeue. Circular queue wraps around using `(position + 1) % maxSize`, reusing all positions.

```
Linear:    [_][_][_][40][50]  ← spaces wasted
Circular:  [60][70][_][40][50] ← spaces reused!
```

---

## ✅ Lab Checklist

- [x] Task 1: Stack array implementation
- [x] Task 2: Queue array implementation
- [x] Task 3: OOP implementation
- [x] Pseudocode documented
- [x] Complexity analyzed
- [x] Challenge question answered

---

## 📝 Files Created

1. **Task1_StackArray.java** - Stack with array
2. **Task2_QueueArray.java** - Queue with array
3. **Task3_StackQueueOOP.java** - OOP implementation
4. **Problem1_UndoRedo.java** - Undo/Redo simulation
5. **Problem2_CustomerQueue.java** - Customer management
6. **Problem3_PalindromeChecker.java** - Palindrome checker
7. **PSEUDOCODE.md** - Algorithm documentation
8. **SUMMARY.md** - This file

---

## 🚀 Quick Run

```powershell
cd "Lab 5\src\lab\pkg5\stack\and\queue\using\arrays\and\objects"

javac *.java

java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task1_StackArray
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task2_QueueArray
java lab.pkg5.stack.and.queue.using.arrays.and.objects.Task3_StackQueueOOP
```

---

## 📖 Remember

- **Stack = Plates:** Last plate on, first plate off
- **Queue = Line:** First person in line, first person served
- **LIFO ≠ FIFO:** Opposite behaviors from similar structures
- **O(1) operations:** What makes them efficient!

---

**Course:** Data Structures and Algorithms  
**Instructor:** Engr. Jamie Eduardo Rosal, MSCpE  
**Lab:** Stack and Queue Implementation
