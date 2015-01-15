class Solution {
public:
#define M2
#ifdef M1
    int singleNumber(int A[], int n) {
        int x = 0; //注意：0与任何整数异或都得到该整数本身。
        for(int i=0; i<n; ++i) {
            x ^= A[i];
        }
        return x;
    }
#endif
#ifdef M2 // common solution for single number
    int singleNumber(int A[], int n) {
        const int W = sizeof(int) * 8;
        vector<int> b(W, 0);

        for(int i=0; i<n; ++i) {
            for(int j=0; j<W; ++j) {
                b[j] += 0x1 & (A[i] >> j);
                b[j] %= 2;
            }
        }

        int result = 0;
        for(int i=0; i<W; ++i) {
            result += b[i] << i;
        }

        return result;
    }
#endif
};
