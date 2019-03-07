/*
200. Number of Islands
Medium

Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1
Example 2:

Input:
11000
11000
00100
00011

Output: 3
*/

#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <queue>
#include <utility>
#include <string>
#include <sstream>
using namespace std;


class Solution {
private:
    int rows, cols;
    const vector<std::pair<int, int> > directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

public:
    int numIslands(vector<vector<char>>& grid) {
        rows = grid.size();
        if (rows == 0) return 0;
        cols = grid[0].size();
        if (cols == 0) return 0;
        
        vector<vector<bool>> visited(rows, vector<bool>(cols, false));
        
        int numberOfIsland = 0;
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                     bfs(grid, i, j, visited);
                    ++ numberOfIsland;
                }
            }
        }
        
        return numberOfIsland;
    }
    
    void bfs(const vector<vector<char>>& grid, int i, int j, vector<vector<bool>> &visited) {
        if (visited[i][j]) return;
        
        std::queue<std::pair<int, int>> q;
        q.push(make_pair(i, j));
        
        while(!q.empty()) {
            std::pair<int, int> p = q.front();
            q.pop();
            
            int i = p.first;
            int j = p.second;
            
            if (visited[i][j]) continue; // because one point may be added several times to the queue.
            
            visited[i][j] = true;
            
            for(auto d : directions) {
                int nextI = i+d.first;
                int nextJ = j+d.second;
                if (nextI >= 0 && nextI < rows 
                    && nextJ >=0 && nextJ < cols 
                    && grid[nextI][nextJ] == '1' 
                    && !visited[nextI][nextJ]) {
                    q.push(make_pair(nextI, nextJ));
                }
            }
        }
    }
};



class Solution {
private:
    int rows, cols;
    const vector<std::pair<int, int> > directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    void bfs(const vector<vector<char>>& grid, int i, int j, bool *visited) {
        if (visited[i*cols + j]) return;

        std::queue<std::pair<int, int>> q;
        q.push(make_pair(i, j));

        while(!q.empty()) {
            std::pair<int, int> p = q.front();
            q.pop();

            int i = p.first;
            int j = p.second;

            if (visited[i*cols + j]) continue; // because one point may be added several times to the queue.

            visited[i*cols + j] = true;

            for(auto d : directions) {
                int nextI = i+d.first;
                int nextJ = j+d.second;
                if (nextI >= 0 && nextI < rows 
                    && nextJ >=0 && nextJ < cols 
                    && grid[nextI][nextJ] == '1' 
                    && !visited[nextI*cols + nextJ]) {
                    q.push(make_pair(nextI, nextJ));
                }
            }
        }
    }
    
    void dfs(const vector<vector<char>>& grid, int i, int j, bool *visited) {
        if (visited[i*cols + j]) return;
        
        visited[i*cols + j] = true;
        
        for(auto d : directions) {
            int nextI = i+d.first;
            int nextJ = j+d.second;
            if (nextI >= 0 && nextI < rows 
                && nextJ >=0 && nextJ < cols 
                && grid[nextI][nextJ] == '1' 
                && !visited[nextI*cols + nextJ]) {
                dfs(grid, nextI, nextJ, visited);
            }
        }
    }
    
public:
    int numIslands(vector<vector<char>>& grid) {
        rows = grid.size();
        if (rows == 0) return 0;
        cols = grid[0].size();
        if (cols == 0) return 0;
        
        //vector<vector<bool>> visited(rows, vector<bool>(cols, false));
        bool visited[rows*cols];
        fill_n(&visited[0], rows*cols, false);
        
        int numberOfIsland = 0;
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                if (grid[i][j] == '1' && !visited[i*cols+ j]) {
                     //bfs(grid, i, j, visited);
                    dfs(grid, i, j, visited);
                    ++ numberOfIsland;
                }
            }
        }
        
        return numberOfIsland;
    }

};


int main() {
    Solution s;
    
    vector<vector<char>> v1 = {{'1','1','1','1','0'},
                                {'1','1','0','1','0'},
                                {'1','1','0','0','0'},
                                {'0','0','0','0','0'}};

    cout << s.numIslands(v1) << endl;
 
    return 0;
}
