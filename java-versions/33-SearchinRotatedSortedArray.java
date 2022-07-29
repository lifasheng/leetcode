/*
33. Search in Rotated Sorted Array

You are given an integer array nums sorted in ascending order, and an integer target.
Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
If target is found in the array return its index, otherwise, return -1.

Example 1:
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4

Example 2:
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

Example 3:
Input: nums = [1], target = 0
Output: -1
*/
class Solution {
    public int search2(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int low = 0, high = nums.length-1;
        while(low <= high) {
            int m = (low+high)/2;
            if (nums[m] == target) {
                return m;
            } else if (nums[m] > target) {
                if (nums[m] <= nums[high]) { // right side is ordered
                    high = m-1;
                } else { // left side is ordered
                    if (nums[low] > target) {
                        low = m+1;
                    } else {
                        high = m-1;
                    }
                }
            } else { // nums[m] < target
                if (nums[low] <= nums[m]) { // left side is ordered
                    low = m+1;
                } else { // right side is ordered
                    if (nums[high] < target) {
                        high = m-1;
                    } else {
                        low = m+1;
                    }
                }
            }
        }
        return -1;
    }
    
    public int search1(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int low = 0, high = nums.length-1;
        while(low <= high) {
            int m = (low+high)/2;
            if (nums[m] == target) {
                return m;
            } else if (nums[low] <= nums[m]) { // left side is ordered
                if (nums[low] <= target && target < nums[m]) {
                    high = m-1;
                } else {
                    low = m+1;
                }
            } else { // right side is ordered
                if (nums[m] < target && target <= nums[high]) {
                    low = m+1;
                } else {
                    high = m-1;
                }
            }
        }
        return -1;
    }
    
    
    private int findPivot(int[] nums) {
        int n = nums.length;
        if (nums[0] <= nums[n-1]) {
            return 0;
        }
        int low = 0, high = n-1;
        while (low <= high) {
            if (low == high) {
                return low;
            }
            int m = (low+high)/2;

            if (m-1 >= 0 && nums[m-1] > nums[m] 
                && m+1 < n && nums[m] < nums[m+1]) {
                return m;
            }

            if (nums[low] <= nums[m]) {
                if (nums[m] <= nums[high]) {
                    return low;
                } else {
                    low = m+1;
                }
            } else {
                high = m-1;
            }
        }
        return -1;
    }
    private int basicSearch(int[] nums, int low, int high, int target) {
        while(low <= high) {
            int m = (low+high)/2;
            if (nums[m] == target) {
                return m;
            } else if (nums[m] < target) {
                low = m+1;
            } else {
                high = m-1;
            }
        }
        return -1;
        
    }
    
    public int search3(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int pivot = findPivot(nums);
        
        if (pivot > 0 && nums[0] <= target && target <= nums[pivot-1]) {
            return basicSearch(nums, 0, pivot-1, target);
        } else {
            return basicSearch(nums, pivot, nums.length-1, target);
        }
        
    }
    
    public int search(int[] nums, int target) {
        return search3(nums, target);
    }
}



// 注意等于的判断
class Solution {
    public int search2(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return mid;
            
            if (nums[mid] < target) { 
                if (nums[low] <= nums[mid]) { // 前半段有序, 此时不可能在前半段
                    low = mid + 1;
                } else { // 后半段有序
                    if (target <= nums[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            } else { //  target < nums[mid]
                if (nums[mid] <= nums[high]) { // 后半段有序，此时不可能在后半段
                    high = mid - 1;
                } else { // 前半段有序
                    if (nums[low] <= target) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }
        return -1;
    }
    
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return mid;
            
            if (nums[mid] < target) { 
                if (nums[mid] <= nums[high]) { // 后半段有序
                    if (target <= nums[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    } 
                } else { // 前半段有序
                    // 前半段不可能还有target
                    low = mid + 1;
                }
            } else {  // target < nums[mid]
                if (nums[mid] <= nums[high]) {  // 后半段有序
                    // 后半段不可能还有target
                    high = mid - 1;
                } else { // 前半段有序
                    if (nums[low] <= target) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }

        return -1;
    }
}
