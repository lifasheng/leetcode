/*
very very good!!!

Given an integer array nums, find the subarray with the largest sum, and return its sum.

Example 1:
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: The subarray [4,-1,2,1] has the largest sum 6.

Example 2:
Input: nums = [1]
Output: 1
Explanation: The subarray [1] has the largest sum 1.

Example 3:
Input: nums = [5,4,-1,7,8]
Output: 23
Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 
Constraints:
1 <= nums.length <= 105
-104 <= nums[i] <= 104
 
Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
*/


class Solution {
    public int maxSubArray(int[] nums) {
        return solution3(nums);
    }

    // O(n^2) Time Limit Exceeded
    private int solution1(int[] nums) {
        int n = nums.length;

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < n; ++i) {
            // single item case
            maxSum = Math.max(maxSum, nums[i]);

            // multiple items case
            int subSum = nums[i];
            for (int j = i + 1; j < n; ++j) {
                subSum += nums[j];
                maxSum = Math.max(maxSum, subSum);
            }
        }

        return maxSum;
    }

    /*
    动态规划：

    不能定义为： nums[0..i] 中的「最大的子数组和」为 dp[i]。因为这样没法定义状态转移方程。
    因为子数组一定是连续的，按照这个dp 数组定义，并不能保证 nums[0..i] 中的最大子数组与 nums[i+1] 是相邻的，
    也就没办法从 dp[i] 推导出 dp[i+1]。


    而应该定义为：以 nums[i] 为结尾的「最大子数组和」为 dp[i]。
    这样状态转移方程为： dp[i] = max(nums[i], dp[i-1] + nums[i]) // 要么自成一派，要么和前面的子数组合并
    */
    private int solution2(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        dp[0] = nums[0];

        for (int i = 1; i < n; ++i) {
            dp[i] = Math.max(nums[i], dp[i-1] + nums[i]);
        }

        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            maxSum = Math.max(maxSum, dp[i]);
        }

        return maxSum;
    }


    // 分治法
    private int solution3(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        return divideAndConquer(nums, 0, n-1);
    }

    private int divideAndConquer(int[] nums, int low, int high) {
        if (low == high) return nums[low];
        if (low+1 == high) {
            return Math.max(Math.max(nums[low], nums[high]), nums[low] + nums[high]);
        }

        int mid = (low + high) / 2;
        int leftMaxSum = divideAndConquer(nums, low, mid);
        int rightMaxSum = divideAndConquer(nums, mid+1, high);

        int leftSuffix = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid; i >= low; --i) {
            leftSum += nums[i];
            leftSuffix = Math.max(leftSuffix, leftSum);
        }

        int rightPrefix = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid+1; i <= high; ++i) {
            rightSum += nums[i];
            rightPrefix = Math.max(rightPrefix, rightSum);
        }

        return Math.max(Math.max(leftMaxSum, rightMaxSum), leftSuffix + rightPrefix);
    }
}




/*
Maximum Subarray Sum: Kadane’s Algorithm
https://www.interviewbit.com/blog/maximum-subarray-sum/

Q. Is Kadane’s algorithm Dynamic Programming?
A. Yes, It is an iterative dynamic programming algorithm.

public static int maximumSubarraySum(int[] arr) {
    int n = arr.length;
    int maxSum = Integer.MIN_VALUE;
    int currSum = 0;

    for (int i = 0; i < n; i++) {
        currSum += arr[i];

        if (currSum > maxSum) {
            maxSum = currSum;
        }

        if (currSum < 0) {
            currSum = 0;
        }
    }

    return maxSum;
}

*/


