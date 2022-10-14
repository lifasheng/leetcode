/*
Medium
Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
A subarray is a contiguous part of an array.

Example 1:
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.

Example 2:
Input: nums = [1]
Output: 1

Example 3:
Input: nums = [5,4,-1,7,8]
Output: 23

Constraints:
1 <= nums.length <= 105
-104 <= nums[i] <= 104
 
Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
*/

class Solution {
    public int maxSubArray(int[] nums) {
        return maxSubArray_recursive(nums);
    }
    
    // solution 1: brute force: O(N^2), TLE
    public int maxSubArray_bruteForce(int[] nums) {
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            int curMaxSum = 0;
            for (int j = i; j < n; ++j) {
                curMaxSum += nums[j];
                maxSum = Math.max(maxSum, curMaxSum);
            }
        }
        return maxSum;
    }
    
    // solution 2: dp, very good!
    public int maxSubArray_onepass(int[] nums) {
        int n = nums.length;
        int maxSum = nums[0];
        int sum = nums[0];
        for (int i = 1; i < n; ++i) {
            if (sum > 0) {
                sum += nums[i];
            } else {
                sum = nums[i];
            }
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
    
    // solution 3: divide and conquer, O(NlogN), very good!
    public int maxSubArray_recursive(int[] nums) {
        return maxSubArray_merge(nums, 0, nums.length - 1);
    }
    
    private int maxSubArray_merge(int[] nums, int start, int end) {
        if (start > end) return Integer.MIN_VALUE;
        
        int mid = start + (end - start) / 2;
        
        int bestLeft = 0;
        int cur = 0;
        for (int i = mid - 1; i >= start; --i) {
            cur += nums[i];
            bestLeft = Math.max(bestLeft, cur);
        }
        
        int bestRight = 0;
        cur = 0;
        for (int i = mid + 1; i <= end; ++i) {
            cur += nums[i];
            bestRight = Math.max(bestRight, cur);
        }
        
        int combined = nums[mid] + bestLeft + bestRight;
        
        int left = maxSubArray_merge(nums, start, mid - 1);
        int right = maxSubArray_merge(nums, mid + 1, end);
        
        return Math.max(combined, Math.max(left, right)); 
    }
    
}

