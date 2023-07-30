class Solution {
public:
#define M2
#ifdef M1
// 递归版， 深搜法 O(n!/(n-k)!/k!) time, O(n) space
    vector<vector<int> > combine(int n, int k) {
        vector<vector<int> > result;
        vector<int> path;
        combine(n, k, 1, path, result);
        return result;
    }
    
    void combine(int n, int k, int start, vector<int> &path, vector<vector<int> > &result) {
        if (k == path.size()) {
            result.push_back(path);
            return;
        }
        
        for(int i=start; i<=n; ++i) {
            path.push_back(i);
            combine(n, k, i+1, path, result);
            path.pop_back();
        }
    }
#endif
#ifdef M2
// 迭代版
/*
这个解法思路很巧妙，采用了类似于位向量法的方法，并结合了有重复元素的permutations的方法。
比如n=4, k=2，位向量为selected，初始值是[1 1 0 0]，对应[1 2];
然后对这个位向量找它的prev_permutation:
1 1 0 0 => [1 2]
1 0 1 0 => [1 3]
1 0 0 1 => [1 4]
0 1 1 0 => [2 3]
0 1 0 1 => [2 4]
0 0 1 1 => [3 4]
由于STL中的prev_permutation就能很好地处理重复元素的permutations，所以可以用之。
如果要自己实现的话，思路类似于next_permutation，四步法。
如果想用next_permutation的话，则selected初始值为[0 0 1 1]。
*/
    template <typename BirectionalIterator>
    bool my_prev_permutation(BirectionalIterator first, BirectionalIterator last) {
        if (first == last) return false;
        typedef reverse_iterator<BirectionalIterator> ReverseIterator;
        typedef typename iterator_traits<BirectionalIterator>::value_type ValueType; // 这里需要加tyepname，因为依赖了类iterator_traits中的类型。
        ReverseIterator rfirst(last), rlast(first);
    
        //step1
        ReverseIterator pivot = next(rfirst);
        while(pivot != rlast && *pivot <= *(prev(pivot))) { // 和next_permutation不同之处： <=
            ++pivot;
        }
    
        if (pivot == rlast) {
            reverse(first, last);
            return false;
        }
    
        //step2
        ReverseIterator change = find_if(rfirst, pivot, bind1st(greater<ValueType>(), *pivot)); // 和next_permutation不同之处： greater
    
        //step3
        swap(*pivot, *change);
    
        //step4
        reverse(rfirst, pivot);
    
        return true;
    }
    
    vector<vector<int> > combine(int n, int k) {
        vector<int> values(n);
        for(int i=0; i<n; ++i) { // [1 2 .. n]
            values[i] = i+1;
        }
        vector<bool> selected(n, false);
        for(int i=0, j=k; j; ++i, --j) { // 位向量[1 1 .. 0 0]
            selected[i] = true;
        }
        
        vector<vector<int> > result;
        do{
            vector<int> solution(k);
            for (int i = 0, index = 0; i < n; ++i) {
                if (selected[i]) solution[index++] = values[i];
            }
            result.push_back(solution);
        } while(my_prev_permutation(selected.begin(), selected.end()));
    
        return result;
    }
#endif
};
