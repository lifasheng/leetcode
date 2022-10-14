/*
Hard

Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.

Example 1:
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.

Example 2:
Input: heights = [2,4]
Output: 4

Constraints:
1 <= heights.length <= 105
0 <= heights[i] <= 104
*/

class Solution {
    // [1, 2, 2], [1,3,2,4], [4,3,2,1]
    //单调递增栈 Monotonic Stack, very very good!
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = heights.length;
        
        int maxArea = 0;
        int i = 0;
        while(i <= n) {
            // 相当于在heights末尾插入一个0，因为java 中不能push一个新的值到array中，所以才这么做
            int hi = (i == n) ? 0 : heights[i]; 
            
            if (stack.isEmpty() || hi >= heights[stack.peek()]) {
                stack.push(i);
                ++ i; // move forward
            } else {
                int index = stack.pop();
                int height = heights[index];
                //因为是单调递增栈，所以当栈为空时，说明height是栈中最小的高度，所以i就表示它的宽度
                // 用[4，3，2，1] 和[1,3,2,4] 这两个case就能体会这个宽度的计算了
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, width * height);
            }
        }
        return maxArea;
    }
}



class Solution {
    public int largestRectangleArea(int[] heights) {
        return largestRectangleArea_stack(heights);
    }
    
    // TLE, O(N^2)
    private int largestRectangleArea_bruteForce(int[] heights) {
        int maxArea = 0;
        for (int i = 0; i < heights.length; ++i) {
            int width = 1;
            int j = i - 1;
            while (j >= 0 && heights[j] >= heights[i]) {
                -- j;
                ++ width;
            }
            
            j = i + 1;
            while (j < heights.length && heights[j] >= heights[i]) {
                ++ j;
                ++ width;
            }
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        return maxArea;
    }
    
    // O(NlogN), worst case O(N^2) TLE
    private int largestRectangleArea_divideAndConquer(int[] heights) {
        return helper(heights, 0, heights.length - 1);
    }
    
    private int helper(int[] heights, int start, int end) {
        if (start > end) return 0;
        
        int minIndex = start;
        for (int i = start + 1; i <= end; ++i) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }
        
        return Math.max(heights[minIndex] * (end - start + 1),
                       Math.max(helper(heights, start, minIndex - 1), helper(heights, minIndex + 1, end)));
    }
    
    // solution 3: monotonic stack
    private int largestRectangleArea_stack(int[] heights) {
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        int n = heights.length;
        int i = 0;
        while (i < n) {
            if (stack.isEmpty() || heights[i] > heights[stack.peek()]) {
                stack.push(i);
                ++ i;
            } else {
                int index = stack.pop();
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, heights[index] * width);
            }
        }
        
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
            maxArea = Math.max(maxArea, heights[index] * width);
        }
        return maxArea;
    }
}

