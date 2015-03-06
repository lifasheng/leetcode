class Solution {
public:
// 时间复杂度 O(n^2),空间复杂度 O(1)
    vector<int> spiralOrder(vector<vector<int> > &matrix) {
        vector<int> result;
        if (matrix.empty()) return result;
        
        int beginX = 0, endX = matrix[0].size()-1; // 水平方向
        int beginY = 0, endY = matrix.size()-1;    // 垂直方向
        
        while(1) {
            // 从左到右
            for(int j=beginX; j<=endX; ++j) result.push_back(matrix[beginY][j]);
            ++beginY;
            if (beginY > endY) break;
            
            // 从上到下
            for(int i=beginY; i<=endY; ++i) result.push_back(matrix[i][endX]);
            --endX;
            if (beginX > endX) break;
            
            // 从右到左
            for(int j=endX; j>=beginX; --j) result.push_back(matrix[endY][j]);
            --endY;
            if (beginY > endY) break;
            
            // 从下到上
            for(int i=endY; i>=beginY; --i) result.push_back(matrix[i][beginX]);
            ++beginX;
            if (beginX > endX) break;
        }
        
        return result;
    }
};
