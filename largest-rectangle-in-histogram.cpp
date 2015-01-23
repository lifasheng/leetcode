class Solution {
public:
#define M2
#ifdef M1
/*
http://blog.csdn.net/abcbc/article/details/8943485
解法一是穷举法，对于直方图的每一个右边界，穷举所有的左边界。将面积最大的那个值记录下来。
时间复杂度为O(n^2). 单纯的穷举在LeetCode上面过大集合时会超时。
可以通过选择合适的右边界，做一个剪枝(Pruning)。观察发现当height[k] >= height[k - 1]时，
无论左边界是什么值，选择height[k]总会比选择height[k - 1]所形成的面积大。
因此，在选择右边界的时候，首先找到一个height[k] < height[k - 1]的k，然后取k - 1作为右边界，穷举所有左边界，找最大面积。
*/
    int largestRectangleArea(vector<int> &height) {
        int size = height.size();
        int maxArea = 0;
        for(int i=0; i<size; ++i) {
            // 做一次剪枝，如果height[0] > height[1], 也就是说对于递减的高度其实没有改进; i.e. [3,1,2,3,4,5]
            // 但对于递增的高度，改进很明显。i.e. [1,2,3,4,5,6]
            for (int k = i + 1; k < size; k++) {
                if (height[k] < height[k - 1]) {
                    i = k - 1;
                    break;
                } else {
                    i = k;
                }
            }
            
            int lowest = INT_MAX;
            for(int j=i; j>=0; --j) {
                lowest = min(lowest, height[j]);
                int curArea = lowest * (i-j+1);
                maxArea = max(maxArea, curArea);
            }
        }
        
        return maxArea;
    }
#endif
#ifdef M2 // O(n) time, O(n) space
/*
理解：这个算法有几个地方很巧妙：
1. height.push_back(0); 使得循环可以统一处理height中所有的高度;
2. 栈内存储的是高度递增的下标。
3. 对于每一个直方图高度，分两种情况。
   (1)：当栈空或者当前高度大于栈顶下标所指示的高度时，当前下标入栈。否则，
   (2)：当前栈顶出栈，并且用这个下标所指示的高度计算面积。而这个方法为什么只需要一个栈呢？
        注意对于第二种情况时，for循环的循环下标并没有增加，也就是说下次循环还和当前高度进行比较。
        对于第二种情况，当出栈后，栈顶为空时，说明前面所有的高度都大于当前高度，同时前面所有的高度都大于等于出栈下标对应的高度，
        所以计算当前直方图之前的面积时width = i; 如果出后，栈顶不为空，则前面有递增的高度存在，我们就一个一个的计算他们的面积，
        直到当前元素入栈。
*/
    int largestRectangleArea(vector<int> &height) {
        stack<int> s;
        int result = 0;
        
        height.push_back(0);
        for(int i=0; i<height.size(); ) {
            if (s.empty() || height[i] > height[s.top()]) {
                s.push(i);
                ++i;
            }
            else {
                int temp = s.top();
                s.pop();
                int width = s.empty() ? i : i - s.top() - 1;
                result = max(result, height[temp] * width);
            }
        }
        
        return result;
    }
#endif
};
