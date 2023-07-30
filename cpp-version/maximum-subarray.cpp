class Solution {
public:
#define M3
#ifdef M1
/*
divide and conquer approach 分治法. O(nLogn) time.
1) Divide the given array in two halves
2) Return the maximum of following three
….a) Maximum subarray sum in left half (Make a recursive call)
….b) Maximum subarray sum in right half (Make a recursive call)
….c) Maximum subarray sum such that the subarray crosses the midpoint
The lines 2.a and 2.b are simple recursive calls. 
How to find maximum subarray sum such that the subarray crosses the midpoint? 
We can easily find the crossing sum in linear time. 
The idea is simple, find the maximum sum starting from mid point and ending at some point on left of mid, 
then find the maximum sum starting from mid + 1 and ending with sum point on right of mid + 1. 
Finally, combine the two and return.
*/
    int maxSubArray(int A[], int n) {
        if (n<=0) return 0;
        return maxSubArray(A, 0, n-1);
    }
    int maxSubArray(int A[], int low, int high) {
        if (low == high) return A[low];
        
        int mid = (low+high)/2;
        int leftMax = maxSubArray(A, low, mid);
        int rightMax = maxSubArray(A, mid+1, high);
        int crossMax = maxCrossSum(A, low, mid, high);
        return max(crossMax, max(leftMax, rightMax));
    }
    int maxCrossSum(int A[], int low, int mid, int high) {
        int sum = 0; 
        int leftSum = INT_MIN; //注意，这里不能用0,如果A中全是负数的话，结果是最大的那个负数。
        for(int i=mid; i>=low; --i) {
            sum += A[i];
            leftSum = max(leftSum, sum);
        }
        
        sum = 0;
        int rightSum = INT_MIN;
        for(int i=mid+1; i<=high; ++i) {
            sum += A[i];
            rightSum = max(rightSum, sum);
        }
        
        return leftSum + rightSum;
    }
#endif
#ifdef M2
/*
最大连续子序列和。
设状态为 f[j],表示以 A[j] 结尾的最大连续子序列和,则状态转移方程如下:
f [j] = max {f [j − 1] + A[j], A[j]} , 其中1 ≤ j ≤ n
target = max {f [j]} , 其中1 ≤ j ≤ n
注意，这里的target不是f[n-1]，比如A=[-1, -2]，结果应该是-1,而f[n-1]=-2.
另外再强调一下，result的初始值不能设成0, 因为要处理全是负数的情况。
*/
/*
    // O(n) time, O(n) space。
    int maxSubArray(int A[], int n) {
        if (n<=0) return 0;
        vector<int> f(n, INT_MIN);
        f[0] = A[0];
        int result = A[0];
        for(int i=1; i<n; ++i) {
            f[i] = max(f[i-1]+A[i], A[i]);
            result = max(f[i], result);
        }
        return result;
    }
*/
    // O(n) time, O(1) space
    // 对比上一个实现，可以发现，求f[i]时只用到了f[i-1]，所以其实不用一个数组，而只需要一个变量来辅助即可。
    int maxSubArray(int A[], int n) {
        int result = INT_MIN, f = 0;
        for (int i = 0; i < n; ++i) {
            f = max(f + A[i], A[i]);
            result = max(result, f);
        }
        return result;
    }
#endif
#ifdef M3
/*
O(n) time, O(1) space.
当我们从头到尾遍历这个数组的时候,对于数组里的一个整数,它有几种选择呢?
它只有两种选择:
1、加入之前的 SubArray;2. 自己另起一个 SubArray。那什么时候会出现这两种情况呢?
如果之前 SubArray 的总体和大于 0 的话,我们认为其对后续结果是有贡献的。这种情况下我们
选择加入之前的 SubArray
如果之前 SubArray 的总体和为 0 或者小于 0 的话,我们认为其对后续结果是没有贡献,甚至是
有害的(小于 0 时)。这种情况下我们选择以这个数字开始,另起一个 SubArray。
这个写法和M2的原理是一样的。
*/
    int maxSubArray(int A[], int n) {
        int sum = 0;
        int result = INT_MIN;
        for(int i=0; i<n; ++i) {
            sum += A[i];
            result = max(result, sum);
            if (sum < 0) {
                sum = 0;
            }
            
        }
        
        return result;
    }
#endif
};
