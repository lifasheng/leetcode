/*
16. 3Sum Closest
Medium
Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

 

Example 1:

Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
*/

class Solution {
    // 排序，左右夹逼，O(n^2)
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        
        int n = nums.length;
        int result = Integer.MAX_VALUE;
        int min_diff = Integer.MAX_VALUE;
        
        for(int i=0; i<n-2; ++i) {
            if (i>0 && nums[i-1] == nums[i]) { // 可有可无，因为重复了也不影响结果
                continue;
            }
            
            int p=i+1, q=n-1;
            while(p<q) {
                int sum = nums[i]+nums[p]+nums[q];
                int diff = Math.abs(target-sum);
                if (diff < min_diff) {
                    min_diff = diff;
                    result = sum;
                }
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    ++p;
                } else {
                    --q;
                }
            }
        }
        return result;
    }
}

