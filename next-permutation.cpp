class Solution {
public:
#define M1
// 对于prev_permutation, 主要是在step1和step2的比较操作是相反的。
// 即：step1中用<=，step2中用greater<int>()
#ifdef M1
    void nextPermutation(vector<int> &num) {
        next_permutation(num.begin(), num.end());
    }
    
    template<typename BidirectionalIterator>
    bool next_permutation(BidirectionalIterator first, BidirectionalIterator last) {
        if (first == last) return false;
        
        typedef std::reverse_iterator<BidirectionalIterator> ReverseIterator;
        
        ReverseIterator rfirst(last);
        ReverseIterator rlast(first);
        
        ReverseIterator pivot = next(rfirst); // start from the second element in reverse direction
        
        // step1
        // note that >= is important, consider example: [1, 1]
        while(pivot != rlast && *pivot >= *(prev(pivot))) {
            ++pivot;
        }
        
        if (pivot == rlast) {
            reverse(first, last);
            return false;
        }
        
        // step2
        ReverseIterator change = find_if(rfirst, pivot, bind1st(less<int>(), *pivot));
        
        // step3
        std::swap(*pivot, *change);
        
        // step 4
        reverse(rfirst, pivot);
        
        return true;
    }
#endif
};
