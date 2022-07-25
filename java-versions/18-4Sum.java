/*
18. 4Sum
Medium
Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

Notice that the solution set must not contain duplicate quadruplets.

 

Example 1:

Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
Example 2:

Input: nums = [], target = 0
Output: []

*/

// [0,0,0,0], target = 0
// [-2,-1,-1,1,1,2,2], target = 0
class Solution {
    private void twoSum(int[] nums, int i, int j, int target, List<List<Integer>> result) {
        int p=j+1, q=nums.length-1;
        while(p<q) {
            int sum = nums[i]+nums[j]+nums[p]+nums[q];
            if (sum == target) {
                result.add(Arrays.asList(nums[i], nums[j], nums[p], nums[q]));
                ++p;
                --q;
                while(p<q && nums[p-1] == nums[p]) { // 去重
                    ++p;
                }
                while(p<q && nums[q] == nums[q+1]) { // 去重
                    --q;
                }
            } else if (sum < target) {
                ++p;
            } else {
                --q;
            }
        }
    }
    // 排序，左右夹逼， Time: O(n^3), Space: O(1)
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0; i<nums.length-3; ++i) {
            if (i>0 && nums[i-1] == nums[i]) { // 去重
                continue;
            }
            
            for(int j=i+1; j<nums.length-2; ++j) {
                if (j-1>i && nums[j-1] == nums[j]) { //j-1>i保证j至少是i之后的第二个位置，才需要去重， 考虑[0,0,0,0]的情况。
                    continue;
                }
                twoSum(nums, i, j, target, result);
            }
        }
        return result;
    }
    
    // Time: O(n^2), worst O(n^4), Space: O(n^2)
    public List<List<Integer>> fourSum2(int[] nums, int target) {
        Set<List<Integer>> result = new HashSet<>();
        
        // 用一个hashmap先缓存两个数的和
        Map<Integer, List<Map.Entry<Integer,Integer>>> m = new HashMap<>();
        int n = nums.length;
        for(int i=0; i<n-1; ++i) {
            for(int j=i+1; j<n; ++j) {
                int sumIJ = nums[i]+nums[j];
                m.putIfAbsent(sumIJ, new ArrayList<>());
                List<Map.Entry<Integer,Integer>> entrys = m.get(sumIJ);
                entrys.add(Map.entry(i, j));   
            }
        }
        
        for(int i=0; i<n-3; ++i) {
            // if(i>0 && nums[i-1] == nums[i]) {
            //     continue;
            // }
            for(int j=i+1; j<n-2; ++j) {
                // if(j-1>i && nums[j-1] == nums[j]) {
                //     continue;
                // }
                int key = target-nums[i]-nums[j];
                if (m.containsKey(key)) {
                    for(Map.Entry<Integer,Integer> entry: m.get(key)) {
                        if (entry.getKey() > j) {
                            // we need to sort the numbers, so that we can dedup them.
                            List<Integer> l = Arrays.asList(nums[i], nums[j],
                                                nums[entry.getKey()], nums[entry.getValue()]);
                            Collections.sort(l);
                            result.add(l);
                        }
                    }
                }
            }
        }
        return new ArrayList<List<Integer>>(result);
    }
    
    
    public List<List<Integer>> fourSum(int[] nums, int target) {
        return fourSum2(nums, target);
    }
}




// latest version, better to understand
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return nSum_recursive(nums, 4, 0, target);
    }
    
    // here, target is set as long type, which is important.
    // test case: [1000000000,1000000000,1000000000,1000000000]       -294967296
    private List<List<Integer>> nSum_recursive(int[] nums, int n, int start, long target) {
        List<List<Integer>> result = new ArrayList<>();

        int size = nums.length;
        if (n < 2 || start >= size) return result;

        // 递归出口
        if (n == 2) {
            int low = start, high = size - 1;
            while (low < high) {
                int left = nums[low];
                int right = nums[high];
                int sum = nums[low] + nums[high];
                if (sum == target) {
                    result.add(new LinkedList<>(Arrays.asList(nums[low], nums[high])));

                    while (low < high && nums[low] == left) ++ low;
                    while (low < high && nums[high] == right) -- high;
                } else if (sum < target) {
                    while (low < high && nums[low] == left) ++ low;
                } else {
                    while (low < high && nums[high] == right) -- high;
                }
            }
            return result;
        }

        // n > 2 时，递归计算 (n-1)Sum 的结果
        for (int i = start; i < size; ++i) {
            List<List<Integer>> subResult = nSum_recursive(nums, n - 1, i + 1, target - nums[i]);
            for (List<Integer> list : subResult) {
                list.add(0, nums[i]);
                result.add(list);
            }
            while (i + 1 < size && nums[i + 1] == nums[i]) ++i;
        }
        return result;
    }
}



