class Solution {
public:
/*
贪心法,分别找到价格最低和最高的一天,低进高出,注意最低的一天要在最高的一天之前。
把原始价格序列变成差分序列,本题也可以做是最大 m 子段和,m = 1。
*/
    int maxProfit(vector<int> &prices) {
        if (prices.size() < 2) return 0;
        
        int result = 0;
        int cur_min = prices[0];
        for(int i=1; i<prices.size(); ++i) {
            result = max(result, prices[i]-cur_min);
            cur_min = min(cur_min, prices[i]);
        }
        
        return result;
    }
};
