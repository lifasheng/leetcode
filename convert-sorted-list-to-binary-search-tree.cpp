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
#define M2
#ifdef M1  //分治法 O(nlogn)time, O(logn)space
    TreeNode *sortedListToBST(ListNode *head) {
        if (head == NULL) return NULL;
        if (head->next == NULL) return new TreeNode(head->val);
        
        //fast runner to find middle item(pointed by slow)
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
#endif
#ifdef M2
// http://articles.leetcode.com/2010/11/convert-sorted-list-to-balanced-binary.html
// 时间复杂度 O(n),空间复杂度 O(logn)
    TreeNode *sortedListToBST(ListNode *head) {
        if (head == NULL) return NULL;
        if (head->next == NULL) return new TreeNode(head->val);
        ListNode *p = head;
        int len = 1;
        while(p->next) {
            ++len;
            p = p->next;
        }
        return sortedListToBST(head, 0, len-1);
    }
    
    // 自底向上的方法，这里最巧妙的地方就是list是引用，这样就保证子递归中已经把list正确地往后移动了。
    TreeNode *sortedListToBST(ListNode* &list, int start, int end) {
        if (start > end) return NULL;
        
        int mid = start + (end-start)/2; // 效果同(start+end)/2, 防止溢出, good
        TreeNode *leftTree = sortedListToBST(list, start, mid-1);
        TreeNode *root = new TreeNode(list->val);
        list = list->next;
        root->left = leftTree;
        root->right = sortedListToBST(list, mid+1, end);
        return root;
    }
#endif
};
