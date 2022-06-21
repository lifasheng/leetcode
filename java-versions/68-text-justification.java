/*
68. Text Justification
Hard
Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

Note:

A word is defined as a character sequence consisting of non-space characters only.
Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
The input array words contains at least one word.
 

Example 1:

Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
Example 2:

Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
Explanation: Note that the last line is "shall be    " instead of "shall     be", because the last line must be left-justified instead of fully-justified.
Note that the second line is also left-justified becase it contains only one word.
Example 3:

Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]
 

Constraints:

1 <= words.length <= 300
1 <= words[i].length <= 20
words[i] consists of only English letters and symbols.
1 <= maxWidth <= 100
words[i].length <= maxWidth

*/


// from leetcode discussion area.

class Solution {
    // Adds each word to a buffer. If it doesn't fit, flush the buffer then continue;
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> currWords = new ArrayList<>(), done = new ArrayList<>();
        int currLen = 0;
        
        for (int i = 0 ; i < words.length ; i++) {
            if (currLen + currWords.size() + words[i].length() <= maxWidth) {
                currLen += words[i].length();
                currWords.add(words[i]);
            } else {
                StringBuilder sb = new StringBuilder();
                
                if (currWords.size() == 1) {
                    sb.append(currWords.get(0));
                    while (sb.length() != maxWidth) sb.append(" ");
                } else {
                    int spaces = maxWidth - currLen;
                    int between = spaces/(currWords.size()-1);
                    int remainder = spaces%(currWords.size()-1);
                
                    for (int j = 0 ; j < currWords.size() ; j++) {
                        if (sb.length() != 0) {
                            for (int k = 0 ; k < between ; k++) sb.append(" ");
                            if (remainder-- > 0) sb.append(" ");
                        }
                        sb.append(currWords.get(j));
                    }
                }
                
                done.add(sb.toString());
                currWords.clear();
                currLen = 0;
                i--;
            }
        }
        StringBuilder sb = new StringBuilder(); 
        for (String s : currWords) {
            if (sb.length() != 0) sb.append(" ");
            sb.append(s);
        }
        while (sb.length() != maxWidth) sb.append(" ");
        done.add(sb.toString());
        
        return done;
    }
}


