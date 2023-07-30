class Solution {
public:
#define M2
#ifdef M1 // O(m*n) time, O(m+n) space, use two arrays to help
    void setZeroes(vector<vector<int> > &matrix) {
        const int row = matrix.size();
        const int col = matrix[0].size();
        
        vector<bool> rows(row, false);
        vector<bool> cols(col, false);
        
        for(int i=0; i<row; ++i) {
            for(int j=0; j<col; ++j) {
                if (matrix[i][j] == 0) {
                    rows[i] = cols[j] = true;
                }
            }
        }
        
        for(int i=0; i<row; ++i) {
            if (rows[i]) {
                for(int j=0; j<col; ++j) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        for(int j=0; j<col; ++j) {
            if (cols[j]) {
                for(int i=0; i<row; ++i) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
#endif
#ifdef M2 //O(m*n) time, O(1) space, use first row and first col to help
    /*
     * 注意：
     * (1) 这里肯定要单独判断第一行和第一列是否含有0,
     *     否则如果第一行和第一列没有0,而其它行列有0时，也会将第一行或第一列全部设为0;
     * (2) 对于把matrix[0][j] = matrix[i][0] = 0这个阶段（代码中的第一个两重循环），i和j可以从0开始，
     *     不过没必要，因为第一行和第一列已经处理过了。
     * (3) 对于代码中的第二个两重循环，i和j就不能从0开始，因为如果matrix[0][0] = 0的话，
     *     在这种情况下会把第一行全部设为0, 从而导致整个数组都全部设为0.
     *
     * 所以总结一下就是，先单独判断第一行和第一列是否含有0元素，然后对[1..n-1][1..n-1]进行判断和处理，最后再处理第一行和第一列。
     */
    void setZeroes(vector<vector<int> > &matrix) {
        const int row = matrix.size();
        const int col = matrix[0].size();
        
        bool isFirstRowZero = false;
        bool isFirstColZero = false;
        
        // judge if there is 0 in first row
        for(int j=0; j<col; ++j) {
            if (matrix[0][j] == 0) {
                isFirstRowZero = true;
                break;
            }
        }
        
        // judge if there is 0 in first col
        for(int i=0; i<row; ++i) {
            if (matrix[i][0] == 0) {
                isFirstColZero = true;
                break;
            }
        }
        
        // if matrix[i][j] = 0, then update matrix[0][j] and matrix[i][0]
        for(int i=1; i<row; ++i) {
            for(int j=1; j<col; ++j) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0; // the jth of first row is set to 0
                    matrix[i][0] = 0; // the ith of first col is set to 0
                }
            }
        }
        
        // set matrix to 0 according to first row and col
        for(int i=1; i<row; ++i) {
            for(int j=1; j<col; ++j) {
                if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        if (isFirstRowZero) {
            for(int j=0; j<col; ++j) {
                matrix[0][j] = 0;
            }
        }
        
        if (isFirstColZero) {
            for(int i=0; i<row; ++i) {
                matrix[i][0] = 0;
            }
        }
    }
#endif
};
