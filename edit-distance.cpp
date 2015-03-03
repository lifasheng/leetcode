class Solution {
public:
#define M2
#ifdef M1
/*
递归版本：
https://secweb.cs.odu.edu/~zeil/cs361/web/website/Lectures/styles/pages/editdistance.html
Suppose, for example, that we wanted to compute the edit distance between “Zeil” and “trials”. 
Starting with “Zeil”, we consider what would have to be done to get “trials” if the last step 
taken were “add”, “remove”, or “change”, respectively:
* If we knew how to convert “Zeil” to “trial”, we could add “s” to get the desired word.
* If we knew how to convert “Zei” to “trials”, then we would actually have “trialsl” 
  and we could remove that last character to get the desired word.
* If we knew how to convert “Zei” to “trial”, then we would actually have “triall” 
  and we could change the final “l” to “s” to get the desired word.
Notice that what we have done is to reduce the original problem to 3 “smaller” problems: 
convert “Zeil” to “trial”, or “Zei” to “trials”, or “Zei” to “trial”.
*/
    // 递归 + 备忘录， 如果不用备忘录，会超时。
    map<pair<int, int>, int> cache; // 这里不能用unordered_map，除非自己定义了相应的hash函数。
    int minDistance(string word1, string word2) {
        return minDistance(word1, word2, word1.size(), word2.size());
    }
    int minDistance(string &word1, string &word2, int m, int n) {
        if (m == 0 || n == 0) return max(m, n);
        
        auto key = make_pair(m, n);
        auto iter = cache.find(key);
        if (iter != cache.end()) {
            return iter->second;
        }
        
        if (word1[m-1] == word2[n-1]) {
            return cache[key] = minDistance(word1, word2, m-1, n-1);
        }
        else {
            int md1 = minDistance(word1, word2, m, n-1);   // add/insert
            int md2 = minDistance(word1, word2, m-1, n);   // remove/delete
            int md3 = minDistance(word1, word2, m-1, n-1); // change/replace
            
            return cache[key] = min(min(md1, md2), md3) + 1;
        }
        
        
    }
#endif
#ifdef M2
/*
设状态为 f[i][j],表示 A[0,i] 和 B[0,j] 之间的最小编辑距离。
设 A[0,i] 的形式是str1c, B[0,j] 的形式是 str2d,
1. 如果 c==d,则 f[i][j]=f[i-1][j-1];
2. 如果 c!=d,
(a) 如果将 c 替换成 d,则 f[i][j]=f[i-1][j-1]+1;
(b) 如果在 c 后面添加一个 d,则 f[i][j]=f[i][j-1]+1;
(c) 如果将 c 删除,则 f[i][j]=f[i-1][j]+1;
二维DP,时间复杂度 O(m*n),空间复杂度 O(m*n)
*/
    int minDistance(string word1, string word2) {
        const int m = word1.size();
        const int n = word2.size();
        if (m==0 || n==0) return max(m, n);
        
        // 长度为 n 的字符串,有 n+1 个隔板
        vector<vector<int> > f(m+1, vector<int>(n+1, 0));
        
        for(int i=0; i<=m; ++i) {
            f[i][0] = i;
        }
        for(int j=0; j<=n; ++j) {
            f[0][j] = j;
        }
        for(int i=1; i<=m; ++i) {
            for(int j=1; j<=n; ++j) {
                if (word1[i-1] == word2[j-1]) {
                    f[i][j] = f[i-1][j-1];
                }
                else {
                    f[i][j] = min(min(f[i][j-1], f[i-1][j]), f[i-1][j-1])+1;
                }
            }
        }
        
        return f[m][n];
    }
#endif
};
