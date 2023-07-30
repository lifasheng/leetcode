/*
Given a non-empty array of integers, return the k most frequent elements.

For example,
Given [1,1,1,2,2,3] and k = 2, return [1,2].

Note: 
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
*/

/*

when we meet top k issue, we should consider priority_queue.

STL priority_queue: template parameter, comparator!!!

*/

typedef pair<int, int> P;
struct Order {
    bool operator()(P const& a, P const& b) const {
        return a.second < b.second || a.second == b.second && a.first < b.first;
    }
};

class Solution {
public:
    vector<int> topKFrequent(vector<int>& nums, int k) {
        std::unordered_map<int, int> m;
        std::priority_queue< P, vector<P>, Order > Q;
        for (auto i : nums) {
            m[i] ++;
        }
        
        for (auto i : m) {
            Q.push(i);
        }

        vector<int> result;
        while(!Q.empty()) {
            result.push_back(Q.top().first);
            Q.pop();
            if (--k == 0) break;
        }
        
        return result;
    }
};
