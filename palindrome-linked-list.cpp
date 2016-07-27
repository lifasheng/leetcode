#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <stack>
#include <unordered_map>
using namespace std;

/*
 Given a singly linked list, determine if it is a palindrome.
*/

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};
void printList(ListNode *head);

class Solution {
public:
#if 0
    // 用stack来辅助，这样就不用翻转后半部分链表, 从而也就不会改变原来的链表结构
    bool isPalindrome(ListNode* head) {
        if (head == NULL || head->next == NULL) return true;
        ListNode *mid = findMiddleNode(head);
        ListNode *secondHalfHead = mid->next;
        stack<ListNode*> stk;
        while(secondHalfHead) {
            stk.push(secondHalfHead);
            secondHalfHead = secondHalfHead->next;
        }
        ListNode *p = head;
        while(!stk.empty()) { // note that 1 2 1 case
            ListNode *p2 = stk.top();
            stk.pop();
            if (p->val != p2->val) return false;
            p = p->next;
        }
        return true;
    }

#else

    // 翻转后半部分链表，判断完之后再翻转回去，保持原来的链表结构.
    // 注意链表可能不是palindrome的，所以如果我们不能破坏其结构。
    // 另外，我们也不能通过翻转整个链表来做，因为翻转整个链表，那就没法比较了。
    bool isPalindrome(ListNode* head) {
        if (head == NULL || head->next == NULL) return true;
        ListNode *mid = findMiddleNode(head);
        ListNode *secondHalfHead = reverseList(mid->next);
        ListNode *p1 = head, *p2 = secondHalfHead;
        while(p2) { // note that 1 2 1 case
            if (p1->val != p2->val) return false;
            p1 = p1->next;
            p2 = p2->next;
        }
        mid->next = reverseList(secondHalfHead);

        return true;
    }

#endif

    ListNode* reverseList(ListNode *head) {
        if (head == NULL || head->next == NULL) return head;

        ListNode *head2 = NULL;
        ListNode *p1 = head, *p2 = head->next;
        while(p1) {
            p1->next = head2;
            head2 = p1;
            p1 = p2;
            if (p2) {
                p2 = p2->next;
            }
        }
        return head2;

    }
    ListNode* findMiddleNode(ListNode *head) {
        ListNode dummy(-1);
        dummy.next = head;
        ListNode *fast = &dummy, *slow = &dummy;
        while(fast && fast->next) {
            fast = fast->next->next;
            slow = slow->next;
        }
        return slow;
    }
};

ListNode* buildList(vector<int> nums) {
    ListNode *head = NULL;
    ListNode *tail = NULL;
    for(auto i : nums) {
        ListNode *node = new ListNode(i);
        if (head == NULL) {
            head = tail = node;
        }
        else {
            tail->next = node;
            tail = node;
        }
    }

    return head;
}
void deleteList(ListNode *head) {
    while(head) {
        ListNode *p = head;
        head = head->next;
        delete p;
    }
}

void printList(ListNode *head) {
    ListNode *p = head;
    while(p) {
        cout << p->val << " ";
        p = p->next;
    }
}

int main() {
    Solution solution;

    {
    vector<int> nums = {1,2,3,2,1};
    ListNode *head = buildList(nums);
    printList(head); cout << endl;
    assert(solution.isPalindrome(head));
    printList(head); cout << endl;
    deleteList(head);
    }
    {
    vector<int> nums = {1,2,2,1};
    ListNode *head = buildList(nums);
    printList(head); cout << endl;
    assert(solution.isPalindrome(head));
    printList(head); cout << endl;
    deleteList(head);
    }
    {
    vector<int> nums = {1,1};
    ListNode *head = buildList(nums);
    printList(head); cout << endl;
    assert(solution.isPalindrome(head));
    printList(head); cout << endl;
    deleteList(head);
    }
    {
    vector<int> nums = {1};
    ListNode *head = buildList(nums);
    printList(head); cout << endl;
    assert(solution.isPalindrome(head));
    printList(head); cout << endl;
    deleteList(head);
    }
    {
    vector<int> nums = {1, 2};
    ListNode *head = buildList(nums);
    printList(head); cout << endl;
    assert(!solution.isPalindrome(head));
    printList(head); cout << endl;
    deleteList(head);
    }
    {
    assert(solution.isPalindrome(NULL));
    }

    return 0;
}
