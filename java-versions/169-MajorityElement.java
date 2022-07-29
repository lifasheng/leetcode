/*
Given an array nums of size n, return the majority element.

The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.

Example 1:
Input: nums = [3,2,3]
Output: 3

Example 2:
Input: nums = [2,2,1,1,1,2,2]
Output: 2

Constraints:
n == nums.length
1 <= n <= 5 * 104
-109 <= nums[i] <= 109
 
Follow-up: Could you solve the problem in linear time and in O(1) space?
*/

class Solution {
    public int majorityElement(int[] nums) {
        return majorityElement3(nums);
    }
    
    // using map
    private int majorityElement1(int[] nums) {
        if (nums.length == 1) return nums[0];
        
        Map<Integer, Integer> m = new HashMap<>();
        for (int i : nums) {
            if (m.containsKey(i)) {
                m.put(i, m.get(i) + 1);
                if (m.get(i) > nums.length / 2) return i;
            } else {
                m.put(i, 1);
            }
        }
        return -1;
    }
    
    // sort
    private int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
    
    // O(N) time, O(1) space
    private int majorityElement3(int[] nums) {
        Integer target = null;
        int count = 0;
        for (int i : nums) {
            if (count == 0) {
                target = i;
            }
            if (i == target) {
                ++ count;
            } else {
                -- count;
            }
        }
        return target;
    }
}


