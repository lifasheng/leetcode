class Solution {
public:
#define M2
#ifdef M1
    int binary_to_gray(int n) {
        return n ^ (n>>1);
    }
    vector<int> grayCode(int n) {
        vector<int> result;
        int size = 1<<n;
        result.reserve(size);
        
        for(int i=0; i<size; ++i) {
            result.push_back(binary_to_gray(i));
        }
        
        return result;
    }
#endif
#ifdef M2
    vector<int> grayCode(int n) {
        vector<int> result;
        result.push_back(0);
        for(int i=0; i<n; ++i) {
            int highest_bit = 1<<i;
            int cur_size = result.size();
            for(int j=cur_size-1; j>=0; --j) {
                result.push_back(result[j] | highest_bit);
            }
        }
        
        return result;
    }
#endif
};
