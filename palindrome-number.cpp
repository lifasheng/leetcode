class Solution {
public:
#define M2
    
#ifdef M1
/*
用O(n)辅助空间
负数被认为是非palindrome。
*/
    bool isPalindrome(int x) {
        if (x<0) return false;
        
        //下面的代码可以处理正负数。
        vector<int> v;
        while(x) {
            v.push_back(x%10);
            x /= 10;
        }
        
        int i=0, j=v.size()-1;
        while(i<j) {
            if (v[i] != v[j]) return false;
            ++i;
            --j;
        }
        return true;
    }
#endif
    
#ifdef M2
/*
不断地取第一位和最后一位(10 进制下)进行比较,相等则取第二位和倒数第
二位,直到完成比较或者中途找到了不一致的位
*/
    bool isPalindrome(int x) {
        //负数， 注意即使要求处理负数，也不能直接x=-x，因为-INT_MIN == INT_MIN
        if (x<0) return false; 
        
        /*
        注意，这里必须用d<=x/10,而不能d<x,因为可能会溢出。
        比如：  x  = 1874994781 
        而 INT_MAX = 2147483647
        当      d  = 1000000000 时， d<x, d*10 就会溢出, 结果是d又小于x，造成死循环。
        // wrong implementation
        int d = 1;
        while(d<x) d *= 10;
        d = 10;
        */
        int d = 1;
        while(d<=x/10) d *= 10;
        
        while(x) {
            int l =x/d;
            int r = x%10;
            if (l!=r) return false;
            
            x = x%d/10;
            d /= 100;
        }
        
        return true;
    }
#endif
};




// test case: -1: false, 101: true, 1001: true, 1010: false, 6: true
// 思路：
// 首先判断一个数有多少位，然后类似于string，用两个指针从前后往中间迭代判断是否相等。
class Solution {
public:
    bool isPalindrome(int x) {
        if (x<0) {
            return false;

            // if (x == INT_MIN) return false;
            // return isPalindrome(-x);
        }

        int m = howManyDigits(x);

        int low = 1, high = m;
        while (low <= high) {
            if (getDigit(x, low) != getDigit(x, high)) {
                return false;
            }
            ++low;
            --high;
        }

        return true;
    }

    int getDigit(int x, int i) {
        int n = pow(10, i-1);
        x /= n;
        return x%10;
    }

    int howManyDigits(int x) {
        int m=1;
        while(x>=10) {
            ++m;
            x/=10;
        }
        return m;
    }
};
