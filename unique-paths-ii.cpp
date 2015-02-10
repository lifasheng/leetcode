class Solution {
public:
    int uniquePathsWithObstacles(vector<vector<int> > &obstacleGrid) {
        if (obstacleGrid.empty()) return 0;
        
        const int m = obstacleGrid.size();
        const int n = obstacleGrid[0].size();
        
        vector<vector<int> > cache(m+1, vector<int>(n+1, -1));
        return uniquePathsWithObstacles(obstacleGrid, m, n, cache);
    }
    
    int uniquePathsWithObstacles(vector<vector<int> > &obstacleGrid, int m, int n, vector<vector<int> > &cache) {
        if (m<1 || n<1) return 0;
        
        if (obstacleGrid[m-1][n-1]) return 0; // 注意下标, 和unique paths相比，就这一不同。
        
        if (m==1 && n==1) return 1;
        
        int up, left;
        if (cache[m-1][n] < 0) {
            cache[m-1][n] = uniquePathsWithObstacles(obstacleGrid, m-1, n, cache);
        }
        up = cache[m-1][n];
        
        if (cache[m][n-1] < 0) {
            cache[m][n-1] = uniquePathsWithObstacles(obstacleGrid, m, n-1, cache);
        }
        left = cache[m][n-1];
        
        return up + left;
    }
};
