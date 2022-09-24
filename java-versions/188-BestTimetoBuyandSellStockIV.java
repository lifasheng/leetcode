/*
Hard
You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
Find the maximum profit you can achieve. You may complete at most k transactions.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.

Example 2:
Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 
Constraints:
1 <= k <= 100
1 <= prices.length <= 1000
0 <= prices[i] <= 1000
*/

class Solution {
    // very very good!
    // Time: O(n(n-k)) if 2k <= n, O(n) if 2k > n, n is prices' length
    // max size of transactions is O(n), and we need O(n-k) iterations.
    // transactions只会有n/2的长度，因为我们只会存上升的对，而且可以合并连续上升的对
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        // solve special cases
        if (n <= 0 || k <= 0) {
            return 0;
        }
        
        // 先找到所有的价格上升的价格对，因为只有prices[i] > prices[i - 1] 才可能获利。
        ArrayList<Pair<Integer, Integer>> transactions = new ArrayList<>();
        for (int i = 1; i < prices.length; ++i) {
            if (prices[i] > prices[i - 1]) {
                // 合并连续上升的对，不合并也没关系。
                if (transactions.size() > 0) {
                    Pair<Integer, Integer> last = transactions.get(transactions.size() - 1);
                    if (last.getValue() == prices[i - 1]) {
                        transactions.set(transactions.size() - 1, new Pair(last.getKey(), prices[i]));
                        continue;
                    }
                } 
                
                transactions.add(new Pair(prices[i - 1], prices[i]));
            }
        }
        
        // 尝试删除最小的价格对或者合并相邻的两个价格对，看哪个造成的损失最小
        // 我们不能简单地删除最小的，有可能合并会更好，反之亦然。
        while (transactions.size() > k) {
            int minDeleteLoss = Integer.MAX_VALUE;
            int deleteIndex = 0;
            
            int minMergeLoss = Integer.MAX_VALUE;
            int mergeIndex = 0;
            for (int i = 0; i < transactions.size(); ++i) {
                Pair<Integer, Integer> pair = transactions.get(i);
                int deleteLoss = pair.getValue() - pair.getKey();
                if (deleteLoss < minDeleteLoss) {
                    deleteIndex = i;
                    minDeleteLoss = deleteLoss;
                }
                
                if (i > 0) {
                    Pair<Integer, Integer> prePair = transactions.get(i - 1);
                    int mergeLoss = prePair.getValue() - pair.getKey();
                    if (mergeLoss < minMergeLoss) {
                        minMergeLoss = mergeLoss;
                        mergeIndex = i;
                    }
                }
            }
            
            if (minDeleteLoss < minMergeLoss) {
                transactions.remove(deleteIndex);
            } else {
                Pair<Integer, Integer> newPair = new Pair(
                    transactions.get(mergeIndex - 1).getKey(), 
                    transactions.get(mergeIndex).getValue());
                transactions.set(mergeIndex - 1, newPair);
                transactions.remove(mergeIndex);
            }
        }
        
        int res = 0;
        for (Pair<Integer, Integer> pair : transactions) {
            res += (pair.getValue() - pair.getKey());
        }
        return res;
    }
}

