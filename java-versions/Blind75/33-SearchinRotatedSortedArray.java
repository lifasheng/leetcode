/*
very very good!!!

There is an integer array nums sorted in ascending order (with distinct values).

Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].

Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
You must write an algorithm with O(log n) runtime complexity.


Example 1:
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4

Example 2:
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

Example 3:
Input: nums = [1], target = 0
Output: -1
 
Constraints:
1 <= nums.length <= 5000
-104 <= nums[i] <= 104
All values of nums are unique.
nums is an ascending array that is possibly rotated.
-104 <= target <= 104
*/


class Solution {
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length-1, target);
    }

    private int binarySearch(int[] nums, int low, int high, int target) {
        if (low == high) {
            return (nums[low] == target) ? low : -1;
        }

        if (low + 1 == high) {
            if (nums[low] == target) {
                return low;
            }
            if (nums[high] == target) {
                return high;
            }
            return -1;
        }

        int mid = (low + high)/2;
        if (nums[mid] == target) {
            return mid;
        }

        if (nums[mid] < target) {
            if (nums[low] < nums[mid]) { // 左边递增
                return binarySearch(nums, mid+1, high, target);
            } else { // 右边递增
                if (nums[high] < target) {
                    return binarySearch(nums, low, mid-1, target);
                } else {
                    return binarySearch(nums, mid+1, high, target);
                }
            }
        } else { // nums[mid] > target
            if (nums[low] < nums[mid]) { // 左边递增
                if (nums[low] > target) {
                    return binarySearch(nums, mid+1, high, target);
                } else {
                    return binarySearch(nums, low, mid-1, target);
                }
            } else { // 右边递增
                return binarySearch(nums, low, mid-1, target);
            }
        }
    }
}

/*
[4,5,6,7,0,1,2] target = 5
[6 7 0 1 2 4 5] target = 4
[3 1] target = 1
 
*/



