/*
695. Max Area of Island
Medium

Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

Example 1:

[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
Example 2:

[[0,0,0,0,0,0,0,0]]
Given the above grid, return 0.
Note: The length of each dimension in the given grid does not exceed 50.

*/

#include <iostream>
#include <vector>
#include <queue>
#include <queue>
#include <utility>
#include <string>
#include <sstream>
using namespace std;

class Solution {
private:
    int rows, cols;
    const vector<std::pair<int, int> > directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    int bfs(const vector<vector<int>>& grid, int i, int j, vector<vector<bool>> &visited) {
        if (visited[i][j]) return 0;

        std::queue<std::pair<int, int>> q;
        q.push(make_pair(i, j));

        int area = 0;
        while(!q.empty()) {
            std::pair<int, int> p = q.front();
            q.pop();

            int i = p.first;
            int j = p.second;

            if (visited[i][j]) continue; // because one point may be added several times to the queue.

            ++area;

            visited[i][j] = true;

            for(auto d : directions) {
                int nextI = i+d.first;
                int nextJ = j+d.second;
                if (nextI >= 0 && nextI < rows 
                    && nextJ >=0 && nextJ < cols 
                    && grid[nextI][nextJ]
                    && !visited[nextI][nextJ]) {
                    q.push(make_pair(nextI, nextJ));
                }
            }
        }

        return area;
    }
    
    int dfs(const vector<vector<int>>& grid, int i, int j, vector<vector<bool>> &visited) {
        if (visited[i][j]) return 0;
        
        visited[i][j] = true;
        
        int area = 1;

        for(auto d : directions) {
            int nextI = i+d.first;
            int nextJ = j+d.second;
            if (nextI >= 0 && nextI < rows 
                && nextJ >=0 && nextJ < cols 
                && grid[nextI][nextJ]
                && !visited[nextI][nextJ]) {
                area += dfs(grid, nextI, nextJ, visited);
            }
        }
        
        return area;
    }
public:    
    int maxAreaOfIsland(vector<vector<int>>& grid) {
        rows = grid.size();
        if (rows == 0) return 0;
        cols = grid[0].size();
        if (cols == 0) return 0;
        
        vector<vector<bool>> visited(rows, vector<bool>(cols, false));
        
        int maxArea = 0;
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                if (grid[i][j] && !visited[i][j]) {
                    //int area = bfs(grid, i, j, visited);
                    int area = dfs(grid, i, j, visited);
                    maxArea = max(area, maxArea);
                }
            }
        }
        
        return maxArea;
    }
};



// use array instead of vector to improve the latency.
class Solution {
private:
    int rows, cols;
    const vector<std::pair<int, int> > directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    int bfs(const vector<vector<int>>& grid, int i, int j, bool *visited) {
        if (visited[i*cols + j]) return 0;

        std::queue<std::pair<int, int>> q;
        q.push(make_pair(i, j));

        int area = 0;
        while(!q.empty()) {
            std::pair<int, int> p = q.front();
            q.pop();

            int i = p.first;
            int j = p.second;

            if (visited[i*cols + j]) continue; // because one point may be added several times to the queue.

            ++area;

            visited[i*cols + j] = true;

            for(auto d : directions) {
                int nextI = i+d.first;
                int nextJ = j+d.second;
                if (nextI >= 0 && nextI < rows 
                    && nextJ >=0 && nextJ < cols 
                    && grid[nextI][nextJ]
                    && !visited[nextI*cols + nextJ]) {
                    q.push(make_pair(nextI, nextJ));
                }
            }
        }

        return area;
    }
    
    int dfs(const vector<vector<int>>& grid, int i, int j, bool* visited) {
        if (visited[i*cols + j]) return 0;
        
        visited[i*cols + j] = true;
        
        int area = 1;

        for(auto d : directions) {
            int nextI = i+d.first;
            int nextJ = j+d.second;
            if (nextI >= 0 && nextI < rows 
                && nextJ >=0 && nextJ < cols 
                && grid[nextI][nextJ]
                && !visited[nextI*cols + nextJ]) {
                area += dfs(grid, nextI, nextJ, visited);
            }
        }
        
        return area;
    }
public:    
    int maxAreaOfIsland(vector<vector<int>>& grid) {
        rows = grid.size();
        if (rows == 0) return 0;
        cols = grid[0].size();
        if (cols == 0) return 0;
        
        bool visited[rows*cols] = {false};
        
        int maxArea = 0;
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                if (grid[i][j] && !visited[i*cols + j]) {
                    //int area = bfs(grid, i, j, visited);
                    int area = dfs(grid, i, j, visited);
                    maxArea = max(area, maxArea);
                }
            }
        }
        
        return maxArea;
    }
};




int main() {
    Solution s;
    
    vector<vector<int>> v1 = {{1,1,1,1,0},
                                {1,1,0,1,0},
                                {1,1,0,0,0},
                                {0,0,0,0,0}};

    cout << s.maxAreaOfIsland(v1) << endl;
 
    return 0;
}




#========================
python implementation
#========================
class Solution(object):
    def maxAreaOfIsland(self, grid):
        spotted = set()
        max_island_size = 0
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == 1 and (i, j) not in spotted:
                    max_island_size = max(max_island_size, self.dfs(grid, i, j, spotted))
        return max_island_size
    

    def dfs(self, grid, i, j, spotted):
        if i>=0 and i<len(grid) and j>=0 and j<len(grid[0]) \
            and grid[i][j] == 1 and (i, j) not in spotted:
            spotted.add( (i,j) )
            return 1 + \
                self.dfs(grid, i-1, j, spotted) + \
                self.dfs(grid, i+1, j, spotted) + \
                self.dfs(grid, i, j-1, spotted) + \
                self.dfs(grid, i, j+1, spotted)
        else:
            return 0



