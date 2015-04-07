class Solution {
public:
/*
设状态 f[i][j],表示 s1[0,i] 和 s2[0,j],匹配 s3[0, i+j]。
如果 s1 的最后一个字符等于 s3 的最后一个字符,则 f[i][j]=f[i-1][j];
如果 s2 的最后一个字符等于 s3 的最后一个字符,则 f[i][j]=f[i][j-1]。
因此状态转移方程如下:
f[i][j] = (s1[i - 1] == s3 [i + j - 1] && f[i - 1][j])
|| (s2[j - 1] == s3 [i + j - 1] && f[i][j - 1]);
比如： "abc", "bcde", "abbcdce"
f为：
1 0 0 0 0 
1 1 0 0 0 
1 1 1 1 0 
0 1 0 1 1
*/
    bool isInterleave(string s1, string s2, string s3) {
        if (s1.size() + s2.size() != s3.size()) return false;
        const int n1 = s1.size();
        const int n2 = s2.size();
        bool f[n1+1][n2+1];
        fill_n(&f[0][0], (n1+1)*(n2+1), true);
        
        for(int i=1; i<=n1; ++i) f[i][0] = s1[i-1] == s3[i-1] && f[i-1][0];
        for(int j=1; j<=n2; ++j) f[0][j] = s2[j-1] == s3[j-1] && f[0][j-1];
        for(int i=1; i<=n1; ++i) {
            for(int j=1; j<=n2; ++j) {
                f[i][j] = (s1[i-1] == s3[i+j-1] && f[i-1][j]) 
                || (s2[j-1] == s3[i+j-1] && f[i][j-1]);
            }
        }
        return f[n1][n2];
    }
};
