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
/*
 * 注意： 这里扩展的时候一定要马上将该结点设置为已扩展的，不然就会存在重复扩展的情况，
 * 导致出现memory limit exceed的错误。
 * 考虑全是O的board：
 * O O O O
 * O O O O
 * O O O O
 * O O O O
 * 从左上角开始，访问顺序是:
 * 1  2  4  7
 * 3  5  8  11
 * 6  9  12 14
 * 10 13 15 16
 *
 * queue里的元素在每个循环是：
 * 1
 * 2 3
 * 3 4 5  => 2 扩展出一个5
 * 4 5 5 6  => 3 又扩展出一个5
 * 5 5 6 7 8
 * 5 6 7 8 8 9  => 第一个5扩展出8,9
 * 6 7 8 8 9 8 9 => 第二个5又扩展出8,9
 * ... ...
 * 这样下去将会出现queue的大小大于m*n (对于4×4的board，queue最大达到20), 从而memory limit exceed.
 *
 * 有几种方法可以解决这个问题：
 * 1. 像下面的代码一样，扩展时就标记已扩展;
 * 2. 也可以像word ladder ii那样，用两个set：cur和next，访问完cur后，对得到的next统一标记。
*/
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
//################################
// 这个会超出内存限制，具体解释参考上面的注释。
class Solution {
public:
    void solve(vector<vector<char>> &board) {
        if (board.empty()) return;
        const int m = board.size();
        const int n = board[0].size();
        for(int i=0; i<m; ++i) {
            if (board[i][0] == 'O') bfs(board, i, 0);
            if (board[i][n-1] == 'O') bfs(board, i, n-1);
        }
        for(int j=1; j<n-1; ++j) {
            if (board[0][j] == 'O') bfs(board, 0, j);
            if (board[m-1][j] == 'O') bfs(board, m-1, j);
        }
        for(int i=0; i<m; ++i) {
            for(int j=0; j<n; ++j) {
                if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
                else {
                    board[i][j] = 'X';
                }
            }
        }
    }
    void bfs(vector<vector<char> > &board, int i, int j) {
        const int m = board.size();
        const int n = board[0].size();
        
        auto isvalid = [&](int x, int y) {
            return x>=0 && x<m && y>=0 && y<n && board[x][y] == 'O'; 
        };
        
        queue<pair<int, int> > q;
        if (isvalid(i,j)) {
            q.push({i,j});
            //board[i][j] = 'Y';
        }
        
        while(!q.empty()) {
            auto p = q.front();
            q.pop();
            i = p.first;
            j = p.second;
            
            board[i][j] = 'Y';
            
            if (isvalid(i-1,j)) { 
                q.push({i-1, j}); 
                //board[i-1][j] = 'Y';
            }
            if (isvalid(i+1,j)) { 
                q.push({i+1, j}); 
                //board[i+1][j] = 'Y'; 
            }
            if (isvalid(i,j-1)) { 
                q.push({i, j-1}); 
                //board[i][j-1] = 'Y';
            }
            if (isvalid(i,j+1)) { 
                q.push({i, j+1}); 
                //board[i][j+1] = 'Y';
            }
        }
    }
};
//##########################################
// 以下实现类似于word ladder ii，用两个set，这样就可以避免重复了。
// 但是这里和word ladder ii又有点区别，主要是，这里next这个set不用统一去重，
// 因为它是在访问完每个位置时做标记，而这有点像word ladder。
class Solution {
public:
    void solve(vector<vector<char>> &board) {
        if (board.empty()) return;
        const int m = board.size();
        const int n = board[0].size();
        for(int i=0; i<m; ++i) {
            if (board[i][0] == 'O') bfs(board, i, 0);
            if (board[i][n-1] == 'O') bfs(board, i, n-1);
        }
        for(int j=1; j<n-1; ++j) {
            if (board[0][j] == 'O') bfs(board, 0, j);
            if (board[m-1][j] == 'O') bfs(board, m-1, j);
        }
        for(int i=0; i<m; ++i) {
            for(int j=0; j<n; ++j) {
                if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
                else {
                    board[i][j] = 'X';
                }
            }
        }
    }
    void bfs(vector<vector<char> > &board, int i, int j) {
        const int m = board.size();
        const int n = board[0].size();
        
        auto isvalid = [&](int x, int y) {
            return x>=0 && x<m && y>=0 && y<n && board[x][y] == 'O'; 
        };
        
        set<pair<int, int> > cur, next;
        if (isvalid(i,j)) {
            cur.insert({i,j});
        }
        
        while(!cur.empty()) {
            for(auto p: cur) {
                i = p.first;
                j = p.second;
                
                board[i][j] = 'Y';
                
                if (isvalid(i-1,j)) { 
                    next.insert({i-1, j}); 
                }
                if (isvalid(i+1,j)) { 
                    next.insert({i+1, j}); 
                }
                if (isvalid(i,j-1)) { 
                    next.insert({i, j-1}); 
                }
                if (isvalid(i,j+1)) { 
                    next.insert({i, j+1}); 
                }
            }
            cur.clear();
            swap(cur, next);
        }
    }
};
