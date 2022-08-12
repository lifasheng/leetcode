/*
Solve a given equation and return the value of 'x' in the form of a string "x=#value". The equation contains only '+', '-' operation, the variable 'x' and its coefficient. You should return "No solution" if there is no solution for the equation, or "Infinite solutions" if there are infinite solutions for the equation.

If there is exactly one solution for the equation, we ensure that the value of 'x' is an integer.

Example 1:
Input: equation = "x+5-3+x=6+x-2"
Output: "x=2"

Example 2:
Input: equation = "x=x"
Output: "Infinite solutions"

Example 3:
Input: equation = "2x=x"
Output: "x=0"
 
Constraints:
3 <= equation.length <= 1000
equation has exactly one '='.
equation consists of integers with an absolute value in the range [0, 100] without any leading zeros, and the variable 'x'.
*/

class Solution {
    public String solveEquation(String equation) {
        
        int eqIndex = equation.indexOf("=");
        String left = equation.substring(0, eqIndex);
        String right = equation.substring(eqIndex + 1);
        
        // add + at first to make sure every number has a sign
        if (left.charAt(0) != '+' && left.charAt(0) != '-') {
            left = "+" + left;
        }
        if (right.charAt(0) != '+' && right.charAt(0) != '-') {
            right = "+" + right;
        }

        int leftSum = 0, rightSum = 0;
        for (String s : splitEquation(left)) {
            if (s.contains("x")) {
                if (s.equals("+x")) {
                    leftSum += 1;
                } else if (s.equals("-x")) {
                    leftSum -= 1;
                } else {
                    leftSum += Integer.valueOf(s.substring(0, s.length() - 1)); // remove x, get its coefficient
                }
            } else {
                s = (s.charAt(0) == '+' ? "-" : "+") + s.substring(1); // change sign because we move all number to right
                rightSum += Integer.valueOf(s);
            }
        }
        
        for (String s : splitEquation(right)) {
            if (s.contains("x")) {
                s = (s.charAt(0) == '+' ? "-" : "+") + s.substring(1); // change sign because we move all x to left
                
                if (s.equals("+x")) {
                    leftSum += 1;
                } else if (s.equals("-x")) {
                    leftSum -= 1;
                } else {
                    leftSum += Integer.valueOf(s.substring(0, s.length() - 1)); // remove x, get its coefficient
                }
            } else {
                rightSum += Integer.valueOf(s);
            }
        }
        
        if (leftSum == 0 && rightSum != 0) return "No solution";
        if (leftSum == 0 && rightSum == 0) return "Infinite solutions";
        return "x=" + (rightSum / leftSum);
        
    }
    
    // "+x+5-3+x" => ["+x", "+5", "-3", "+x"]
    private List<String> splitEquation(String equation) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < equation.length()) {
            char c = equation.charAt(i);
            if (c == '+' || c == '-') {
                if (sb.length() > 0) {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append(c);
            } else {
                sb.append(c);
            }
            ++ i;
        }
        result.add(sb.toString());
        return result;
    }
}


