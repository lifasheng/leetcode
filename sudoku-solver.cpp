class Solution {
public:
    bool check(bool used[], char ch) {
        if (ch == '.') return true;
        if (used[ch-'1']) return false;
        return used[ch-'1'] = true;
    }
    // 该函数的实现类似于valid sudoku。
    bool isvalid(vector<vector<char> > &board, int row, int col) {
        bool used[9];
        
        fill_n(used, 9, false);
        for(int i=0; i<9; ++i) {
            if (!check(used, board[i][col])) return false;
        }
        fill_n(used, 9, false);
        for(int j=0; j<9; ++j) {
            if (!check(used, board[row][j])) return false;
        }
        fill_n(used, 9, false);
        for(int i=row/3*3; i<row/3*3+3; ++i) {
            for(int j=col/3*3; j<col/3*3+3; ++j) {
                if (!check(used, board[i][j])) return false;
            }
        }
        
        return true;
    }
    void solveSudoku(vector<vector<char> > &board) {
        dfs(board, 0, 0);
    }
    
    bool dfs(vector<vector<char> > &board, int row, int col) {
        if (row>=9) return true;
        if (col>=9) return dfs(board, row+1, 0);
        
        if (board[row][col] != '.') return dfs(board, row, col+1);
        
        for(int k=0; k<9; ++k) {
            board[row][col] = k+'1'; // 扩展状态
            if (isvalid(board, row, col)) {
                if (dfs(board, row, col+1)) return true;
            }
            board[row][col] = '.'; // 重置状态
        }
        return false;
    }
};
