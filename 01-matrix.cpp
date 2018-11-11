/*
Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.
Example 1: 
Input:

0 0 0
0 1 0
0 0 0
Output:
0 0 0
0 1 0
0 0 0
Example 2: 
Input:

0 0 0
0 1 0
1 1 1
Output:
0 0 0
0 1 0
1 2 1
Note:
The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
*/


#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include <stdio.h>
#include <sstream>
using namespace std;

/*
idea:BFS

first: if it's 0, set result to 0, and push to queue; otherwise, init it to INT_MAX;
second: bfs, starting from 0

if the current value is less than new calculated value, then skip it;
otherwise, update it and push the position to queue.

*/
class Solution {
public:
    vector<vector<int>> updateMatrix(vector<vector<int>>& matrix) {
        const int rows = matrix.size();
        if (rows == 0) return vector<vector<int>>();
        
        const int cols = matrix[0].size();
        vector<vector<int>> result(rows, vector<int>(cols, INT_MAX));
        
        std::queue<pair<int, int>> q;
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                if (matrix[i][j] == 0) {
                    result[i][j] = 0;
                    q.push(make_pair(i,j));
                } else {
                    result[i][j] = INT_MAX;
                }
            }
        }

        const vector<pair<int, int>> neighbors = {
            make_pair(-1, 0), make_pair(1, 0), 
            make_pair(0, -1), make_pair(0, 1)
        };

        while(!q.empty()) {
            pair<int, int> pos = q.front();
            q.pop();

            for(auto neighbor: neighbors) {
                int m = pos.first + neighbor.first;
                int n = pos.second + neighbor.second;
                if (m < 0 || m >= rows
                    || n <0 || n >= cols
                    || result[m][n] < result[pos.first][pos.second]+1) {
                    continue;
                }

                result[m][n] = result[pos.first][pos.second]+1;
                q.push(make_pair(m, n));
            }
        }

        return result;
    }
};

void printMatrix(vector<vector<int>> &result) {
    for(auto v:result) {
        for(auto i:v) {
            cout << i << " ";
        }
        cout << endl;
    }
}
int main() {
    Solution s;
    
    vector<int> r1 = {0,0,0};
    vector<int> r2 = {0,1,0};
    vector<int> r3 = {1,1,1};
    vector<vector<int>> matrix = {r1, r2, r3};
    printMatrix(matrix);
    vector<vector<int>> result = s.updateMatrix(matrix);
    printMatrix(result);

 
    return 0;
}


