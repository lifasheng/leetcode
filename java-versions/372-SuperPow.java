/*
Medium

Your task is to calculate ab mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.

Example 1:
Input: a = 2, b = [3]
Output: 8

Example 2:
Input: a = 2, b = [1,0]
Output: 1024

Example 3:
Input: a = 1, b = [4,3,3,8,5,2]
Output: 1
 
Constraints:
1 <= a <= 231 - 1
1 <= b.length <= 2000
0 <= b[i] <= 9
b does not contain leading zeros.
*/

class Solution {
    private static final int MAGIC_NUMBER = 1337;
    public int superPow(int a, int[] b) {
        if (a == 1) return 1;
        if (b.length == 1 && b[0] == 0) return 1;
        
        if (b[b.length - 1] % 2 == 1) {
            b[b.length - 1] -= 1;
            return ((a % MAGIC_NUMBER) * (superPow(a, b) % MAGIC_NUMBER)) % MAGIC_NUMBER;
        }
        
        int r = (superPow(a, divide2(b)) % MAGIC_NUMBER);
        return (r * r) % MAGIC_NUMBER;
    }
    
    private int[] divide2(int[] b) {
        for (int i = 0; i < b.length; ++i) {
            if (b[i] % 2 == 1) {
                int bi = b[i];
                b[i] /= 2;
                b[i + 1] += (bi % 2) * 10;
            } else {
                b[i] /= 2;
            }
        }
        
        if (b[0] != 0) {
            return b;
        }
        else {
            int[] c = new int[b.length - 1];
            for (int i = 1; i < b.length; ++i) {
                c[i - 1] = b[i];
            }
            return c;
        }
    }
}

