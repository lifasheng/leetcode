class Solution {
public:
    vector<vector<int> > generate(int numRows) {
        vector<vector<int> > result;
        if (numRows <=0) return result;
        
        result.push_back(vector<int>(1,1)); // 第一行
        // 对第2第numRows行，每一行的头尾是1, 中间元素是上一层的左上角和右上角之和。
        for(int i=2; i<=numRows; ++i) {
            vector<int> cur(i, 1); // 当前行
            const vector<int> &prev = result[i-2]; // 上一行
            
            for(int j=1; j<i-1; ++j) {
                cur[j] = prev[j-1] + prev[j]; // 左上角和右上角之和
            }
            result.push_back(cur);
        }
        return result;
    }
};
