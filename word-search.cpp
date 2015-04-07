class Solution {
public:
    bool exist(vector<vector<char> > &board, string word) {
        if (board.empty()) return word.size() == 0;
        const int m = board.size();
        const int n = board[0].size();
        bool used[m][n];
        fill_n(&used[0][0], m*n, false);
        for(int i=0; i<m; ++i) {
            for(int j=0;j<n; ++j) {
                if (dfs(board, word, 0, i, j, &used[0][0])) return true;
            }
        }
        return false;
    }
    bool dfs(vector<vector<char> > &board, string &word, int start, int row, int col, bool *used) {
        if (start == word.size()) return true;
        
        const int m = board.size();
        const int n = board[0].size();
        
        if (row<0 || row>=m || col<0 || col>=n) return false;
        if (word[start] == board[row][col] && !used[row*n+col]) {
            used[row*n+col] = true;
            if (dfs(board, word, start+1, row, col+1, used)) return true;
            if (dfs(board, word, start+1, row+1, col, used)) return true;
            if (dfs(board, word, start+1, row, col-1, used)) return true;
            if (dfs(board, word, start+1, row-1, col, used)) return true;
            used[row*n+col] = false;
        }
        return false;
    }
};
