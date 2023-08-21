/*
very very good!

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
    char[][] board;
    int m;
    int n;
    boolean[][] visited;
    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.m = board.length;
        this.n = board[0].length;
        this.visited = new boolean[m][n];        

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(word, 0, i, j)) return true;
                }
            }
        }

        return false;
    }

    private static int[][] DIRECTIONS = new int[][]{
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    /*
    思路: Backtracking 的方法来解决这道题目。我们需要先从board中找到第一个字符出现的位置，
    然后从这个位置开始往上，下，左，右开始寻找；这里我们要借助一个dfs(x,y, index, board, word)的函数来寻找；
     当index==word.length的时候就找到了对应的字符，返回true; 
     注意边界条件的check，x,y要保证在board内，或者当board[x][y]!=word.charAt(index)的时候都返回false。
    
    Time复杂度: 遍历整个m * n 的board的时间复杂度为m * n,对于每个点都会往上下左右来遍历寻找，(来时的路不能往回走啊) 
    k为word长度，大概要遍历3^k次，所以总的时间复杂度大概在m*n*3^k.
    */
    private boolean dfs(String word, int index, int i, int j) {
        if (index == word.length()) return true;

        if (i < 0 || j < 0 || i >= m || j >= n 
        || word.charAt(index) != board[i][j] || visited[i][j]) {
            return false;
        }

        visited[i][j] = true;

        for (int[] direction : DIRECTIONS) {
            int newI = i + direction[0];
            int newJ = j + direction[1];

            if (dfs(word, index + 1, newI, newJ)) return true;
        }

        visited[i][j] = false;
        return false;
    }
}



