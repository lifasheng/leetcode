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
#define M2
#ifdef M1 // use hash map to help. O(n) time , O(n) space
    bool hasCycle(ListNode *head) {
        typedef ListNode* PTR;
        unordered_map<PTR, bool> m;
        
        while(head) {
            if (m[head]) {
                return true;
            }
            else {
                m[head] = true;
            }
            head = head->next;
        }
        
        return false;
    }
#endif
#ifdef M2 // fast runner. O(n) time, O(1) space.
    bool hasCycle(ListNode *head) {
        ListNode *fast = head, *slow = head;
        
        while(fast && fast->next) {
            slow = slow->next;
            fast = fast->next->next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
#endif
};
