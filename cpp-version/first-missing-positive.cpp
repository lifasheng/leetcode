class Solution {
public:
/*
》用一个hash或一个数组来保存每个数的出现次数，这个方法很简单直接，但要O(n)space。
桶排序的方法：
http://n00tc0d3r.blogspot.com/2013/03/find-first-missing-positive.html
Let's take another look at the problem. It is asking for the first missing POSITIVE integer.
So, given a number in the array,
if it is non-positive, ignore it;
if it is positive, say we have A[i] = x, we know it should be in slot A[x-1]! 
That is to say, we can swap A[x-1] with A[i] so as to place x into the right place.
We need to keep swapping until all numbers are either non-positive or in the right places. 
The result array could be something like [1, 2, 3, 0, 5, 6, ...]. 
Then it's easy to tell that the first missing one is 4 by iterate through the array and compare each value with their index.
找重复元素使用的方法，这个方法不仅改变元素位置，还改变元素的值，正数的会变成负数
不过思路值得学习。
http://www.geeksforgeeks.org/find-the-smallest-positive-number-missing-from-an-unsorted-array/
http://www.geeksforgeeks.org/find-the-two-repeating-elements-in-a-given-array/
*/
    int firstMissingPositive(int A[], int n) {
        // bucket sort
        for (int i=0; i<n; ++i) {
            while(A[i] != i+1) {
                // A[i] <=0 || A[i] > n 保证A[i]-1作为下标时不会越界。
                if (A[i] <= 0 || A[i] > n || A[i] == A[A[i]-1]) break;
                std::swap(A[i], A[A[i]-1]);
            }
        }
        
        // find the first missing positive integer
        int i = 0;
        for(i=0; i<n; ++i) {
            if (A[i] != i+1) break;
        }
        
        return i+1;
    }
};
