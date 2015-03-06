class Solution {
public:
    vector<vector<int> > generate(int numRows) {
        vector<vector<int> > result;
        if (numRows <=0) return result;
        
        result.push_back(vector<int>(1,1)); // 第一行
        // 对第2第numRows行，每一行的头尾是1, 中间元素是上一层的左上角和右上角之和。
        for(int i=2; i<=numRows; ++i) {
            vector<int> v;
            v.push_back(1);
            for(int j=1; j<i-1; ++j) {
                v.push_back(result[i-2][j-1]+result[i-2][j]);
            }
            v.push_back(1);
            result.push_back(v);
        }
        return result;
    }
};
