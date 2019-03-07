/*
827. Making A Large Island
Hard

In a 2D grid of 0s and 1s, we change at most one 0 to a 1.

After, what is the size of the largest island? (An island is a 4-directionally connected group of 1s).

Example 1:

Input: [[1, 0], [0, 1]]
Output: 3
Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
Example 2:

Input: [[1, 1], [1, 0]]
Output: 4
Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
Example 3:

Input: [[1, 1], [1, 1]]
Output: 4
Explanation: Can't change any 0 to 1, only one island with area = 4.
 

Notes:

1 <= grid.length = grid[0].length <= 50.
0 <= grid[i][j] <= 1.

*/


#include <iostream>
#include <vector>
#include <queue>
#include <unordered_set>
#include <map>
#include <set>
#include <utility>
#include <string>
#include <sstream>
using namespace std;

class Solution {
private:
    int rows, cols;
    const vector<std::pair<int, int> > directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    int bfs(const vector<vector<int>>& grid, 
            int i, int j, 
            bool *visited,
           set<pair<int, int>> *islandSet, 
           map<pair<int, int>, set< set< pair<int, int> >* > > &zeroToIslandsMap) {
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
            
            islandSet->insert(p);

            visited[i*cols + j] = true;

            for(auto d : directions) {
                int nextI = i+d.first;
                int nextJ = j+d.second;
                
                if (nextI >= 0 && nextI < rows 
                    && nextJ >=0 && nextJ < cols) {
                    if (!grid[nextI][nextJ]) {
                        zeroToIslandsMap[make_pair(nextI, nextJ)].insert(islandSet);
                    } else {
                        if (!visited[nextI*cols + nextJ]) {
                            q.push(make_pair(nextI, nextJ));
                        }
                    }
                }
            }
        }

        return area;
    }
    
    int dfs(const vector<vector<int>>& grid, 
            int i, int j, 
            bool* visited, 
            set<pair<int, int>> *islandSet, 
            map<pair<int, int>, set< set< pair<int, int> >* > > &zeroToIslandsMap) {
        
        if (visited[i*cols + j]) return 0;
        
        visited[i*cols + j] = true;
        
        int area = 1;
        
        islandSet->insert(make_pair(i, j));

        for(auto d : directions) {
            int nextI = i+d.first;
            int nextJ = j+d.second;
            if (nextI >= 0 && nextI < rows 
                && nextJ >=0 && nextJ < cols) {
                if (!grid[nextI][nextJ]) {
                    zeroToIslandsMap[make_pair(nextI, nextJ)].insert(islandSet);
                } else {
                    if (!visited[nextI*cols + nextJ]) {
                        area += dfs(grid, nextI, nextJ, visited, islandSet, zeroToIslandsMap);
                    }
                }
            }
        }
        
        return area;
    }
    
public:
    int largestIsland(vector<vector<int>>& grid) {
        rows = grid.size();
        if (rows == 0) return 0;
        cols = grid[0].size();
        if (cols == 0) return 0;
        
        bool visited[rows*cols]; // = {false};
	fill_n(&visited[0], rows*cols, false);

        map<pair<int, int>, set< set< pair<int, int> >* > > zeroToIslandsMap;
        
        bool allZero = true, allOne = true;
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                if (grid[i][j]) {
                    allZero = false;
                }
                if (!grid[i][j]) {
                    allOne = false;
                }
                if (grid[i][j] && !visited[i*cols + j]) {
                    set<pair<int, int>> *islandSet = new set<pair<int, int>>();
                    int area = bfs(grid, i, j, visited, islandSet, zeroToIslandsMap);
                    //int area = dfs(grid, i, j, visited, islandSet, zeroToIslandsMap);
                }
            }
        }
        
        if (allZero) return 1;
        if (allOne) return rows*cols;
        
        int maxArea = 0;
        for (auto x : zeroToIslandsMap) {
            set< set< pair<int, int> >* > ss = x.second;
            int expandedArea = 0;
            for (auto &y : ss) {
                expandedArea += y->size();
            }
            
            if (expandedArea) { // if not all zero
                ++expandedArea;
            }
            
            maxArea = max(maxArea, expandedArea);
        }
        
        return maxArea;
    }
};

int main()
{

    Solution s;
    
    vector<vector<int>> v1 = {{1,1,1,1,0},
                                {1,1,0,1,0},
                                {1,1,0,0,0},
                                {0,0,0,0,0}};

    cout << s.largestIsland(v1) << endl;

    return 0;
}

