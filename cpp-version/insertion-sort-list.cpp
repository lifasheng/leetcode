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
// 对于两个结点以上的链表，我们从第二个结点开始处理，将它们插入已排序的链表中。
    ListNode *insertionSortList(ListNode *head) {
        if (!head || !head->next) return head;
        
        ListNode dummy(-1);
        dummy.next = head;
        
        ListNode *p = head->next, *next = p->next;
        head->next = NULL; // important!
        
        while(p) {
            // 找到p的插入位置，即prev之后。
            ListNode *prev = &dummy;
            while(prev->next && p->val >= prev->next->val) {
                prev = prev->next;
            }
            
            // 插入p结点到prev之后
            p->next = prev->next;
            prev->next = p;
            
            // 更新p和next指针
            p = next;
            if (p) next = p->next; // important!
        }
        
        return dummy.next;
    }
    // similar implementation, code is clearer.
    ListNode *insertionSortList(ListNode *head) {
        if (head == NULL || head->next == NULL) return head;
        
        ListNode dummy(-1);
        ListNode *p = head;
        while(p) {
            ListNode *pnext = p->next;
            
            ListNode *prev = &dummy;
            while(prev->next) {
                if (prev->next->val < p->val) prev = prev->next;
                else break;
            }
            p->next = prev->next;
            prev->next = p;
            p = pnext;
        }
        return dummy.next;
    }
};
