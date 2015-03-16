class Solution {
public:
    bool check(char ch, bool used[]) {
        if (ch == '.') return true;
        if (used[ch-'1']) return false;
        used[ch-'1'] = true;
        return true;
    }
    bool isValidSudoku(vector<vector<char> > &board) {
        bool used[9];
        for (int i=0; i<9; ++i) {
            fill(used, used+9, false);
            
            for(int j=0; j<9; ++j) {
                if (!check(board[i][j], used)) {
                    return false;
                }
            }
            
            fill(used, used+9, false);
            
            for(int j=0; j<9; ++j) {
                if (!check(board[j][i], used)) {
                    return false;
                }
            }
        }
        
        for (int r=0; r<3; ++r) {
            for (int c=0; c<3; ++c) {
                fill(used, used+9, false);
                
                for(int i=r*3; i<r*3+3; ++i) {
                    for(int j=c*3; j<c*3+3; ++j) {
                        if (!check(board[i][j], used)) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
};



class Solution {
public:
    bool check(bool isUsed[], char ch) {
        if (ch == '.') return true;
        if (isUsed[ch-'1']) return false;
        return isUsed[ch-'1'] = true;
    }
    bool isValidSudoku(vector<vector<char> > &board) {
        bool isUsed[9];
        for(int i=0; i<9; ++i) {
            fill_n(isUsed, 9, false);
            for(int j=0; j<9; ++j) {
                if (!check(isUsed, board[i][j])) return false;
            }
            
            fill_n(isUsed, 9, false);
            for(int j=0; j<9; ++j) {
                if (!check(isUsed, board[j][i])) return false;
            }
        }
        
        for(int r=0; r<3; ++r) {
            for(int c=0; c<3; ++c) {
                fill_n(isUsed, 9, false);
                for(int i=0; i<3; ++i) {
                    for(int j=0; j<3; ++j) {
                        if (!check(isUsed, board[r*3+i][c*3+j])) return false;
                    }
                }
            }
        }
        
        return true;
    }
};
