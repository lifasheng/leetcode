/*

206. Reverse Linked List
Easy

Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?

*/

/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    #if 0
    ListNode* reverseList(ListNode* head) {        
        ListNode* head2 = NULL;
        ListNode* p = NULL;
        while(head != NULL) {
            p = head->next;
            head->next = head2;
            head2 = head;
            head = p;
        }
        
        return head2;
    }
    
    #endif
    
    #if 1
    /*
    Try to record the new head and new tail.
    New head will be updated once, new tail will be updated every time the sub call returned.
    */
    ListNode* reverseList(ListNode* head) {        
        if (head == NULL) return head;
        ListNode *newHead = NULL;
        ListNode *newTail = NULL;
        reverseList(head, newHead, newTail);
        return newHead;
    }
    
    void reverseList(ListNode* head, ListNode* &newHead, ListNode* &newTail) {        
        if (head->next == NULL) {
            newHead = newTail = head;
            return;
        }
        
        ListNode *p = head->next;
        head->next = NULL;
        reverseList(p, newHead, newTail);
        newTail->next = head;
        newTail = head;
    }
    #endif
    
    #if 0
    /*
    The recursive version is slightly trickier and the key is to work backwards. Assume that the rest of the list had already been reversed, now how do I reverse the front part? Let's assume the list is: n1 → … → nk-1 → nk → nk+1 → … → nm → Ø

    Assume from node nk+1 to nm had been reversed and you are at node nk.

    n1 → … → nk-1 → nk → nk+1 ← … ← nm

    We want nk+1’s next node to point to nk.

    So,

    nk.next.next = nk;

    Be very careful that n1's next must point to Ø. If you forget about this, your linked list has a cycle in it. This bug could be caught if you test your code with a linked list of size 2.
    */
    ListNode* reverseList(ListNode* head) {       
        if (head == NULL || head->next == NULL) return head;
        ListNode* p = reverseList(head->next);
        head->next->next = head;
        head->next = NULL;
        return p;
    }
    #endif
};




//////////////////////////

/*
https://practice.geeksforgeeks.org/problems/reverse-a-linked-list/1

this recursive way is quite similar with iterative idea.

It try to change next->next = current recursively.

*/


Node* reverseList(Node *prev, Node *curr) {
    if (curr == NULL) return prev;
    
    Node* next = curr->next;
    curr->next = prev;
    return reverseList(next, curr);
}

// Should reverse list and return new head.
Node* reverseList(Node *head)
{
    if (head == NULL || head->next == NULL) return head;
    
    Node *next = head->next;
    head->next = NULL;

    return reverseList(head, next);
}




