class Solution {
public:
/*
这一题约束最多只能买卖两次股票，并且手上最多也只能持有一支股票。
因为不能连续买入两次股票，所以买卖两次肯定分布在前后两个不同的区间。区间[0,1,2...i]的最大利润 + 区间[i,i+1,....n-1]的最大利润
设状态 f (i),表示区间 [0, i](0 ≤ i ≤ n − 1) 的最大利润,状态 g(i),表示区间 [i, n − 1](0 ≤ i ≤n − 1) 的最大利润,
则最终答案为 max {f (i) + g(i)} , 0 ≤ i ≤ n − 1。
允许在一天内买进又卖出,相当于不交易,因为题目的规定是最多两次,而不是一定要两次。
动态规划，第一步从左到右扫描，类似于(best time to buy and sell stock)，先计算出子序列[0,…,i]中的最大利润，用一个数组保存下来，那么时间是O(n)。
第二步是逆向扫描，计算子序列[i,…,n-1]上的最大利润，这一步同时就能结合上一步的结果计算最终的最大利润了，这一步也是O(n)。
所以最后算法的复杂度是O(n)。
*/
    int maxProfit(vector<int> &prices) {
        if (prices.size() < 2) return 0;
        
        const int n = prices.size();
        vector<int> f(n, 0);
        vector<int> g(n, 0);
        
        int valley = prices[0];
        for(int i=1; i<n; ++i) {
            valley = min(valley, prices[i]);
            f[i] = max(f[i-1], prices[i] - valley);
        }
        
        int peak = prices[n-1];
        for(int i=n-2; i>=0; --i) {
            peak = max(peak, prices[i]);
            g[i] = max(g[i+1], peak-prices[i]);
        }
        
        int result = 0;
        for(int i=0; i<n; ++i) {
            result = max(result, f[i]+g[i]);
        }
        
        return result;
    }
};
