class Solution {
public:
#define M3
    
#ifdef M1
/*
递归深搜 + 备忘录, 没有备忘录会TLE。
为了避免递归的时候创建子串，我们用下标m来标识需要递归的字符串在原始字符串中的起始下标。
test case: 
"ab" ["a", "b"]
"a"  []
极端case： "aaaaaaaaaaaaaaaab" ["a" "aa" "aaa" "aaaa" "aaaaaa"]
*/
    unordered_map<int, bool> cache;
    bool wordBreak(string s, unordered_set<string> &dict) {
        if (s.empty()) return false;
        return wordBreak(s, 0, dict);
    }
    
    bool wordBreak(string &s, int m, unordered_set<string> &dict) {
        auto iter = cache.find(m);
        if (iter != cache.end()) {
            return iter->second; 
        }
        
        if (dict.find(s.substr(m)) != dict.end()) return cache[m] = true;
        
        for(int i=m+1; i<s.size(); ++i) {
            string s1 = s.substr(m, i-m);
            if (dict.find(s1) != dict.end()) {
                if (wordBreak(s, i, dict)) return cache[m] = true;
            }
        }
        return cache[m] = false;
    }
#endif
#ifdef M2
/*
还有一种写法是用[start, end]来标识一个字符串。这种写法更得用备忘录。
这种写法其实是没必要的。
例如s="abcd"， 当分割成：s1="abc", s2="d"时, 按照这种写法，会对s1和s2分别递归。
而对s1进行递归，分以下情况：["a, "bc"], ["ab", "c"] ["abc"]
可以看出这几种情况在到达s的时候都已经处理过了，包括["abc"]是在分割成s1和s2时。
当然，这种写法看起来很清晰。
*/
    vector<vector<int> > cache; // -1: unvisited, 0: false, 1: true
    // 这里也可以用map<pair<int,int>, bool> cache
    bool wordBreak(string s, unordered_set<string> &dict) {
        if (s.empty()) return false;
        vector<vector<int> > temp(s.size(), vector<int>(s.size(), -1));
        cache = temp;
        return wordBreak(s, 0, s.size()-1, dict);
    }
    
    bool wordBreak(string &s, int start, int end, unordered_set<string> &dict) {
        if (cache[start][end] != -1) return cache[start][end] == 1;
        
        if (dict.find(s.substr(start, end-start+1)) != dict.end()) {
            cache[start][end] = 1;
            return true;
        }
        
        for(int i=start; i<end; ++i) {
            if (wordBreak(s, start, i, dict) && wordBreak(s, i+1, end, dict)) {
                cache[start][end] = 1;
                return true;
            }
        }
        
        cache[start][end] = 0;
        return false;
    }
#endif
#ifdef M3
/*
DP: 类似M1的思路
设状态为 f (i),表示 s[0,i] 是否可以分词,则状态转移方程为
f (i) = any_of (f (j)&&s[j + 1, i] ∈ dict), 0 ≤ j < i
*/
    bool wordBreak(string s, unordered_set<string> &dict) {
        if (s.empty()) return false;
        
        const int n = s.size();
        // 长度为 n 的字符串有 n+1 个隔板
        vector<bool> f(n+1, false);
        f[0] = true; // 空串
        
        for(int i=1; i<=n; ++i) {
            for(int j = 0; j<i; ++j) {
                string ts = s.substr(j, i-j);
                if (dict.find(ts) != dict.end() && f[j]) {
                    f[i] = true;
                    break;
                }
            }
        }
        
        return f[n];
    }
#endif
};
