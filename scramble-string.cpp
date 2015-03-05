class Solution {
public:
#define M3
#ifdef M1
/*
http://n00tc0d3r.blogspot.com/2013/05/scramble-string.html
递归(即深搜),对两个 string 进行分割,然后比较四对字符串。
ntuitively, we can solve it with recursion.
Given two strings, try every possible split of s1 and 
check whether the resulted substrings of s1 equal or "swap-equal" of the corresponding substrings of s2.
For example, given "great" and "rgate", the splits are
("great") vs. ("rgate")
("g", "reat") vs. ("r", "gate") or ("e", "rgat")
("gr", "eat") vs. ("rg", "ate") or ("te", "rga")
("gre", "at") vs. ("rga", "te") or ("ate", "rg")
("grea", "t") vs. ("rgat", "e") or ("gate", "r")
时间复杂度分析：
In the worst situation, for any recursion f(n), we will check all the combinations twice, 
i.e., f(n) = 2[f(1) + f(n-1)] +2[f(2) + f(n-2)] … + 2[f(n/2) + f(n/2+1)], thus, f(n+1)=3*f(n), we have f(n)=3^n.
比如n=4：
子串长度：
1 3
2 2
3 1
n = 5
子串长度：
1 4
2 3
3 2
4 1
f(n)   = 2[f(1) + f(2) + f(3) + ... + f(n-2) + f(n-1)]
f(n+1) = 2[f(1) + f(2) + f(3) + ... + f(n-1) + f(n)]
f(n+1) - f(n) = 2[f(n)]
f(n+1) = 3f(n)
f(n) = 3f(n-1) = 3[3f(n-2)] = 3[3[3f(n-3)]] = 3^3f(n-3) = ... = 3^(n-1)f(1) = 3^(n-1)
*/
    typedef string::iterator Iterator;
    bool isScramble(string s1, string s2) {
        if (s1.size() != s2.size()) return false;
        if (s1 == s2) return true;
        
        return isScramble(s1.begin(), s1.end(), s2.begin());
    }
    bool isScramble(Iterator first1, Iterator last1, Iterator first2) {
        if (first1 == last1) return true;
        int length = distance(first1, last1);
        Iterator last2 = next(first2, length);
        
        if (length == 1) return *first1 == *first2;
        
        if (!isSameChar(first1, last1, first2)) return false; // 剪枝： 如果不做这步判断，会TLE超时。
        
        for(int i=1; i<length; ++i) {
            if( (isScramble(first1, first1+i, first2) && isScramble(first1+i, last1, first2+i))
            ||  (isScramble(first1, first1+i, last2-i) && isScramble(first1+i, last1, first2)))
            return true;
        }
        
        return false;
    }
    
    bool isSameChar(Iterator first1, Iterator last1, Iterator first2) {
        vector<int> hash(256, 0);
        
        int length = distance(first1, last1);
        
        for(Iterator i=first1; i!=first1+length; ++i) {
            ++hash[*i];
        }
        for(Iterator i=first2; i!=first2+length; ++i) {
            --hash[*i];
        }
        for(int i=0; i<hash.size(); ++i) {
            if (hash[i]) return false;
        }
        
        return true;
    }
#endif
#ifdef M2
// 递归+备忘录
    typedef string::iterator Iterator;
    map<tuple<Iterator, Iterator, Iterator>, bool> cache; // 这里用map，如果用unordered_map则需要定义hash函数。
    
    bool isScramble(string s1, string s2) {
        if (s1.size() != s2.size()) return false;
        if (s1 == s2) return true;
        
        return isScramble(s1.begin(), s1.end(), s2.begin());
    }
    bool isScramble(Iterator first1, Iterator last1, Iterator first2) {
        if (first1 == last1) return true;
        int length = distance(first1, last1);
        Iterator last2 = next(first2, length);
        
        if (length == 1) return *first1 == *first2;
        
        if (!isSameChar(first1, last1, first2)) return false; // 剪枝： 如果不做这步判断，会TLE超时。
        
        for(int i=1; i<length; ++i) {
            if( (getOrUpdate(first1, first1+i, first2) && getOrUpdate(first1+i, last1, first2+i))
            ||  (getOrUpdate(first1, first1+i, last2-i) && getOrUpdate(first1+i, last1, first2)))
            return true;
        }
        
        return false;
    }
    
    bool getOrUpdate(Iterator first1, Iterator last1, Iterator first2) {
        auto key = make_tuple(first1, last1, first2);
        auto pos = cache.find(key);
        
        if (pos != cache.end()) {
            return pos->second;
        }
        else {
            cache[key] = isScramble(first1, last1, first2);
            return cache[key];
        }
    }
    
    bool isSameChar(Iterator first1, Iterator last1, Iterator first2) {
        vector<int> hash(256, 0);
        
        int length = distance(first1, last1);
        
        for(Iterator i=first1; i!=first1+length; ++i) {
            ++hash[*i];
        }
        for(Iterator i=first2; i!=first2+length; ++i) {
            --hash[*i];
        }
        for(int i=0; i<hash.size(); ++i) {
            if (hash[i]) return false;
        }
        
        return true;
    }
#endif
#ifdef M3
/*
http://leetcode.tanglei.name/content/dp/Scramble-String.html
DP:
设状态为 f[n][i][j],表示长度为 n,起点为 s1[i] 和起点为 s2[j] 两个字符串是否互为 scramble,
则状态转移方程为:
f[n][i][j]} = (f[k][i][j] && f[n-k][i+k][j+k]) 
           || (f[k][i][j+n-k] && f[n-k][i+k][j])
*/
    bool isScramble(string s1, string s2) {
        if (s1.size() != s2.size()) return false;
        if (s1 == s2) return true;
        
        const int N = s1.size();
        bool f[N+1][N][N];
        fill_n(&f[0][0][0], (N+1)*N*N, false);
        
        for(int i=0; i<N; ++i) {
            for(int j=0; j<N; ++j) {
                f[1][i][j] = (s1[i] == s2[j]);
            }
        }
        for(int n=2; n<=N; ++n) {
            for(int i=0; i<=N-n; ++i) {
                for(int j=0; j<=N-n; ++j) {
                    for(int k=1; k<n; ++k) {
                        if ( (f[k][i][j] && f[n-k][i+k][j+k])
                        ||   (f[k][i][j+n-k] && f[n-k][i+k][j]) ) {
                            f[n][i][j] = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return f[N][0][0];
    }
#endif
};