class Solution {
public:
// 可以看成一维数组的二分查找，关键是一维下标到二下标的转换。
    bool searchMatrix(vector<vector<int> > &matrix, int target) {
        if (matrix.empty()) return false;
        
        int m = matrix.size();
        int n = matrix[0].size();
        int low = 0, high = m*n-1;
        while (low <= high) {
            int mid = (low+high)/2;
            if (matrix[mid/n][mid%n] == target) {
                return true;
            }
            else if (matrix[mid/n][mid%n] < target) {
                low = mid+1;
            }
            else {
                high = mid-1;
            }
        }
        return false;
    }
};
