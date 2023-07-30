/*
Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
Example 1:
Input:
    3
   / \
  9  20
    /  \
   15   7
Output: [3, 14.5, 11]
Explanation:
The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
Note:
The range of node's value is in the range of 32-bit signed integer.
*/

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
private:
    double avgOfCurLevel(const vector<TreeNode*> & v) {
        double sumOfCurLevel = 0;
        for (auto n : v) {
            sumOfCurLevel += n->val;
        }
        return sumOfCurLevel/v.size();
    }    
public:
    vector<double> averageOfLevels(TreeNode* root) {
        vector<double> avgOfEachLevel;
        vector<TreeNode*> curLevel, nextLevel;
        curLevel.push_back(root);
        while(!curLevel.empty()) {
            avgOfEachLevel.push_back(avgOfCurLevel(curLevel));
            
            for (auto n : curLevel) {
                if (n->left) {
                    nextLevel.push_back(n->left);
                }
                if (n->right) {
                    nextLevel.push_back(n->right);
                }
            }
            
            curLevel = nextLevel;
            nextLevel.clear();
        }
        return avgOfEachLevel;
    }
};
