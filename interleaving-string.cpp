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


注意：代码中是如何初始化f[i][0]和f[0][j]的。
观察状态转移方程可以看出，该方程对于i==0或j==0的情况是没法处理的，
所以要想到将其作为基本情况提前进行处理。
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




// 递归方法 + 备忘录法
class Solution {
public:
    bool isInterleave(string s1, string s2, string s3) {
        if (s1.size()+s2.size()!=s3.size()) return false;

        map<pair<int, int>, bool> m;
        return isInterleave(s1, s2, s3, 0, 0, m);
    }

    bool isInterleave(string &s1, string &s2, string &s3,
                     int i1, int i2, map<pair<int, int>, bool> &m) {
        if (i1 == s1.size()) return s2.substr(i2) == s3.substr(i1+i2);
        if (i2 == s2.size()) return s1.substr(i1) == s3.substr(i1+i2);
        if (s1[i1] != s3[i1+i2] && s2[i2] != s3[i1+i2]) return false;

        pair<int, int> p = make_pair(i1, i2);
        if (m.find(p) != m.end()) return m[p];

        bool b = false;
        if (s1[i1] != s2[i2]) {
            if (s1[i1] == s3[i1+i2]) {
                b = isInterleave(s1, s2, s3, i1+1, i2, m);

            } else {
                b = isInterleave(s1, s2, s3, i1, i2+1, m);
            }
        } else {
            b = isInterleave(s1, s2, s3, i1+1, i2, m)
                || isInterleave(s1, s2, s3, i1, i2+1, m);
        }

        m[p] = b;
        return b;
    }
};
