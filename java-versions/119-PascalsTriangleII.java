/*
Easy
Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.
In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

Example 1:
Input: rowIndex = 3
Output: [1,3,3,1]

Example 2:
Input: rowIndex = 0
Output: [1]

Example 3:
Input: rowIndex = 1
Output: [1,1]
 
Constraints:
0 <= rowIndex <= 33
Follow up: Could you optimize your algorithm to use only O(rowIndex) extra space?
*/

class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < rowIndex + 1; ++i) {
            result.add(1);
        }
        
        for (int i = 0; i <= rowIndex; ++i) {
            for (int j = i; j >= 0; --j) { // 这里需要从右往左，run with example就能理解了。
                if (j == 0 || j == i) {
                    result.set(j, 1);
                } else {
                    result.set(j, result.get(j - 1) + result.get(j));
                }
            }
        }
        
        return result;
    }
}

