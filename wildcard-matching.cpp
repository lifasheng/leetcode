class Solution {
public:
#define M2
#ifdef M0 // 递归版本，会超时
bool isMatch(const char *s, const char *p) {
        if (*p == '*'){
            while(*p == '*') ++p; // skip continuous '*'
            if (*p == '\0') return true;
            // p现在是'*'后面的第一个非'*'字符，它需要和s中的某个字符匹配，
            // 如果不匹配的话，则++s，因为p前面的*可以匹配s中的任意个字符。
            while(*s != '\0' && !isMatch(s,p)) {
                ++s;                
            }
            return *s != '\0';
        }
        else if (*p == '\0' || *s == '\0') return *p == *s;
        else if (*p == *s || *p == '?') return isMatch(++s,++p);
        else return false;
    }
#endif
#ifdef M1
/*
O(m+n) time, O(1) space
Good!
http://yucoding.blogspot.com/2013/02/leetcode-question-123-wildcard-matching.html
不是DP，这个解是个二叉树结构
if *p == '*'
isMatch(s, p) = isMatch(s, p + 1) || isMatch(s + 1, p + 1) || ... || isMatch(s + n, p+1)
= isMatch(s, p + 1) || isMatch(s + 1, p)
else
只有一个分叉 = isMatch(s+1, p+1)
这个算法的关键是当左子树再遇到＊的时候，上次遇到＊分裂出来的右子树就不用搜索了。
例如：s = aab... p = *a*b...
aab..., *a*b...
aab..., a*b... ab..., *a*b...
ab..., *b...
第二次遇到＊的时候 s = ab... p = *b...
如果s和p不匹配，那么上次遇到＊的右子树ab..., *a*b...也肯定不匹配（可以用反证法来证明）。
如果匹配，搜索左子树就能找到结果。 
假设ab...和*a*b...匹配，那么ab...和*b...肯定匹配，和条件相反。
http://www.cnblogs.com/zhuli19901106/p/3572736.html
*/
    bool isMatch(const char *s, const char *p) {
        if (!s && !p) return true;
        if (!s || !p) return false;
        
        //if (*p == '\0') return *s == '\0';
        
        const char* star=NULL;
        const char* ss=s; 
        while (*s) {
            // this is a match, then goes to next element s++ p++.
            if ((*p=='?')||(*p==*s)) {
                s++;p++;continue;
            }
            // save this *'s position and the matched s position.
            // this will also skip continuous *
            if (*p=='*') {
                star=p++; ss=s;continue;
            }
            // if there is an *, set current p to the next element of *, and set current s to the next saved s position until there is a match.
            if (star) {
                p = star+1; s=++ss;continue;
            } 
            
            return false; // if there is no *, return false.
        }
        // i.e ["bananas", "ba*na***y]
        while (*p=='*') {
            p++;
        }
        return !*p;
    }
#endif
#ifdef M2
/*
TBD: more explanation here!!!
DP:  O(n*m) time, O(n*m) space
dp[i][j] = 1 表示s[1~i] 和 p[1~j] match。
则：
if p[j] == '*' && (dp[i][j-1] || dp[i-1][j]) 
    dp[i][j] =  1 
else p[j] = ? || p[j] == s[i]
    dp[i][j] = dp[i-1][j-1];
else 
    dp[i][j] = false;
    
    http://tech-lightnight.blogspot.com/2012/12/wildcard-matching.html
*/
    bool isMatch(const char *s, const char *p) {
        const int len_s = strlen(s);
        const int len_p = strlen(p);
  
        // 重要的判断，不然超时
        // p 中非*字符的个数不能大于s的字符个数
        int cnt = 0;
        for(const char* tmp = p; *tmp; ++tmp) {
            if (*tmp != '*') {
                ++cnt;
            }
        }
        if (cnt > len_s) return false;
     
        bool dp[len_s+1][len_p+1];
        memset(dp, 0,sizeof(dp));
        
        dp[0][0] = true;
        for (int i = 1; i <= len_p; i++) {
            if (dp[0][i-1] && p[i-1] == '*') {
                dp[0][i] = true;
            }
            
            for (int j = 1; j <= len_s; ++j) {
                if (p[i-1] == '*') {
                    dp[j][i] = (dp[j-1][i] || dp[j][i-1]);
                }
                else if (p[i-1] == '?' || p[i-1] == s[j-1]) {
                    dp[j][i] = dp[j-1][i-1];
                }
                else {
                    dp[j][i] = false;
                }
            }
        }
        
        return dp[len_s][len_p];
    }
#endif
};
