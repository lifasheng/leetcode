/*
31. Next Permutation
Medium
Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such an arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).

The replacement must be in place and use only constant extra memory.

 

Example 1:

Input: nums = [1,2,3]
Output: [1,3,2]
Example 2:

Input: nums = [3,2,1]
Output: [1,2,3]
Example 3:

Input: nums = [1,1,5]
Output: [1,5,1]
Example 4:

Input: nums = [1]
Output: [1]
*/

class Solution {
    private int findPivot(int[] nums) {
        for(int i=nums.length-2; i>=0; --i) {
            if (nums[i]<nums[i+1]) {
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
    
    public void nextPermutation(int[] nums) {
        // 1. 从右往左找到第一个非升序的位置
        int pivot = findPivot(nums);
        if (pivot == -1) { // 如果全部是升序，只能翻转整个数组
            reverse(nums, 0);
            return;
        }
        
        // 2. 从右往左找到第一个比pivot那个数大的数
        int firstGreater = pivot;
        for(int i=nums.length-1; i>pivot; --i) {
            if (nums[i] > nums[pivot]) {
                firstGreater = i;
                break;
            }
        }
        
        // 3. 交换两个数
        swap(nums, pivot, firstGreater);
        
        // 4. 翻转pivot右边的数
        reverse(nums, pivot+1);
    }
}


