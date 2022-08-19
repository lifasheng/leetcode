/*
Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given an integer, convert it to a roman numeral.

Example 1:
Input: num = 3
Output: "III"
Explanation: 3 is represented as 3 ones.

Example 2:
Input: num = 58
Output: "LVIII"
Explanation: L = 50, V = 5, III = 3.

Example 3:
Input: num = 1994
Output: "MCMXCIV"
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 
Constraints:
1 <= num <= 3999
*/

class Solution {
    
    // 两种方法：一种是构建完整的map，另一种是把基本的数字类型从大到小排列，然后和当前数字比较。
    private static final Map<Integer, String> m = new HashMap<>();
    static {
        m.put(1, "I");
        m.put(5, "V");
        m.put(10, "X");
        m.put(50, "L");
        m.put(100, "C");
        m.put(500, "D");
        m.put(1000, "M");
        m.put(4, "IV");
        m.put(9, "IX");
        m.put(40, "XL");
        m.put(90, "XC");
        m.put(400, "CD");
        m.put(900, "CM");
        
        m.put(2, "II");
        m.put(3, "III");
        m.put(6, "VI");
        m.put(7, "VII");
        m.put(8, "VIII");
        
        m.put(20, "XX");
        m.put(30, "XXX");
        m.put(60, "LX");
        m.put(70, "LXX");
        m.put(80, "LXXX");
        
        m.put(200, "CC");
        m.put(300, "CCC");
        m.put(600, "DC");
        m.put(700, "DCC");
        m.put(800, "DCCC");
        
        m.put(2000, "MM");
        m.put(3000, "MMM");
    }
    
    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] romans = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length && num > 0; ++i) {
            while (values[i] <= num) {
                num -= values[i];
                sb.append(romans[i]);
            }
        }
        return sb.toString();
    }
    
    public String intToRoman2(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1000; i >= 1; i /= 10) {
            if (num >= i) {
                int n = num / i;
                num %= i;
                sb.append(m.get(n * i));
            }
        }
        return sb.toString();
    }
    
}

