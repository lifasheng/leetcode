/*
Medium

Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.
The test cases are generated so that the answer can fit in a 32-bit integer.

Example 1:
Input: nums = [1,2,3], target = 4
Output: 7
Explanation:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
Note that different sequences are counted as different combinations.

Example 2:
Input: nums = [9], target = 3
Output: 0

Constraints:
1 <= nums.length <= 200
1 <= nums[i] <= 1000
All the elements of nums are unique.
1 <= target <= 1000

Follow up: What if negative numbers are allowed in the given array? How does it change the problem? What limitation we need to add to the question to allow negative numbers?
*/

class Solution {
    private int answer = 0;
    public int combinationSum4(int[] nums, int target) {
        return combinationSum4_dp(nums, target);
    }
    
    // 关键是理解 combines(target) = Union(combines(target - nums[i])) if target >= nums[i]
    // 这道题和普通的combination不一样。
    Map<Integer, Integer> memo1 = new HashMap<>();
    private int combinationSum4_memo(int[] nums, int target) {
        return dfs(nums, target);
    }
    
    private int dfs(int[] nums, int gap) {
        if (gap == 0) {
            return 1;
        }
        if (memo1.containsKey(gap)) return memo1.get(gap);
        
        int result = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] > gap) continue;
            result += dfs(nums, gap - nums[i]);
        }
        memo1.put(gap, result);
        return result;
    }
    
    
    private int combinationSum4_dp(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int combSum = 1; combSum <= target; ++combSum) {
            for (int num : nums) {
                if (num > combSum) continue;
                dp[combSum] += dp[combSum - num];
            }
        }
        return dp[target];
    }
    
    
    // 得到所有的组合，类似于上面的memo方法
    Map<Integer, List<List<Integer>>> memo2 = new HashMap<>();
    public List<List<Integer>> allCombinationSum4(int[] nums, int target) {
        return dfs2(nums, target);
    }

    private List<List<Integer>> dfs2(int[] nums, int gap) {
        if (gap == 0) {
            return Arrays.asList(new ArrayList<>());
        }

        if (memo2.containsKey(gap)) return  memo2.get(gap);
        List<List<Integer>> result = new ArrayList<>();
        for (int num : nums) {
            if (num > gap) continue;
            List<List<Integer>> subResult = dfs2(nums, gap - num);
            for (List<Integer> list : subResult) {
                List<Integer> newList = new ArrayList<>(list);
                newList.add(num);
                result.add(newList);
            }
        }
        memo2.put(gap, result);
        return result;
    }
}


