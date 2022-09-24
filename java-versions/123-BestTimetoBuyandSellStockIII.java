/*
Hard
You are given an array prices where prices[i] is the price of a given stock on the ith day.
Find the maximum profit you can achieve. You may complete at most two transactions.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: prices = [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.

Example 2:
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.

Example 3:
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
 
Constraints:
1 <= prices.length <= 105
0 <= prices[i] <= 105
*/

class Solution {
    
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/solution/
    // very very very good!  解法解释得很详细，可以好好学习理解一下。
    public int maxProfit(int[] prices) {
        return maxProfit_2(prices);
    }
    
    /*
    dp: very good!
    left_profits[i] array would hold the maximum profits that one can gain from doing one single transaction 
    on the left subsequence of prices from the index zero to i, (i.e. Prices[0], Prices[1], ... Prices[i]).
    
    right_profits[i] array would hold the maximum profits that one can gain from doing one single transaction 
    on the right subsequence of the prices from the index i up to N-1, (i.e. Prices[i], Prices[i+1], ... Prices[N-1]).
    
    max_profits[i]=left_profits[i]+right_profits[i+1]
    */
    public int maxProfit_2(int[] prices) {
        int n = prices.length;
        
        int[] leftProfits = new int[n];
        int[] rightProfits = new int[n];
        
        int maxProfit = 0;
        int currMin = prices[0];
        for (int i = 1; i < n; ++i) {
            maxProfit = Math.max(maxProfit, prices[i] - currMin);
            leftProfits[i] = maxProfit;
            currMin = Math.min(currMin, prices[i]);
        }
        
        int currMax = prices[n - 1];
        maxProfit = 0;
        for (int i = n - 2; i >= 0; --i) {
            maxProfit = Math.max(maxProfit, currMax - prices[i]);
            rightProfits[i] = maxProfit;
            currMax = Math.max(currMax, prices[i]);
        }
        
        maxProfit = 0;
        for (int i = 0; i < n - 1; ++i) {
            maxProfit = Math.max(maxProfit, leftProfits[i] + rightProfits[i + 1]);
        }
        
        return Math.max(maxProfit, leftProfits[n-1]);
    }
    
    // O(N^2), 将数组分为两段，对这两段分别求max profit。TLE
    public int maxProfit_1(int[] prices) {
        int n = prices.length;
        
        int maxProfit = 0;
        for (int i = 1; i < n; ++i) {
            maxProfit = Math.max(maxProfit, findMaxProfit(prices, 0, i) + findMaxProfit(prices, i, n - 1));
        }
        return maxProfit;
    }
    
    // 和Best Time to Buy and Sell Stock I 一样的算法，O(N)
    private int findMaxProfit(int[] prices, int start, int end) {
        int maxProfit = 0;
        int currMin = prices[start];
        for (int i = start + 1; i <= end; ++i) {
            maxProfit = Math.max(maxProfit, prices[i] - currMin);
            currMin = Math.min(currMin, prices[i]);
        }
        return maxProfit;
    }
}

