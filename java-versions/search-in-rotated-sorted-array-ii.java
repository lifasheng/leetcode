/*
81. Search in Rotated Sorted Array II
Medium

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).

You are given a target value to search. If found in the array return true, otherwise return false.

Example 1:

Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
Example 2:

Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
Follow up:

This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
Would this affect the run-time complexity? How and why?

*/

// [1,3,1,1,1]
// [1,1,1,3,1]
class Solution {
    public boolean search1(int[] nums, int target) {
        if (nums.length == 0) {
            return false;
        }
        int low = 0, high = nums.length-1;
        while(low <= high) {
            int m = (low+high)/2;
            if (nums[m] == target) {
                return true;
            } else if (nums[low] == nums[m]) { // handle duplicate
                ++low;
            } 
            else if (nums[low] < nums[m]) { // left side is ordered
                if (nums[low] <= target && target < nums[m]) {
                    high = m-1;
                } else {
                    low = m+1;
                }
            } else { // right side is ordered
                if (nums[m] < target && target <= nums[high]) {
                    low = m+1;
                } else {
                    high = m-1;
                }
            }
        }
        return false;
    }
    public boolean search(int[] nums, int target) {
        return search1(nums, target);
    }
}

Complexity Analysis

Time complexity : O(N)O(N) worst case, O(\log N)O(logN) best case, where NN is the length of the input array.

Worst case: This happens when all the elements are the same and we search for some different element. At each step, we will only be able to reduce the search space by 1 since arr[mid] equals arr[start] and it's not possible to decide the relative position of target from arr[mid]. Example: [1, 1, 1, 1, 1, 1, 1], target = 2.

Best case: This happens when all the elements are distinct. At each step, we will be able to divide our search space into half just like a normal binary search.


