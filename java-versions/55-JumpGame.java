/*
Medium
You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.

Return true if you can reach the last index, or false otherwise.

Example 1:
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:
Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 
Constraints:
1 <= nums.length <= 104
0 <= nums[i] <= 105
*/

class Solution {
    public boolean canJump(int[] nums) {
        return canJump_dp(nums);
    }
    
    public boolean canJump_greedy(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; ++i) {
            farthest = Math.max(farthest, nums[i] + i);
            if (farthest <= i) return false;
        }
        return farthest >= nums.length - 1;
    }
    
    // dp[i] 表示达到i位置时剩余的跳力，若到达某个位置时跳力为负了，说明无法到达该位置。
    public boolean canJump_dp(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = 1; i < nums.length; ++i) {
            dp[i] = Math.max(dp[i - 1], nums[i - 1]) - 1;
            if (dp[i] < 0) return false;
        }
        return true;
    }
}

