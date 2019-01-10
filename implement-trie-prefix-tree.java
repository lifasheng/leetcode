/*
Implement a trie with insert, search, and startsWith methods.

Example:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
Note:

You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.

*/


class Trie {
    class TrieNode {
        public TrieNode[] childNode;
        public boolean isWord;

        public TrieNode() {
            isWord = false;
            childNode = new TrieNode[26];
        }
    }

    private TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            int idx = c-'a';
            if (cur.childNode[idx] == null) {
                cur.childNode[idx] = new TrieNode();
            }
            cur = cur.childNode[idx];
        }
        cur.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            int idx = c-'a';
            if (cur.childNode[idx] == null) {
                return false;
            }
            cur = cur.childNode[idx];
        }
        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c: prefix.toCharArray()) {
            int idx = c-'a';
            if (cur.childNode[idx] == null) {
                return false;
            }
            cur = cur.childNode[idx];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
