/*
Medium

Given two integers n and k, return all possible combinations of k numbers chosen from the range [1, n].
You may return the answer in any order.

Example 1:
Input: n = 4, k = 2
Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
Explanation: There are 4 choose 2 = 6 total combinations.
Note that combinations are unordered, i.e., [1,2] and [2,1] are considered to be the same combination.

Example 2:
Input: n = 1, k = 1
Output: [[1]]
Explanation: There is 1 choose 1 = 1 total combination.

Constraints:
1 <= n <= 20
1 <= k <= n
*/

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        return combine_backtrack(n, k);
    }
    
    // very very good!
    private List<List<Integer>> combine_backtrack(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(n, k, result, path, 1);
        return result;
    }
    
    private void backtrack(int n, int k, List<List<Integer>> result, List<Integer> path, int start) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i <= n; ++i) {
            path.add(i);
            backtrack(n, k, result, path, i + 1); // important, not start + 1 here
            path.remove(path.size() - 1);
        }
    }
}

