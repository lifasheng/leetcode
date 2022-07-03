/*
Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.

A subarray is a contiguous part of the array.

Example 1:
Input: nums = [1,0,1,0,1], goal = 2
Output: 4
Explanation: The 4 subarrays are bolded and underlined below:
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]

Example 2:
Input: nums = [0,0,0,0,0], goal = 0
Output: 15
*/



class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        return numSubarraysWithSum_prefixSum(nums, goal);
    }
    
    // Solution 1: O(N^2) time exceed limitation
    private int numSubarraysWithSum_naive(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; ++i) {
            int sum = 0;
            for (int j = i; j < n; ++j) {
                sum += nums[j];
                if (sum == goal) {
                    ++count;
                } else if (sum > goal) {
                    break;
                }
            }
        }
        return count;
    }
    
    // better Solution 2: O(N), prefix sum !!! TODO: master it!
    // https://www.youtube.com/watch?v=GZOBCV-soyE
    // 0 1 ... i-1, i, ...j-1, j ...
    // sum[i:j] = prefix[j] - prefix[i-1] = goal
    private int numSubarraysWithSum_prefixSum(int[] nums, int goal) {
        // key: prefix sum, value: count of index
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, 1);
        
        int count = 0;
        int prefixSum = 0;
        for (int i = 0; i < nums.length; ++i) {
            prefixSum += nums[i];
            
            int diff = prefixSum - goal;
            if (m.containsKey(diff)) {
                count += m.get(diff);
            }
            
            m.put(prefixSum, m.computeIfAbsent(prefixSum, k -> 0) + 1);
        }
        return count;
    }
}


