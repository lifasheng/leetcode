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
    ListNode *addTwoNumbers(ListNode *l1, ListNode *l2) {
        ListNode dummy(-1); // note the constructor needs parameter
        ListNode *p1 = l1, *p2 = l2, *p3 = &dummy;
        
        int val1, val2, carry = 0, sum = 0;
        
        while(p1 || p2) {
            val1 = p1 ? p1->val : 0;
            val2 = p2 ? p2->val : 0;
            sum = val1 + val2 + carry;
            carry = sum / 10;
            
            ListNode *temp = new ListNode(sum % 10);
            p3->next = temp;
            p3 = temp;
            
            if (p1) { 
                p1 = p1->next;
            }
            if (p2) {
                p2 = p2->next;
            }
        }
        
        if (carry) {
            p3->next = new ListNode(carry);
        }
        
        return dummy.next;
    }
};
