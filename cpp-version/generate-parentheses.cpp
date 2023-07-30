class Solution {
public:
/*
理解：
We know that in any balanced set, the first character has to be “(”. We also know that somewhere
in the set is the matching “)” for that opening one. In between that pair of parentheses is a balanced
set of parentheses, and to the right of it is another balanced set:
(A)B,
where A is a balanced set of parentheses and so is B. Both A and B can contain up to n − 1 pairs
of parentheses, but if A contains k pairs, then B contains n − k − 1 pairs. Notice that we will allow
either A or B to contain zero pairs, and that there is exactly one way to do so: don’t write down any
parentheses.
Thus we can count all the configurations where A has 0 pairs and B has n − 1 pairs, where A has
1 pair and B has n − 2 pairs, and so on. Add them up, and we get the total number of configurations
with n balanced pairs.
Here are the formulas. It is a good idea to try plugging in the numbers you know to make certain
that you haven’t made a silly error. In this case, the formula for C 3 indicates that it should be equal
to C 3 = 2 · 1 + 1 · 1 + 1 · 2 = 5.
C 1 = C 0 C 0 
C 2 = C 1 C 0 + C 0 C 1
C 3 = C 2 C 0 + C 1 C 1 + C 0 C 2 
C 4 = C 3 C 0 + C 2 C 1 + C 1 C 2 + C 0 C 3
...
*/
    unordered_map<int, vector<string> > cache;
    vector<string> generateParenthesis(int n) {
        if (n==0) return {""};
        if (n==1) return {"()"};
        if (cache.find(n) == cache.end()) {
            vector<string> result;
            for(int i=0; i<n; ++i) {
                for(auto si:generateParenthesis(i)) {
                    for(auto sj: generateParenthesis(n-1-i)) {
                        result.push_back("(" + si + ")" + sj);
                    }
                }
            }
            cache[n] = result;
        }
        
        return cache[n];
    }
    /*
    // this implementation can pass OJ.
    vector<string> generateParenthesis(int n) {
        if (n==0) return {""};
        if (n==1) return {"()"};
        vector<string> result;
        for(int i=0; i<n; ++i) {
            for(auto si:generateParenthesis(i)) {
                for(auto sj: generateParenthesis(n-1-i)) {
                    result.push_back("(" + si + ")" + sj);
                }
            }
        }
        
        return result;
    }
    */
};
