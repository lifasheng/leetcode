#include <iostream>
#include <cassert>
using namespace std;

int myNum = 1702766719;
int N = 2126753390;

// Forward declaration of guess API.
// @param num, your guess
// @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
int guess(int num);

#if 0

class Solution {
public:
    int guessNumber(int n) {
        assert(n>=1);
        long low = 1, high = n;
        long mid = low;
        while(low <= high) {
            mid = (low+high) >> 1;
            int g = guess(mid);
            if (g == 0) return mid;
            else if (g == -1) high = mid-1;
            else low = mid+1;
        }
        return mid;
    }
};

#else

class Solution {
public:
    int guessNumber(int n) {
        assert(n>=1);
        int low = 1, high = n;
        int mid = low;
        while(low <= high) {
            mid = (int)( (low>>1) + (high >> 1) );
            if (low & 0x1 && high & 0x1) mid += 1;
            int g = guess(mid);
            if (g == 0) return mid;
            else if (g == -1) high = mid-1;
            else low = mid+1;
        }
        return mid;
    }
};

#endif

int guess(int num) {
   if (myNum < num) return -1; 
   else if (myNum > num) return 1;
   else return 0;
}


int main() {
    Solution s;
    assert(s.guessNumber(N) == myNum);

    N = 1;
    myNum = 1;
    assert(s.guessNumber(N) == myNum);

    N = 3;
    myNum = 3;
    assert(s.guessNumber(N) == myNum);

    N = 4;
    myNum = 4;
    assert(s.guessNumber(N) == myNum);

    return 0;
}
