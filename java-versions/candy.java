/*
135. Candy
Hard
There are N children standing in a line. Each child is assigned a rating value.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
What is the minimum candies you must give?

Example 1:

Input: [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
             The third child gets 1 candy because it satisfies the above two conditions.
*/


// [1,3,2,2,1] => 7
// [1,0,2] => 5
// [1,2,2] => 4
// [1,2,87,87,87,2,1] => 13
// [1,6,10,8,7,3,2] => 18
class Solution {
    private int solve(int[] ratings, int[] candies, int i) {
        if (candies[i] == 0) {
            candies[i] = 1;
            if (i>0 && ratings[i] > ratings[i-1]) {
                candies[i] = Math.max(candies[i], solve(ratings, candies, i-1)+1);
            }
            if (i<ratings.length-1 && ratings[i] > ratings[i+1]) {
                candies[i] = Math.max(candies[i], solve(ratings, candies, i+1)+1);
            }
        }    
        return candies[i];
    }
    
    // 递归+备忘录法 Time: O(N), Space: O(N)
    public int candy1(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        int sum = 0;
        for(int i=0; i<n; ++i) {
            sum += solve(ratings, candies, i);
        }
        return sum;
    }
    
    // Time: O(N), Space: O(N)
    // 先初始化每个人为1， 
    // 然后，左右各扫描一遍， 第一遍，从左到右，如果rating增加，则加一，第二遍，从右往左，如果rating增加，则加一，并且和已有的candy比较，取其大者。
    public int candy2(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        for(int i=1; i<n; ++i) {
            if (ratings[i] > ratings[i-1]) {
                candies[i] = candies[i-1]+1;
            }
        }
        
        int sum = candies[n-1];
        for(int i=n-2; i>=0; --i) {
            if (ratings[i] > ratings[i+1]) {
                candies[i] = Math.max(candies[i], candies[i+1]+1);
            }
            sum += candies[i];
        }
        return sum;
    }
    
    public int candy(int[] ratings) {
        return candy2(ratings);
    }
}

