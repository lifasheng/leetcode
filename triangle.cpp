class Solution {
public:
/*
DP， bottom up: from bottom to top
设状态为f(i,j),表示从从位置(i,j)出发,路径的最小和,则状态转移方程为
f(i, j) = min{ f(i+1, j), f(i+1, j+1) } + (i, j)
如果用二维数组的话，需要O(n^2)的space。
由于我们是一行一行从下往上求解，并且上一行比下一行的元素要少，所以用一个长度为n的一维数组就可以了，即O(n) space。
此时的状态转移方程为：
f(j) = min{ f(j), f(j+1) } + (i, j)
*/
    int minimumTotal(vector<vector<int> > &triangle) {
        if (triangle.empty()) return 0;
        
        const int n = triangle.size();
        vector<int> f(triangle[n-1]);
        for(int i=n-2; i>=0; --i) {
            for(int j=0; j<=i; ++j) {
                f[j] = min(f[j], f[j+1]) + triangle[i][j];
            }
        }
        
        return f[0];
    }
};
