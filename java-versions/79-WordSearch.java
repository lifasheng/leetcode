/*
Medium

Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

Example 1:
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true

Example 2:
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true

Example 3:
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false

Constraints:
m == board.length
n = board[i].length
1 <= m, n <= 6
1 <= word.length <= 15
board and word consists of only lowercase and uppercase English letters.
 
Follow up: Could you use search pruning to make your solution faster with a larger board?
*/


class Solution {
    /*
    Time Complexity: O(N * 3^L)
    where NN is the number of cells in the board and LL is the length of the word to be matched.
   
    For the backtracking function, initially we could have at most 4 directions to explore,
    but further the choices are reduced into 3 (since we won't go back to where we come from).
    As a result, the execution trace after the first step could be visualized as a 3-ary tree,
    each of the branches represent a potential exploration in the corresponding direction.
    Therefore, in the worst case, the total number of invocation would be the number of nodes in a full 3-nary tree,
    which is about 3^L.
    */
    
    public boolean exist(char[][] board, String word) {
        return exist_backtrack(board, word);
    }
    
    private static final int[][] DIRECTIONS = {
        {-1, 0},
        {0, 1},
        {1, 0},
        {0, -1}
    };
    
    private char[][] board;
    private int ROWS;
    private int COLS;
    private boolean exist_backtrack(char[][] board, String word) {
        this.board = board;
        this.ROWS = board.length;
        this.COLS = board[0].length;
        boolean[][] visited = new boolean[ROWS][COLS];
        
        // 剪枝可有可无，但是加上之后可以加快很多速度
        if (!pruning(word)) return false;
        
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                if (board[i][j] == word.charAt(0)) {
                    if(dfs(word, 0, i, j, visited)) return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(String word, int start,
                        int i, int j, 
                        boolean[][] visited) {

        if (start == word.length() - 1) {
            return true;
        }
        
        visited[i][j] = true;
        
        for (int[] direction : DIRECTIONS) {
            int newI = i + direction[0];
            int newJ = j + direction[1];
            
            if (newI < 0 || newI >= ROWS || newJ < 0 || newJ >= COLS 
            || board[newI][newJ] != word.charAt(start + 1) 
            || visited[newI][newJ]) continue;
            
            if (dfs(word, start + 1, newI, newJ, visited)) return true;
        }
        
        visited[i][j] = false;
                
        return false;
    }
    
    // 加了这个剪枝函数之后就可以达到faster than 98%，否则只是faster than 5%
    private boolean pruning(String word) {
        // prune 1:
        // if word can't fit in board
        if (word.length() > ROWS * COLS) return false;

        // prune 2:
        // if word has more of a letter than is in board
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                char c = board[i][j];
                charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            }
        }

        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) - 1);
            if (charCount.get(c) < 0) return false;
        }
        return true;
    }
}

