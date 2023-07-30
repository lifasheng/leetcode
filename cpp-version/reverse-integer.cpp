class Solution {
public:
/*
1. 正数
2. 负数： 注意， -123%10 = -3, -123/10 = -12
3. 0
4. 100 => 001 还是 1
5. 正溢出
6. 负溢出
*/
    int reverse(int x) {
        int y = 0;
        while(x) {
            // handle overflow, return 0 if overflow
            if(y>0) {
                if (y>INT_MAX/10 || (y==INT_MAX/10 && x%10>INT_MAX%10)) {
                    return 0;
                }
            }
            else if (y<0) {
                if (y<INT_MIN/10 || (y==INT_MIN/10 && x%10<INT_MIN%10)) {
                    return 0;
                }
            }
            y = y*10 + x%10;
            x /= 10;
        }
        
        return y;
    }
};
