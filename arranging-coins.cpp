/*
You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k coins.

Given n, find the total number of full staircase rows that can be formed.

n is a non-negative integer and fits within the range of a 32-bit signed integer.

Example 1:

n = 5

The coins can form the following rows:
¤
¤ ¤
¤ ¤

Because the 3rd row is incomplete, we return 2.
Example 2:

n = 8

The coins can form the following rows:
¤
¤ ¤
¤ ¤ ¤
¤ ¤

Because the 4th row is incomplete, we return 3.
*/


class Solution {
public:
    int arrangeCoins(int n) {
        int k = 0;
        long sum = 0;
        for (int i=1; i<=n; ++i) {
            if (sum+i <= n) {
                sum += i;
                ++k;
            } else {
                break;
            }
        }
        
        return k;
    }
};




/*
1+2+...+x = n => x*(1+x)/2 = n
x^2 + x - 2n = 0
x = (sqrt(8.0n+1)-1)/2;

https://en.wikipedia.org/wiki/Quadratic_formula
*/
class Solution {
public:
    int arrangeCoins(int n) {
        return (sqrt(8.0*n+1)-1)/2;
    }
};
