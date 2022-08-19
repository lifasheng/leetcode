/*
Easy

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer.

Example 1:
Input: s = "III"
Output: 3
Explanation: III = 3.

Example 2:
Input: s = "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.

Example 3:
Input: s = "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 
Constraints:
1 <= s.length <= 15
s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
It is guaranteed that s is a valid roman numeral in the range [1, 3999].
*/


class Solution {
    // 两种方法，一种是判断当前的值是不是比前面的大，另一种就是判断字符串组合。
    static Map<String, Integer> m = new HashMap<>();
    static {
        int[] nums = new int[] {1, 5, 10, 50, 100, 500, 1000, 4, 9, 40, 90, 400, 900};
        String[] romans = new String[] {"I", "V", "X", "L", "C", "D", "M", "IV", "IX", "XL", "XC", "CD", "CM"};
        for (int i = 0; i < romans.length; ++i) {
            m.put(romans[i], nums[i]);
        }
    }
    public int romanToInt(String s) {
        char[] arr = s.toCharArray();
        int num = 0;
        // for (int i = 0; i < arr.length; ) {
        //     if (
        //         (arr[i] == 'I' && i + 1 < arr.length && (arr[i + 1] == 'V' || arr[i + 1] == 'X'))
        //         || (arr[i] == 'X' && i + 1 < arr.length && (arr[i + 1] == 'L' || arr[i + 1] == 'C')) 
        //         || (arr[i] == 'C' && i + 1 < arr.length && (arr[i + 1] == 'D' || arr[i + 1] == 'M'))) {
        //         num += m.get(new String("" + arr[i] + arr[i + 1]));
        //         i += 2;
        //     } else {
        //         num += m.get(new String("" + arr[i]));
        //         i ++;
        //     }
        // }
        
        num += m.get(new String("" + arr[0]));
        for (int i = 1; i < arr.length; ++i) {
            String si = new String("" + arr[i]);
            String si_1 = new String("" + arr[i - 1]);
            if (i > 0 && (m.get(si) > m.get(si_1))) {
                num += (m.get(si) - 2 * m.get(si_1));
            } else {
                num += m.get(si);
            }
        }
        return num;
    }
}

