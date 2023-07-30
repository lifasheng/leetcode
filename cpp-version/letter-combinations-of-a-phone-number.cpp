class Solution {
public:
#define M1
    inline string getString(char c) {
        switch(c) {
            case '2': return "abc";
            case '3': return "def";
            case '4': return "ghi";
            case '5': return "jkl";
            case '6': return "mno";
            case '7': return "pqrs";
            case '8': return "tuv";
            case '9': return "wxyz";
            default: return "";
        }
    }
    
#ifdef M1
/*
递归版本：深搜法，时间复杂度 O(3^n),空间复杂度 O(n)
*/
    vector<string> letterCombinations(string digits) {
        vector<string> result;
        vector<char> path;
        dfs(digits, path, 0, result);
        return result;
    }
    
    void dfs(const string &digits, vector<char> &path, int start, vector<string> &result) {
        if (path.size() == digits.size()) {
            string s(path.begin(), path.end());
            result.push_back(s);
            return;
        }
        
        string value = getString(digits[start]);
        for(int i=0; i<value.size(); ++i) {
            path.push_back(value[i]);
            dfs(digits, path, start+1, result);
            path.pop_back();
        }
    }
#endif
#ifdef M2
/* 迭代版：
思路：
类似于subsets或permutation中的一种迭代或递归方法，
就是将规模为n的问题转化为先求规n-1的问题，再将第n个元素加入到已有的结果中。
假设digits="23"，假设已经求得'2'的结果，即["a", "b", "c"]，
则把'3'加入后得到题目中的9个string。
这种思路递归很简单。
迭代的话需要一些技巧，代码和subsets的迭代很类似。
*/
    vector<string> letterCombinations(string digits) {
        vector<string> result(1, "");
        
        for(int i=0; i<digits.size(); ++i) {
            string value = getString(digits[i]);
            int n = result.size();
            int m = value.size();
            
            // 先扩大数组，并把已有的结果拷贝到扩展后的位置
            result.resize(n*m); // 注意这里不是reserve，reserve不改变size。
            for(int j=0; j<m; ++j) {
                copy(result.begin(), result.begin()+n, result.begin()+n*j);
            }
            
            // 将value中的字符分别加到已有的结果中。
            for(int j=0; j<m; ++j) {
                for_each(result.begin()+n*j, result.begin()+n*(j+1), [&](string &s) {
                    s += value[j]; // here, we need to capture value and j
                });
            }
        }
        
        return result;
    }
#endif
};
