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
思路：
1.先找到第m个结点的前一个结点prev;
2.对m到n个结点进行翻转，采用两个辅助的指针来记录翻转之后的链表的头和尾;
3.将翻转部分和原来的链表中没有翻转的部分链接起来;
注意：这里翻转的方法是将每个待翻转结点插到新链表头结点之前，并更新头结点指针；
由于我们是翻转部分链表，所以需要记录新链表的尾结点指针，以便和原始链表连起来。
*/
    ListNode *reverseBetween(ListNode *head, int m, int n) {
        if (!head || m<1 || n<m) return NULL;
        
        ListNode dummy(-1);
        dummy.next = head;
        ListNode *prev = &dummy;
        
        int count = 1;
        
        // find mth node and its prev node
        while (count < m) {
            ++count;
            prev = prev->next;
        }
        
        ListNode *pm = prev->next;
        ListNode *head2 = NULL, *tail2 = NULL;
        ListNode *temp = NULL;
        // reverse nodes from mth to nth, and they are inserted into a new list [head2, tail2];
        while (count <= n) {
            ++count;
            ListNode *temp = pm;
            pm = pm->next;
            temp->next = head2;
            head2 = temp;
            if (!tail2) {
                tail2 = temp;
            }
        }
        
        // insert the reversed nodes into original list
        prev->next = head2;
        tail2->next = pm;
        
        return dummy.next;
    }
#endif
#ifdef M2
/*
思路：
1.先找到第m个结点的前一个结点prev;
2.对m到n个结点进行翻转，采用头插法;
注意：这里翻转的方法需要待翻转链表之前有一个辅助指针。
*/
    ListNode *reverseBetween(ListNode *head, int m, int n) {
        if (!head || m<1 || n<m) return NULL;
        
        ListNode dummy(-1);
        dummy.next = head;
        ListNode *prev = &dummy;
        
        int count = 1;
        
        // find mth node and its prev node
        while (count < m) {
            ++count;
            prev = prev->next;
        }
        
        ListNode *head2 = prev;
        prev = head2->next;
        ListNode *cur = prev->next;
        
        while (count < n) {
            ++count;
            prev->next = cur->next;
            cur->next = head2->next;
            head2->next = cur; // 头插法
            cur = prev->next;
        }
        
        return dummy.next;
    }
#endif
};
