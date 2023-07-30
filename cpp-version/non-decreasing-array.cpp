/*
Given an array with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

Example 1:
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:
Input: [4,2,1]
Output: False
Explanation: You can't get a non-decreasing array by modify at most one element.
Note: The n belongs to [1, 10,000].

*/

/*

我们可以从前往后扫描一遍，看看需要替换几次；再从后往前扫描一遍，看看需要替换几次。
如果两遍都是少于或等于1次，就ok。

替换的时候，替换成前一个值。
比如：[3, 4, 2, 3]

从前往后扫描时， 要递增，所以 4 => 3, 2 => 3, 替换2次
从后往前扫描时， 要递减，所以 4 => 2, 3 => 2, 替换2次

*/



#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include<stdio.h>
using namespace std;


class Solution {
private:
    int frontToEndPass(std::vector<int>& nums) {
        int n = 0;
        int nextNum = nums[0];

        for (int i=1; i<nums.size(); ++i) {
            if (nums[i] < nextNum) {
                ++n; // assume we set nums[i] = nextNum
            } else {
                nextNum = nums[i];
            }
        }
        return n;
    }

    int endToFrontPass(std::vector<int>& nums) {
        int n = 0;
        int size = nums.size();
        int nextNum = nums[size-1];

        for (int i=size-2; i>=0; --i) {
            if (nums[i] > nextNum) {
                ++n; // assume we set nums[i] = nextNum
            } else {
                nextNum = nums[i];
            }
        }
        return n;
    }

public:
    bool checkPossibility(vector<int>& nums) {
        if (nums.size() < 2) return true;

        int n1 = frontToEndPass(nums);
        int n2 = endToFrontPass(nums);
        cout << n1 << ", " << n2 << "   ";
        return n1<=1 || n2<=1;
    }
};


int main() {
    Solution s;
    vector<int> v1 = {4,2,1};    // false
    vector<int> v2 = {4,2,3};    // true
    vector<int> v3 = {3,4,2,3};  // false
    vector<int> v4 = {0,4,2,3};  // true
    vector<int> v5 = {3,4,2};    // true
    cout << s.checkPossibility(v1) << endl;
    cout << s.checkPossibility(v2) << endl;
    cout << s.checkPossibility(v3) << endl;
    cout << s.checkPossibility(v4) << endl;
    cout << s.checkPossibility(v5) << endl;
    return 0;
}
