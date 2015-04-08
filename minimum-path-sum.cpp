class Solution {
public:
    /* // DP, 二维数组， 注意f[0][1]的处理很巧妙。
    int minPathSum(vector<vector<int> > &grid) {
        if (grid.empty()) return 0;
        
        const int m = grid.size();
        const int n = grid[0].size();
        
        vector<vector<int> > f(m+1, vector<int>(n+1, INT_MAX));
        f[0][1] = 0;
        for(int i=1; i<=m; ++i) {
            for(int j=1; j<=n; ++j) {
                f[i][j] = min(f[i-1][j], f[i][j-1]) + grid[i-1][j-1];
            }
        }
        return f[m][n];
    }
    */
    // DP + 滚动数组
    int minPathSum(vector<vector<int> > &grid) {
        if (grid.empty()) return 0;
        
        const int m = grid.size();
        const int n = grid[0].size();
        
        vector<int> f(n+1, INT_MAX);
        f[1] = 0;
        for(int i=1; i<=m; ++i) {
            for(int j=1; j<=n; ++j) {
                f[j] = min(f[j], f[j-1]) + grid[i-1][j-1];
            }
        }
        return f[n];
    }
};
