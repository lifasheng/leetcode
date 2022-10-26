/*
Medium

Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
Note: You must not use any built-in BigInteger library or convert the inputs to integer directly.

Example 1:
Input: num1 = "2", num2 = "3"
Output: "6"

Example 2:
Input: num1 = "123", num2 = "456"
Output: "56088"

Constraints:
1 <= num1.length, num2.length <= 200
num1 and num2 consist of digits only.
Both num1 and num2 do not contain any leading zero, except the number 0 itself.
*/


class Solution {
    /*
    very good!
    整个计算过程大概是这样，有两个指针 i，j 在 num1 和 num2 上游走，计算乘积，同时将乘积叠加到 res 的正确位置.
    细心观察之后就发现，num1[i] 和 num2[j] 的乘积对应的就是 res[i+j] 和 res[i+j+1] 这两个位置。
    */
    public String multiply(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();
        if (n1 < n2) {
            return multiply(num2, num1);
        }
        int[] res = new int[n1 + n2];
        for (int j = n2 - 1; j >= 0; --j) {
            int nj = num2.charAt(j) - '0';
            for (int i = n1 - 1; i >= 0; --i) {
                int ni = num1.charAt(i) - '0';
                
                // 乘积在 res 对应的索引位置
                int p1 = i + j;
                int p2 = i + j + 1;
                
                // 叠加到 res 上
                int mul = ni * nj;
                int sum = res[p2] + mul;
                
                res[p2] = (sum % 10);
                res[p1] += (sum / 10);
            }
        }
        
        // 结果前缀可能存的 0（未使用的位）
        int index = 0;
        while (index < res.length && res[index]  == 0) {
            ++ index;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < res.length; ++i) {
            sb.append((char)(res[i] + '0'));
        }
        
        return sb.length() > 0 ? sb.toString() : "0";
    }
}


