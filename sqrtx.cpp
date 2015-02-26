class Solution {
public:
/*
对于一个非负数x， 它的平方根是小于x/2+1的, (x/2+1)^2 = x^2/4+x+1 > x
在[0, x/2+1]范围内可以进行二分搜索，求出x的平方根。
*/
    int sqrt(int x) {
        int low = 1, high = x/2+1;
        while(low <= high) {
            int mid = (low+high)/2;
            int temp = x/mid; // 这里不用mid*mid是防止overflow
            
            if (temp == mid) {
                return mid;
            }
            else if (temp > mid) {
                low = mid+1;
            }
            else {
                high = mid-1;
            }
        }
        
        return high;
    }
};
