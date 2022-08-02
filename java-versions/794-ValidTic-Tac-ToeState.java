/*
Given a Tic-Tac-Toe board as a string array board, return true if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game.

The board is a 3 x 3 array that consists of characters ' ', 'X', and 'O'. The ' ' character represents an empty square.

Here are the rules of Tic-Tac-Toe:
Players take turns placing characters into empty squares ' '.
The first player always places 'X' characters, while the second player always places 'O' characters.
'X' and 'O' characters are always placed into empty squares, never filled ones.
The game ends when there are three of the same (non-empty) character filling any row, column, or diagonal.
The game also ends if all squares are non-empty.
No more moves can be played if the game is over.

Example 1:
Input: board = ["O  ","   ","   "]
Output: false
Explanation: The first player always plays "X".

Example 2:
Input: board = ["XOX"," X ","   "]
Output: false
Explanation: Players take turns making moves.

Example 3:
Input: board = ["XOX","O O","XOX"]
Output: true
 
Constraints:
board.length == 3
board[i].length == 3
board[i][j] is either 'X', 'O', or ' '.
*/

class Solution {
    public boolean validTicTacToe(String[] board) {
        int countX = count(board, 'X');
        int countO = count(board, 'O');
        
        // X first, and they play one by one
        if (countX < countO) return false;
        if ((countX - countO) > 1) return false;
        
        
        // countX should alwasy be = countO or = countO + 1
        // if X win, then countX should be > countO, not equal
        if (countX >= 3 && isWin(board, 'X') && (countX == countO)) return false;
        // if O win, then countO should be = countX
        if (countO >= 3 && isWin(board, 'O') && (countX > countO)) return false;
        
        return true;
    }
    
    private int count(String[] board, char t) {
        int count = 0;
        for (String s : board) {
            for (int i = 0; i < 3; ++i) {
                if (s.charAt(i) == t) ++ count;
            }
        }
        return count;
    }
    
    private boolean isWin(String[] board, char t) {
        // 3 rows
        for (int i = 0; i < 3; ++i) {
            boolean allEquals = true;
            for (int j = 0; j < 3; ++j) {
                if (board[i].charAt(j) != t) {
                    allEquals = false;
                    break;
                }
            }
            if (allEquals) return true;
        }
        
        // 3 cols
        for (int j = 0; j < 3; ++j) {
            boolean allEquals = true;
            for (int i = 0; i < 3; ++i) {
                if (board[i].charAt(j) != t) {
                    allEquals = false;
                    break;
                }
            }
            if (allEquals) return true;
        }
        
        // two diagnoals
        boolean allEquals = true;
        for (int i = 0; i < 3; ++i) {
            if (board[i].charAt(i) != t) {
                allEquals = false;
                break;
            }
        }
        if (allEquals) return true;
        
        allEquals = true;
        for (int i = 0; i < 3; ++i) {
            if (board[i].charAt(2-i) != t) {
                allEquals = false;
                break;
            }
        }
        if (allEquals) return true;
        
        return false;
    }
}

