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
 
    ListNode *mergeTwoLists(ListNode *l1, ListNode *l2) {
        ListNode dummy(-1);
        ListNode *p = &dummy;
        
        while(l1 && l2) {
            if (l1->val < l2->val) {
                p->next = l1;
                l1 = l1->next;
            }
            else {
                p->next = l2;
                l2 = l2->next;
            }
            p = p->next;
        }
        
        p->next = l1 ? l1 : l2;
        return dummy.next;
    }
    
#ifdef M1
// O(n1+n2+n3...) time 超时了
    ListNode *mergeKLists(vector<ListNode *> &lists) {
        if (lists.empty()) return NULL;
        
        ListNode *result = lists[0];
        for(int i=1; i<lists.size(); ++i) {
            result = mergeTwoLists(result, lists[i]);
        }
        
        return result;
    }
#endif
 
#ifdef M2 // 分治法
    typedef vector<ListNode*>::iterator Iterator;
    ListNode *mergeKLists(vector<ListNode *> &lists) {
        return mergeKLists(lists.begin(), lists.end());
    }
    // [first, last)
    ListNode *mergeKLists(Iterator first, Iterator last) {
        if (first >= last) return NULL;
        
        int d = distance(first, last);
        
        if (d == 1) return *first; // 这个递归出口很重要，不然就死循环了。
        
        ListNode *left = mergeKLists(first, first+d/2);
        ListNode *right = mergeKLists(first+d/2, last);
        return mergeTwoLists(left, right);
    }
#endif
};
