/*
168. Excel Sheet Column Title
Given a positive integer, return its corresponding column title as appear in an Excel sheet.

For example:

    1 -> A
    2 -> B
    3 -> C
    ...
    26 -> Z
    27 -> AA
    28 -> AB 
    ...
Example 1:

Input: 1
Output: "A"
Example 2:

Input: 28
Output: "AB"
Example 3:

Input: 701
Output: "ZY"

*/

// 26 = > Z
// 27 => AA
// 28 => AB
// 52 => AZ
// 53 => BA
// 701 => ZY
class Solution {
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int m = n%26;
            if (m == 0) {
                sb.append((char)('A' + 25));
                n = n/26-1;
            } else {
                sb.append((char) ('A' + m - 1));
                n /= 26;
            }
        }
        return sb.reverse().toString();
    }
}

