/*
Medium

Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.
Each number in candidates may only be used once in the combination.
Note: The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]

Example 2:
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]

Constraints:
1 <= candidates.length <= 100
1 <= candidates[i] <= 50
1 <= target <= 30
*/

class Solution {
    // very very good!
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(candidates, target, result, path, 0);
        return result;
    }
    
    private void backtrack(int[] candidates, int gap, List<List<Integer>> result, List<Integer> path, int start) {
        if (gap == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; ++i) {
            if (gap < candidates[i]) break;
            
            // 剪枝逻辑，值相同的树枝，只遍历第一条
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            
            path.add(candidates[i]);
            backtrack(candidates, gap - candidates[i], result, path, i + 1);
            path.remove(path.size() - 1);
        }
    }
}

