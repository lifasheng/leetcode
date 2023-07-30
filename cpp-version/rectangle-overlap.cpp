/*
A rectangle is represented as a list [x1, y1, x2, y2], where (x1, y1) are the coordinates of its bottom-left corner, and (x2, y2) are the coordinates of its top-right corner.

Two rectangles overlap if the area of their intersection is positive.  To be clear, two rectangles that only touch at the corner or edges do not overlap.

Given two (axis-aligned) rectangles, return whether they overlap.

Example 1:

Input: rec1 = [0,0,2,2], rec2 = [1,1,3,3]
Output: true
Example 2:

Input: rec1 = [0,0,1,1], rec2 = [1,0,2,1]
Output: false
Notes:

Both rectangles rec1 and rec2 are lists of 4 integers.
All coordinates in rectangles will be between -10^9 and 10^9.
*/


/*
Just from experience with these types of problems, it's usually easier to find out the opposite of what doesn't overlap. The following cases they don't overlap:
Case 1:if the maximum of rec1 is less than or equal to the minimum from rec2 in the x
Case 2:if the maximum of rec1 is less than or equal to the minimum from rec2 in the y
Case 3: just swap rec1 and rec2 in Case 1
Case 4:Just swap rec1 and rec2 in Case 2
All other cases they overlap.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <string>
#include<stdio.h>
using namespace std;

class Solution {
public:
    bool isRectangleOverlap(vector<int>& rec1, vector<int>& rec2) {
        return !(rec1[2] <= rec2[0] || rec1[3] <= rec2[1] 
        || rec2[2] <= rec1[0] || rec2[3] <= rec1[1]);

    }
};


int main() {
    Solution s;
    vector<int> rec1 = {7,8,13,15};
    vector<int> rec2 = {10,8,12,20};
    cout <<s.isRectangleOverlap(rec1, rec2)<<endl;
    return 0;
}


