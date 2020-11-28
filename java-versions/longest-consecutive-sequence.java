/*

128. Longest Consecutive Sequence
Hard

Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

Follow up: Could you implement the O(n) solution? 

 

Example 1:

Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
Example 2:

Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9

*/


class Solution {
    public int longestConsecutive(int[] nums) {        
        Map<Integer, Boolean> m = new HashMap<>();
        for(int i : nums) {
            m.put(i, false);
        }
        int result = 0;
        for(int i : m.keySet()) {
            if (m.get(i)) {
                continue;
            }
            m.put(i, true);
            int n = 1;
            for(int j=i-1; m.containsKey(j); --j) {
                ++n;
                m.put(j, true);
            }
            for(int j=i+1; m.containsKey(j); ++j) {
                ++n;
                m.put(j, true);
            }
            result = Math.max(result, n);
        }
        return result;
    }
}


