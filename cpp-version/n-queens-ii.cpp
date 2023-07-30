class Solution {
public:
//思路和n-queens一样，就是把构造合法解的地方换成统计合法解的个数
    bool isValidStatus(vector<int> &C, int k) {
        for(int i=0; i<k; ++i) {
            if (C[i] == C[k] || abs(i-k) == abs(C[i]-C[k])) return false;
        }
        return true;
    }
#define M1
#ifdef M1
    int totalNQueens(int n) {
        vector<int> C(n, 0);
        int count = 0;
        dfs(C, 0, count);
        return count;
    }
    
    void dfs(vector<int> &C, int row, int &count) {
        const int n = C.size();
        if (row == n) {
            ++count;
            return;
        }
        
        for(int j=0; j<n; ++j) {
            C[row] = j;
            if (isValidStatus(C, row)) {
                dfs(C, row+1, count);
            }
        }
    }
#endif
#ifdef M2
    int totalNQueens(int n) {
        vector<int> C(n, 0);
        
        int count = 0;
        typedef pair<int, int> state_t;
        
        stack<state_t> stk;
        int row = 0, col = 0;
        while ((row<n && col<n) || !stk.empty()) {
            if (row<n && col<n) {
                C[row] = col;
                if (isValidStatus(C, row)) {
                    stk.push({row, col});
                    ++row;
                    col = 0;
                }
                else {
                    ++col;
                }
            }
            else { // row>=n || col>=n， 他们的逻辑很类似，所以合并一下。
                if (row >= n) ++count;
                
                state_t st = stk.top();
                stk.pop();
                row = st.first;
                col = st.second;
                ++col;
            }
        }
        return count;
    }
#endif
};
