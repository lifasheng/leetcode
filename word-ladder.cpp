class Solution {
public:
/*
思路： 层次遍历进行搜索。由于是层次遍历，所以如果一旦到达了目标字符串，则当前深度就是最小的。
*/
    vector<string> state_extend(string &s, unordered_set<string> &visited, unordered_set<string> &dict) {
        vector<string> result;
        for(size_t i=0; i<s.size(); ++i) {
            for(char c='a'; c<='z'; ++c) {
                if (s[i] != c) {
                    swap(s[i], c);
                    if (dict.count(s) && !visited.count(s)) { // 在字典中并且没访问过
                        visited.insert(s);
                        result.push_back(s);
                    }
                    swap(s[i], c);
                }
            }
        }
        return result;
    }
    
    int ladderLength(string start, string end, unordered_set<string> &dict) {
        unordered_set<string> visited; // 用于去重
        queue<string> cur, next; // 当前层和下一层
        int level = 1; // 当前第几层
        
        cur.push(start);
        while(!cur.empty()) {
            while(!cur.empty()) {
                string s = cur.front();
                cur.pop();
                
                if (s == end) return level;
                
                vector<string> exts = state_extend(s, visited, dict);
                for(size_t i=0; i<exts.size(); ++i) {
                    next.push(exts[i]);
                }
            }
            swap(cur, next);
            ++level;
        }
        
        return 0;
    }
};
