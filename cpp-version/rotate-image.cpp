class Solution {
public:
#define M2
#ifdef M1
    void rotate(vector<vector<int> > &matrix) {
        int n = matrix.size();
        
        // 沿着左下角到右上角的对角线翻转
        for(int i=0; i<n; ++i) {
            for(int j=0; j<n-i-1; ++j) {
                    swap(matrix[i][j], matrix[n-1-j][n-1-i]);
            }
        }
        
        // 沿着中间的水平线翻转
        for(int i=0; i<n/2; ++i) {
            for(int j=0; j<n; ++j) {
                swap(matrix[i][j], matrix[n-1-i][j]);
            }
        }
    }
#endif
#ifdef M2
    void rotate(vector<vector<int> > &matrix) {
        int n = matrix.size();
        
        // 沿着中间的水平线翻转
        for(int i=0; i<n/2; ++i) {
            for(int j=0; j<n; ++j) {
                swap(matrix[i][j], matrix[n-1-i][j]);
            }
        }
        
        // 沿着左上角到右下角的对角线翻
        for(int i=0; i<n; ++i) {
            for(int j=i+1; j<n; ++j) {
                    swap(matrix[i][j], matrix[j][i]);
            }
        }
        
    }
#endif
};
