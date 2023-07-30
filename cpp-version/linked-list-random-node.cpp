/*
Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.

Follow up:
What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently without using extra space?

Example:

// Init a singly linked list [1,2,3].
ListNode head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
Solution solution = new Solution(head);

// getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
solution.getRandom();
*/


/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */

// my solution
class Solution {
private:
    vector<ListNode*> vn;
public:
    /** @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node. */
    Solution(ListNode* head) {
        srand (time(NULL));
        while(head != NULL) {
            vn.push_back(head);
            head = head->next;
        }
    }
    
    /** Returns a random node's value. */
    int getRandom() {
        int nextInt = rand() % vn.size();
        return vn[nextInt]->val;
    }
};

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */


using "Reservoir sampling" O(1) space, O(n) time complexity，c++
according to the wiki https://en.wikipedia.org/wiki/Reservoir_sampling
here is sudo code for k size reservoir:

/*
  S has items to sample, R will contain the result
*/
ReservoirSample(S[1..n], R[1..k])
  // fill the reservoir array
  for i = 1 to k
      R[i] := S[i]

  // replace elements with gradually decreasing probability
  for i = k+1 to n
    j := random(1, i)   // important: inclusive range
    if j <= k
        R[j] := S[i]
you need to remember the range [ 0, i ] should be inclusive.

class Solution {
private:
    ListNode* head;
public:
    /** @param head The linked list's head. Note that the head is guanranteed to be not null, so it contains at least one node. */
    Solution(ListNode* head) {
        this->head = head;
    }
    
    /** Returns a random node's value. */
    int getRandom() {
        int res = head->val;
        ListNode* node = head->next;
        int i = 2;
        while(node){
            int j = rand()%i;
            if(j==0)
                res = node->val;
            i++;
            node = node->next;
        }
        return res;
    }
};
