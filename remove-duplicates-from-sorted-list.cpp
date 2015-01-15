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
#ifdef M1 // 迭代版
// 注意：链表的去重和数的去重还是有一些区别的，链表去重时可以直接删除重复的结点，而数组要删除的话效率较差。
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head) return NULL;
        
        ListNode *prev = head;
        ListNode *cur = prev->next;
        
        while(cur) {
            if (cur->val != prev->val) {
                prev = prev->next;
                cur = cur->next;
            }
            else {
                prev->next = cur->next;
                delete cur;
                cur = prev->next;
            }
        }
        
        return head;
    }
#endif
#ifdef M2 // 递归版
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head || head->next == NULL) return head;
        
        ListNode *prev = head;
        ListNode *cur = prev->next;
        if (prev->val == cur->val) {
            delete prev;
            return deleteDuplicates(cur);
        }
        else {
            prev->next = deleteDuplicates(cur);
            return prev;
        }
    }
#endif
};
