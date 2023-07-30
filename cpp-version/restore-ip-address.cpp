class Solution {
public:
    vector<string> restoreIpAddresses(string s) {
        vector<string> result;
        string path;
        dfs(s, path, 0, 0, result);
        return result;
    }
    void dfs(string &s, string path, int start, int step, vector<string> &result) {
        if (start == s.size() && step == 4) {
            path.resize(path.size()-1);
            result.push_back(path);
            return;
        }
        
        //剪枝， 而且通常是第一个剪枝先到达。
        if (s.size()-start > (4-step)*3) return;
        if (s.size()-start < (4-step)) return;
        
        int num = 0;
        for(int i=start; i<start+3; ++i) {
            num = num * 10 + (s[i] - '0');
            
            if (num <= 255) {
                path += s[i]; // 注意，这里path必须累加，而且dfs递归之后，不用执行撤销动作!!!
                dfs(s, path+'.', i+1, step+1, result);
            }
            
            if (num == 0) break; // 不允许前缀 0,但允许单个 0
        }
    }
};
