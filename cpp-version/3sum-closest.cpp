class Solution {
public:
    int threeSumClosest(vector<int> &num, int target) {
        int result = 0;
        int min_gap = INT_MAX;
        if (num.size() < 3) return result;
        
        sort(num.begin(), num.end());
        
        typedef vector<int>::iterator Iterator;
        Iterator end = num.end();
        for(Iterator a=num.begin(); a!=prev(end, 2); ++a) {
            Iterator b = next(a);
            Iterator c = prev(end);
            
            while(b < c) {
                int sum = *a+*b+*c;
                int new_gap = abs(sum - target);
                if (new_gap < min_gap) {
                    min_gap = new_gap;
                    result = sum;
                }
                
                if (sum < target) {
                    ++b;
                }
                else { // sum >= target
                    --c;
                }
            }
        }
        
        return result;
    }
};
