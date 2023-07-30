#include <iostream>
#include <cassert>
#include <string>
#include <stack>
using namespace std;

/*
 Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

     push(x) -- Push element x onto stack.
     pop() -- Removes the element on top of the stack.
     top() -- Get the top element.
     getMin() -- Retrieve the minimum element in the stack.

Example:

MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.

*/


class MinStack {
private:
    stack<int> origNumStk;
    stack<int> minNumStk;
public:
    /** initialize your data structure here. */
    MinStack() {
        
    }
    
    void push(int x) {
        origNumStk.push(x);
        if (minNumStk.empty()) { // !!! important !!!
            minNumStk.push(x);
        }
        else {
            minNumStk.push(min(minNumStk.top(), x));
        }
    }
    
    void pop() {
        origNumStk.pop();
        minNumStk.pop();
    }
    
    int top() {
        if (!origNumStk.empty()) {
            return origNumStk.top();
        }
        else {
            assert(0);
        }
    }
    
    int getMin() {
        if (!minNumStk.empty()) {
            return minNumStk.top();
        }
        else {
            assert(0);
        }
    }

    bool empty() {
        return origNumStk.empty();
    }
};

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

int main() {

    //{2, 1, 4, 3, 0, 5};
    MinStack ms;
    ms.push(2);
    cout << ms.top() << ", " << ms.getMin() << endl;
    ms.push(1);
    cout << ms.top() << ", " << ms.getMin() << endl;
    ms.push(4);
    cout << ms.top() << ", " << ms.getMin() << endl;
    ms.push(3);
    cout << ms.top() << ", " << ms.getMin() << endl;
    ms.push(0);
    cout << ms.top() << ", " << ms.getMin() << endl;
    ms.push(5);
    cout << ms.top() << ", " << ms.getMin() << endl;

    return 0;
}
