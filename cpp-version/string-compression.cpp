/*
443. String Compression
Easy

Given an array of characters, compress it in-place.

The length after compression must always be smaller than or equal to the original array.

Every element of the array should be a character (not int) of length 1.

After you are done modifying the input array in-place, return the new length of the array.

 
Follow up:
Could you solve it using only O(1) extra space?

 
Example 1:

Input:
["a","a","b","b","c","c","c"]

Output:
Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]

Explanation:
"aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
 

Example 2:

Input:
["a"]

Output:
Return 1, and the first 1 characters of the input array should be: ["a"]

Explanation:
Nothing is replaced.
*/


class Solution {
private:
    // please note cur is a reference
    void fillRepeatNumbers(vector<char> &chars, int prev, int next, int &cur) {
        int repeatNumber = next - prev;
        chars[++cur] = chars[prev]; // fill the character itself
        
        // fill the repeat number
        if (repeatNumber > 1) {
            std::string s = std::to_string(repeatNumber);
            for (auto c : s) {
                chars[++cur] = c;
            }
        }
    }
public:
    int compress(vector<char>& chars) {
        int n = chars.size();
        if (n<=1) return n;
        
        int cur = -1; // index for compressed array
        int prev = 0; // previous char in chars
        int next = 1; // next char in chars
        while(next < n) {
            if (chars[next] != chars[prev]) {
                fillRepeatNumbers(chars, prev, next, cur);
                prev = next;
                ++next;
            } else {
                ++next;
            }
        }
        
        // fill for last character
        fillRepeatNumbers(chars, prev, next, cur);
        
        return cur+1;
    }
};
