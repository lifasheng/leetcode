/*
Design your implementation of the linked list. You can choose to use the singly linked list or the doubly linked list. A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node. If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement these functions in your linked list class:

get(index) : Get the value of the index-th node in the linked list. If the index is invalid, return -1.
addAtHead(val) : Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
addAtTail(val) : Append a node of value val to the last element of the linked list.
addAtIndex(index, val) : Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
deleteAtIndex(index) : Delete the index-th node in the linked list, if the index is valid.
Example:

MyLinkedList linkedList = new MyLinkedList();
linkedList.addAtHead(1);
linkedList.addAtTail(3);
linkedList.addAtIndex(1, 2);  // linked list becomes 1->2->3
linkedList.get(1);            // returns 2
linkedList.deleteAtIndex(1);  // now the linked list is 1->3
linkedList.get(1);            // returns 3
Note:

All values will be in the range of [1, 1000].
The number of operations will be in the range of [1, 1000].
Please do not use the built-in LinkedList library.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include <stdio.h>
#include <sstream>
using namespace std;


class MyLinkedList {
private:
    struct ListNode {
        public:
            int val;
            ListNode *next;
            ListNode(int v): val(v), next(NULL) {}
    };
    
    struct ListNode *head, *tail;
    int numOfNode;

    ListNode* findPreviousNode(int index) {
        ListNode *prev = head;
        --index;
        while(index>0) {
            --index;
            prev = prev->next;
        }
        return prev;
    }

public:
    /** Initialize your data structure here. */
    MyLinkedList() {
        head = tail = NULL;
        numOfNode = 0;
    }

    string str() {
        stringstream ss;
        ss << "MyLinkedList[numOfNode:" << numOfNode << "]:";
        ListNode *p = head;
        while(p) {
            ss << p->val << " ";
            p = p->next;
        }
        return ss.str();
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    int get(int index) {
        if (index < 0 || index >= numOfNode) return -1;
        if (index == 0) {
            return head->val;
        } else {
            ListNode *prev = findPreviousNode(index);
            return prev->next->val;
        }
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    void addAtHead(int val) {
        ListNode *node = new ListNode(val);
        if (head == NULL) {
            head = tail = node;
        } else {
            node->next = head;
            head = node;
        }
        ++numOfNode;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    void addAtTail(int val) {
        ListNode *node = new ListNode(val);
        if (tail == NULL) {
            head = tail = node;
        } else {
            tail->next = node;
            tail = node;
        }
        ++numOfNode;
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    void addAtIndex(int index, int val) {
        if (index < 0 || index > numOfNode) return;
        if (index == 0) {
            addAtHead(val);
        } else if (index == numOfNode) {
            addAtTail(val);
        } else { //if (index < numOfNode)
            ListNode *prev = findPreviousNode(index);
            ListNode *node = new ListNode(val);
            node->next = prev->next;
            prev->next = node;

            ++numOfNode;
        }
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    void deleteAtIndex(int index) {
        if (index < 0 || index >= numOfNode) return;
        if (index == 0) {
            ListNode *t = head;
            head = head->next;
            if (head == NULL) {  // last node was deleted, update tail!!!
                tail = NULL;
            }
            delete t;
        } else {
            ListNode *prev = findPreviousNode(index);
            ListNode *node = prev->next;
            prev->next = node->next;
            if (tail == node) { // tail was deleted, update tail!!!
                tail = prev;
            }
            delete node;
        }
        --numOfNode;
    }
};

int main() {
     //["MyLinkedList","addAtHead","addAtIndex","addAtIndex","addAtTail","addAtHead","addAtIndex","addAtIndex","addAtIndex","addAtTail","addAtTail","deleteAtIndex"]
     //[[],[0],[1,9],[1,5],[7],[1],[5,8],[5,2],[3,0],[1],[0],[6]]
     MyLinkedList *obj = new MyLinkedList();
     obj->addAtHead(0); cout << obj->str() << endl;
     obj->addAtIndex(1, 9); cout << obj->str() << endl;
     obj->addAtIndex(1, 5); cout << obj->str() << endl;
     obj->addAtTail(7); cout << obj->str() << endl;
     obj->addAtHead(1); cout << obj->str() << endl;
     obj->addAtIndex(5, 8); cout << obj->str() << endl;
     obj->addAtIndex(5, 2); cout << obj->str() << endl;
     obj->addAtIndex(3, 0); cout << obj->str() << endl;
     obj->addAtTail(1); cout << obj->str() << endl;
     obj->addAtTail(0); cout << obj->str() << endl;
     obj->deleteAtIndex(6); cout << obj->str() << endl;
 
    return 0;
}

