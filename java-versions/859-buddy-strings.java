/*
859. Buddy Strings

Given two strings A and B of lowercase letters, return true if you can swap two letters in A so the result is equal to B, otherwise, return false.

Swapping letters is defined as taking two indices i and j (0-indexed) such that i != j and swapping the characters at A[i] and A[j]. For example, swapping at indices 0 and 2 in "abcd" results in "cbad".

 

Example 1:

Input: A = "ab", B = "ba"
Output: true
Explanation: You can swap A[0] = 'a' and A[1] = 'b' to get "ba", which is equal to B.
Example 2:

Input: A = "ab", B = "ab"
Output: false
Explanation: The only letters you can swap are A[0] = 'a' and A[1] = 'b', which results in "ba" != B.
Example 3:

Input: A = "aa", B = "aa"
Output: true
Explanation: You can swap A[0] = 'a' and A[1] = 'a' to get "aa", which is equal to B.
Example 4:

Input: A = "aaaaaaabc", B = "aaaaaaacb"
Output: true
Example 5:

Input: A = "", B = "aa"
Output: false
 

Constraints:

0 <= A.length <= 20000
0 <= B.length <= 20000
A and B consist of lowercase letters.
*/

/*
1. if A.length() != B.length(), return false
2. if A == B, then we need to check if there are duplicate chars, if yes, return true, else return false;
3. A != B, we scan from start to end, when we find first different char at index i, A[i] != B[i], we find j in A which satisfy A[j] == B[i];

if j not found, then it means A and B contains two different chars, like A contains x, B contains y, no matter how we swap, it will not be equal.

if j found, then it means, if we swap A[i], A[j], A should be equal to B;
*/
class Solution {
    private  boolean containsDuplicateChar(String A) {
        Set<Character> s = new HashSet<>();
        for (int i=0; i<A.length(); i++) {
            if (s.contains(A.charAt(i))) {
                return true;
            } else {
                s.add(A.charAt(i));
            }
        }
        return false;
    }

    public  boolean buddyStrings(String A, String B) {
        if (A.length() != B.length()) {
            return false;
        }

        if (A.equals(B)) {
            return containsDuplicateChar(A);
        }

        for (int i=0; i<A.length(); i++) {
            if (A.charAt(i) != B.charAt(i)) {
                int j = A.indexOf(B.charAt(i));
                if (j == -1) {
                    return false;
                } else {
                    char arr[] = A.toCharArray();
                    char temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    return B.equals(new String(arr));
                }
            }
        }
        return false;
    }
}


