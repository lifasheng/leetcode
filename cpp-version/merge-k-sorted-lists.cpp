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
#define M3
 
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
/* 超时了!!!
时间复杂度分析：
为了便于分析，我们假设这K个list，每个list的大小都为n。
这里会进行K-1次合并，每次合并的时间分别是：
n +n = 2n,
2n+n = 3n,
3n+n = 4n,
...
(K-1)n +n = K*n
所以总的时间复杂度为：2n+3n+4n+...+k*n = O(nk * k)
*/
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
/*
同样假设这K个list，每个list的大小都为n。
T(k) = 2T(k/2) + nk
     = 4T(k/4) + 2nk
     = 8T(k/8) + 3nk
     = ...
     = kT(1) + logk * nk
所以时间复杂度为O(nk * logk)
*/
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
#ifdef M3 // 使用堆的方法
/*
http://blog.csdn.net/linhuanmars/article/details/19899259
维护一个K个元素的最小堆，一开始把每个list的第一个（也是最小的）元素加入堆中，
然后从堆中取出这K个元素中的最小元素，连接到最终链表中，并将该元的下一个元素加入堆中。
该算法对每个元素要读取一次，即是k*n次，然后每次读取元素要把新元素插入堆中要logk的复杂度，
所以总时间复杂度是O(nklogk)。空间复杂度是堆的大小，即为O(k)。
*/
    class ListNodeComparison {
        public:
        bool operator()(const ListNode *lh, const ListNode *rh) {
            return lh->val > rh->val;  // > 是最小堆， < 最大堆
        }
    };
    ListNode *mergeKLists(vector<ListNode *> &lists) {
        if (lists.empty()) return NULL;

        std::priority_queue<ListNode*, vector<ListNode*>, ListNodeComparison> q;
        ListNode dummy(-1);
        ListNode *prev = &dummy;
        for(int i=0;i<lists.size(); ++i) {
            if (lists[i]) {
                q.push(lists[i]);
            }
        }

        while (!q.empty()) {
            ListNode *p = q.top();
            q.pop();
            prev->next = p;
            prev = prev->next;
            if (p->next) {
                q.push(p->next);
            }
        }

        return dummy.next;
    }
#endif
};
