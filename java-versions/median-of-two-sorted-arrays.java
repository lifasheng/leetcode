/*

4. Median of Two Sorted Arrays
Hard

Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

Follow up: The overall run time complexity should be O(log (m+n)).

 

Example 1:

Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
Example 2:

Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
Example 3:

Input: nums1 = [0,0], nums2 = [0,0]
Output: 0.00000
Example 4:

Input: nums1 = [], nums2 = [1]
Output: 1.00000
Example 5:

Input: nums1 = [2], nums2 = []
Output: 2.00000

*/


//https://www.youtube.com/watch?v=LPFhl65R7ww
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int x = nums1.length;
        int y = nums2.length;
        if (x > y) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        // 这里我们不用x-1，因为这里是找切点位置，和二分查找某个特定位置有一点区别。
        int low = 0, high = x;
        while(low <= high) {
            int partitionX = (low + high)/2;
            int partitionY = (x+y+1)/2-partitionX;
            
            int maxLeftX = partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX-1];
            int minRightX = partitionX == x ? Integer.MAX_VALUE : nums1[partitionX];
            
            int maxLeftY = partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY-1];
            int minRightY = partitionY == y ? Integer.MAX_VALUE : nums2[partitionY];
            
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ( (x+y) % 2 == 0) {
                    return ((double)(Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)))/2;
                } else {
                    return (double)(Math.max(maxLeftX, maxLeftY));
                }
            } else if (maxLeftX > minRightY) {
                high = partitionX - 1;
            } else {
                low = partitionX + 1;
            }
        }
        return -1;
    }
}





/*
Complexity Analysis

Time complexity: O(log(min(m,n))).
At first, the searching range is [0, m]. And the length of this searching range will be reduced by half after each loop. So, we only need log(m) loops. Since we do constant operations in each loop, so the time complexity is O\big(\log(m)\big)O(log(m)). Since m≤n, so the time complexity is O(log(min(m,n))).

Space complexity: O(1)O(1).
We only need constant memory to store 99 local variables, so the space complexity is O(1)O(1).

*/
