class Solution {
public:
/*
思路：纵向扫描，从位置0开始，对每一个位置比较所有字符串，直到遇到一个不匹配。
O(n1+n2+...) time, O(1) space.
注意，substr(pos, len): The substring is the portion of the object that starts at character 
position pos and spans len characters (or until the end of the string, whichever comes first).
*/
    string longestCommonPrefix(vector<string> &strs) {
        if (strs.empty()) return "";

        for(int j=0; j<strs[0].size(); ++j) {
            for(int i=1; i<strs.size(); ++i) {
                if (strs[i][j] != strs[0][j]) {
                    return strs[0].substr(0, j);
                }
            }
        }
        
        return strs[0];
    }
};
