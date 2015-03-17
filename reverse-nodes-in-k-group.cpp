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
#ifdef M1
/*
思路：采用头插法来对每个group进行翻转
*/
    ListNode *reverseKGroup(ListNode *head, int k) {
        if (!head || k<=1 || head->next == NULL) return head;
        
        ListNode dummy(-1);
        dummy.next = head;
        
        // head2是每个group的参考头结点
        ListNode *head2 = &dummy, *prev, *cur = head;
        int len = 1;
        
        // 计算链表长度
        while(cur->next) {
            ++len;
            cur = cur->next;
        }
        
        cur = head;
        
        while(len >= k) {
            prev = cur;
            cur = prev->next;
            
            // 开始一个group的翻转
            int step = k-1;
            while(step > 0) {
                --step;
                
                prev->next = cur->next;
                cur->next = head2->next;
                head2->next = cur;
                cur = prev->next;
            }
            // 一个group翻转结束！
            
            // 设置下一个group的参考结点
            // prev在头插法中相当于尾结点, 所以它也是下一个group的参考结点
            head2 = prev; 
            
            // 更新剩余链表长度
            len -= k;
        }
        
        return dummy.next;
    }
    // similar implementation, code is clearer.
    ListNode *reverseKGroup(ListNode *head, int k) {
        if (head == NULL || head->next == NULL || k<=1) return head;
        
        // get length of list
        int len=1;
        ListNode *p = head;
        while(p->next) {
            ++len;
            p = p->next;
        }
        
        if (k>len) return head;
        
        // 头插法, head2 + prev + cur
        ListNode dummy(-1);
        dummy.next = head;
        ListNode *head2 = &dummy;
        ListNode *prev, *cur;
        
        for(int group=1; group<=len/k; ++group) {
            prev = head2->next;
            cur = prev->next;
            // reverse current group
            for(int i=1; i<k; ++i) {
                prev->next = cur->next;
                cur->next = head2->next;
                head2->next = cur;
                cur = prev->next;
            }
            
            // 更新头插法相应的结点。
            // 设置下一个group的参考结点
            // prev在头插法中相当于尾结点, 所以它也是下一个group的参考结点
            head2 = prev;
        }
        return dummy.next;
    }
#endif
#ifdef M2 // 递归版本
/*
先从头扫描k个结点，这k个结点为一个group，剩余结点递归处理。
对该group，进行翻转。
翻转方法是插入到头结点之前，并更新头结点。不是头插法。
*/
    ListNode *reverseKGroup(ListNode *head, int k) {
        if (!head || k<=1 || head->next == NULL) return head;
        
        ListNode *next_group = head;
        for(int i=0; i<k; ++i) {
            if (next_group) {
                next_group = next_group->next;
            }
            else {
                return head;
            }
        }
        
        ListNode *new_next_group = reverseKGroup(next_group, k);
        
        ListNode *prev = NULL, *cur = head;
        
        while(cur != next_group) {
            ListNode *next = cur->next;
            cur->next = prev ? prev : new_next_group; // link this group with next group if prev == NULL
            prev = cur;
            cur = next;
        }
        
        return prev; // new head of this group
    }
    // similar implementation.
    ListNode *reverseKGroup(ListNode *head, int k) {
        if (head == NULL || head->next == NULL || k<=1) return head;
        
        // find the head of next group
        ListNode *next_group = head;
        for(int i=1; i<=k; ++i) {
            if (next_group) {
                next_group = next_group->next;
            }
            else {
                return head;
            }
        }
        
        // reverse the first group that starts from head
        // 这里采用的不是头插法，而是普通的链表翻转方法，值得注意的是，tail2=head。
        ListNode *head2 = NULL, *tail2 = head;
        while(head!=next_group) {
            ListNode *next = head->next;
            head->next = head2;
            head2 = head;
            head = next;
        }
        tail2->next = reverseKGroup(next_group, k);
        
        return head2;
    }
#endif
};
