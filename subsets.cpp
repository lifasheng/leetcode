class Solution {
public:
#define M4
 
#ifdef M1 // 递归版
/*
思路： 对每个元素，要么出现，要么不出现，两种选择。
比如[3,2,1]
可以先对[2,1]递归，得到[][1][2][1 2]
再在将3加入到上面各个数组中，得到[3][1 3][2 3][1 2 3]
[][1][2][1 2] 刚好是3不出现的情况，
[3][1 3][2 3][1 2 3] 是3 出现的情况
注意：需要先对数组进行排序，这种方法用降序比较好。
*/
    vector<vector<int> > subsets(vector<int> &S) {
        sort(S.begin(), S.end(), greater<int>());
        return subsets(S.begin(), S.end());
    }
    
    template <typename ForwardIterator>
    vector<vector<int> > subsets(ForwardIterator first, ForwardIterator last) {
        if (first == last) {
            vector<vector<int> > result;
            result.push_back(vector<int>());
            return result;
        }
        
        vector<vector<int> > result = subsets(next(first), last);
        int n = result.size(); // 必须先存下来，不然后面追加新的数组到二维数组中会改变二维数组的大小
        for(int i=0; i<n; ++i) {
            vector<int> v = result[i];
            //v.insert(v.begin(),*first); // 如果一开始是升序排序，则要插入在头部;
            v.push_back(*first); // 如果一开始是降序排序，则插入在尾部;
            result.push_back(v);
        }
        
        return result;
    }
#endif
#ifdef M2 // 也是递归版
// 增量构造法,深搜,时间复杂度 O(2^n),空间复杂度 O(n)
    vector<vector<int> > subsets(vector<int> &S) {
        sort(S.begin(), S.end());
        
        vector<vector<int> > result;
        vector<int> path;
        subsets(S, path, 0, result);
        return result;
    }
    void subsets(const vector<int> &S, vector<int> &path, int step, vector<vector<int> > &result) {
        if (step == S.size()) {
            result.push_back(path);
            return;
        }
        // 不选S[step]
        subsets(S, path, step+1, result);
        
        // 选S[step]
        path.push_back(S[step]);
        subsets(S, path, step+1, result);
        path.pop_back();
    }
#endif
#ifdef M3 // 也是递归版
// 位向量法,深搜,时间复杂度 O(2^n),空间复杂度 O(n)
    vector<vector<int> > subsets(vector<int> &S) {
        sort(S.begin(), S.end());
        
        vector<vector<int> > result;
        vector<bool> selected(S.size(), false);
        subsets(S, selected, 0, result);
        return result;
    }
    void subsets(const vector<int> &S, vector<bool> &selected, int step, vector<vector<int> > &result) {
        if (step == S.size()) {
            vector<int> subset;
            for(int i=0; i<selected.size(); ++i) {
                if (selected[i]) subset.push_back(S[i]);
            }
            result.push_back(subset);
            return;
        }
        // 不选S[step]
        selected[step] = false;
        subsets(S, selected, step+1, result);
        
        // 选S[step]
        selected[step] = true;
        subsets(S, selected, step+1, result);
    }
#endif
#ifdef M4 // 迭代版，思路和M1类似
    vector<vector<int> > subsets(vector<int> &S) {
        sort(S.begin(), S.end());
        
        vector<vector<int> > result(1); // [] is in result
        for(int i=0; i<S.size(); ++i) {
            int n = result.size(); // 必须先存下来，不然后面追加新的数组到二维数组中会改变二维数组的大小
            for(int j=0; j<n; ++j) {
                vector<int> v(result[j]); // copy a vector
                v.push_back(S[i]);
                result.push_back(v);
            }
        }
        return result;
    }
#endif
};
