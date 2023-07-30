class Solution {
public:
#define M2
#ifdef M1 //备忘录法 O(n) time, O(n) space
    int candy(vector<int> &ratings) {
        int size = ratings.size();
        vector<int> f(size);
        int sum = 0;
        for(int i=0; i<size; ++i) {
            sum += solve(ratings, f, i);
        }
        return sum;
    }
    
    int solve(vector<int> &ratings, vector<int> &f, int i) {
        if (f[i] == 0) {
            f[i] = 1;
            if (i>0 && ratings[i] > ratings[i-1]) {
                f[i] = max(f[i], solve(ratings, f, i-1)+1);
            }
            
            if (i<ratings.size()-1 && ratings[i] > ratings[i+1]) {
                f[i] = max(f[i], solve(ratings, f, i+1)+1);
            }
        }
        
        return f[i];
    }
#endif
#ifdef M2 // 左右两边各扫描一遍，O(n) time, O(n) space
    int candy(vector<int> &ratings) {
        int size = ratings.size();
        
        vector<int> num(size);
        
        // from left to right
        num[0] = 1;
        for(int i=1; i<size; ++i) {
            if (ratings[i-1] < ratings[i]) {
                num[i] = num[i-1] +1;
            }
            else {
                num[i] = 1;
            }
        }
        
        // from right to left
        int sum = num[size-1];
        for(int i=size-2; i>=0; --i) {
            if (ratings[i] > ratings[i+1]) {
                num[i] = max(num[i], num[i+1] + 1);
            }
            
            sum += num[i];
        }
        
        return sum;
    }
#endif
};
