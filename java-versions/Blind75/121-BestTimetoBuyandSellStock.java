/*
good!!!

You are given an array prices where prices[i] is the price of a given stock on the ith day.
You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

Example 1:
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.

Example 2:
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.
 
Constraints:
1 <= prices.length <= 105
0 <= prices[i] <= 104
*/

class Solution {
    public int maxProfit(int[] prices) {
        return solution2(prices);
    }

    // O(n^2) Time Limit Exceeded
    private int solution1(int[] prices) {
        int n = prices.length;
        int maxProfit = 0;
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                maxProfit = Math.max(prices[j] - prices[i], maxProfit);
            }
        }
        return maxProfit;
    }

    private int solution2(int[] prices) {
        int n = prices.length;
        int maxProfit = 0;
        int profit = 0;
        for (int i = 0; i < n-1; ++i) {
            int diff = prices[i + 1] - prices[i];
            profit += diff;
            if (profit < 0) {
                profit = 0;
            }
           
            maxProfit = Math.max(profit, maxProfit);
        }
        return maxProfit;
    }
}

/*
prices = [7,1,5,3,6,4]
// [-6, 4, -2, 3, -2]
*/


