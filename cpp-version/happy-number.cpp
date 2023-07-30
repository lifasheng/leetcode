#include <iostream>
#include <cassert>
#include <string>
#include <unordered_set>
using namespace std;

/*
Write an algorithm to determine if a number is "happy".

A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

Example: 19 is a happy number

    1^2 + 9^2 = 82
    8^2 + 2^2 = 68
    6^2 + 8^2 = 100
    1^2 + 0^2 + 0^2 = 1

*/

/*

12 就不是一个happy number

*/

#define SLOW_FAST

#ifdef MySolution

class Solution {
public:
    int digitSquareSum(int num) {
        int result = 0;
        while(num) {
            int d = num%10;
            result += d*d;
            num /= 10;
        }
        return result;
    }
    bool isHappy(int n) {
        if (n<=0) return false;

        unordered_set<int> si;
        while(n != 1) {
            if (si.count(n)) {
                return false;
            }
            si.insert(n);
            n = digitSquareSum(n);
        }
        return true;
    }
};

#endif


#ifdef MagicSolution

/*
I have to admit that when I first saw the tile I was at a loss to how to begin.
So I tested several numbers, and I found 
        all happy numbers return 1 finally,
        all unhappy numbers return 4 finally.
maybe it is  the math-logic.
*/
class Solution {
public:
    bool isHappy(int n) {
        if (1 == n) {
            return true;
        }
        else if (4 == n) {
            return false;
        }
        else {
            int sum = 0;
            while (n){
                sum += pow(n % 10, 2);
                n /= 10;
            }
            return isHappy(sum);
        }
    }
};
#endif


#ifdef SLOW_FAST
/*
O(1) space and no magic math property involved
I see the majority of those posts use hashset to record values. Actually, we can simply adapt the Floyd Cycle detection algorithm. I believe that many people have seen this in the Linked List Cycle detection problem.
*/

class Solution {
public:
    int digitSquareSum(int num) {
        int result = 0;
        while(num) {
            int d = num%10;
            result += d*d;
            num /= 10;
        }
        return result;
    }
    bool isHappy(int n) {
        if (n<=0) return false;

        int slow = n, fast = n;
        do {
            slow = digitSquareSum(slow);
            fast = digitSquareSum(digitSquareSum(fast));
        } while(slow != fast);

        return slow == 1;
    }
};

#endif

int main() {
    Solution solution;

    //assert(solution.isHappy(0) == false);
    assert(solution.isHappy(12) == false);
    assert(solution.isHappy(19) == true);

    return 0;
}
