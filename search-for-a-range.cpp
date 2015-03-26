class Solution {
public:
// 注意这里c++98和c++11的两种写法
/*
#if 0 // c++98的写法
    template <typename ForwardIterator, typename T>
    ForwardIterator lower_bound(ForwardIterator first, ForwardIterator last, const T& val) {
        typedef typename iterator_traits<ForwardIterator>::difference_type DifferenceType;
        while(first < last) {
            DifferenceType d = distance(first, last);
            ForwardIterator mid = first;
            advance(mid, d/2);
            if (val > *mid) {
                first = ++mid;
            }
            else {
                last = mid;
            }
        }
        
        return first;
    }
#endif // #if 0
*/
    // lower_bound 返回大于等于target的第一个位置
    // If all the element in the range compare less than val, the function returns last.
    template <typename ForwardIterator, typename T>
    ForwardIterator lower_bound(ForwardIterator first, ForwardIterator last, const T& val) {
        while(first < last) {
            ForwardIterator mid = next(first, distance(first, last)/2);
            if (val > *mid) { // 因为我们找大于等于target的元素，所以对小于的元素直接跳过。
                first = ++mid;
            }
            else {
                last = mid;
            }
        }
        
        return first;
    }
    // upper_bound 返回大于target的第一个位置
    template <typename ForwardIterator, typename T>
    ForwardIterator upper_bound(ForwardIterator first, ForwardIterator last, const T& val) {
        while(first < last) {
            ForwardIterator mid = next(first, distance(first, last)/2);
            
            if (val >= *mid) { // 因为我们找大于target的元素，所以对小于等于的元素直接跳过。
                first = ++mid;
            }
            else {
                last = mid;
            }
        }
        
        return first;
    }
    vector<int> searchRange(int A[], int n, int target) {
        auto lb = lower_bound(A, A+n, target);
        auto ub = upper_bound(lb, A+n, target);
        if (lb == A+n || *lb != target) {
            return vector<int>{-1, -1};
        }
        else {
            return vector<int>{distance(A, lb), distance(A, prev(ub))};
        }
    }
};

// rewrite search for a range.
class Solution {
public:
    template <typename FI, typename T>
    FI lower_bound(FI first, FI last, const T &val) {
        while (first < last) {
            FI mid = next(first,  distance(first, last)/2);
            if (*mid < val) first = next(mid);
            else last = mid;
        }
        return first;
    }
    template <typename FI, typename T>
    FI upper_bound(FI first, FI last, const T &val) {
        while (first < last) {
            FI mid = next(first,  distance(first, last)/2);
            if (*mid <= val) first = next(mid);
            else last = mid;
        }
        return first;
    }
    vector<int> searchRange(int A[], int n, int target) {
        if (!A || n<=0) return {-1, -1};
        int *lb = lower_bound(A, A+n, target);
        int *ub = upper_bound(A, A+n, target);
        // lb == A+n 表示所有的数都小于target;
        // *lb != target 表示所有的数都大于target, 此时lb == A, 但不能用lb == A,因为这会排除A[0]==target的情况。
        if (lb == A+n || *lb !=target) return {-1, -1};
        return {distance(A, lb), distance(A, prev(ub))};
    }
};
