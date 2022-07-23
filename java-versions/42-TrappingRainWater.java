/*
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

Example 1:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.

Example 2:
Input: height = [4,2,0,3,2,5]
Output: 9

Constraints:
n == height.length
1 <= n <= 2 * 104
0 <= height[i] <= 105
*/

class Solution {
    public int trap(int[] height) {
        return trap_usingstack(height);
    }
    
    // solution 1: brute force: TLE, O(N^2)
    private int trap_bruteForce(int[] height) {
        int n = height.length;
        if (n <= 1) return 0;
        
        int result = 0;
        for (int i = 0; i < n; ++i) {
            int leftMax = 0;
            int rightMax = 0;
            
            // [0 .. i], inclusive!!!
            for (int j = 0; j <= i; ++j) {
                leftMax = Math.max(leftMax, height[j]);
            }
            
            
            // 剪枝: pruning
            if (leftMax <= height[i]) continue;
            
            // [i .. n-1], inclusive!!!
            for (int j = i; j < n; ++j) {
                rightMax = Math.max(rightMax, height[j]);
            }
            
            result += (Math.min(leftMax, rightMax) - height[i] );
        }
        return result;
    }
    
    // solution 2: dp, memo, O(N)
    private int trap_dpMemo(int[] height) {
        int n = height.length;
        if (n <= 1) return 0;
        
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        leftMax[0] = height[0];
        for (int i = 1; i < n; ++i) {
            leftMax[i] = Math.max(height[i], leftMax[i-1]);
        }
        
        rightMax[n-1] = height[n-1];
        for (int i = n - 2; i >= 0; --i) {
            rightMax[i] = Math.max(height[i], rightMax[i+1]);
        }
        
        int result = 0;
        for (int i = 0; i < n; ++i) {
            result += (Math.min(leftMax[i], rightMax[i]) - height[i]);
        }
        return result;
    }
    
    
    // solution 3: iterative using stack, very good!  
    // For monotonic-stack concept, refer to https://liuzhenglaichn.gitbook.io/algorithm/monotonic-stack
    /* We keep a stack and iterate over the array. We add the index of the bar to the stack if bar is smaller than or equal to the bar at top of stack, which means that the current bar is bounded by the previous bar in the stack. If we found a bar longer than that at the top, we are sure that the bar at the top of the stack is bounded by the current bar and a previous bar in the stack, hence, we can pop it and add resulting trapped water to ans.
    
    如果当前位置的高度小于栈顶位置的高度，则表示当前位置的左边界是栈顶，可以入栈。
    反之，表示当前位置是栈顶位置的右边界，栈顶位置要出栈，出栈之后如果栈顶还有一个元素，则表示刚才出栈的位置左右两边都有边界，可以装水。
    // test case: [4, 1, 2, 4] 用这个例子就可以体会整个计算过程。
    */
    private int trap_usingstack(int[] height) {
        int n = height.length;
        if (n <= 1) return 0;
        
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int index = stack.pop();
                
                if (stack.isEmpty()) break;
                
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[index];
                result += (distance * boundedHeight);
            }
            stack.push(i);
        }
        return result;
    }
    
}

