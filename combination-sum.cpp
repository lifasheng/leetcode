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
