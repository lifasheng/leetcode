class Solution {
public:
#define M2
#ifdef M1
/*
http://blog.csdn.net/abcbc/article/details/8978146
给定两个字符串S和T，求S有多少个不同的子串与T相同。
当我们遇到字符串中子串或匹配的问题时，可以考虑是否可以DP。
可以先尝试做一个二维的表int[][] dp，用来记录匹配子序列的个数（以S ="rabbbit",T = "rabbit"为例）：
    r a b b b i t
  1 1 1 1 1 1 1 1
r 0 1 1 1 1 1 1 1
a 0 0 1 1 1 1 1 1
b 0 0 0 1 2 3 3 3
b 0 0 0 0 1 3 3 3
i 0 0 0 0 0 0 3 3
t 0 0 0 0 0 0 0 3  
从这个表可以看出，无论T的字符与S的字符是否匹配，dp[i][j] = dp[i][j - 1].
就是说，假设S已经匹配了j - 1个字符，得到匹配个数为dp[i][j - 1].
现在无论S[j]是不是和T[i]匹配，匹配的个数至少是dp[i][j - 1]。
除此之外，当S[j]和T[i]相等时，我们可以让S[j]和T[i]匹配，然后让S[j - 1]和T[i - 1]去匹配。所以递推关系为：
dp[0][0] = 1; // T和S都是空串.
dp[0][1 ... S.length() - 1] = 1; // T是空串，S只有一种子序列匹配。
dp[1 ... T.length() - 1][0] = 0; // S是空串，T不是空串，S没有子序列匹配。
dp[i][j] = dp[i][j - 1] + (T[i - 1] == S[j - 1] ? dp[i - 1][j - 1] : 0).1 <= i <= T.length(), 1 <= j <= S.length()
设 状 态 为 f (i, j), 表 示 T[0,i] 在 S[0,j] 里 出 现 的 次 数。 
如果T[i]!=S[j],则f(i, j) = f(i, j-1);
如果T[i]==S[j],则有两种选择:
               当我们不使用S[j]时，f(i, j) = f(i, j-1);
               当我们使用S[j]时，f(i, j) = f(i-1, j-1);
               所以总的个数是f(i, j) = f(i, j-1) + f(i-1, j-1);
*/
    int numDistinct(string S, string T) {
        const int n = T.size();
        const int m = S.size();
        int f[n+1][m+1];
        
        // S为空
        for(int i=0; i<=n; ++i) {
            f[i][0] = 0;
        }
        // T为空, 包含了f[0][0] = 1
        for(int j=0; j<=m; ++j) {
            f[0][j] = 1;
        }
        
        for(int i=1; i<=n; ++i) {
            for(int j=1; j<=m; ++j) {
                f[i][j] = f[i][j-1];
                if (T[i-1] == S[j-1]) {
                    f[i][j] += f[i-1][j-1];
                }
            }
        }
        
        return f[n][m];
    }
#endif
#ifdef M2
/*
原理类似M1，但是这里我们尝试使用滚数组来节省空间。
// 时间复杂度 O(m*n),空间复杂度 O(n)
设 状 态 为 f (i, j), 表 示 T[0,j] 在 S[0,i] 里 出 现 的 次 数。
f(i, j) = f(i-1, j) + f(i-1, j-1);
这里只所以把T和S的下标换一下，是为了方便使用滚动数组。
从新的f(i,j)定义可以看出，它在二数组中只依赖上一行的结果。
所以我们可以用一维滚动数组来替代二维数组。
在M1中，f(i, j) = f(i, j-1) + f(i-1, j-1);它还依赖当前层的元素，不方便使用滚动数组。
*/
    int numDistinct(string S, string T) {
        const int m = S.size();
        const int n = T.size();
        
        // 当i代表S的下标时，f的第一行元素是[1 0 0 0 ... 0]
        vector<int> f(n+1);
        f[0] = 1;
        
        for(int i=1; i<=m; ++i) {
            for(int j=n; j>0; --j) { // 这里T要从后往前扫描，不然f[j-1]就不是上一行的值，而是更新后的值。
                if (S[i-1] == T[j-1]) {
                    f[j] += f[j-1];
                }
            }
        }
        
        return f[n];
    }
#endif
};
// f[i][j]表示S[0-i]和T[0-j]的匹配子串的个数
class Solution {
public:
    int numDistinct(string S, string T) {
        const int m = S.size();
        const int n = T.size();
        if (m<n) return 0;
        vector<vector<int> > f(m+1, vector<int>(n+1));
        
        for(int j=0; j<=n; ++j) f[0][j] = 0;
        for(int i=0; i<=m; ++i) f[i][0] = 1;
        
        for(int i=1; i<=m; ++i) {
            for(int j=1; j<=n; ++j) {
                if (i>=j) {
                    f[i][j] = f[i-1][j];
                    if (S[i-1] == T[j-1]) f[i][j] += f[i-1][j-1];
                }
            }
        }
        
        return f[m][n];
    }
};
