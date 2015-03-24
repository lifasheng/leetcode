/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
/**
 * Definition for binary tree
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
    TreeNode *sortedListToBST(ListNode *head) {
        if (head == NULL) return NULL;
        if (head->next == NULL) return new TreeNode(head->val);
        
        // fast runner to find middle item (slow points to the middle item).
        ListNode dummy(-1);
        dummy.next = head;
        ListNode *slow = &dummy, *fast = &dummy, *prev = &dummy;
        while(fast && fast->next) {
            prev = slow;
            slow = slow->next;
            fast = fast->next->next;
        }
        
        ListNode *head2 = slow->next;
        prev->next = NULL;
        
        TreeNode *root = new TreeNode(slow->val);
        root->left = sortedListToBST(dummy.next); // 注意，这里不能用head，考虑1->2->NULL; 
        root->right = sortedListToBST(head2);
        return root;
    }
};
