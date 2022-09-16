/*
Medium
Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.

The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.

Example 1:
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.

Example 2:
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]

Example 3:
Input: candidates = [2], target = 1
Output: []

Constraints:
1 <= candidates.length <= 30
2 <= candidates[i] <= 40
All elements of candidates are distinct.
1 <= target <= 500
*/

class Solution {
    /*
    Let N be the number of candidates, T be the target value, and M be the minimal value among the candidates.

    Time Complexity: O(N^(T/M+1)), space: O(T/M), 考虑成一颗N叉树，高度为T/M, total maximal number of nodes in N-ary tree.
    */
    // 先排序，再N 叉树的遍历，不会有重复解， very very good！
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
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
            if (candidates[i] > gap) break;
            
            path.add(candidates[i]);
            backtrack(candidates, gap - candidates[i], result, path, i);
            path.remove(path.size() - 1);
        }
    }
}

