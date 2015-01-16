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
思路： fast runner的方法。
用两个指针p和q，起始时都指向dummy。先让指针q往前走n步，再两个指针一起走，直到q到了尾结点。
此时p指向的是待删除结点的前一个结点。
O(n) time, O(1) space
*/
    ListNode *removeNthFromEnd(ListNode *head, int n) {
        if (!head || n<=0) return head;
        
        ListNode dummy(-1);
        dummy.next = head;
        
        ListNode *p = &dummy, *q = &dummy;
        for(int i=0; i<n; ++i) {
            q = q->next;
        }
        
        while(q->next) {
            p = p->next;
            q = q->next;
        }
        
        ListNode *temp = p->next;
        p->next = p->next->next;
        delete temp;
        
        return dummy.next;
    }
};
