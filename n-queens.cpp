class Solution {
public:
#define M3
#ifdef M1
/*
递归版：回溯法（backtracking）
时间复杂度 O(n!),空间复杂度 O(n)， 相当于对N列元素进行全排列，再判断。
一个皇后q(x,y)能被满足以下条件的皇后q(row,col)吃掉
1）x=row(同一行不能有两个皇后)，这个很容易保证，因为我们一行一行的递归，所以肯定不会出现同一行放两个皇后。
2) y=col（同一列不能有两个皇后）
3) col + row = y+x;（斜向正方向）
4) col - row = y-x;（斜向反方向）
下面程序中的columns就是用于第二个条件的判断;
而main_diagonal和anti_diagonal分别用于第三个和第四个条件的判断。
*/
    vector<vector<string> > solveNQueens(int n) {
        this->columns = vector<int>(n, 0);
        this->main_diagonal = vector<int>(2*n, 0);
        this->anti_diagonal = vector<int>(2*n, 0);
        
        vector<vector<string> > result;
        vector<int> C(n, 0);
        dfs(C, result, 0);
        return result;
    }
    
private:
    // 这三个变量用于剪枝
    vector<int> columns;       // 标识哪些列放置了皇后
    vector<int> main_diagonal; // 占据了哪些主对角线
    vector<int> anti_diagonal; // 占据了哪些副对角线
    void dfs(vector<int> &C, vector<vector<string> > &result, int row) {
        const int n = C.size();
        if (row == n) { // 递归出口，[0..n-1]行都已经摆好了，此时找到了一个合法解。
            vector<string> solution;
            for(int i=0; i<n; ++i) {
                string s(n, '.');
                for(int j=0; j<n; ++j) {
                    if (j == C[i]) {
                        s[j] = 'Q';
                    }
                }
                solution.push_back(s);
            }
            result.push_back(solution);
            return;
        }
        
        for(int j=0; j<n; ++j) { // 扩展状态,一列一列的试
            // 这里row+j肯定是小于2*n的，所以不会越界，而row-j可能为负，所以要加n，保证不越界。
            bool isOK = columns[j] == 0 && main_diagonal[row+j] == 0 && anti_diagonal[row-j+n] == 0;
            if (!isOK) continue; // 剪枝:只有合法才继续递归。
            
            // 扩展动作
            C[row] = j;
            columns[j] = main_diagonal[row+j] = anti_diagonal[row-j+n] = 1;
            
            dfs(C, result, row+1);
            
            // 撤销动作
            columns[j] = main_diagonal[row+j] = anti_diagonal[row-j+n] = 0;
            //C[row] = 0; // 这个撤销没必要。
        }
    }
#endif
#ifdef M2
// 这个版本的实现和上一个版本思路类似，以行优先（皇后的行号按顺序递增，只考虑第i个皇后放置在第i行的哪一列），但细节有所不同。
    vector<vector<string> > solveNQueens(int n) {
        vector<vector<string> > result;
        vector<int> C(n, 0);
        dfs(C, result, 0);
        return result;
    }
    
    /* 
     *判断函数，判断第k个皇后是否可以放在某一个位置 
     *如果与之前的皇后出现在同一列或同一对角线则放置失败，返回0，否则返回1
     *这里我们判断一个状态是否合法，是用当前行和前面的行逐一进行比较，速度稍慢，但省了辅助空间。
    */  
    bool isValidStatus(vector<int> &C, int k) {
        for(int i=0; i<k; ++i) { // 第k行和前面k-1个行进行比较
            if (C[i] == C[k] || abs(i-k) == abs(C[i] - C[k])) return false;
        }
        return true;
    }
    void dfs(vector<int> &C, vector<vector<string> > &result, int row) {
        const int n = C.size();
        
        if (row == n) {
            vector<string> solution;
            for(int i=0; i<n; ++i) {
                string s(n, '.');
                for(int j=0; j<n; ++j) {
                    if (C[i] == j) {
                        s[j] = 'Q';
                    }
                }
                solution.push_back(s);
            }
            result.push_back(solution);
            return;
        }
        
        for(int j=0; j<n; ++j) { // 扩展状态,一列一列的试
            C[row] = j;
            // 因为我们这里除了数组C外，没有中间状态被改变，所以不用撤销动作。
            // 对于C[row]的赋值，下一次循环就会改变它，所以也没必要撤销。
            if (isValidStatus(C, row)) {
                dfs(C, result, row+1);
            }
        }
    }
#endif
#ifdef M3
/*
迭代版：用栈来辅助，思路类似于二叉树的先根遍历。
还记得二叉树遍历时的那个经典框架吗？ while(p || !s.empty())
书上的那种不用栈的迭代，我经常写乱，但是这个版本的，我应该不会忘记了。
*/
    bool isValidStatus(vector<int> &C, int k) {
        for(int i=0; i<k; ++i) { // 第k行和前面k-1个行进行比较
            if (C[i] == C[k] || abs(i-k) == abs(C[i] - C[k])) return false;
        }
        return true;
    }
    vector<vector<string> > solveNQueens(int n) {
        vector<vector<string> > result;
        vector<int> C(n, 0);
        
        typedef pair<int, int> state_t;
        
        stack<state_t> stk;
        int row = 0, col = 0;
        while((row<n && col<n) || !stk.empty()) {
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
            else if (col >= n) { // 回退到上一行的下一列
                state_t st = stk.top();
                stk.pop();
                row = st.first;
                col = st.second;
                ++col;
            }
            else { // row >= n， 找到一个合法解，同样回退到上一行的下一列。
                {
                vector<string> solution;
                for(int i=0; i<n; ++i) {
                    string s(n, '.');
                    for(int j=0; j<n; ++j) {
                        if (C[i] == j) {
                            s[j] = 'Q';
                        }
                    }
                    solution.push_back(s);
                }
                result.push_back(solution);
                }
                state_t st = stk.top();
                stk.pop();
                row = st.first;
                col = st.second;
                ++col;
            }
        }
        return result;
    }
#endif
};
