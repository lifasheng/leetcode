class Solution {
public:
// 这道题主要是注意重复元素的处理。
// test case:
// [1 1]   target = 1, output [1]
// [1 1 2] target = 3, output = [1 2]
// [1 1 2] target = 4, output = [1 1 2]
    vector<vector<int> > combinationSum2(vector<int> &num, int target) {
        sort(num.begin(), num.end());
        
        vector<vector<int> > result;
        vector<int> path;
        dfs(num, target, 0, path, result);
        return result;
    }
    
    void dfs(vector<int> &num, int gap, int start, vector<int> &path, vector<vector<int> > &result) {
        if (gap == 0) {
            result.push_back(path);
            return;
        }
        
        int previous = -1;
        for(int i=start; i<num.size(); ++i) {
            if (gap < num[i]) return;
            
            if (previous == num[i]) continue;
            
            previous = num[i];
            
            path.push_back(num[i]);
            dfs(num, gap-num[i], i+1, path, result);
            path.pop_back();
        }
    }
};

// similar implementation.
class Solution {
public:
    vector<vector<int> > combinationSum2(vector<int> &num, int target) {
        vector<vector<int> > result;
        vector<int> path;
        sort(num.begin(), num.end());
        dfs(num, target, 0, path, result);
        return result;
    }
    void dfs(vector<int>&num, int gap, int start, vector<int> &path, vector<vector<int> > &result) {
        if (gap == 0) {
            result.push_back(path);
            return;
        }
        
        for(int i=start; i<num.size(); ++i) {
            if (gap < num[i]) break;
            if (i>start && num[i] == num[i-1]) continue;
            path.push_back(num[i]);
            dfs(num, gap-num[i], i+1, path, result);
            path.pop_back();
        }
    }
};
