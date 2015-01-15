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
#define M4
#ifdef M1
/*
思路：类似于Remove Duplicates from Sorted List， 不过需要记录是否遇到重复元素。
如果遇到了，则需要删除重复元素的最后一个。
重复元素的位置有两种情况： 
一种是在链表的前面或中间，这可以在循环中同一处理。
一种是在链表的最后面，这需要在循环外进行处理。
这里对某个重复元素，是先删后面的重复部分，再最后删第一个。
如1->1->1->2, 第一个1是在其它1之后被删除。
*/
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head || head->next == NULL) return head;
        
        ListNode dummy(-1); 
        dummy.next = head;
        
        ListNode *prev_prev = &dummy;
        ListNode *prev = head;
        ListNode *cur = prev->next;
        bool isDuplicated = false;
        
        while (cur) {
            if (prev->val != cur->val) {
                // 如果isDuplicated == true，说明prev是一个重复元素，所以需要删除它（这是该重复元素的第一个位置）， 如dummy->1->1->2
                // 这种情况下，注意更新prev_prev指针。
                if (isDuplicated) {
                    delete prev;
                    prev = cur;
                    cur = cur->next;
                    prev_prev->next = prev;
                    isDuplicated = false;
                }
                else {
                    prev_prev = prev_prev->next;
                    prev = prev->next;
                    cur = cur->next;
                }
            }
            else { // prev->val == cur->val
                isDuplicated = true;
                prev->next = cur->next;
                delete cur;
                cur = prev->next;
            }
        }
        
        if (isDuplicated) { // 重复元素在最后的情况
            delete prev;
            prev_prev->next = NULL;
        }
        
        return dummy.next;
    }
#endif
#ifdef M2
/*
思路：用两个指针，prev从dummy开始，cur从head开始。
如果当前元素和下一个元素重复，则循环删除自己，并在循环外删除最后一个重复位置，然后更新prev指针。
否则，就正常往后扫描。
*/
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head || head->next == NULL) return head;
        
        ListNode dummy(-1); 
        dummy.next = head;
        
        ListNode *prev = &dummy;
        ListNode *cur = head;
        
        while(cur) {
            bool isDuplicated = false;
            
            while(cur->next != NULL && cur->val == cur->next->val) {
                ListNode *temp = cur;
                cur = cur->next;
                delete temp;
                isDuplicated = true;
            }
            
            if (isDuplicated) { // 删除该重复元素的最后一个位置
                ListNode *temp = cur;
                cur = cur->next;
                delete temp;
                prev->next = cur;
            }
            else {
                cur = cur->next;
                prev = prev->next;
            }
        }
        
        return dummy.next;
    }
#endif
#ifdef M3 // 递归版, 思路类似于Remove Duplicates from Sorted List， 不过需要记录是否遇到重复元素。
// test case:
// 1->1,   1->1->2,  2->1->1,  2->1->1->3
    ListNode *deleteDuplicates(ListNode *head) {
        return deleteDuplicates(head, false);
    }
    ListNode *deleteDuplicates(ListNode *head, bool isDuplicated) {
        if (!head) return NULL;
        if (head->next == NULL) {
            if (isDuplicated) { // 比Remove Duplicates from Sorted List 多了这一个判断。
                delete head;
                return NULL;
            }
            else {
                return head;
            }
        }
        
        ListNode *prev = head;
        ListNode *cur = prev->next;
        
        if (prev->val == cur->val) {
            delete prev;
            return deleteDuplicates(cur, true);
        }
        else {
            if (isDuplicated) { // 比Remove Duplicates from Sorted List 多了这一个判断。
                delete prev;
                return deleteDuplicates(cur, false);
            }
            else {
                prev->next = deleteDuplicates(cur, false);
                return prev;
            }
        }
    }
#endif
#ifdef M4 //递归版, 在递归中先循环删除最前面的重复元素，再递归
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head || head->next == NULL) return head;
        
        ListNode *p = head->next;
        if (p->val == head->val) {
            while(p && p->val == head->val) {
                ListNode *temp = p;
                p = p->next;
                delete temp;
            }
            delete head;
            
            return deleteDuplicates(p);
        }
        else {
            head->next = deleteDuplicates(p);
            return head;
        }
    }
#endif
};
