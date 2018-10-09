/*

We want to use quad trees to store an N x N boolean grid. Each cell in the grid can only be true or false. The root node represents the whole grid. For each node, it will be subdivided into four children nodes until the values in the region it represents are all the same.

Each node has another two boolean attributes : isLeaf and val. isLeaf is true if and only if the node is a leaf node. The val attribute for a leaf node contains the value of the region it represents.

Your task is to use a quad tree to represent a given grid. The following example may help you understand the problem better:

Given the 8 x 8 grid below, we want to construct the corresponding quad tree:



It can be divided according to the definition above:



 

The corresponding quad tree should be as following, where each node is represented as a (isLeaf, val) pair.

For the non-leaf nodes, val can be arbitrary, so it is represented as *.



Note:

N is less than 1000 and guaranteened to be a power of 2.
If you want to know more about the quad tree, you can refer to its wiki.

*/


#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include<stdio.h>
using namespace std;


class Node {
public:
    bool val;
    bool isLeaf;
    Node* topLeft;
    Node* topRight;
    Node* bottomLeft;
    Node* bottomRight;

    Node() {}

    Node(bool _val, bool _isLeaf, Node* _topLeft, Node* _topRight, Node* _bottomLeft, Node* _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
};

class Solution {
private:
    bool areAllCellsSame(vector<vector<int>>& grid) {
        int N = grid.size();
        for(int i=0; i<N; ++i) {
            for(int j=0; j<N; ++j) {
                if (grid[i][j] != grid[0][0]) return false;
            }
        }
        return true;
    }

    vector<vector<int>> splitGrid(vector<vector<int>>& grid, int block) {
        vector<vector<int>> subGrid;
        int N = grid.size();
        if (block == 0) { // topLeft
            for(int i=0; i<N/2; ++i) {
                vector<int> rows;
                for(int j=0; j<N/2; ++j) {
                    rows.push_back(grid[i][j]);
                }
                subGrid.push_back(rows);
            }
        } else if (block == 1) { // topRight
            for(int i=0; i<N/2; ++i) {
                vector<int> rows;
                for(int j=N/2; j<N; ++j) {
                    rows.push_back(grid[i][j]);
                }
                subGrid.push_back(rows);
            }
        } else if (block == 2) { // bottomLeft
            for(int i=N/2; i<N; ++i) {
                vector<int> rows;
                for(int j=0; j<N/2; ++j) {
                    rows.push_back(grid[i][j]);
                }
                subGrid.push_back(rows);
            }
        } else {// block == 3 // bottomRight
            for(int i=N/2; i<N; ++i) {
                vector<int> rows;
                for(int j=N/2; j<N; ++j) {
                    rows.push_back(grid[i][j]);
                }
                subGrid.push_back(rows);
            }
        }

        return subGrid;
    }

    void printGrid(vector<vector<int>>& grid) {
        int N = grid.size();
        for(int i=0; i<N; ++i) {
            for(int j=0; j<N; ++j) {
                cout << grid[i][j] << " ";
            }
            cout << endl;
        }
    }
public:
    Node* construct(vector<vector<int>>& grid) {
        int N = grid.size();
        Node *root = new Node();
        if (areAllCellsSame(grid)) {
            //printGrid(grid);
            root->val = grid[0][0];
            root->isLeaf = true;
            root->topLeft = NULL;
            root->topRight = NULL;
            root->bottomLeft = NULL;
            root->bottomRight = NULL;
        } else {
            root->val = false;
            root->isLeaf = false;
            vector<vector<int>> topLeft = splitGrid(grid, 0);
            vector<vector<int>> topRight = splitGrid(grid, 1);
            vector<vector<int>> bottomLeft = splitGrid(grid, 2);
            vector<vector<int>> bottomRight = splitGrid(grid, 3);
            root->topLeft = construct(topLeft);
            root->topRight = construct(topRight);
            root->bottomLeft = construct(bottomLeft);
            root->bottomRight = construct(bottomRight);
        }
        return root;
    }

    void printNode(Node* root, int level) {
        if (root == NULL) return;

        string space = "";
        for(int i=0; i<level; ++i) {
            space += ' ';
        }
        cout << space << "val:" << root->val << ", isLeaf:" << root->isLeaf << endl;
        printNode(root->topLeft, level+4);
        printNode(root->topRight, level+4);
        printNode(root->bottomLeft, level+4);
        printNode(root->bottomRight, level+4);
    }
};

int main() {
    Solution s;
    vector<vector<int>> grid;
    vector<int> r1 = {1, 1, 0, 0};
    vector<int> r2 = {1, 1, 0, 0};
    vector<int> r3 = {0, 0, 1, 0};
    vector<int> r4 = {1, 0, 1, 1};
    grid = {r1, r2, r3, r4};

    Node * root = s.construct(grid);
    s.printNode(root, 0);

    return 0;
}

