/*
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.

A subarray is a contiguous non-empty sequence of elements within an array.

Example 1:
Input: nums = [1,1,1], k = 2
Output: 2

Example 2:
Input: nums = [1,2,3], k = 3
Output: 2
 
Constraints:
1 <= nums.length <= 2 * 104
-1000 <= nums[i] <= 1000
-107 <= k <= 107
*/


// https://zhuanlan.zhihu.com/p/36439368
// very very good!
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int prefixSum = 0;
        int res = 0;
        map.put(0, 1);
        for (int i = 0; i < nums.length; ++i) {
            prefixSum += nums[i];
            res += map.getOrDefault(prefixSum - k, 0);
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return res;
    }
}
/*
class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; ++i) {
            int sum = 0;
            for (int j = i; j < n; ++j) {
                sum += nums[j];
                if (sum == k) {
                    ++ res;
                }
            }
        }
        return res;
    }
}
*/

/*
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        hash = new HashMap<>();
        hash.put(0, 1);
        int sum = 0;
        for(int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            int diff = sum - k;
            if(hash.containsKey(diff)) {
                count += hash.get(diff);
            }
            hash.put(sum, hash.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    private Map<Integer, Integer> hash; // sum -> count
}
*/


