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
    ListNode *rotateRight(ListNode *head, int k) {
        if (!head || head->next == NULL || k <=0) return head;
        
        // 扫描一遍链表，得到链表长度和尾结点
        int len = 1;
        ListNode *p = head;
        while (p->next) {
            ++len;
            p = p->next;
        }
        
        p->next = head; // 把链表的头尾连起来，形成环
        
        // k可能大于len，所以取模。
        // 从尾结点开始，往前走len-k步，就到了分界点。
        k = len - k % len;
        for(int i=0; i<k; ++i) {
            p = p->next;
        }
        
        head = p->next;
        p->next = NULL;
        
        return head;
    }
};
