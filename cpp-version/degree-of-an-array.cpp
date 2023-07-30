/*
Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.

Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.

Example 1:
Input: [1, 2, 2, 3, 1]
Output: 2
Explanation: 
The input array has a degree of 2 because both elements 1 and 2 appear twice.
Of the subarrays that have the same degree:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
The shortest length is 2. So return 2.
Example 2:
Input: [1,2,2,3,1,4,2]
Output: 6
Note:

nums.length will be between 1 and 50,000.
nums[i] will be an integer between 0 and 49,999.

*/

/*
idea: we use a map to help.
for each number, we will record its first appearance pos, and its appearance times.

[1,2,2,3,1,4,2]:
1->{0, 1} degree=1, minLen=1
2->{1, 1} degree=1, minLen=1
2->{1, 2} degree=2, minLen=2
3->{3, 1} degree=2, minLen=2
1->{0, 2} degree=2, minLen=2, here we have two numbers with degree=2, but minLen = 2 instead of 5 (nums[4] - nums[0])
4->{5, 1} degree=2, minLen=2
2->{1, 3} degree=3, minLen=6


[1,2,2,3,1]:
1->{0, 1} degree=1, minLen=1
2->{1, 1} degree=1, minLen=1
2->{1, 2} degree=2, minLen=2
3->{3, 1} degree=2, minLen=2
1->{0, 2} degree=2, minLen=2, here we have two numbers with degree=2, but minLen = 2 instead of 5 (nums[4] - nums[0])

*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include<stdio.h>
using namespace std;


class Solution {
public:
    int findShortestSubArray(vector<int>& nums) {
        // in the pair, first indicates the first pos of that number;
        // second indicates how many times the number appears.
        unordered_map<int, std::pair<int, int>> positionMap;
        int degree = 1;
        int minLen = 1;
        for(int i=0; i<nums.size(); ++i) {
            auto iter = positionMap.find(nums[i]);
            if (iter != positionMap.end()) {
                std::pair<int, int> pos = iter->second;
                pos.second++;
                positionMap[nums[i]] = pos;

                if (pos.second > degree) {
                    degree = pos.second;
                    minLen = i-pos.first+1;
                } else if (pos.second == degree) {
                    minLen = min(minLen, i-pos.first+1);
                }        
            } else {
                std::pair<int, int> pos(i, 1);
                positionMap[nums[i]] = pos;
            }
        }
        return minLen;
    }
};

int main() {
    Solution s;
    vector<int> v = {1,2,2,3,1,4,2};
    vector<int> v2 = {1, 2, 2, 3, 1};

    cout << s.findShortestSubArray(v) << endl;
    cout << s.findShortestSubArray(v2) << endl;

    return 0;
}


