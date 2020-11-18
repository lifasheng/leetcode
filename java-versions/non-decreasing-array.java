/*
Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n - 2).

 

Example 1:

Input: nums = [4,2,3]
Output: true
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:

Input: nums = [4,2,1]
Output: false
Explanation: You can't get a non-decreasing array by modify at most one element.
 

Constraints:

1 <= n <= 10 ^ 4
- 10 ^ 5 <= nums[i] <= 10 ^ 5

*/

/*
Test cases:
// [4,2,1]
// [4,2,3]
// [3,4,2,3]
// [1,3,1,3]
// [2,3,2,3]
// [5,7,1,8]

Idea:
When we find one decreasing case, nums[i] > nums[i+1],
we try to change nums[i+1] == nums[i], and continue to scan from left to right. If another decreasing case found, then need at least two modifications.

However, [4,2,3] is an example that the above thought does not work.
So, we also need to scan from right to left.
*/
class Solution {
    public boolean checkPossibility(int[] nums) {
        if (nums.length <= 1) {
            return true;
        }
        
        int leftToRightCount = 0;
        int prevNum = nums[0];
        for (int i=1; i<nums.length; i++) {
            if (prevNum <= nums[i]) {
                prevNum = nums[i];
            } else {
                leftToRightCount ++;
            }
        }
        
        int rightToLeftCount = 0;
        int nextNum = nums[nums.length-1];
        for (int i=nums.length-2; i>=0; i--) {
            if (nums[i] <= nextNum) {
                nextNum = nums[i];
            } else {
                rightToLeftCount ++;
            }
        }
        
        return leftToRightCount <= 1 || rightToLeftCount <= 1;
    }
}

