/*
15. 3Sum
Medium

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Notice that the solution set must not contain duplicate triplets.



Example 1:

Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Example 2:

Input: nums = []
Output: []
Example 3:

Input: nums = [0]
Output: []
*/


// [-1,0,1,2,-1,-4]
// [0, 0, 0]
// [0, 0, 0, 0]
class Solution {
    // 排序数组，左右夹逼
    public List<List<Integer>> threeSum(int[] nums) {
        return threeSum_recursive(nums);
    }

    // solution 1:
    private List<List<Integer>> threeSum_1(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            List<List<Integer>> twoSumList = twoSum(nums, i);
            for (List<Integer> list : twoSumList) {
                list.add(nums[i]);
                result.add(list);
            }
            // 去重
            while (i < n - 1 && nums[i + 1 ] == nums[i]) {
                ++ i;
            }
        }

        return result;
    }

    private List<List<Integer>> twoSum(int[] nums, int i) {
        List<List<Integer>> result = new ArrayList<>();
        int low = i + 1, high = nums.length - 1;
        while (low < high) {
            int sum = nums[low] + nums[high] + nums[i];
            int left = nums[low];
            int right = nums[high];
            if (sum < 0) {
                while (low < high && nums[low] == left) {
                    ++ low;
                }
            } else if (sum > 0) {
                while (low < high && nums[high] == right) {
                    -- high;
                }
            } else {
                List<Integer> list = new ArrayList<>();
                Collections.addAll(list, nums[low], nums[high]);
                result.add(list);
                while (low < high && nums[low] == left) {
                    ++ low;
                }
                while (low < high && nums[high] == right) {
                    -- high;
                }
            }
        }
        return result;
    }

    // solution 2: recursive
    private List<List<Integer>> threeSum_recursive(int[] nums) {
        Arrays.sort(nums);
        return nSum_recursive(nums, 3, 0, 0);
    }

    private List<List<Integer>> nSum_recursive(int[] nums, int n, int start, int target) {
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
