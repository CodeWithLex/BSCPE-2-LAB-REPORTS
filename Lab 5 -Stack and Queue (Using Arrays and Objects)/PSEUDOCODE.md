# Pseudocode - Lab 5: Stack and Queue

## Task 1: Stack (LIFO)

```
BEGIN
    stack[5], top = -1
    
    // Push
    stack[++top] = 10
    stack[++top] = 20
    stack[++top] = 30
    
    // Display
    FOR i = top to 0: PRINT stack[i]
    
    // Pop
    PRINT stack[top--]
    PRINT stack[top--]
    
    // Display
    FOR i = top to 0: PRINT stack[i]
END
```

---

## Task 2: Queue (FIFO)

```
BEGIN
    queue[5], front = 0, rear = -1
    
    // Enqueue
    queue[++rear] = 10
    queue[++rear] = 20
    queue[++rear] = 30
    
    // Display
    FOR i = front to rear: PRINT queue[i]
    
    // Dequeue
    PRINT queue[front++]
    PRINT queue[front++]
    
    // Display
    FOR i = front to rear: PRINT queue[i]
END
```

---

## Task 3: Stack and Queue Classes (OOP)

### Stack Class
```
CLASS Stack:
    stack[], top, maxSize
    
    push(value):
        IF top < maxSize-1: stack[++top] = value
    
    pop():
        IF top >= 0: RETURN stack[top--]
    
    display():
        FOR i = top to 0: PRINT stack[i]
```

### Queue Class
```
CLASS Queue:
    queue[], front, rear, maxSize
    
    enqueue(value):
        IF rear < maxSize-1: queue[++rear] = value
    
    dequeue():
        IF front <= rear: RETURN queue[front++]
    
    display():
        FOR i = front to rear: PRINT queue[i]
```

### Main
```
BEGIN
    stack = new Stack(5)
    stack.push(10), stack.push(20), stack.push(30)
    stack.display()
    stack.pop(), stack.pop()
    stack.display()
    
    queue = new Queue(5)
    queue.enqueue(100), queue.enqueue(200), queue.enqueue(300)
    queue.display()
    queue.dequeue(), queue.dequeue()
    queue.display()
END
```

---

## Quick Reference

**Stack (LIFO):**
```
Push 1,2,3 → [1,2,3] ← top
Pop → 3, Pop → 2
Result: [1]
```

**Queue (FIFO):**
```
Enqueue 1,2,3 → [1,2,3]
                 ↑     ↑
               front rear
Dequeue → 1, Dequeue → 2
Result: [3]
```

**Complexity:** Push/Pop/Enqueue/Dequeue = O(1)
