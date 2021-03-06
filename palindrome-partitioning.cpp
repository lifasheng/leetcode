class Solution {
public:
/*
When face the "return all", "get all ", "find all possible", "find the total number of", an idea is to use the recursion. 
To get the all the partitions of a string s:
1. find all the palindromes in substring s[0], and all the palindromes in substring s[1:end]
2. find all the palindromes in substring s[0:1], and all the palindromes in substring s[2:end]
...
find all the palindromes in substring s[1:end-1], and all the palindromes in substring s[end]
So the problem is quite clear, when we do recursion, two things should be considered:
1. stop condition:  when the search goes to the last position in the string
2. for loop or while loop:   for position=current start position to the end.
*/
    vector<vector<string>> partition(string s) {
        vector<vector<string>> result;
        vector<string> path; // 一个 partition 方案
        DFS(s, path, result, 0);
        return result;
    }
    
    // 搜索必须以 s[start] 开头的 partition 方案
    void DFS(string &s, vector<string>& path, vector<vector<string>> &result, int start) {
        if (start == s.size()) {
            result.push_back(path);
            return;
        }
        for (int i = start; i < s.size(); i++) {
            if (isPalindrome(s, start, i)) { // 从 i 位置砍一刀
                path.push_back(s.substr(start, i - start + 1));
                DFS(s, path, result, i + 1); // 继续往下砍
                path.pop_back(); // 撤销上上行
            }
        }
    }
    
    bool isPalindrome(const string &s, int start, int end) {
        while (start < end) {
            if (s[start] != s[end]) return false;
            ++start;
            --end;
        }
        return true;
    }
};


class Solution {
public:
    vector<vector<string> > partition(string s) {
        const int n = s.size();
        vector<vector<string> > result;
        vector<string> path;
        bool f[n][n];
        fill_n(&f[0][0], n*n, false);
        for(int i=n-1; i>=0; --i) {
            for(int j=i; j<n; ++j) {
                f[i][j] = (s[i] == s[j]) && ((j-i<2) || f[i+1][j-1]);
            }
        }
        
        dfs(s, 0, path, result, &f[0][0]);
        return result;
    }
    
    void dfs(string &s, int start, vector<string> &path, vector<vector<string> > &result, bool *f) {
        const int n = s.size();
        if (start == n) {
            result.push_back(path);
            return;
        }
        
        for(int i=start; i<n; ++i) {
            if (f[start*n+i]) {
                path.push_back(s.substr(start, i-start+1));
                dfs(s, i+1, path, result, f);
                path.pop_back();
            }
        }
    }
};
