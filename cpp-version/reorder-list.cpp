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
/*
思路：
先用fast runner方法，找到中间结点（注意奇偶个数），从中间结点断开，将后段链表翻转，再和前段链表合并。
*/
    void reorderList(ListNode *head) {
        if (!head || head->next == NULL) return;
        
        ListNode dummy(-1);
        dummy.next = head;
        
        // 找到中间结点，slow为中间结点
        ListNode *slow = &dummy, *fast = &dummy;
        while(fast && fast->next) {
            slow = slow->next;
            fast = fast->next->next;
        }
        
        ListNode *p = slow->next; //后段链表翻转前的头结点
        slow->next = NULL; // 断开前段链表
        
        // 翻转后段链表
        ListNode *head2 = NULL; 
        while(p) {
            ListNode *pn = p->next;
            p->next = head2;
            head2 = p;
            p = pn;
        }
        
        p = &dummy; 
        // 合并两段链表
        while (head && head2) {
            ListNode *hn = head->next;
            ListNode *hn2 = head2->next;
            
            p->next = head;
            head->next = head2;
            p = head2;
            
            head = hn;
            head2 = hn2;
        }
        if (head) { // 奇数个结点时，前段链表多一个结点。
            p->next = head;
        }
        
        head = dummy.next;
        
        return ;
    }
};
