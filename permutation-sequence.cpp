class Solution {
public:
    string getPermutation(int n, int k) {
        string s(n, '0');
        for (int i=0; i<n; ++i) {
            s[i] += i+1;
        }
        
        return kth_permutation(s, k);
    }
#if 1
    int Factorial(int n) {
        int result = 1;
        for(int i=1; i<=n; ++i) {
            result *= i;
        }
        return result;
    }
    
    template <typename Sequence>
    Sequence kth_permutation(const Sequence &seq, int k) {
        int n = seq.size();
        
        Sequence temp(seq);
        Sequence result;
        
        --k; // start from zero
        
        int base = Factorial(n-1);
        
        typedef typename Sequence::iterator Iterator;
        for (int i=n-1; i>0; --i) {
            Iterator iter = next(temp.begin(), k/base);
            result.push_back(*iter);
            temp.erase(iter);
            k %= base;
            base /= i;
        }
        
        result.push_back(temp[0]);
        
        return result;
    }
#else
#endif
};
