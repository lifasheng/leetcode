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
用prev指针指向cur的前一个结点，cur和next指向当前结点和下一个结点。
每次循环，可以翻转cur和next，然后更新prev指针和cur指针
O(n) time, O(1) space
Test case: 1->2,  1->2->3, 1->2->3->4, 1->2->3->4->5
*/
    ListNode *swapPairs(ListNode *head) {
        if (!head || head->next == NULL) return head;
        
        ListNode dummy(-1);
        dummy.next = head;
        
        ListNode *prev = &dummy;
        ListNode *cur = head;
        ListNode *next = NULL;
        
        while(cur && cur->next) { // 保证有两个结点
            next = cur->next;
            
            cur->next = next->next;
            next->next = cur;
            prev->next = next;
            
            prev = cur;
            cur = cur->next;
        }
        
        return dummy.next;
    }
};
