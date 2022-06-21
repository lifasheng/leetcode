/*
67. Add Binary
Easy
Given two binary strings a and b, return their sum as a binary string.

Example 1:
Input: a = "11", b = "1"
Output: "100"

Example 2:
Input: a = "1010", b = "1011"
Output: "10101"

Constraints:

1 <= a.length, b.length <= 104
a and b consist only of '0' or '1' characters.
Each string does not contain leading zeros except for the zero itself.
*/


class Solution {
    private char[] pack(String s, int len) {
        char[] arr = new char[len];
        int diff = len-s.length();
        Arrays.fill(arr, 0, diff, '0');
        for(int i=0; i<s.length(); ++i) {
            arr[i+diff] = s.charAt(i);
        }
        return arr;
    }
    
    // pack 0 to make two string length same
    private String addBinary1(String a, String b) {
        char[] x, y;
        if (a.length() > b.length()) {
            x = a.toCharArray();
            y = pack(b, a.length());
        } else {
            x = b.toCharArray();
            y = pack(a, b.length());
        }
        
        char[] res = new char[x.length];
        int carry = 0;
        for(int i=x.length-1; i>=0; --i) {
            int xi = x[i] - '0';
            int yi = y[i] - '0';
            int sum = xi+yi+carry;
            carry = sum/2;
            res[i] = (char)(sum%2 + '0');
        }
        return carry == 1 ? "1" + new String(res) : new String(res);
        
    }
    
    
    private void reverseCharArray(char[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        int i=0, j=arr.length-1;
        while(i<j) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            ++i;
            --j;
        }
    }
    
    // reverse char arry
    private String addBinary2(String a, String b) {
        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        
        reverseCharArray(x);
        reverseCharArray(y);
        int len = Math.max(x.length, y.length);
        char[] res = new char[len];
        int i=0;
        int carry = 0;
        while(i<x.length || i<y.length) {
            int xi = i<x.length ? x[i]-'0' : 0;
            int yi = i<y.length ? y[i]-'0' : 0;
            int sum = xi+yi+carry;
            carry = sum/2;
            res[i] = (char)(sum%2+'0');
            ++i;
        }
        
        reverseCharArray(res);
        return carry == 1 ? "1" + new String(res) : new String(res);
    }
    
    public String addBinary(String a, String b) {
        return addBinary2(a, b);
    }
}


