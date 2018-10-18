/*
Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.

Example 1:
Input: [3, 1, 4, 1, 5], k = 2
Output: 2
Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
Although we have two 1s in the input, we should only return the number of unique pairs.
Example 2:
Input:[1, 2, 3, 4, 5], k = 1
Output: 4
Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
Example 3:
Input: [1, 3, 1, 5, 4], k = 0
Output: 1
Explanation: There is one 0-diff pair in the array, (1, 1).
Note:
The pairs (i, j) and (j, i) count as the same pair.
The length of the array won't exceed 10,000.
All the integers in the given input belong to the range: [-1e7, 1e7].

*/


/* 
idea:
1. k must be >=0 since it is finding absolute value.
2. if k == 0, then we need to use map and only count when it appears twice.
    test cases: [1, 3, 1, 2], [1, 1, 1, 3, 4]
3. if k > 0, we just need a set
   if the number is already in the set, it means it is already processed, then we skip it.
   otherwise, we try to find its pair in the set. After that, insert it into the set.

Time: O(n)
Spcae: O(n): test case [1,2,3,4,5]
*/
class Solution {
public:
    int findPairs(vector<int>& nums, int k) {
        if (k<0) return 0;
        
        int pairCount = 0;
        if (k== 0) {
            std::unordered_map<int, int> m;
            for (int i : nums) {
                m[i] ++;
                if (m[i] == 2) {
                    ++pairCount;
                }
            }
        } else {
            std::unordered_set<int> s;
            for(int i : nums) {
                if (!s.count(i)) {
                    if (s.count(i+k)) ++pairCount;
                    if (s.count(i-k)) ++pairCount;
                    s.insert(i);
                }
            }
        }
        return pairCount;;
    }
};
