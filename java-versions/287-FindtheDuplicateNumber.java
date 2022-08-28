/*
Medium

Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
There is only one repeated number in nums, return this repeated number.
You must solve the problem without modifying the array nums and uses only constant extra space.

Example 1:
Input: nums = [1,3,4,2,2]
Output: 2

Example 2:
Input: nums = [3,1,3,4,2]
Output: 3

Constraints:
1 <= n <= 105
nums.length == n + 1
1 <= nums[i] <= n
All the integers in nums appear only once except for precisely one integer which appears two or more times.
 
Follow up:
How can we prove that at least one duplicate number must exist in nums?
Can you solve the problem in linear runtime complexity?
*/

class Solution {
    public int findDuplicate(int[] nums) {
        return findDuplicate_useSet(nums);
    }
    
    // Solution 1: use set, O(N) space
    private int findDuplicate_useSet(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.contains(i)) {
                return i;
            } else {
                set.add(i);
            }
        }
        return -1;
    }
    
    // Solution 2: negative marking, very good! O(1) space
    // 注意：这道题不能简单地用xor，因为数组里面的数只要在[1,n]范围即可，不是书一定会出现1..n这n个数。
    // [1,2,2,2,2] 也是合法的，所以用xor就不行了，一般就想到用negative marking的方法。
    private int findDuplicate_negativeMarking(int[] nums) {
        int n = nums.length;
        int duplicate = -1;
        for (int i = 0; i < n; ++i) {
            int j = Math.abs(nums[i]);
            if (nums[j] > 0) {
                nums[j] = -nums[j];
            } else {
                duplicate = Math.abs(nums[i]);
            }
        }
        
        // restore nums
        for (int i = 0; i < n; ++i) {
            nums[i] = Math.abs(nums[i]);
        }
        return duplicate;
    }
}

