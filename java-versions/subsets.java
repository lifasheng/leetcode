/*
78. Subsets
Medium
Given an integer array nums, return all possible subsets (the power set).

The solution set must not contain duplicate subsets.

 

Example 1:

Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
Example 2:

Input: nums = [0]
Output: [[],[0]]
*/


// Time: O(2^N) , Space: O(N)
class Solution {
    private void recursiveHelper(int[] nums, int start, boolean[] bitmap, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> list = new ArrayList<>();
            for(int i=0; i<bitmap.length; ++i) {
                if (bitmap[i]) {
                    list.add(nums[i]);
                }
            }
            result.add(list);
            return;
        }
        
        recursiveHelper(nums, start+1, bitmap, result);
        bitmap[start] = true;
        recursiveHelper(nums, start+1, bitmap, result);
        bitmap[start] = false;
    }
    
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] bitmap = new boolean[nums.length];
        recursiveHelper(nums, 0, bitmap, result);
        return result;
    }
}


