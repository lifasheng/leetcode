class Solution {
public:
// 时间复杂度 O(n!),空间复杂度 O(n)
    vector<vector<int> > combinationSum(vector<int> &candidates, int target) {
        sort(candidates.begin(), candidates.end());
        
        vector<vector<int> > result;
        vector<int> path;
        dfs(candidates, target, 0, path, result);
        return result;
    }
    
    void dfs(vector<int> &candidates, int gap, int start, vector<int> &path, vector<vector<int> > &result) {
        if (gap == 0) {
            result.push_back(path);
            return;
        }
        
        for(int i=start; i< candidates.size(); ++i) {
            if (gap < candidates[i]) return; // 剪枝
            
            path.push_back(candidates[i]); // 执行扩展动作
            dfs(candidates, gap-candidates[i], i, path, result);
            path.pop_back(); // 撤销动作
        }
    }
};

// similar implementation, this implementation is slower than above implementation
// since it has duplicate and need to remove duplicate.
class Solution {
public:
    vector<vector<int> > combinationSum(vector<int> &candidates, int target) {
        set<vector<int> > result;
        vector<int> path;
        sort(candidates.begin(), candidates.end());
        dfs(candidates, target, path, result);
        
        vector<vector<int> > r;
        for(auto p:result) {
            r.push_back(p);
        }
        return r;
    }
    void dfs(vector<int> &candidates, int gap, vector<int> &path, set<vector<int> > &result) {
        if (gap == 0) {
            vector<int> temp(path);
            sort(temp.begin(), temp.end());
            result.insert(temp);
            return;
        }
        
        for(int i=0; i<candidates.size(); ++i) {
            if (gap < candidates[i]) break;
            
            path.push_back(candidates[i]);
            dfs(candidates, gap-candidates[i], path, result);
            path.pop_back();
        }
    }
};
