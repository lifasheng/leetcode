class Solution {
public:
/*
http://yucoding.blogspot.com/2013/08/leetcode-question-133-palindrome.html
When you are facing the problem asking "return the minimum/maximum, best, shortest...", it is also a good direction targeting the DP (sometimes greedy also works fine).
http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
For this problem, firstly we consider the main part.
It is a good way to look for the "state", and the "optimal solution" based on that state, to solve the DP problem. In other words, if you found the correct state, and the function of the state representing the optimal solution, all you need is some loops and initialization implementing the algorithm.
Here the state is not too hard ----  minimum cut.   
Define res[i] = the minimum cut from 0 to i in the string. The result we need eventually is res[s.size()-1].
We know res[0]=0. Next we are looking for the optimal solution function f.
For example, let s = "leet".
f(0) = 0;  // minimum cut of str[0:0]="l", which is a palindrome, so not cut is needed.
f(1) = 1; // str[0:1]="le" How to get 1? 
                 f(1) might be:  (1)   f(0)+1=1, the minimum cut before plus the current char.
                                 (2)   0, if str[0:1] is a palindrome  (here "le" is not )
f(2) = 1; // str[0:2] = "lee" How to get 2?
                f(2) might be:   (1)  f(1) + 1=2
                                 (2)  0, if str[0:2] is a palindrome (here "lee" is not)
                                 (3)  f(0) + 1,  if str[1:2] is a palindrome, yes! 
f(3) = 2; // str[0:3] = "leet" How to get 2?
                f(3) might be:   (1)  f(2) + 1=2
                                 (2)  0, if str[0:3] is a palindrome (here "leet" is not)
                                 (3)  f(0) + 1,  if str[1:3] is a palindrome (here "eet" is not)
                                 (4)  f(1) + 1,  if str[2:e] is a palindrome (here "et" is not)
OK, output f(3) =2 as the result.
So, the optimal function is:
f(i) = min [  f(j)+1,  j=0..i-1   and str[j:i] is palindrome
                    0,   if str[0,i] is palindrome ]
*/
    int minCut(string s) {
        const int n = s.size();
        vector<int>res(n,0);
        bool mp[n][n];
        
        fill_n(&mp[0][0], n*n, false);
        // 先确定[i,j]之间是否为palindrome。
        for (int i=n-1;i>=0;i--){
            for (int j=i;j<n;j++) {
                mp[i][j] = (s[i]==s[j]) && (j-i<2 || mp[i+1][j-1]);
            }
        }
        
        // 在得到上述表之后，可以用上面注释中的DP公式求出res[i]。
        for (int i=0;i<n;i++){
            int ms = n; // 最多切n-1刀
            if (mp[0][i]){
                res[i]=0;
            }else{
                for (int j=0;j<i;j++){
                    if (mp[j+1][i] && ms>res[j]+1 ) {
                            ms=res[j]+1;
                    } 
                }
                res[i]=ms;
            }
        }   
        return res[n-1];
    }
};
class Solution {
public:
    int minCut(string s) {
        const int n = s.size();
        bool f[n][n];
        fill_n(&f[0][0], n*n, false);
        for(int i=n-1; i>=0; --i) {
            for(int j=i; j<n; ++j) {
                f[i][j] = s[i] == s[j] && ((j-i<2) || f[i+1][j-1]);
            }
        }
        
        int cut[n];
        fill_n(cut, n, INT_MAX);
        for(int i=0; i<n; ++i) {
            if(f[0][i]) {
                cut[i] = 0;
            }
            else {
                for(int k=i-1; k>=0; --k) {
                    if (f[k+1][i]) {
                        cut[i] = min(cut[i], cut[k]+1);
                    }
                }
            }
        }
        
        return cut[n-1];
    }
};


class Solution {
public:
    int minCut(string s) {
        const size_t n = s.size();
        if (n<=1) return 0;

        vector<vector<bool> > f(n, vector<bool>(n, false));
        generateF(s,f);

        map<pair<int, int>, int> m;
        return minCut(s, f, m, 0, n-1);
    }

    // 递归处理
    // 将字符串的每个位置进行切分，然后对子串进行递归调用。
    // 采用备忘录法来记录已有结果。
    int minCut(string &s, vector<vector<bool> > &f,
               map<pair<int, int>, int> &m, int beg, int end) {
        if (f[beg][end]) return 0;
        pair<int, int> p = make_pair(beg, end);
        if (m.find(p) != m.end()) return m[p];

        int cut = s.size();
        for(int i=beg; i<end; ++i) {

            // 这一步是优化的关键，它节省了很多不必要的调用。否则会超时！
            // 假设字符串分成ABC三段，一开始是A，BC这种划分，A是一个字符，BC是剩下的字符，A肯定是palindrome。
            // 而到了AB，C这种划分时，如果AB不是palindrome，则没有必要对AB进行调用，
            // 因为这种划分不可能比A,BC这种划分的结果更好，最多是相等。
            if (!f[beg][i]) continue;

            //int c = minCut(s,f,m,beg, i) + minCut(s,f,m,i+1,end)+1;
            int c1 = 0;//minCut(s,f,m,beg,i);
            int c2 = minCut(s,f,m,i+1,end);
            int c = c1+c2+1;
            if (c<cut) {
                cut = c;
                m[p]=c;
            }
        }
        return cut;
    }

    // 判断从i到j是否是palindrome
    void generateF(string &s, vector<vector<bool> > &f) {
        const size_t n = s.size();
        for(int i=n-1; i>=0; --i) {
            for (int j=i; j<n; ++j) {
                f[i][j] = s[i] == s[j] && ( (j-i<2)||f[i+1][j-1] );
            }
        }
    }
};
