/*
Medium

Given an integer array nums that may contain duplicates, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

Example 1:
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]

Example 2:
Input: nums = [0]
Output: [[],[0]]
 
Constraints:
1 <= nums.length <= 10
-10 <= nums[i] <= 10
*/

class Solution {
    /*
    这题有重复元素,但本质上,跟上一题很类似,上一题中元素没有重复,相当于每个元素只能
    选 0 或 1 次,这里扩充到了每个元素可以选 0 到若干次而已。
    比如[1 2 2]
    map: 1 -> 1, 2 -> 2
    所以1是选0次或1次，2是选0次或1次或2次。
    所以下面的solutio同样适合前一题。
    1 ---> [] -> 2 ---> []      => []
      |            |
      |            |--> [2]     => [2]
      |            |
      |            |--> [2 2]   => [2 2]
      |
      --> [1] -> 2 ---> [1]     => [1]
                   |
                   |--> [1 2]   => [1 2]
                   |
                   |--> [1 2 2] => [1 2 2]
    */
    // dfs
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 首先计算每个元素的重复次数
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // 将每个元素的重复次数放到数组中，因为数组下标从0开始，刚好表示第几步（step）。
        int[] uniqNums = new int[freq.size()];
        int[] freqs = new int[freq.size()];
        int i = 0;
        for (Integer key : freq.keySet()) {
            uniqNums[i] = key;
            freqs[i] = freq.get(key);
            ++ i;
        }
        
        // 回溯法
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        subsetsWithDup_dfs(uniqNums, freqs, result, path, 0);
        return result;
    }
    
    private void subsetsWithDup_dfs(int[] uniqNums, 
                                    int[] freqs, 
                                    List<List<Integer>> result,
                                    List<Integer> path, 
                                    int index) {
        if (index == uniqNums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i <= freqs[index]; ++i) {
            for (int j = 0; j < i; ++j) {
                path.add(uniqNums[index]);
            }
        
            subsetsWithDup_dfs(uniqNums, freqs, result, path, index + 1);
        
            for (int j = 0; j < i; ++j) {
                path.remove(path.size() - 1);
            }
        }
    }
}

