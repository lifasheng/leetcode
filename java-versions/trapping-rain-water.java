/*
42. Trapping Rain Water
Hard
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

Example 1:

Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
Example 2:

Input: height = [4,2,0,3,2,5]
Output: 9
*/

// Time: O(N), Space: O(1)
class Solution {
    public int trap(int[] height) {
        if (height == null) {
            return 0;
        }
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        int max = 0;
        for(int i=0; i<n; ++i) {
            leftMax[i] = max;
            max = Math.max(max, height[i]);
        }
        
        max = 0;
        for(int i=n-1; i>=0; --i) {
            rightMax[i] = max;
            max = Math.max(max, height[i]);
        }
        
        int result = 0;
        for(int i=0; i<n; ++i) {
            int min = Math.min(leftMax[i], rightMax[i]);
            if (min > height[i]) {
                result += (min - height[i]);
            }
        }
        
        return result;
        
    }
}

