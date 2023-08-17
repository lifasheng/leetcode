/*
Given an integer array nums, find a subarray that has the largest product, and return the product.
The test cases are generated so that the answer will fit in a 32-bit integer.

Example 1:
Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.

Example 2:
Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 
Constraints:
1 <= nums.length <= 2 * 104
-10 <= nums[i] <= 10
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
*/

class Solution {
    public int maxProduct(int[] nums) {
        return solution1(nums);
    }

    /*
    // [-2,3,-4]
    // [-2 3 24]
    // [-2 -6 -4]

    https://leetcode.wang/leetcode-152-Maximum-Product-Subarray.html
    我们先定义一个数组 dpMax，用 dpMax[i] 表示以第 i 个元素的结尾的子数组，乘积最大的值，也就是这个数组必须包含第 i 个元素。

    那么 dpMax[i] 的话有几种取值。
    当 nums[i] >= 0 并且dpMax[i-1] > 0，dpMax[i] = dpMax[i-1] * nums[i]
    当 nums[i] >= 0 并且dpMax[i-1] < 0，此时如果和前边的数累乘的话，会变成负数，所以dpMax[i] = nums[i]
    当 nums[i] < 0，此时如果前边累乘结果是一个很大的负数，和当前负数累乘的话就会变成一个更大的数。所以我们还需要一个数组 dpMin 来记录以第 i 个元素的结尾的子数组，乘积最小的值。
        当dpMin[i-1] < 0，dpMax[i] = dpMin[i-1] * nums[i]
        当dpMin[i-1] >= 0，dpMax[i] = nums[i]
    当然，上边引入了 dpMin 数组，怎么求 dpMin 其实和上边求 dpMax 的过程其实是一样的。

    按上边的分析，我们就需要加很多的 if else来判断不同的情况，这里可以用个技巧。

    我们注意到上边dpMax[i] 的取值无非就是三种，dpMax[i-1] * nums[i]、dpMin[i-1] * nums[i] 以及 nums[i]。
    所以我们更新的时候，无需去区分当前是哪种情况，只需要从三个取值中选一个最大的即可。

    dpMax[i] = max(dpMax[i-1] * nums[i], dpMin[i-1] * nums[i], nums[i]);
    求 dpMin[i] 同理。
    dpMin[i] = min(dpMax[i-1] * nums[i], dpMin[i-1] * nums[i], nums[i]);
    */
    private int solution1(int[] nums) {
        int n = nums.length;

        int[] dpMax = new int[n];
        int[] dpMin = new int[n];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];

        for (int i = 1; i < n; ++i) {
            dpMax[i] = Math.max(nums[i], Math.max(dpMax[i-1] * nums[i], dpMin[i-1] * nums[i]));
            dpMin[i] = Math.min(nums[i], Math.min(dpMax[i-1] * nums[i], dpMin[i-1] * nums[i]));
        }

        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            maxSum = Math.max(maxSum, dpMax[i]);
        }

        return maxSum;
    }

    private int solution2(int[] nums) {
        int max=nums[0]; // 当前区间最大乘积
        int min=nums[0]; // 当前区间最小乘积
        int res=max; // 最大区间乘积
        // 从下标1开始循环所有数字
        for(int i=1;i<nums.length;i++){
            int n=nums[i]; // 当前数字
            int ma=n*max; // 当前数字乘以最大乘积
            int mi=n*min; // 当前数字乘以最小乘积
            max=Math.max(n,Math.max(ma,mi)); // 更新最大乘积
            min=Math.min(n,Math.min(ma,mi)); // 更新最小乘积
            res=Math.max(res,max); // 更新最大区间乘积
        }
        return res;
    }
}


