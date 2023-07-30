class Solution {
public:
/*
你用数字1到20按照zigzag的格式写一下，就可以发现一些规律。
我们可以按照2*nRows-2的个数来取模分组;
对每一组的元素，新的下标为[0..2*nRows-2-1];
如果新的下标<nRows, 则对应垂直元素，反之，对应斜线元素。
*/
    string convert(string s, int nRows) {
        if (nRows <=0) return "";
        if (nRows == 1) return s;
        
        vector<vector<char> > vvc(nRows);
        int group = 2*nRows-2;
        for(int i=0; i<s.size(); ++i) {
            int newi = i%group;
            
            if (newi<nRows) {
                vvc[newi].push_back(s[i]);
            }
            else {
                vvc[group-newi].push_back(s[i]);
            }
        }
        
        string result;
        for(int i=0; i<vvc.size(); ++i) {
            for(int j=0;j<vvc[i].size(); ++j) {
                result += vvc[i][j];
            }
        }
        
        return result;
    }
};
