/*
70. Climbing Stairs
Easy
You are climbing a staircase. It takes n steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 

Example 1:

Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
 

Constraints:

1 <= n <= 45
*/

class Solution {
    
    // Time Limit Exceeded
    public int climbStairs1(int n) {
        if (n<=1) {
            return 1;
        }    
        
        return climbStairs1(n-1) + climbStairs1(n-2);
    }
    
    // 备忘录法, Time: O(n), Space: O(n)
    private int solveClimbStairs(int n, Map<Integer, Integer> m) {
        if (n<=1) {
            return 1;
        }
        if (m.containsKey(n)) {
            return m.get(n);
        }
        
        int result = solveClimbStairs(n-1, m) + solveClimbStairs(n-2, m);
        m.put(n, result);
        return result;
    }
    
    public int climbStairs2(int n) {
        Map<Integer, Integer> m = new HashMap<>();
        return solveClimbStairs(n, m);
    }
    
    // Time: O(n), Space: O(n)
    public int climbStairs3(int n) {
        if (n<=1) {
            return 1;
        }
        
        int[] arr = new int[n+1];
        arr[0] = 1;
        arr[1] = 1;
        for(int i=2; i<=n; ++i) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n];
    }
    
    // Time: O(n), Space: O(1)
    public int climbStairs4(int n) {
        if (n<=1) {
            return 1;
        }
        
        int a = 1;
        int b = 1;
        int r = 0;
        for(int i=2; i<=n; ++i) {
            r = a + b;
            a = b;
            b = r;
        }
        return r;
    }
    
    public int climbStairs(int n) {
        return climbStairs4(n);
    }
}




