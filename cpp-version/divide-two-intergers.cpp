class Solution {
public:
/*
dividend/divisor
注意： 对于int，-INT_MIN == INT_MIN, 仍然是负数，这也是溢出，所以改为long
test case:
-2147483648, -1 会溢出，因为INT_MAX=2147483647, 所以返回INT_MAX
-2147483648, 2,
1, 0
0, 2
3, 1
example:
16/3 = 5：
loop1:
dd=16, ds=3, tempds=3:
3*2*2*2=24>16, tempds=24,count=8
dd-=24/2=16-12=4
result += 8/2 =4
loop2:
dd=4, ds=3, tempds=3:
3*2=6>4, tempds=6, count=2
dd-=6/2=4-3=1
result += 2/2 = 4+1 = 5
*/
    int divide(int dividend, int divisor) {
        if (dividend == 0) return 0;
        if(divisor == 0) return INT_MAX;
        if (divisor == 1)  return dividend;
        
        if (dividend == INT_MIN && divisor == -1) return INT_MAX;
        
        int result = 0; // 当 dividend = INT_MIN 时,divisor = -1 时,结果会溢出
        const bool sign = (dividend>0 && divisor<0) || (dividend<0 && divisor>0); // 异号
        
        unsigned long long dd = dividend>0?dividend:-(long long)dividend; // 这里-(long long)是必须的，不能写成-dividend。
        unsigned long long ds = divisor>0?divisor:-(long long)divisor;
        while(dd >= ds) {
            unsigned long long count = 1; // 小心溢出
            unsigned long long tempds = divisor>0?divisor:-(long long)divisor;
            while(dd >=tempds) {
                tempds <<= 1;
                count <<=1;
            }
            dd -= (tempds>>1);
            result += (count >>1);
        }
         
        return sign?-result:result;
    }
};
