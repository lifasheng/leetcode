/*
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
Note:
All elements in nums1 and nums2 are unique.
The length of both nums1 and nums2 would not exceed 1000.
*/



#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include<stdio.h>
using namespace std;


class Solution {
public:
    vector<int> nextGreaterElement(vector<int>& findNums, vector<int>& nums) {
        std::unordered_map<int, int> nextGreaterNum;
        for (size_t i = 0; i<nums.size(); ++i) {
            for (size_t j=i+1; j<nums.size(); ++j) {
                if (nums[j] > nums[i]) {
                    nextGreaterNum[nums[i]] = nums[j];
                    break;
                }
            }
        }

        vector<int> result(findNums.size());
        for (size_t i=0; i<findNums.size(); ++i) {
            result[i] = nextGreaterNum.count(findNums[i]) > 0 ? nextGreaterNum[findNums[i]] : -1;
        }

        return result;
    }
};


int main() {
    Solution s;
    vector<int> v1 = {4,1,2}; //{2, 4};
    vector<int> v2 = {1,3,4,2};//{1, 2, 3, 4};
    vector<int> v = s.nextGreaterElement(v1, v2);
    for(auto iter=v.begin(); iter!=v.end(); ++iter) { cout << *iter << "  " ;}
    cout << endl;
    return 0;
}


