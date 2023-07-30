class Solution {
public:
    template <typename ForwardIterator, typename T>
    ForwardIterator my_lower_bound(ForwardIterator first, ForwardIterator last, const T& val) {
        while(first != last) {
            ForwardIterator mid = next(first, distance(first, last)/2);
            if (val > *mid) {
                first = ++mid;
            }
            else {
                last = mid;
            }
        }
        
        return first;
    }
    int searchInsert(int A[], int n, int target) {
        return distance(A, my_lower_bound(A, A+n, target));
    }
};
