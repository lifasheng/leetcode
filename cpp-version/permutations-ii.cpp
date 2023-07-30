class Solution {
public:
#define M2
#ifdef M1
// 实现STL中的next_permutation, 时间复杂度 O(n!),空间复杂度 O(1)
// 代码同permutations。
    template <typename BirectionalIterator>
    bool my_next_permutation(BirectionalIterator first, BirectionalIterator last) {
        if (first == last) return false;
        
        typedef reverse_iterator<BirectionalIterator> ReverseIterator;
        
        ReverseIterator rfirst(last), rlast(first);
        
        // step1
        ReverseIterator pivot = next(rfirst);
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
        swap(*pivot, *change);
        
        // step4
        reverse(rfirst, pivot);
        
        return true;
    }
    vector<vector<int> > permuteUnique(vector<int> &num) {
        vector<vector<int> > result;
        sort(num.begin(), num.end()); // 这种方法要求排序
        
        do {
            result.push_back(num);
        }
        while(my_next_permutation(num.begin(), num.end()));
        
        return result;
    }
#endif
#ifdef M2
/*
这个解法的思路和permutations的类似，不过需要避免重复的排列。
如何避免呢？ 在将num[start]和num[i]交换前，我们需要判断在[start,i)这个区间是否有等于num[i]的元素，如果有，则不用交换，反之则可以交换。
为什么呢？因为在[start,i)这个区间如果有等于num[i]的元素num[j]，则交换num[start]和num[i]之后，得到的排列会和之前的排列重复。
注意，这种解法是通过固定前面的元素，让后面的元素进行全排列，所以将num[start]和num[i]交换后得到的排列集合和将num[start]和num[j]交换后得到的排列集合
可能顺序不一样，但集合元素是一样的，从而会产生重复。
test case: [1 1 4] [1 4 4] [1 1 4 4]

!!! 需要理解的是，我们不能以为i==start || num[i] != num[i-1]这个可以代替canSwap里面的逻辑。
比如 [0 0 0 1 9]，这个要跟起来确实很费劲，有时间再跟一下。 
*/
    vector<vector<int> > permuteUnique(vector<int> &num) {
        vector<vector<int> > result;
        permuteUnique(num, 0, result);
        return result;
    }
    bool canSwap(vector<int> &num, int start, int i) {
        vector<int>::iterator first = next(num.begin(), start);
        vector<int>::iterator last  = next(num.begin(), i);
        return find(first, last, num[i]) == last;
    }
    void permuteUnique(vector<int> &num, int start, vector<vector<int> > &result) {
        if (start == num.size()-1) {
            result.push_back(num);
            return;
        }
        
        for(int i=start; i<num.size(); ++i) {
            if (canSwap(num, start, i)) {
                swap(num[i], num[start]);
                permuteUnique(num, start+1, result);
                swap(num[i], num[start]);
            }
        }
    }
#endif
};
