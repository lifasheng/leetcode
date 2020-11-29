/*
46. Permutations
Medium

Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

Example 1:

Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
Example 2:

Input: nums = [0,1]
Output: [[0,1],[1,0]]
Example 3:

Input: nums = [1]
Output: [[1]]
 

Constraints:

1 <= nums.length <= 6
-10 <= nums[i] <= 10
All the integers of nums are unique.
*/

class Solution {
    private int findPivot(int[] nums) {
        for(int i=nums.length-2; i>=0; --i) {
            if(nums[i] < nums[i+1]) {
                return i;
            }
        }
        return -1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int start) {
        int i=start, j=nums.length-1;
        while(i<j) {
            swap(nums, i, j);
            ++i;
            --j;
        }
    }
    
    private void nextPermutation(int[] nums) {
        int pivot = findPivot(nums);
        if(pivot == -1) {
            reverse(nums, 0);
            return;
        }
        
        int firstGreater = 0;
        for(int i=nums.length-1; i>pivot; --i) {
            if(nums[i] > nums[pivot]) {
                firstGreater = i;
                break;
            }
        }
        
        swap(nums, pivot, firstGreater);
        
        reverse(nums, pivot+1);
    }
    
    private int factorial(int n) {
        int result = 1;
        for(int i=2; i<=n; ++i) {
            result *= i;
        }
        return result;
    }
    
    // Time: O(n*n!), because each nextPermutation is O(n), Space: O(1)    
    // same method as nextPermutation
    public List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = factorial(nums.length);
        for(int i=1; i<=n; ++i) {
            result.add(Arrays.stream(nums) // IntStream 
                       .boxed()            // Stream<Integer>
                       .collect(Collectors.toList()));
            nextPermutation(nums);
        }
        return result;
    }
    
    
    private void recursivePermutation(List<Integer> nums, int start, List<List<Integer>> result) {
        if (start == nums.size()-1) {
            result.add(new ArrayList<Integer>(nums)); // we have to copy, otherwise the reference (pointer) is added.
            return;
        }
        
        for(int i=start; i<nums.size(); ++i) {
            Collections.swap(nums, start, i);
            recursivePermutation(nums, start+1, result);
            Collections.swap(nums, start, i);
        }
    }
    
    // Time: O(N!), Space: O(N!)
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // convert int[] to List<Integer>, so that we don't need to convert int[] to List<Integer> in the recursive method, which requires a lot of boxed operation. Also we can leverage Collections.swap(List, i, j)
        List<Integer> numList = Arrays.stream(nums)
            .boxed().collect(Collectors.toList());
        recursivePermutation(numList, 0, result);
        return result;
    }
    
    public List<List<Integer>> permute(int[] nums) {
        return permute2(nums);
    }
}


