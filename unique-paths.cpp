class Solution {
public:
#define M4
#ifdef M1 // 递归超时
    int uniquePaths(int m, int n) {
        if (m<1 || n <1) return 0;
        
        if (m == 1 && n == 1) return 1;
        
        return uniquePaths(m-1, n) + uniquePaths(m, n-1);
    }
#endif
#ifdef M2
// 深搜 + 缓存,即备忘录法, top-down 自顶向下
// 时间复杂度 O(m*n),空间复杂度 O(m*n)
    int uniquePaths(int m, int n) {
        vector<vector<int> > cache(m+1, vector<int>(n+1, -1));
        
        return uniquePaths(m, n, cache);
    }
    
    int uniquePaths(int m, int n, vector<vector<int> > &cache) {
        if (m<1 || n<1) return 0;
        if (m == 1 && n == 1) return 1;
        
        int up, left;
        if (cache[m-1][n] < 0) {
            cache[m-1][n] = uniquePaths(m-1, n, cache);
        }
        up = cache[m-1][n];
        
        if (cache[m][n-1] < 0) {
            cache[m][n-1] = uniquePaths(m, n-1, cache);
        }
        left = cache[m][n-1];
        
        return up + left;
    }
#endif
#ifdef M3 // DP， 自底向上
    int uniquePaths(int m, int n) {
        vector<vector<int> > cache(m+1, vector<int>(n+1, 0));
        cache[0][1] = 1; // 因为cache[1][1] = 1,为了不在循环中特殊处理，我们在这做个特殊处理，将cache[0][1]设为1,这样就统一了。
        for(int i=1; i<=m; ++i) {     // 注意 i<=m
            for(int j=1; j<=n; ++j) { // 注意 j<=n
                cache[i][j] = cache[i-1][j] + cache[i][j-1];
            }
        }
        return cache[m][n];
    }
#endif
#ifdef M4 // 组合公式
    // n!/start!: n*(n-1)*(n-2)...*(start+1)
    long long factor(int n, int start) {
        long long result = 1;
        for(int i=start+1; i<=n; ++i) {
            result *= i;
        }
        return result;
    }
    // C(n, k) = n!/k!/(n-k)!
    // 千万不能直接分别求n!, k!，因为n很大是，就会出现大数溢出。
    int combination(int n, int k) {
        if(k == 0) return 1;
        if(k == 1) return n;
        
        long long ret = factor(n, k);
        ret /= factor(n-k, 1);
        return (int)ret;
    }
    int uniquePaths(int m, int n) {
        if (m<1 || n<1) return 0;
        if (m==1 || n==1) return 1;
        // max 可以防止 n 和 k 差距过大,从而防止 combination() 溢出
        return combination(m+n-2, max(m-1, n-1));
    }
#endif
};
