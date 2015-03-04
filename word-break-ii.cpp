class Solution {
public:
/*
    动规,时间复杂度 O(n^2),空间复杂度 O(n^2)
    这里需要特别注意的是，i是从1..n，因为0对应空串，而j是从0..i, 而且求子串的时候是从j开始，长度为i-j。
*/
    vector<string> wordBreak(string s, unordered_set<string> &dict) {
        const int n = s.size();
        // 长度为 n 的字符串有 n+1 个隔板
        vector<bool> f(n+1, false);
        f[0] = true; // 空串
        
        // prev[i][j] 为 true,表示 s[j, i) 是一个合法单词,可以从 j 处切开
        // 第一行未用
        vector<vector<bool> > prev(n+1, vector<bool>(n, false));
        
        for(int i=1; i<=n; ++i) {
            for(int j=0; j<i; ++j) {
                string ts = s.substr(j, i-j);
                if (dict.find(ts) != dict.end() && f[j]) {
                    f[i] = true;
                    prev[i][j] = true;
                }
            }
        }
        
        vector<string> result;
        vector<string> path;
        dfs(s, prev, path, n, result);
        return result;
    }
    /*
    DFS 遍历树,生成路径
    该函数中i，j和上面循环中的i，j相对应。
    理解： 比如s="abcd", dict=["a" "abc" "b" "cd"], 则结果应该是["a" "b" "cd"]
    i 初始时是n，表示求1..n的分割。一旦找到一个有效的分割j，则s[j,i]是最终结果的一部分。
    此时我们对s[1..j]进行dfs，所以此时的i=j。
    */
    void dfs(string &s, vector<vector<bool> > &prev, vector<string> &path, int i, vector<string> &result) {
        if (i == 0) {
            string r;
            for(int k=path.size()-1; k>=0; --k) {
                r += path[k];
                if (k!=0) r+= " ";
            }
            result.push_back(r);
            return;
        }
        
        for(int j=0; j<i; ++j) {
            if (prev[i][j]) {
                path.push_back(s.substr(j, i-j));
                dfs(s, prev, path, j,  result);
                path.pop_back();
            }
        }
    }
};
