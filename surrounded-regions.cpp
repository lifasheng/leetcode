class Solution {
public:
/*
思路： 从外往内搜索。外围一圈上的O肯定会保留下来。然后，从外围的O能达到的O也要保留，剩下其他的O就是内部的O。
直接从边界上的O开始BFS或DFS遍历即可，把他们都变成'#'（啥mark都可以，只要别是'O'或'X'），
然后遍历棋盘每个棋子，把遇到的'O'变成'X'， '#'还原成'O'即可。
*/
#define M2
#ifdef M1
// DFS, 对于大数据，比如100×100的数组，如果全部为'O'，递归的话则要从board[0][0]递归到board[m-1][n-1]，要m*n层调用栈，栈的层次太深了。
// 所以只能用非递归，用栈来辅助。
    void dfs(vector<vector<char>> &board, int x, int y){
        typedef pair<int, int> state_t;
        int m = board.size();
        int n = board[0].size();
        auto isValid = [&](state_t &s) {
            int x = s.first;
            int y = s.second;
            if (x<0 || x>=m || y<0 || y>=n || board[x][y] != 'O') return false;
            return true;
        };
        
        stack<state_t> stk;
        state_t start = {x, y};
        if (isValid(start)) {
            stk.push(start);
            board[x][y] = '#';
        }
        while(!stk.empty()) {
            state_t s = stk.top();
            stk.pop();
            
            x = s.first;
            y = s.second;
            
            state_t neighbors[4] = {{x-1, y}, {x+1, y}, {x, y-1}, {x, y+1}};
            for(int i=0; i<4; ++i) {
                if (isValid(neighbors[i])) {
                    // 既有标记功能又有去重功能
                    board[neighbors[i].first][neighbors[i].second] = '#';
                    stk.push(neighbors[i]);
                }
            }
        }
    }
    /*
    // 递归dfs会导致栈溢出。
    void dfs2(vector<vector<char>> &board, int x, int y){
        int m = board.size();
        int n = board[0].size();
        if(x<0 || x>=m || y<0 || y>=n || board[x][y]!='O') return;
        board[x][y]='#';
        dfs(board, x-1,y);
        dfs(board, x+1,y);
        dfs(board, x,y-1);
        dfs(board, x,y+1);
    }
    */
    void solve(vector<vector<char>> &board) {
        if(board.empty()) return;
        
        int m = board.size();
        int n = board[0].size();
        
        for(int j=0;j<n;j++){
            dfs(board, 0,j);
            dfs(board, m-1,j);
        }
        
        for(int i=1;i<m-1;i++){
            dfs(board, i,0);
            dfs(board, i,n-1);
        }
        
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++){
                if(board[i][j]=='O') board[i][j]='X';
                else if(board[i][j]=='#') board[i][j]='O';
            }
    }
#endif
    
#ifdef M2 
// BFS,时间复杂度 O(n),空间复杂度 O(n)
// 这里的BFS，是以元素(x,y)为起点，它的下一层就是它的周围的四个元素。
    void bfs(vector<vector<char> > &board, int x, int y) {
        typedef pair<int, int> state_t;
        int m = board.size();
        int n = board[0].size();
        auto isValid = [&](state_t &s) {
            int x = s.first;
            int y = s.second;
            if (x<0 || x>=m || y<0 || y>=n || board[x][y] != 'O') return false;
            return true;
        };
        
        auto state_extend = [&](state_t &s) {
            vector<state_t> result;
            int x = s.first;
            int y = s.second;
            state_t neighbors[4] = {{x-1, y}, {x+1, y}, {x, y-1}, {x, y+1}};
            for(int i=0; i<4; ++i) {
                if (isValid(neighbors[i])) {
                    // 既有标记功能又有去重功能
                    // 这里类似于word ladder，一旦扩展就标记为已访问。
                    board[neighbors[i].first][neighbors[i].second] = '#';
                    result.push_back(neighbors[i]);
                }
            }
            return result;
        };
        
        queue<state_t> q;
        state_t start = {x, y};
        if (isValid(start)) {
            board[x][y] = '#'; // 将起始结点标记为已访问。
            q.push(start);
        }
        
        while (!q.empty()) {
            state_t s = q.front();
            q.pop();
            
            vector<state_t> exts = state_extend(s);
            for(int i=0; i<exts.size(); ++i) {
                q.push(exts[i]);
            }
        }
    }
    void solve(vector<vector<char>> &board) {
        if(board.empty()) return;
        
        int m = board.size();
        int n = board[0].size();
        
        for(int j=0;j<n;j++){
            bfs(board, 0,j);
            bfs(board, m-1,j);
        }
        
        for(int i=1;i<m-1;i++){
            bfs(board, i,0);
            bfs(board, i,n-1);
        }
        
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++){
                if(board[i][j]=='O') board[i][j]='X';
                else if(board[i][j]=='#') board[i][j]='O';
            }
    }
#endif
};
