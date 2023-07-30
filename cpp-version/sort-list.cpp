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
// 归并排序法
    ListNode *mergeTwoLists(ListNode *l1, ListNode *l2) {
        ListNode dummy(-1);
        ListNode *p = &dummy;
        while(l1 && l2) {
            if (l1->val < l2->val) {
                p->next = l1;
                l1 = l1->next;
            }
            else {
                p->next = l2;
                l2 = l2->next;
            }
            
            p = p->next;
        }
        
        p->next = l1 ? l1 : l2;
        
        return dummy.next;
    }
    ListNode *sortList(ListNode *head) {
        if (!head || !head->next) return head;
        
        // dummy 用于辅助找中间结点
        ListNode dummy(-1);
        dummy.next = head;
        
        // fast runner方法, slow 为中间结点（奇偶两种情况都对）
        ListNode *slow = &dummy, *fast = &dummy;
        while(fast && fast->next) {
            slow = slow->next;
            fast = fast->next->next;
        }
        
        ListNode *right = slow->next;
        slow->next = NULL; // 断开前后两段链表
        
        return mergeTwoLists(sortList(head), sortList(right));
    }
};
