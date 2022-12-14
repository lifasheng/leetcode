/*
Medium
Given an integer array nums, reorder it such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

You may assume the input array always has a valid answer.

Example 1:
Input: nums = [3,5,2,1,6,4]
Output: [3,5,1,6,2,4]
Explanation: [1,6,2,5,3,4] is also accepted.

Example 2:
Input: nums = [6,6,5,6,3,8]
Output: [6,6,5,6,3,8]

Constraints:
1 <= nums.length <= 5 * 104
0 <= nums[i] <= 104
It is guaranteed that there will be an answer for the given input nums.
 
Follow up: Could you solve the problem in O(n) time complexity?
*/

class Solution {
    public void wiggleSort(int[] nums) {
        wiggleSort_onePass(nums);
    }
    
    // O(NlogN)
    /*
    先给数组排个序，然后只要每次把第三个数和第二个数调换个位置，第五个数和第四个数调换个位置，以此类推直至数组末尾，这样就能完成摆动排序了
    */
    public void wiggleSort_usingSort(int[] nums) {
        Arrays.sort(nums);
        for (int i = 2; i < nums.length; i += 2) {
            swap(nums, i - 1, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // O(N)
    // https://www.cnblogs.com/grandyang/p/5177285.html
    /*
    根据题目要求的 nums[0] <= nums[1] >= nums[2] <= nums[3]....，可以总结出如下规律：
    当i为奇数时，nums[i] >= nums[i - 1]
    当i为偶数时，nums[i] <= nums[i - 1]
    那么只要对每个数字，根据其奇偶性，跟其对应的条件比较，如果不符合就和前面的数交换位置即可
    */
    public void wiggleSort_onePass(int[] nums) {
        for (int i = 1; i < nums.length; ++i) {
            if ((i % 2 == 1 && nums[i] < nums[i - 1]) || (i % 2 == 0 && nums[i] > nums[i - 1])) {
                swap(nums, i - 1, i);
            }
        }
    }
}

