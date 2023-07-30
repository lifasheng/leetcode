class Solution {
public:
#define M1
 
#ifdef M1
/*
http://www.cnblogs.com/TenosDoIt/p/3719630.html
http://www.cnblogs.com/lichen782/p/leetcode_Jump_Game_II.html
cur表示在result+1步范围内所能到达的最远位置;
last标识当前这一步所能覆盖的范围;
在当前的覆盖范围内，你不用更新步数， 但需要更新所能到达的最远位置，这样下次一旦超出覆盖范围，
我们就能知道我们能到达的下一个覆盖范围，并且步数加1。
*/
    int jump(int A[], int n) {
        int result = 0;
        int last = 0;
        int cur = 0;
        for(int i=0; i<n; ++i) {
            if (i>last) {
                last = cur;
                ++result;
            }
            cur = max(cur, i+A[i]);
        }
        
        return result;
    }
#endif
#ifdef M2
/*
DP, Time Limit Exceeded.
以f[i]表示到达第i个element需要的最少步数，有这样的递归方程： f[i] = min(f[k]) + 1 where 0<=k<i and A[k] >= i-k.
显然f是一个递增的数组。每次循环只需要尽量找到最小的f[k]，使其满足k+A[k]>=i。
最短的路径一定是第一个j能够跳到i的，所以直接+1，break就可以了, 因为f是递增的，后面的f[j]+1不可能比当前遇到的满足条件的f[j]+1更小。
*/
    int jump(int A[], int n) {
        vector<int> f(n, INT_MAX);
		f[0] = 0;
		
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (j + A[j] >= i) {
					f[i] = f[j] + 1;
					break;
				}
			}
		}
	
		return f[n-1];
    }
#endif
};
