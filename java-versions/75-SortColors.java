/*
Medium

Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function.

Example 1:
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]

Example 2:
Input: nums = [2,0,1]
Output: [0,1,2]
 
Constraints:
n == nums.length
1 <= n <= 300
nums[i] is either 0, 1, or 2.

Follow up: Could you come up with a one-pass algorithm using only constant extra space?
*/

class Solution {
    // very very good!
    public void sortColors(int[] nums) {
        sortColors_3pass(nums);
    }
    
    // solution: 3 pass
    private void sortColors_3pass(int[] nums) {
        int c0 = 0, c1 = 0, c2 = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) ++ c0;
            else if (nums[i] == 1) ++ c1;
            else ++ c2;
        }
        int idx = 0;
        for (int i = 0; i < c0; ++i) {
            nums[idx ++] = 0;
        }
        for (int i = 0; i < c1; ++i) {
            nums[idx ++] = 1;
        }
        for (int i = 0; i < c2; ++i) {
            nums[idx ++] = 2;
        }
    }
    
    /*
    Algorithm
    Initialise the rightmost boundary of zeros : p0 = 0. During the algorithm execution nums[idx < p0] = 0.
    Initialise the leftmost boundary of twos : p2 = n - 1. During the algorithm execution nums[idx > p2] = 2.
    Initialise the index of current element to consider : curr = 0.

    While curr <= p2 :
    If nums[curr] = 0 : swap currth and p0th elements and move both pointers to the right.
    If nums[curr] = 2 : swap currth and p2th elements. Move pointer p2 to the left.
    If nums[curr] = 1 : move pointer curr to the right.
    */
    // solution: 1 pass
    private void sortColors_1pass(int[] nums) {
        int n = nums.length;
        int p0 = 0;
        int p2 = n - 1;
        int cur = 0;
        while (cur <= p2) {  // test case [1, 0]
            if (nums[cur] == 0) {
                swap(nums, cur, p0);
                ++ p0;
                ++ cur;
            } else if (nums[cur] == 2) {
                swap(nums, cur, p2);
                -- p2;
            } else {
                ++ cur;
            }
        }
    }
    
    private void swap(int[] nums, int cur, int p) {
        int tmp = nums[cur];
        nums[cur] = nums[p];
        nums[p] = tmp;
    }
}

