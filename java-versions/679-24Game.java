/*
Hard

You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

You are restricted with the following rules:

The division operator '/' represents real division, not integer division.
For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
You cannot concatenate numbers together
For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
Return true if you can get such expression that evaluates to 24, and false otherwise.

Example 1:
Input: cards = [4,1,8,7]
Output: true
Explanation: (8-4) * (7-1) = 24

Example 2:
Input: cards = [1,2,1,2]
Output: false
 
Constraints:
cards.length == 4
1 <= cards[i] <= 9
*/

class Solution {
    // very very good!
    // time: O(N^3 * 3^(N-1) * N! * (N-1)!)
    // space: O(N^2)
    public boolean judgePoint24(int[] cards) {
        return judgePoint24_backtrack(cards);
    }
    
    private boolean judgePoint24_backtrack(int[] cards) {
        List<Double> cardList = new ArrayList<>();
        for (int card : cards) {
            cardList.add((double) card);
        }
        
        return backtrack(cardList);
    }
    
    private boolean backtrack(List<Double> cards) {
        if (cards.size() == 1) {
            return Math.abs(cards.get(0) - 24) < 0.1;
        }
        
        for (int i = 0; i < cards.size(); ++i) {
            for (int j = i + 1; j < cards.size(); ++j) {
                List<Double> newList = new ArrayList<>();
                for (int k = 0; k < cards.size(); ++k) {
                    if (k != i && k != j) {
                        newList.add(cards.get(k));
                    }
                }
                
                List<Double> resultOfIAndJ = generateAllPossibleResults(cards.get(i), cards.get(j));
                
                for (double v : resultOfIAndJ) {
                    newList.add(v);
                    
                    if (backtrack(newList)) return true;
                    
                    newList.remove(newList.size() - 1);
                }
            }
        }
        return false;
    }
    
    private List<Double> generateAllPossibleResults(double a, double b) {
        List<Double> result = new ArrayList<>();
        result.add(a + b);
        result.add(a - b);
        result.add(b - a);
        result.add(a * b);
        if (b != 0) {
            result.add(a / b);
        }
        if (a != 0) {
            result.add(b / a);
        }
        return result;
    }
}

