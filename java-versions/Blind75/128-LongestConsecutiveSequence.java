/*
Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.

Example 1:
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

Example 2:
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9

Constraints:
0 <= nums.length <= 105
-109 <= nums[i] <= 109
*/

class Solution {
    public int longestConsecutive(int[] nums) {
        int n = nums.length;

        Map<Integer, Boolean> visited = new HashMap<>();
        for (int i : nums) {
            visited.put(i, false);
        }

        int maxLength = 0;

        for (int i : nums) {
            if (visited.get(i)) continue;

            int len = 1;
            visited.put(i, true);
            for (int j = i-1; visited.containsKey(j); --j) {
                visited.put(j, true);
                ++len;
            }
            for (int j = i+1; visited.containsKey(j); ++j) {
                visited.put(j, true);
                ++len;
            }

            maxLength = Math.max(maxLength, len);
        }
        return maxLength;
    }
}



