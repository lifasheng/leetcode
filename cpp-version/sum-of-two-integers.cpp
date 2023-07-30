#include <iostream>
#include <cassert>
#include<stdio.h>
using namespace std;

/*
Takeaway:

Adding two integers without using plus operator:
-b == (~b + 1)
a+b == a - (-b) == a - (~b + 1) == a - ~b - 1;

*/

/*
Just a brief Proof why this loop will end within O(logn):

    We definte card(n) to be the count of 1s in n's binary representation.
    by the end of each loop, card(sum) <= min(card(a), card(b)), and the equal sign will only happens when a = b. (think about xor op)
        So if a != b, card(sum) <= min(card(a), card(b))
        If a == b, sum in next loop will be 0, and b will be 0 then, and While will end in 1 extra loop. i.e. this will only happen at the last loop.

So combining the previous, while loops at most O(logn) times.
*/

//Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
class Solution {
public:
#if 0
    int getSum(int a, int b) {
         if (b == 0) return a;
         return getSum( a ^ b, (a & b) << 1);
    }
#else
    int getSum(int a, int b) {
         int sum = a;
         // Iterate till there is no carry
         while (b != 0) {
             // Sum of bits of x and y where at least one of the bits is not set, without carrying
             sum = a ^ b;

             // carry now contains common set bits of x and y
             // Carry is shifted by one so that adding it to a gives the required sum
             b = (a & b) << 1;

             a = sum;
         }
         return sum;
    }
#endif
};

int subtract(int a,int b) {
    /*
    if(b == 0) return a;
    return subtract(a^b, (~a & b)<<1);
    */
    Solution s;
    return s.getSum(s.getSum(a, ~b), 1); // a-b = a+(-b) = a+(~b+1) = (a+~b)+1
}

int main() {
    Solution s;
    int a, b;

    a = 2, b = 6;
    assert(s.getSum(a, b) == (a+b));
    assert(subtract(a, b) == (a-b));

    a = 0, b = 4;
    assert(s.getSum(a, b) == (a+b));
    assert(subtract(a, b) == (a-b));

    a = -1, b = 1;
    assert(s.getSum(a, b) == (a+b));
    assert(subtract(a, b) == (a-b));

    a = -5, b = 4;
    assert(s.getSum(a, b) == (a+b));
    assert(subtract(a, b) == (a-b));

    return 0;
}
