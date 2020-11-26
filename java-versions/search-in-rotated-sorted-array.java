/*

33. Search in Rotated Sorted Array
Medium

You are given an integer array nums sorted in ascending order, and an integer target.

Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

If target is found in the array return its index, otherwise, return -1.

 

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
Example 3:

Input: nums = [1], target = 0
Output: -1


*/



class Solution {
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int low = 0, high = nums.length-1;
        while(low <= high) {
            int m = (low+high)/2;
            if (nums[m] == target) {
                return m;
            } else if (nums[m] > target) {
                if (nums[m] <= nums[high]) { // right side is ordered
                    high = m-1;
                } else { // left side is ordered
                    if (nums[low] > target) {
                        low = m+1;
                    } else {
                        high = m-1;
                    }
                }
            } else { // nums[m] < target
                if (nums[low] <= nums[m]) { // left side is ordered
                    low = m+1;
                } else { // right side is ordered
                    if (nums[high] < target) {
                        high = m-1;
                    } else {
                        low = m+1;
                    }
                }
            }
        }
        return -1;
    }
}



