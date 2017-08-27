class Solution {
public:
    /* // DP, 二维数组， 注意f[0][1]的处理很巧妙。
    int minPathSum(vector<vector<int> > &grid) {
        if (grid.empty()) return 0;
        
        const int m = grid.size();
        const int n = grid[0].size();
        
        vector<vector<int> > f(m+1, vector<int>(n+1, INT_MAX));
        f[0][1] = 0; // 这里也可以是f[1][0] = 0; 主要是为了保证f[1][1] = grid[0][0]
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
        f[1] = 0; // 这个很重要。这里不能是f[0]=0，不然i=2之后，f[1]=min(f[1],f[0])+grid[i-1][j-1]这里永远是f[0]比较小。
        for(int i=1; i<=m; ++i) {
            for(int j=1; j<=n; ++j) {
                f[j] = min(f[j], f[j-1]) + grid[i-1][j-1];
            }
        }
        return f[n];
    }
};




// 这里是没有使用 m+1, n+1的解法。
class Solution {
public:
    int minPathSum(vector<vector<int>>& grid) {
        const size_t row = grid.size();
        if (row == 0) return 0;
        const size_t col = grid[0].size();
        if (col == 0) return 0;

        vector<vector<int> > f(row, vector<int>(col));
        f[0][0] = grid[0][0];
        for(int i=1; i<row; ++i) {
            f[i][0] = f[i-1][0] + grid[i][0];
        }
        for(int j=1; j<col; ++j) {
            f[0][j] = f[0][j-1] + grid[0][j];
        }

        for(int i=1; i<row; ++i) {
            for(int j=1; j<col; ++j) {
                f[i][j] = min(f[i-1][j], f[i][j-1]) + grid[i][j];
            }
        }

        return f[row-1][col-1];
    }
};
