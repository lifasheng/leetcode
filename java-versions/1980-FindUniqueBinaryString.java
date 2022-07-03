/*
Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.

Example 1:
Input: nums = ["01","10"]
Output: "11"
Explanation: "11" does not appear in nums. "00" would also be correct.

Example 2:
Input: nums = ["00","01"]
Output: "11"
Explanation: "11" does not appear in nums. "10" would also be correct.

Example 3:
Input: nums = ["111","011","001"]
Output: "101"
Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
*/


class Solution {
    public String findDifferentBinaryString(String[] nums) {
        Set<String> set = new HashSet<>();
        for (String str : nums) {
            set.add(str);
        }
        
        int n = nums[0].length();
        for (int i = 0; i <= n; ++i) {
            String str = intToBinaryStr(i, n);
            if (!set.contains(str)) {
                return str;
            }
        }
        return "";
    }
    
    private String intToBinaryStr(int num, int bitLength) {
        StringBuilder sb = new StringBuilder();
        while (bitLength > 0) {
            if ((num & 1) == 1) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            num = num >> 1;
            -- bitLength;
        }
        // reverse will get right bit representative for us,
        // however, in this case, it doesn't matter if it is reversed or not.
        //sb.reverse();
        return sb.toString();
    }
}

