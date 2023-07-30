class Solution {
public:
    double pow(double x, int n) {
        if (n<0) return 1.0/power(x, -n);
        return power(x, n);
    }
    double power(double x,  unsigned int n) {
        if (n==0) return 1;
        
        double d = power(x, n/2);
        
        if (n&0x1) {
            return d*d*x;
        }
        else {
            return d*d;
        }
    }
};
