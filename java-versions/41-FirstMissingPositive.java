/*
Hard
Given an unsorted integer array nums, return the smallest missing positive integer.

You must implement an algorithm that runs in O(n) time and uses constant extra space.

Example 1:
Input: nums = [1,2,0]
Output: 3
Explanation: The numbers in the range [1,2] are all in the array.

Example 2:
Input: nums = [3,4,-1,1]
Output: 2
Explanation: 1 is in the array but 2 is missing.

Example 3:
Input: nums = [7,8,9,11,12]
Output: 1
Explanation: The smallest positive integer 1 is missing.
 
Constraints:
1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
*/

class Solution {
    /*
    Algorithm

    Now everything is ready to write down the algorithm.

    Check if 1 is present in the array. If not, you're done and 1 is the answer.
    Replace negative numbers, zeros, and numbers larger than n by 1s.
    Walk along the array. Change the sign of a-th element if you meet number a. Be careful with duplicates : do sign change only once. Use index 0 to save an information about presence of number n since index n is not available.
    Walk again along the array. Return the index of the first positive element.
    If nums[0] > 0 return n.
    If on the previous step you didn't find the positive element in nums, that means that the answer is n + 1.
    */
    // very very good!
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // check if 1 exist in nums
        boolean hasOne = false;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 1) {
                hasOne = true;
                break;
            }
        }
        if (!hasOne) return 1;
        
        // clean up data: negative, 0, or more than n
        for (int i = 0; i < n; ++i) {
            if (nums[i] <= 0 || nums[i] > n) {
                nums[i] = 1;
            }
        }
        
        // go through nums[i], change nums[nums[i]] to negative
        for (int i = 0; i < n; ++i) {
            int j = nums[i];
            if (j < 0) j = -j;
            
            if (j == n) {
                if (nums[0] > 0) {
                    nums[0] = -nums[0];
                }
            } else {
                if (nums[j] > 0) {
                    nums[j] = -nums[j];
                }
            }
        }
        
        for (int i = 1; i < n; ++i) {
            if (nums[i] > 0) {
                return i;
            }
        }
        
        if (nums[0] > 0) return n;
        return n + 1;
    }
}

