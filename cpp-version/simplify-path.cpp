class Solution {
public:
// 注意特殊情况 /../,   /a//b/c
    string simplifyPath(string path) {
        vector<string> dirs; // 当作栈
        
        typedef string::iterator Iterator;
        for(Iterator i = path.begin(); i != path.end(); ) {
            ++i; // 跳过 '/'
            
            Iterator j = find(i, path.end(), '/');
            string dir = string(i, j);
            
            // special case: /a//b/c
            if (!dir.empty() && dir != ".") {
                if (dir == "..") {
                    if (!dirs.empty()) { // special case: /../
                        dirs.pop_back();
                    }
                }
                else {
                    dirs.push_back(dir);
                }
            }
            
            i = j;
        }
        
        string result;
        if (dirs.empty()) {
            result = "/";
        }
        else {
            for(int i=0; i<dirs.size(); ++i) {
                result += "/" + dirs[i];
            }
        }
        
        return result;
    }
};
