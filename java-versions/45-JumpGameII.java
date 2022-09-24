/*
Medium

Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
Each element in the array represents your maximum jump length at that position.
Your goal is to reach the last index in the minimum number of jumps.
You can assume that you can always reach the last index.

Example 1:
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:
Input: nums = [2,3,0,1,4]
Output: 2

Constraints:
1 <= nums.length <= 104
0 <= nums[i] <= 1000
*/

class Solution {
    // very very good!
    public int jump(int[] nums) {
        return jump_3(nums);
    }
    
    // dfs + memo
    // 该算法的时间复杂度是 递归深度 × 每次递归需要的时间复杂度，即 O(N^2)
    private int jump_1(int[] nums) {
        Integer[] memo = new Integer[nums.length];
        return dfs(nums, 0, memo);
    }
    
    private int dfs(int[] nums, int start, Integer[] memo) {
        if (start >= nums.length - 1) return 0;
        
        if (memo[start] != null) return memo[start];
        int minSteps = nums.length;
        for (int i = 1; i <= nums[start]; ++i) {
            int d = dfs(nums, start + i, memo);
            if (d < 0) {
                continue;
            }
            minSteps = Math.min(minSteps, d + 1);
        }
        
        memo[start] = (minSteps == nums.length) ? -1 : minSteps;
        return memo[start];
    }
    
    // dp,  O(N^2)  https://www.jiuzhang.com/solution/jump-game-ii/
    /*
    解题思路
    比较容易想到的是动态规划的方法。
    定义dp状态，dp[i]表示跳到下标为i的位置最少需要几次跳跃.
    初始化状态：dp[0]设为0，其他设成一个不可能的值（正无穷或者nums.length + 1）
    状态转移方程: dp[i] = min(dp[i], dp[j] + 1)，其中dp[j]表示从下标[0,i)中能够能够走到位置i的所有情况。
    */
    private int jump_2(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = 1; i < nums.length; ++i) {
            dp[i] = nums.length;
            for (int j = 0; j < i; ++j) {
                if (nums[j] + j >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length - 1];
    }
    
    // greedy O(N),有点看不懂
    /*
    解题思路
    我们利用贪心思想，实时维护最远可达的位置rightmost，和上次起跳点能到达的最远边界end。step是跳跃次数，即最终的返回结果。
    依次遍历每个位置i，以i作为起跳点，假设这是第step次跳跃，已知第step-1次跳跃时能跳到的最远距离是end
    首先更新rightmost = max(rightmost, A[i] + i)，实时更新能跳到的最远的位置。
    如果end == i，说明已经到达了第step-1次跳跃时能跳到的边界，跳跃次数step就加1，end更新为rightmost作为下个边界。
    跳到终点后，返回跳跃次数step。

    举例分析
    以A = [2, 3, 1, 1, 4]为例。
    i = 0时，以A[0]为起跳点，最远能跳到第rightmost = 2个位置。因为end == i，所以跳的次数加1，step为1。同时，end更新为2，表明下次跳跃的边界。
    i = 1时，更新rightmost = 1 + A[1] = 4。
    i = 2时，最远位置rightmost没有被更新。因为end == i，说明到达边界，所以跳的次数加1，step为2。
    i = 3时，最远位置rightmost没有被更新。
    到达A[4]，遍历结束，最终结果step为2。
    */
    private int jump_3(int[] nums) {
        int end = 0;
        int rightmost = 0; 
        int step = 0;
        for(int i = 0; i < nums.length - 1; i++){
            // 计算能跳到的最远的位置
            rightmost = Math.max(rightmost, nums[i] + i); 
            //遇到边界，就更新边界，并且步数加1
            if (i == end){ 
                end = rightmost;
                step++;
            }
        }
        return step;
    }

    // https://www.youtube.com/watch?v=dJ7sWiOoK7g
    // BFS, 很好理解，very very good!!!  O(N)
    private int jump_4(int[] nums) {
        int jumps = 0;
        int left = 0;
        int right = 0;
        
        while (right < nums.length - 1) {
            int farthest = 0;
            for (int i = left; i <= right; ++i) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            
            left = right + 1;
            right = farthest;
            ++ jumps;
        }
        return jumps;
    }
}

