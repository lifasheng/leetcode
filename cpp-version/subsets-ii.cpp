class Solution {
public:
#define M3
/*
这题有重复元素,但本质上,跟上一题很类似,上一题中元素没有重复,相当于每个元素只能
选 0 或 1 次,这里扩充到了每个元素可以选 0 到若干次而已。
比如[1 2 2]
map: 1 -> 1, 2 -> 2
所以1是选0次或1次，2是选0次或1次或2次。
所以下面的solutio同样适合前一题。
1 ---> [] -> 2 ---> []      => []
  |            |
  |            |--> [2]     => [2]
  |            |
  |            |--> [2 2]   => [2 2]
  |
  --> [1] -> 2 ---> [1]     => [1]
               |
               |--> [1 2]   => [1 2]
               |
               |--> [1 2 2] => [1 2 2]
*/
#ifdef M1
    vector<vector<int> > subsetsWithDup(vector<int> &S) {
        // 首先计算每个元素的重复次数
        unordered_map<int, int> m;
        for_each(S.begin(), S.end(), [&m](int v) {
            m[v]++;
        });
        
        // 将每个元素的重复次数放到数组中，因为数组下标从0开始，刚好表示第几步（step）。
        vector<pair<int, int> > elements;
        for_each(m.begin(), m.end(), [&elements](const pair<int, int> &p) {
           elements.push_back(p); 
        });
        
        // 一定要先排序，题目要求的。
        sort(elements.begin(), elements.end()); // sort by pair.first
        
        vector<vector<int> > result;
        vector<int> path;
        subsetsWithDup(elements, path, 0, result);
        
        return result;
    }
    
    void subsetsWithDup(const vector<pair<int, int> > &elements, vector<int> &path, int step, vector<vector<int> > &result) {
        if (step == elements.size()) {
            result.push_back(path);
            return;
        }
        
        for(int i=0; i<=elements[step].second; ++i) {
           for(int j=0; j<i; ++j) {
               path.push_back(elements[step].first);
           }
           subsetsWithDup(elements, path, step+1, result);
           for(int j=0; j<i; ++j) {
               path.pop_back();
           }
        }
    }
#endif
#ifdef M2 // 类似于subsets中的迭代，不过这里对重复的元素需要特殊处理。
    vector<vector<int> > subsetsWithDup(vector<int> &S) {
        sort(S.begin(), S.end()); // 必须排序
        
        vector<vector<int> > result(1);
        int previous_size = 0;
        for(int i=0; i<S.size(); ++i) {
            int size = result.size();
            for(int j=0; j<size; ++j) {
                if (i == 0 || S[i] != S[i-1] || j >= previous_size) {
                    vector<int> v(result[j]);
                    v.push_back(S[i]);
                    result.push_back(v);
                }
            }
            previous_size = size; // S[i] == S[i-1]的情况下，previous_size 之后的都是这次新加入的
        }
        return result;
    }
#endif
#ifdef M3 // 二进制法 + set去重，其实subsets里的每个方法+set去重都OK，就看会不会超时了。
    vector<vector<int> > subsetsWithDup(vector<int> &S) {
        sort(S.begin(), S.end()); 
        
        // unordered_set 编译通不过，因为它是基于hash的，而我们没有为vector<int>这种类型的元素定义相应的hash函数。
        // set 是基于红黑树的，它不用hash，而是要求定义了比较函数<，vector<int>定义了该函数，所以用set就可以。
        set<vector<int> > result; 
        vector<int> subset;
        int n = S.size();
        for(int i=0; i<(1<<n); ++i) {
            for(int j=0; j<n; ++j) {
                if (i & (1<<j)) {
                    subset.push_back(S[j]);
                }
            }
            result.insert(subset);
            subset.clear();
        }
        
        vector<vector<int> > real_result;
        for_each(result.begin(), result.end(), [&real_result](const vector<int>& v) {
           real_result.push_back(v); 
        });
        
        return real_result;
    }
#endif
};
