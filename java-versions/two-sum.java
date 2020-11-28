/*
1. Two Sum
Easy
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

 

Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Output: Because nums[0] + nums[1] == 9, we return [0, 1].
Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]
Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]

*/


class Solution {
    // two pass hashmap
    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> m = new HashMap<>();
        for(int i=0; i<nums.length; ++i) {
            m.put(nums[i], i);
        }
        for(int i=0; i<nums.length; ++i) {
            if (m.containsKey(target-nums[i])) {
                int j = m.get(target-nums[i]);
                if (i!=j) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("no such result");
    }
    
    // one pass hashmap, faster
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> m = new HashMap<>();
        for(int i=0; i<nums.length; ++i) {
            if (m.containsKey(target-nums[i])) {
                int j = m.get(target-nums[i]);
                if (i!=j) {
                    return new int[]{i, j};
                }
            }
            m.put(nums[i], i);
        }
        throw new RuntimeException("no such result");
    }
    
    public int[] twoSum(int[] nums, int target) {
        return twoSum2(nums, target);
    }
}


