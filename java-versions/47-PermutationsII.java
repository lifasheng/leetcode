/*
Medium

Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in any order.

Example 1:
Input: nums = [1,1,2]
Output:
[[1,1,2],
 [1,2,1],
 [2,1,1]]

Example 2:
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

Constraints:
1 <= nums.length <= 8
-10 <= nums[i] <= 10
*/

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        return permute_backtrack(nums);
    }
    
    // solution 1
    // https://labuladong.github.io/algo/4/31/107/
    private List<List<Integer>> permute_backtrack(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums); // sort first to make sure same value stay together
        backtrack(nums, result, path, used, 0);
        return result;
    }
    
    private void backtrack(int[] nums, 
                           List<List<Integer>> result, 
                           List<Integer> path,
                           boolean[] used, 
                           int index) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < nums.length; ++i) {
            if (used[i]) continue;
            // 新添加的剪枝逻辑，固定相同的元素在排列中的相对位置
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                // 如果前面的相邻相等元素没有用过，则跳过
                continue;
            }
            
            used[i] = true;
            path.add(nums[i]);
            backtrack(nums, result, path, used, index + 1);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
}

