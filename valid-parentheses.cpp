class Solution {
public:
    bool isValid(string s) {
        const string lefts = "({[";
        const string rights = ")}]";
        
        stack<char> stk;
        for(int i=0; i<s.size(); ++i) {
            if (lefts.find(s[i]) != string::npos) {
                stk.push(s[i]);
            }
            else {
                if (stk.empty() || stk.top() != lefts[rights.find(s[i])]) {
                    return false;
                }
                
                stk.pop();
            }
        }
        
        return stk.empty();
    }
};
