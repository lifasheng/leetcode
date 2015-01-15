class Solution {
public:
    int singleNumber(int A[], int n) {
        int x = 0; //注意：0与任何整数异或都得到该整数本身。
        for(int i=0; i<n; ++i) {
            x ^= A[i];
        }
        return x;
    }
};
