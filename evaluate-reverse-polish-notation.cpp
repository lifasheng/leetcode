class Solution {
private:
    bool isOperator(const string &s) {
        const string operators = "+-*/";
        return s.size() == 1 && operators.find(s) != string::npos;
    }
public:
#define M2
#ifdef M1
//迭代版: O(n) space, O(logn) space
// 注意string，const char*， int之间的转换。
    int evalRPN(vector<string> &tokens) {
        stack<string> stk;
        
        for(int i=0; i<tokens.size(); ++i) {
            if (isOperator(tokens[i])) {
                int y = atoi(stk.top().c_str());
                stk.pop();
                int x = atoi(stk.top().c_str());
                stk.pop();
                switch(tokens[i][0]) {
                    case '+': { x += y; break; }
                    case '-': { x -= y; break; }
                    case '*': { x *= y; break; }
                    case '/': { if (y == 0) abort(); x /= y; break; }
                }
                char buf[64];
                sprintf(buf, "%d", x);
                stk.push(buf);
            }
            else {
                stk.push(tokens[i]);
            }
        }
        int result = atoi(stk.top().c_str());
        return result;
    }
#endif
#ifdef M2
//递归版: O(n) space, O(logn) space
    int evalRPN(vector<string> &tokens) {
        string s = tokens.back();
        tokens.pop_back();
        if (!isOperator(s)) {
            return atoi(s.c_str());
        }
        
        int y = evalRPN(tokens);
        int x = evalRPN(tokens);
        
        switch(s[0]) {
            case '+': { x += y; break; }
            case '-': { x -= y; break; }
            case '*': { x *= y; break; }
            case '/': { if (y == 0) abort(); x /= y; break; }
        }
        return x;
    }
#endif
};
