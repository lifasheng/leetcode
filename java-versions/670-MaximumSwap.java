/*
good!

You are given an integer num. You can swap two digits at most once to get the maximum valued number.

Return the maximum valued number you can get.

 

Example 1:

Input: num = 2736
Output: 7236
Explanation: Swap the number 2 and the number 7.
Example 2:

Input: num = 9973
Output: 9973
Explanation: No swap.
 

Constraints:

0 <= num <= 108
*/


class Solution {
    public int maximumSwap(int num) {
        if (num <= 10) return num;

        char[] digits = String.valueOf(num).toCharArray();
        int n = digits.length;

        int[] greaters = new int[n]; // from back to front, find the greaters num
        int[] indexes = new int[n]; // from back to front, record the index of greater num 

        greaters[n-1] = digits[n-1];
        indexes[n-1] = n-1;
        for (int i = n-2; i >= 0; --i) {
            greaters[i] = Math.max(digits[i], greaters[i+1]);

            /*
            num: 1993
            greaters:  9993
            indexes: 1223
            we should swap 1 and the second 9, not the first 9
            */
            if (digits[i] == greaters[i]) {
                if (greaters[i] == greaters[i+1]) {
                    indexes[i] = indexes[i+1];
                } else {
                    indexes[i] = i;
                }
            } else {
                indexes[i] = indexes[i+1];
            }
        }

        // from front to end, find the first one which need to swap
        for (int i = 0; i < n; ++i) {
            if (digits[i] != greaters[i]) {
                swap(digits, i, indexes[i]);
                break;
            }
        }
        return Integer.valueOf(new String(digits));
    }

    private void swap(char[] digits, int i, int j) {
        char temp = digits[i];
        digits[i] = digits[j];
        digits[j] = temp;
    }
}

/*
num: 2736
maxs: 7766
indexes:1133
7236

num:9973
max:9973
indexes: 0123

num: 98368
max:     98888
index:   01444


*/

/*
class Solution {
    public int maximumSwap(int num) {
        if(num == 0) {
            return num;
        }
        char[] digits = Integer.toString(num).toCharArray();
        for(int i = 0; i < digits.length; ++i) {
            int maxIndex = i;
            for(int j = i+1; j < digits.length; ++j) {
                if(digits[j] >= digits[maxIndex]) {
                    maxIndex = j;
                }
            }
            if(digits[maxIndex] != digits[i]) {
                swap(digits, maxIndex, i);
                break;
            }
        }
        return Integer.parseInt(new String(digits));
    }

    private void swap(char[] charArray, int i, int j) {
        char ch = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = ch;
    }
}
*/

