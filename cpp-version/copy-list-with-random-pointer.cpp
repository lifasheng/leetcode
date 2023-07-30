/**
 * Definition for singly-linked list with a random pointer.
 * struct RandomListNode {
 *     int label;
 *     RandomListNode *next, *random;
 *     RandomListNode(int x) : label(x), next(NULL), random(NULL) {}
 * };
 */
class Solution {
public:
#define M2
#ifdef M1
// use a hash map to store the new linked list address
// O(n) time, O(n) space
    RandomListNode *copyRandomList(RandomListNode *head) {
        if (!head) return head;
        
        typedef RandomListNode* PTR;
        unordered_map<PTR, PTR> m;
        
        RandomListNode dummy(-1);
        dummy.next = head;
        RandomListNode *p = head, *q = &dummy;
        
        // copy linked list with next member is copied, but random member is not.
        while(p) {
            RandomListNode *temp = new RandomListNode(p->label);
            q->next = temp;
            q = temp;
            
            m[p] = q; // key: old node address, value: copied node address
            
            p = p->next;
        }
        
        // adjust random member according to address map.
        p = head;
        while(p) {
            m[p]->random = m[p->random];
            p = p->next;
        }
        
        return dummy.next;
    }
#endif
#ifdef M2
    RandomListNode *copyRandomList(RandomListNode *head) {
        if (!head) return head;
        
        // 先在每个结点后面插入一个自己的copy node
        // 1->2->3 => 1->1'->2->2'->3->3'
        // 拷贝结点的random字段为NULL。
        RandomListNode *cur = head;
        while(cur) {
            RandomListNode *node = new RandomListNode(cur->label);
            node->next = cur->next;
            cur->next = node;
            cur = node->next;
        }
        
        // 更新每个拷贝结点的random字段。
        cur = head;
        while(cur) {
            if (cur->random) {
                cur->next->random = cur->random->next;
            }
            
            cur = cur->next->next;
        }
        
        //分拆两个单链表。
        RandomListNode dummy(-1);
        RandomListNode *new_cur = &dummy;
        cur = head;
        while(cur) {
            new_cur->next = cur->next;
            new_cur = new_cur->next;
            cur->next = new_cur->next;
            cur = cur->next;
        }
        
        return dummy.next;
    }
#endif
};
