/*
Given an unsorted array of integers, find the length of longest increasing subsequence.

For example,
Given [10, 9, 2, 5, 3, 7, 101, 18],
The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only necessary for you to return the length.

Your algorithm should run in O(n2) complexity.

Follow up: Could you improve it to O(n log n) time complexity?

*/

/*
思路：看到最大，最多等，考虑动态规划
f[i]表示从[0..i]这个区间的最大递增子串长度。
我们需要从后往前遍历所有比当前数小的数nums[j]，看它的f[j]+1是否大于f[i]。
比如[1, 2, 5, 6, 4, 7]，对于7来说，我们不能只看4, 而是需要看所有小于它的数的f[j]。
*/
class Solution {
public:
#if 0
    int lengthOfLIS(vector<int>& nums) {
        if (nums.empty()) return 0;
        size_t n = nums.size();
        int result = 1;
        vector<int> f(n, 1); // f[i]>=1
        for(int i=1; i<n; ++i) {
            for(int j=i-1; j>=0; --j) {
                if (nums[j] < nums[i]) {
                    f[i] = max(f[i], f[j]+1);
                }
            }
            result = max(result, f[i]);
        }
        return result;
    }
#endif
    
/*
// http://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
The loop runs for N elements. In the worst case (what is worst case input?), 
we may end up querying ceil value using binary search (log i) for many A[i].

Therefore, T(n) = O(N log N). 
Analyse to ensure that the upper and lower bounds are also O( N log N ). 
The complexity is THETA (N log N).
*/
    int lengthOfLIS(vector<int>& v) {
        if (v.size() == 0)
            return 0;

        std::vector<int> tail(v.size(), 0);
        int length = 1; // always points empty slot in tail

        tail[0] = v[0];
        for (size_t i = 1; i < v.size(); i++) {
            if (v[i] < tail[0])
                // new smallest value
                tail[0] = v[i];
            else if (v[i] > tail[length-1])
                // v[i] extends largest subsequence
                tail[length++] = v[i];
            else
                // v[i] will become end candidate of an existing subsequence or
                // Throw away larger elements in all LIS, to make room for upcoming grater elements than v[i]
                // (and also, v[i] would have already appeared in one of LIS, identify the location and replace it)
                tail[CeilIndex(tail, -1, length-1, v[i])] = v[i];
        }

        return length;
    }
    
    // Binary search (note boundaries in the caller)
    int CeilIndex(std::vector<int> &v, int l, int r, int key) {
        while (r-l > 1) {
        int m = l + (r-l)/2;
        if (v[m] >= key)
            r = m;
        else
            l = m;
        }

        return r;
    }

};

