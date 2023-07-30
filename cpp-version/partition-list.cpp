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
    ListNode *partition(ListNode *head, int x) {
        ListNode dummy1(-1), dummy2(-1);
        ListNode *tail1 = &dummy1, *tail2 = &dummy2;
        
        ListNode *p = head;
        while(p) {
            if (p->val < x) {
                tail1->next = p;
                tail1 = p;
            }
            else {
                tail2->next = p;
                tail2 = p;
            }
            p = p->next;
        }
        
        tail1->next = dummy2.next;
        tail2->next = NULL;
        return dummy1.next;
    }
};
