class Solution {
public:
/*
http://www.cnblogs.com/TenosDoIt/p/3436457.html
贪心法,低进高出,把所有正的价格差价相加起来。
把原始价格序列变成差分序列,本题也可以做是最大 m 子段和,m = 数组长度。
*/
    int maxProfit(vector<int> &prices) {
        int result = 0;
        for(int i=1; i<prices.size(); ++i) {
            int diff = prices[i]-prices[i-1];
            if (diff>0) {
                result += diff;
            }
        }
        return result;
    }
};
