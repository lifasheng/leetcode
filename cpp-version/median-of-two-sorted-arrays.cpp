class Solution {
public:
    double findMedianSortedArrays(int A[], int m, int B[], int n) {
        int total = m+n;
        if (total & 0x1) {
            return findK(A, m, B, n, total/2+1);
        }
        else {
            return (findK(A, m, B, n, total/2) + findK(A, m, B, n, total/2+1)) / 2;
        }
    }
    
    double findK(int A[], int m, int B[], int n, int k) {
        // always assume m <= n
        if (m>n) return findK(B, n, A, m, k);
        if (m == 0) return B[k-1];          //递归出口
        if (k == 1) return min(A[0], B[0]); //递归出口
        
        int ia = min(k/2, m), ib = k-ia;
        if (A[ia-1] < B[ib-1]) {
            return findK(A+ia, m-ia, B, n, k-ia);
        }
        else if (A[ia-1] > B[ib-1]) {
            return findK(A, ia, B+ib, n-ib, k-ib);
        }
        else { // A[ia-1] == B[ib-1]
            return A[ia-1];
        }
    }
};
