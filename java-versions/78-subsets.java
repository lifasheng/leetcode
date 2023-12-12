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



class Solution {
    
    public List<List<Integer>> subsets(int[] nums) {
        return subsets_dfs(nums);
    }
    
    
    // solution 1: recursive
    private List<List<Integer>> subsets_recursive(int[] nums) {
        return subsets(nums, nums.length);
    }
    
    private List<List<Integer>> subsets(int[] nums, int n) {
        if (n == 0) {
            List<List<Integer>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        
        List<List<Integer>> result = subsets(nums, n - 1);
        int size = result.size(); // important to define a var here, not in for loop
        for (int i = 0; i < size; ++i) {
            List<Integer> list = new ArrayList(result.get(i));
            list.add(nums[n - 1]);
            result.add(list);
        }
        return result;
    }
    
    // solution 2: dfs
    private List<List<Integer>> subsets_dfs(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(nums, result, path, 0);
        return result;
    }
    
    private void dfs(int[] nums, List<List<Integer>> result, List<Integer> path, int index) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // without nums[index]
        dfs(nums, result, path, index + 1);
        
        // with nums[index]
        path.add(nums[index]);
        dfs(nums, result, path, index + 1);
        path.remove(path.size() - 1);
    }

    // solution 3: use bit
    private List<List<Integer>> subsets_useBit(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < (1 << n); ++i) {
            List<Integer> path = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                if ((i & (1 << j)) > 0) {
                    path.add(nums[j]);
                }
            }
            result.add(path);
        }
        return result;
    }
}




// https://labuladong.github.io/algo/di-san-zha-24031/bao-li-sou-96f79/hui-su-sua-56e11/
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(nums, path, res, 0);
        return res;
    }

    private void backtrack(int[] nums, List<Integer> path, List<List<Integer>> res, int start) {
        res.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; ++i) {
            path.add(nums[i]);
            backtrack(nums, path, res, i + 1);
            path.remove(path.size() - 1);
        }
    }
}


/*
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> sets = new ArrayList<>();
        sets.add(new ArrayList<>());
        for(int i = 0; i < nums.length; ++i) {
            int length = sets.size();
            for(int j = 0; j < length; ++j) {
                List<Integer> newList = new ArrayList<>(sets.get(j));
                newList.add(nums[i]);
                sets.add(newList);
            }
        }
        return sets;
    }
}
*/
