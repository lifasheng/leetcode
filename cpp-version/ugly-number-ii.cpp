#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
 Write a program to find the n-th ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.

Note that 1 is typically treated as an ugly number. 
*/

#if 0
/*
思路：
用数组保存每一个丑数，在生成新的丑数时，拿最后一个丑数分别除以2，3，5，然后将得到的除数在已有的数组中二分查找，找到upper_bound，就是下一个可能的丑数。
注意的是，不能简单地拿最后一个丑数除以2，3，5，然后加1，再乘以2，3，5，这样可能会得到不是丑数，比如14，就是 (12/2+1)*2=14
新的丑数必须是已有丑数相乘得到的。
*/

class Solution {
public:
    int nthUglyNumber(int n) {
        assert(n>=1);
        vector<int> uns;
        uns.push_back(1);
        while(uns.size() < n) {
            auto last = uns[uns.size()-1];
            auto d2 = upper_bound(uns.begin(), uns.end(), last/2);
            auto d3 = upper_bound(uns.begin(), uns.end(), last/3);
            auto d5 = upper_bound(uns.begin(), uns.end(), last/5);
            if (d2 == uns.end()) {
                d2 = prev(d2);
            }
            if (d3 == uns.end()) {
                d3 = prev(d3);
            }
            if (d5 == uns.end()) {
                d5 = prev(d5);
            }
            int m2 = *d2 * 2;
            int m3 = *d3 * 3;
            int m5 = *d5 * 5;
            uns.push_back(min(m2,min(m3,m5)));
        }
        return uns[uns.size()-1];
    }
};

#else

/*
Hint:

    The naive approach is to call isUgly for every number until you reach the nth one. Most numbers are not ugly. Try to focus your effort on generating only the ugly ones.
    An ugly number must be multiplied by either 2, 3, or 5 from a smaller ugly number.
    The key is how to maintain the order of the ugly numbers. Try a similar approach of merging from three sorted lists: L1, L2, and L3.
    Assume you have Uk, the kth ugly number. Then Uk+1 must be Min(L1 * 2, L2 * 3, L3 * 5).


L1: 1*2, 2*2, 3*2, 4*2, 5*2, 6*2, ... , i*2, ...
L2: 1*3, 2*3, 3*3, 4*3, 5*3, 6*3, ... , j*3, ...
L3: 1*5, 2*5, 3*5, 4*5, 5*5, 6*5, ... , k*5, ...
every time we select the smallest in L1,L2,L3 and add it into the vector,for example the smallest is x2(note that x2 is maximal in vector),then we remove the numbers which equals to x2 in L1,L2,L3.
Next time the number we select must be greater than x2, it can't be in the vector.

注意和用最小堆来合并多个有序链表的区别，在合并链表时，相同元素可以重复，而且顺序无关。
但是对于丑数来说，不能出现重复的丑数，所以如果用最小堆来辅助生成丑数的话，要注意判断新生成的丑数是否和已经生成的最后一个丑数相同，如果相同，则再循环一次，生成新的丑数。

We have an array k of first n ugly number. We only know, at the beginning, the first one, which is 1. 
Then 
k[1] = min(k[0]*2, k[0]*3, k[0]*5) = min(2, 3, 5). The answer is k[0]*2 = 2. So we move 2's pointer to 1. 
k[2] = min(k[1]*2, k[0]*3, k[0]*5) = min(4, 3, 5). The answer is k[0]*3 = 3. So we move 3's pointer to 1.
k[3] = min(k[1]*2, k[1]*3, k[0]*5) = min(4, 6, 5). The answer is k[1]*2 = 4. So we move 2's pointer to 2.
k[4] = min(k[2]*2, k[1]*3, k[0]*5) = min(6, 6, 5). The answer is k[0]*5 = 5. So we move 5's pointer to 1.
k[5] = min(k[2]*2, k[1]*3, k[1]*5) = min(6, 6, 10). The answer is k[2]*2 = k[1] * 3 = 6. So we move 2's pointer to 3 and 3's pointer to 2.
k[6] = min(k[3]*2, k[2]*3, k[1]*5) = min(8, 9, 10). The answer is k[3]*2 = 8. So we move 2's pointer to 4.
And so on. 
Be careful about the cases such as 6, in which we need to forward both pointers of 2 and 3. (6 == 2*3 == 3*2)
*/
class Solution {
public:
    int nthUglyNumber(int n) {
        assert(n>=1);
        vector<int> uns(n);
        uns[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0;
        for(int i=1; i<n; ++i) {
            uns[i] = min( uns[p2]*2, min( uns[p3]*3, uns[p5]*5 ) );
            if (uns[i] == uns[p2]*2) ++p2;
            if (uns[i] == uns[p3]*3) ++p3;
            if (uns[i] == uns[p5]*5) ++p5;
        }
        return uns[n-1];
    }
};

#endif

int main() {
    Solution solution;

    assert(solution.nthUglyNumber(1) == 1);
    assert(solution.nthUglyNumber(2) == 2);
    assert(solution.nthUglyNumber(3) == 3);
    assert(solution.nthUglyNumber(4) == 4);
    assert(solution.nthUglyNumber(5) == 5);
    assert(solution.nthUglyNumber(6) == 6);
    assert(solution.nthUglyNumber(7) == 8);
    assert(solution.nthUglyNumber(8) == 9);
    assert(solution.nthUglyNumber(9) == 10);
    assert(solution.nthUglyNumber(10) == 12);
    assert(solution.nthUglyNumber(11) == 15);

    return 0;
}
