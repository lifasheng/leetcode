/*
very good!

A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

Trie() Initializes the trie object.
void insert(String word) Inserts the string word into the trie.
boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 
Example 1:

Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]

Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
 
Constraints:
1 <= word.length, prefix.length <= 2000
word and prefix consist only of lowercase English letters.
At most 3 * 104 calls in total will be made to insert, search, and startsWith.
*/


class Trie {

    private TrieNode root;

    class TrieNode {
        private static final int N = 26;
        boolean isWord;
        TrieNode[] children;

        public TrieNode() {
            this.children = new TrieNode[N];
            this.isWord = false;
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


    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
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

    private TrieNode searchPrefix(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char c = prefix.charAt(i);
            if (p.containsKey(c)) {
                p = p.get(c);
            } else {
                return null;
            }
        }
        return p;
    }
    
    public boolean search(String word) {
        TrieNode p = searchPrefix(word);
        return p != null && p.isWord();
    }
    
    public boolean startsWith(String prefix) {
        TrieNode p = searchPrefix(prefix);
        return p != null;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

