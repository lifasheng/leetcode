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
    private void twoSum(int[] nums, int i, List<List<Integer>> result) {
        int p = i+1, q = nums.length-1;
        while(p<q) {
            int sum = nums[i]+nums[p]+nums[q];
            if(sum == 0) {
                result.add(Arrays.asList(nums[i], nums[p], nums[q]));

                ++p; // move forward
                --q;
                // de-dup and make sure p<q
                while(p<q && nums[p-1] == nums[p]) { 
                    ++p;
                }
                while(p<q && nums[q] == nums[q+1]) {
                    --q;
                }
            } else if (sum < 0) {
                ++p;
            } else {
                --q;
            }
        }
    }
    
    // 排序数组，左右夹逼  
    // Time: O(n^2), 
    // Space: from O(logn) to O(n), depending on the implementation of the sorting algorithm.
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        
        List<List<Integer>> result = new ArrayList<>();
        // i<n-2, nums[i] <=0 剪枝
        for(int i=0; i<nums.length-2 && nums[i]<=0; ++i) {
            if (i>0 && nums[i-1] == nums[i]) { // 去重
                continue;
            }
            twoSum(nums, i, result);
        }
        return result;
    }
}

