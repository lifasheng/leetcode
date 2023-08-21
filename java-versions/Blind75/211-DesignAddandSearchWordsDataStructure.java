/*
very very good!

Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the WordDictionary class:

WordDictionary() Initializes the object.
void addWord(word) Adds word to the data structure, it can be matched later.
bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 

Example:

Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
 

Constraints:

1 <= word.length <= 25
word in addWord consists of lowercase English letters.
word in search consist of '.' or lowercase English letters.
There will be at most 2 dots in word for search queries.
At most 104 calls will be made to addWord and search.
*/


class WordDictionary {

    class TrieNode {
        boolean isWord;
        TrieNode[] children;

        public TrieNode() {
            this.isWord = false;
            this.children = new TrieNode[26];
        }

        public boolean containsKey(char c) {
            return children[c - 'a'] != null;
        }

        public TrieNode get(char c) {
            return children[c - 'a'];
        }

        public void put(char c, TrieNode node) {
            children[c - 'a'] = node;
        }

        public void setIsWord() {
            isWord = true;
        }

        public boolean isWord() {
            return isWord;
        }
    }

    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (!p.containsKey(c)) {
                p.put(c, new TrieNode());
            }
            p = p.get(c);
        }
        p.setIsWord();
    }
    
    public boolean search(String word) {
        return search(word, root);
    }

    private boolean search(String word, TrieNode node) {
        TrieNode p = node;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (c != '.') {
                p = p.get(c);
                if (p == null) {
                    return false;
                }
            } else {
                for (int j = 0; j < 26; ++j) {
                    c = (char)('a' + j);

                    // 很巧妙， 剪枝 + 递归
                    if (p.containsKey(c) && search(word.substring(i+1), p.get(c))) {
                        return true;
                    }
                }

                return false;
            }
        }
        return p.isWord();
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */


