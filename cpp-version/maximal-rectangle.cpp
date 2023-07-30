class Solution {
public:
/*
http://www.cnblogs.com/lichen782/p/leetcode_maximal_rectangle.html
如果我们把每一行看成x坐标，把从那一行开始往上数的1的个数看成是高度。
那么我们可以复用largest-rectangle-in-histogram中求柱状图的最大矩形面积的O(n)方法。
在O(mn)时间内就可以求出每一行形成的“柱状图”的最大矩形面积了。它们之中最大的，就是我们要的答案。
O(mn) time, O(n) space.
*/
    int maxRectangleInHistogram(vector<int> height) {
        stack<int> stk;
        int result = 0;
        
        height.push_back(0);
        for(int i=0; i<height.size();) {
            if (stk.empty() || height[i] > height[stk.top()]) {
                stk.push(i);
                ++i;
            }
            else {
                int temp = stk.top();
                stk.pop();
                int width = stk.empty() ? i : i-stk.top()-1;
                result = max(result, width * height[temp]);
            }
        }
        
        return result;
    }
    
    int maximalRectangle(vector<vector<char> > &matrix) {
        if(matrix.empty()) return 0;
        
        const int row = matrix.size();
        const int col = matrix[0].size();
        vector<int> height(col);
        for(int j=0;j<col;++j) {
            height[j] = matrix[0][j] - '0';
        }
        
        int result = maxRectangleInHistogram(height);
        
        for(int i=1; i<row; ++i) {
            for(int j=0; j<col; ++j) {
                if (matrix[i][j] == '1') {
                    height[j] += 1;
                }
                else { // matrix[i][j] == '0'
                    height[j] = 0;
                }
            }
            
            result = max(result, maxRectangleInHistogram(height));
        }
        
        return result;
    }
};
