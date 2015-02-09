class Solution {
public:
#define M2
// test case: "", "a", "aa", "aba", "abba", "abcba"
#ifdef M1
// 注意： 有奇偶两种模式:aba, abba都是回文
// O(n2) time, O(1) space
    string longestPalindrome(string s) {
        const int size = s.size();
        if (size == 0) return s;
        
        int maxLen = 1; // max length of palindrome
        int idx = 0; // start index of the longest palindrome
        
        // 对每个字符，以它为中心，向两边扩展，判断是否为回文
        // 需要注意的是，这里有两情况，一种是奇数个字符的回文，一种是偶数个字符的回文
        for(int i=0; i<size; ++i) {
            // 奇数个字符的回文，起始长度是1,每次加2, 如：aba
            int j = i-1, k = i+1, len = 1;
            while(j >= 0 && k < size) {
                if (s[j] == s[k]) {
                    len += 2;
                    --j;
                    ++k;
                }
                else {
                    break;
                }
            }
            
            if (len > maxLen) {
                maxLen = len;
                idx = j+1;
            }
            
            // 偶数个字符的回文，起始长度为0, 每次加2，如：abba
            j = i, k = i+1, len = 0;
            while(j >= 0 && k < size) {
                if (s[j] == s[k]) {
                    len += 2;
                    --j;
                    ++k;
                }
                else {
                    break;
                }
            }
            
            if (len > maxLen) {
                maxLen = len;
                idx = j+1;
            }
        }
        
        //return string(s.begin() + idx, s.begin() + idx + max);
        return s.substr(idx, maxLen);
    }
#endif
#ifdef M2
/*
DP: Dynamic Programming
O(n2) time, O(n2) space
定义函数
f[i,j] = 字符串区间[i,j]是否为palindrome.
举个例子，比如S="abccb",
S     = a  b  c  c  b
Index = 0  1  2  3  4
f[0,0] = 1  //each char is a palindrome
f[0,1] = S[0] == S[1]  , f[1,1] =1 
f[0,2] = S[0] == S[2] && f[1,1], f[1,2] = S[1] == S[2] ,  f[2,2] = 1
f[0,3] = S[0] == S[3] && f[1,2], f[1,3] = S[1] == S[3] && f[2,2] , f[2,3] =S[2] ==S[3],  f[3,3]=1       
......................
由此就可以推导出规律
f[i,j] = true  if i == j
       = S[i] == S[j]   if j = i+1
       = S[i] == S[j] && f[i+1][j-1]  if j>i+1
*/
    string longestPalindrome(string s) {
        const int size = s.size();
        if (size == 0) return s;
        
        int maxLen = 1; // max length of palindrome
        int idx = 0; // start index of the longest palindrome
        
        bool f[size][size];
        fill(&f[0][0], &f[0][0]+size*size, false);
        
        // 下面的循环相当于填充二维数组f的右上角（左下角没用到）
        /*
        for(int i=0; i<size; ++i) {
            f[i][i] = true;
            for(int j=0; j<i; ++j) {
                f[j][i] = (s[j] == s[i]) && (j+1 == i || f[j+1][i-1]);
                if (f[j][i] && (i-j+1) > maxLen) {
                    maxLen = i-j+1;
                    idx = j;
                }
            }
        }
        */
        // 从最后一行开始计算，计算右上角。
        // 个人觉得比上面的那段代码好理解一点，所以替换一下，这里要注意 j-i < 2， 包含了 j==i and j=i+1
        for(int i=size-1; i>=0; --i) {
            for(int j=i; j<size; ++j) {
                f[i][j] = (s[i] == s[j]) && (j-i < 2 || f[i+1][j-1]);
                if (f[i][j] && (j-i+1) > maxLen) {
                    maxLen = j-i+1;
                    idx = i;
                }
            }
        }
        
        return s.substr(idx, maxLen);
    }
#endif
};
