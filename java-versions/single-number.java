/*
136. Single Number
Easy

5324

181

Add to List

Share
Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.

Follow up: Could you implement a solution with a linear runtime complexity and without using extra memory?

 

Example 1:

Input: nums = [2,2,1]
Output: 1
Example 2:

Input: nums = [4,1,2,1,2]
Output: 4
Example 3:

Input: nums = [1]
Output: 1
*/

class Solution {
    public int singleNumber1(int[] nums) {
        Map<Integer, Integer> m = new HashMap<>();
        for(int i : nums) {
            m.put(i, m.getOrDefault(i, 0)+1);
        }
        for(int i: nums) {
            if (m.get(i) == 1) {
                return i;
            }
        }
        throw new RuntimeException("bad request");
    }
    
    public int singleNumber2(int[] nums) {
        int result = 0;
        for(int i : nums) {
            result ^= i;
        }
        return result;
    }
    
    public int singleNumber(int[] nums) {
        return singleNumber2(nums);
    }
}

