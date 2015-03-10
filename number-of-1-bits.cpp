class Solution {
public:
#define M1
#ifdef M1
    int hammingWeight(uint32_t n) {
        int count = 0;
        while(n) {
            if (n&0x1)  {
                count++;
            }
            n >>= 1;
        }
        return count;
    }
#endif
#ifdef M2
/*
此算法基于这样一个事实：x-1使得以二进制表示的x，从低向高位开始包括第一个1在内的值，
都由0变成1，由1变成0。如11-01 = 10, 10 – 01 = 01, 01 – 01 = 00, 100 – 001 = 011。
而&操作使得发生变化的位置都变成0，这样就去除了1个1，从而有几个1就&几次，最终x必变成0.
*/
    int hammingWeight(uint32_t n) {
        int count = 0;
        while(n) {
            ++count;
            n = n&(n-1);
        }
        return count;
    }
#endif
#ifdef M3
// http://en.wikipedia.org/wiki/Hamming_weight
// http://blog.csdn.net/gsyzhu/article/details/8095174
    int hammingWeight(uint32_t n) {
        const uint32_t m1=0x55555555; // 01010101...
        const uint32_t m2=0x33333333; // 00110011...
        const uint32_t m4=0x0f0f0f0f; // binary:  4 zeros,  4 ones ...
        const uint32_t m8=0x00ff00ff; // binary:  8 zeros,  8 ones ...
        const uint32_t m16=0x0000ffff; // binary: 16 zeros, 16 ones ...
        
        n = (n&m1) + ((n>>1)&m1);
        n = (n&m2) + ((n>>2)&m2);
        n = (n&m4) + ((n>>4)&m4);
        n = (n&m8) + ((n>>8)&m8);
        n = (n&m16) + ((n>>16)&m16);
        return n;
    }
#endif
};
