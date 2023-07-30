class Solution {
public:
    int removeDuplicates(int A[], int n) {
        if (!A || n <= 0)
            return 0;
        int last = 0; // last valid index
        int m = 0; // flag to indicate if two same number has meet
        for (int i=1; i<n; ++i) {
            if (A[i] == A[last]) {
                if (m == 0) { // meet same number, but just duplicate once, ok
                    m = 1; 
                    A[++last] = A[i];
                }
            }
            else {  // A[i] != A[last]
                m = 0; // start a new number
                A[++last] = A[i];
            }
        }
        
        return last+1;
    }
};
