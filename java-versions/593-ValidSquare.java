/*
Medium

Given the coordinates of four points in 2D space p1, p2, p3 and p4, return true if the four points construct a square.
The coordinate of a point pi is represented as [xi, yi]. The input is not given in any order.
A valid square has four equal sides with positive length and four equal angles (90-degree angles).

Example 1:
Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
Output: true

Example 2:
Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,12]
Output: false

Example 3:
Input: p1 = [1,0], p2 = [-1,0], p3 = [0,1], p4 = [0,-1]
Output: true
 
Constraints:
p1.length == p2.length == p3.length == p4.length == 2
-104 <= xi, yi <= 104
*/

class Solution {
    // 四个顶点之间有六条边，我们对这六条边排序
    // 正方形要求四条边相等，两条对角边相等且等于四条边的两倍(平方之后,省得计算sqrt)
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        long[] distances = new long[] {
            distance(p1, p2),
            distance(p1, p3),
            distance(p1, p4),
            distance(p2, p3),
            distance(p2, p4),
            distance(p3, p4)
        };
        
        Arrays.sort(distances);
        return distances[0] > 0
            && distances[0] == distances[1] 
            && distances[1] == distances[2]
            && distances[2] == distances[3]
            && distances[4] == distances[5]
            && distances[4] == (2 * distances[3]);
    }
    
    private int distance(int[] p1, int[] p2) {
        int x = p1[0] - p2[0];
        int y = p1[1] - p2[1];
        return x * x + y * y;
    }
}

