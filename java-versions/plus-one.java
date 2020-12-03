/*
66. Plus One
Easy
Given a non-empty array of decimal digits representing a non-negative integer, increment one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each element in the array contains a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

 

Example 1:

Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Example 3:

Input: digits = [0]
Output: [1]
*/

class Solution {
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    void reverse(int[] arr) {
        int i=0, j=arr.length-1;
        while(i<j) {
            swap(arr, i, j);
            ++i;
            --j;
        }
    }
    
    public int[] plusOne1(int[] digits) {
        reverse(digits);
        List<Integer> result = new ArrayList<>();
        int carry = 1;
        for(int i:digits) {
            int sum = carry+i;
            result.add(sum%10);
            carry = sum/10;
        }
        if (carry > 0) {
            result.add(carry);
        }
        Collections.reverse(result);
        return result.stream().mapToInt(i->i).toArray();
    }
    
    public int[] plusOne2(int[] digits) {
        int carry = 1;
        for(int i=digits.length-1; i>=0; --i) {
            int sum = carry+digits[i];
            digits[i] = sum%10;
            carry = sum/10;
        }
        if (carry > 0) {
            int[] result = new int[digits.length+1];
            result[0] = carry;
            for(int i=0;i<digits.length;++i) {
                result[i+1] = digits[i];
            }
            return result;
        }
        return digits;
    }
    
    public int[] plusOne(int[] digits) {
        return plusOne2(digits);
    }
}


