/*
633. Sum of Square Numbers
Easy

Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.

Example 1:
Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5
Example 2:
Input: 3
Output: False
*/


class Solution {
public:
    
#define with_sqrt_and_set
    
#ifdef with_sqrt_and_set
    bool judgeSquareSum(int c) {
        int n = (int)sqrt(c);
        
        unordered_set<int> s;
        for(int i=0; i<=n; ++i) {
            s.insert(i*i);
        }
        for (int i=0; i<=n; ++i) {
            if (s.count(c-i*i) > 0) return true;
        }
        return false;
    }
#endif
    
#ifdef with_sqrt_two_pointer // best performance
    bool judgeSquareSum(int c) {
        int n = (int)sqrt(c);
        
        int left=0, right = n;
        while(left <= right) {
            int d = left*left + right*right;
            if (d == c) {
                return true;
            } else if (d>c) {
                --right;
            } else {
                ++left;
            }
        }
        return false;
    }
#endif
    
#ifdef without_sqrt
    bool judgeSquareSum(int c) {
        for(long a=0; a*a<=c; ++a) {
            long b = c-a*a;
            if (binarySearch(0, b, b)) return true;
        }
        
        return false;
    }
    
    bool binarySearch(long s, long e, long n) {
        if (s>e) return false;
        
        while(s <= e) {
            long m = s + (e-s)/2;
            long d = m*m;
            if (d == n) {
                return true;
            } else if (d>n) {
                e = m - 1;
            } else {
                s = m + 1;
            }
        }
        return false;
    }
#endif
    
};
