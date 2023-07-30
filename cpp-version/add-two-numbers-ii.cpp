/*
445. Add Two Numbers II
Medium

You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Follow up:
What if you cannot modify the input lists? In other words, reversing the lists is not allowed.

Example:

Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7

*/


/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */

#if 0

// reverse list and add them.
// note that when we add them, we insert in the head of the result list, so no need to reverse the result list.

class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        if (l1 == NULL) return l2;
        if (l2 == NULL) return l1;
        
        ListNode* rl1 = reverseList(l1);
        ListNode* rl2 = reverseList(l2);
        int carry = 0;
        ListNode* sumList = NULL;
        while(rl1 != NULL && rl2 != NULL) {
            int val = rl1->val + rl2->val + carry;
            carry = val / 10;
            ListNode* p = new ListNode(val % 10);
            p->next = sumList;
            sumList = p;
            rl1 = rl1->next;
            rl2 = rl2->next;
        }
        
        while(rl1 != NULL) {
            int val = rl1->val + carry;
            carry = (val / 10);
            ListNode* p = new ListNode(val % 10);
            p->next = sumList;
            sumList = p;
            rl1 = rl1->next;
        }
        
        while(rl2 != NULL) {
            int val = rl2->val + carry;
            carry = val / 10;
            ListNode* p = new ListNode(val%10);
            p->next = sumList;
            sumList = p;
            rl2 = rl2->next;
        }
        
        if (carry > 0) {
            ListNode* p = new ListNode(carry);
            p->next = sumList;
            sumList = p;
        }
        
        return sumList;
    }
    
    ListNode* reverseList(ListNode *head) {
        if (head == NULL || head->next == NULL) {
            return head;
        }
        
        ListNode* newHead = NULL;
        ListNode* cur = head;
        while(cur != NULL) {
            ListNode* next = cur->next;
            cur->next = newHead;
            newHead = cur;
            cur = next;
        }
        return newHead;        
    }
};
#endif



// recursively way, not reverse the input list
// find the length first, for the longer list, find the sub list with same length as short list.
// recursively add two same length lists, and then append the sum list to the first part sub list of the longer list.
// Pay attention to carry for these two recursively functions!!!
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        if (l1 == NULL) return l2;
        if (l2 == NULL) return l1;
       
        ListNode* longerList = l1;
        ListNode* shortList = l2;
        int len1 = length(l1);
        int len2 = length(l2);
        int diff = abs(len1 - len2);
        
        if (diff == 0) {
            int carry = 0;
            ListNode* sumList = addTwoListsWithSameLengthRecursively(l1, l2, carry);
            if (carry > 0) {
                ListNode* newNode = new ListNode(carry);
                newNode->next = sumList;
                sumList = newNode;
            }
            return sumList;
        }
        
        if (len1 < len2) {
            longerList = l2;
            shortList = l1;
        }
        
        ListNode* p = longerList;
        for(int i=1; i<diff; ++i) {
            p = p->next;
        }
        // p is the tail of the first part.
        ListNode* secondPartOfLongerList = p->next;
        p->next = NULL;
        ListNode* firstPartOfLongerList = longerList;
        
        int carry = 0;
        ListNode* sumList = addTwoListsWithSameLengthRecursively(secondPartOfLongerList, shortList, carry);
        ListNode* finalList = appendList(firstPartOfLongerList, sumList, carry);
        
        if (carry > 0) {
            ListNode* newNode = new ListNode(carry);
            newNode->next = finalList;
            finalList = newNode;
        }
        return finalList;
    }
    
    // append list l2 to the tail of list l1
    ListNode* appendList(ListNode* l1, ListNode* l2, int &carry) {
        if (l1 == NULL) {
            return l2;
        }
        
        ListNode* p = appendList(l1->next, l2, carry);
        l1->next = p;
        int val = l1->val + carry;
        l1->val = val % 10;
        carry = val / 10;
        return l1;
    }
    
    int length(ListNode* head) {
        int len = 0;
        while(head != NULL) {
            ++ len;
            head = head->next;
        }
        return len;
    }
    
    ListNode* addTwoListsWithSameLengthRecursively(ListNode* l1, ListNode* l2, int &carry) {
        if (l1 == NULL || l2 == NULL) {
            return NULL;
        }
        
        ListNode* p = addTwoListsWithSameLengthRecursively(l1->next, l2->next, carry);
        
        int val = l1->val + l2->val + carry;
        ListNode* newNode = new ListNode(val % 10);
        carry = val / 10;
        
        newNode->next = p;
        return newNode;
    }
    
};
