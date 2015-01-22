class Solution {
public:
    int longestValidParentheses(string s) {
        int maxLen = 0, last = -1; // the position of last ')'
        stack<int> lefts; // keep track the position of unmatched '('
        
        for(int i=0; i<s.size(); ++i) {
            if (s[i] == '(') {
                lefts.push(i);
            }
            else {
                // no matching left
                if (lefts.empty()) { // case: )()
                    last = i;
                }
                else { // find a matching pair
                    lefts.pop();
                    if(lefts.empty()) {  // case: (), ()(), )()()
                        maxLen = max(maxLen, i-last);
                    }
                    else { // case: (())
                        maxLen = max(maxLen, i-lefts.top());
                    }
                }
            }
        }
        
        return maxLen;
    }
};
