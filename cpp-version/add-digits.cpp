#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
 Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

For example:

Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

Follow up:
Could you do it without any loop/recursion in O(1) runtime?
*/

#if 0

class Solution {
public:
    int addDigits(int num) {
        assert(num>=0);
        while(num >= 10) {
            int sum = 0;
            while(num) {
                sum += num%10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
};

#else

/*
http://mathworld.wolfram.com/DigitalRoot.html
The base-10 digital roots of the first few integers are 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, ...

digital root 数根
          |-- 0   if n==0
 dr(n) =  |-- 9   if n!=0 && n%9 ==0 
          |-- n%9 if n!=0 
or 
 dr(n) = 1 + ((n-1) % 9), 一个数x的数根为mod(x-1,9)+1.因为正整数对9取模的结果取值为[0,8],而数根的取值为[1,9]。

abc = a*100 + b*10 + c = a*(99+1) + b*(9+1) + c = (a*99 + b*9) + (a+b+c)
所以dr(abc) = dr(a+b+c), 即一个数和它的各数位之和的模9相同

数根就是不断地求这个数的各位数之和，直到求到个位数为止。所以数根一定和该数模9同余，
但是数根又是大于零小于10的，所以数根模9的余数就是它本身，也就是说该数模9之后余数就是数根。

12345 = 1 * 9999 + 2 * 999 + 3 * 99 + 4 * 9 + 5 + (1+ 2+ 3 + 4 + 5)

12345 % 9 = (1 + 2 + 3 + 4 +5 ) % 9

m % 9 = a; n % 9 = b 即 m = 9 * x + a; n = 9 * y + b；可推出 (m + n) % 9 = a + b = m % 9 + n % 9；
[1 * 9999 + 2 * 999 + 3 * 99 + 4 * 9 + (1+ 2+ 3 + 4 + 5)] % 9 = (1 * 9999) % 9 + (2 * 999) % 9 + (3 * 99) % 9 + (4 * 9) % 9 + (1+ 2+ 3 + 4 + 5) % 9 = 0 + 0 + 0 + 0 + (1 + 2 + 3 + 4 + 5) % 9 = (1 + 2 + 3 + 4 + 5) % 9。
证明完成：12345 % 9 = (1 + 2 + 3 + 4 + 5) % 9 ;

因为数根恰好是小于10，与取mod 9结束也一致，所以：
(12345) % 9 = (1 + 2 + 3 + 4 + 5) % 9 = 12 % 9 = (1 +2) % 9 = 3 % 9 = 3。

*/
class Solution {
public:
    int addDigits(int num) {
        assert(num>=0);
        if (num == 0) return 0;
        if (num%9 == 0) return 9;
        return num%9;
    }
};

#endif

int main() {
    Solution solution;

    assert(solution.addDigits(0) == 0);
    assert(solution.addDigits(2) == 2);
    assert(solution.addDigits(12) == 3);
    assert(solution.addDigits(157) == 4);
    assert(solution.addDigits(1289) == 2);

    return 0;
}
