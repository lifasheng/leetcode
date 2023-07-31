/*
Medium

You are given an integer array nums. Two players are playing a game with this array: player 1 and player 2.

Player 1 and player 2 take turns, with player 1 starting first. Both players start the game with a score of 0. At each turn, the player takes one of the numbers from either end of the array (i.e., nums[0] or nums[nums.length - 1]) which reduces the size of the array by 1. The player adds the chosen number to their score. The game ends when there are no more elements in the array.

Return true if Player 1 can win the game. If the scores of both players are equal, then player 1 is still the winner, and you should also return true. You may assume that both players are playing optimally.

Example 1:
Input: nums = [1,5,2]
Output: false
Explanation: Initially, player 1 can choose between 1 and 2. 
If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be left with 1 (or 2). 
So, final score of player 1 is 1 + 2 = 3, and player 2 is 5. 
Hence, player 1 will never be the winner and you need to return false.

Example 2:
Input: nums = [1,5,233,7]
Output: true
Explanation: Player 1 first chooses 1. Then player 2 has to choose between 5 and 7. No matter which number player 2 choose, player 1 can choose 233.
Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
 

Constraints:

1 <= nums.length <= 20
0 <= nums[i] <= 107
*/


class Solution {

    public boolean PredictTheWinner(int[] nums) {
        return PredictTheWinner_solution_dp(nums);
    }

    
    private boolean PredictTheWinner_solution1(int[] nums) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int n : nums) {
            dq.addLast(n);
        }

        Map<String, Integer> m = new HashMap<>();
        return score(dq, m) >= 0;
    }

    /*
    * score represents how many score A will get with A first.
    * 思路：
    * 感觉就是一种递归，A和B两个人一起玩，每次都是A先挑。
    * 我们可以定义一个score函数，表示某人先挑的情况下他能比另一个人多得多少分。注意这里不是实际得多少分，而是差值。
    * 另一个需要注意的是，A先挑玩之后，递归函数就变成了B先挑了。
    * 对于A来说，nums[i,j], 
    *   如果他先挑左边，则他得的分的差值是nums[i] - score(i+1, j, nums)
    *   如果他先挑右边，则他得的分的差值是nums[j] - score(i, j-1, nums)
    *   为什么是减号呢，因为后面的部分是另一个人为先的递归，计算出来的结果是另一个人得的分。
    * 只要score(0, n-1, nums) >= 0 就是A 赢了。
    * 
    * 为了方便的递归，用dq来实现左右两边去除一个元素。
    * 为了用备忘录优化，将dq里面的元素转化成一个string。
    */
    int score(ArrayDeque<Integer> dq, Map<String, Integer> m) {
        int size = dq.size();
        if (size == 0) return 0;

        if (size == 1) return dq.pollFirst();
        if (size == 2) {
            return Math.abs(dq.pollFirst() - dq.pollLast());
        }

        StringBuilder sb = new StringBuilder();
        Object[] arr = dq.toArray();
        for (Object o : arr) {
            sb.append(String.valueOf(o) + "#");
        }
        String key = sb.toString();
        if (m.containsKey(key)) {
            return m.get(key);
        }

        ArrayDeque<Integer> dq2 = dq.clone();
        int r1 = dq.pollFirst() - score(dq, m);
        int r2 = dq2.pollLast() - score(dq2, m);
        int result = Math.max(r1, r2);
        m.put(key, result);
        return result;
    }


    /*
    * 思路： 这个更好理解!!!
    * 和上面的score不同，这个score2函数，返回的一个pair，key代表先选的人的得分，value代表后选的人的得分。
    * 对于i==j的情况，只有一个值，先选的人和后选的人得分分别是（nums[i], 0）
    * 对于i+1==j的情况，只有两个值，先选的人和后选的人得分分别是（max(nums[i], nums[j]), min(nums[i],nums[j]))
    * 对于其他的情况，递归就好了。
    * 假设A,B两人，A先选。
    * A的得分是：
    * 如果A先选i, 则得分等于nums[i] + score2(nums, i+1, j).right, 因为score2(nums, i+1, j)对于B来说是先选，对于A来说是后选了。
    * 如果A先选j, 则得分等于nums[j] + score2(nums, i, j-1).right，因为score2(nums, i, j-1)对于B来说是先选，对于A来说是后选了。
    */
    Map<String, Pair<Integer, Integer>> map = new HashMap<>();
    private boolean PredictTheWinner_solution2(int[] nums) {
        Pair<Integer, Integer> pair = score2(nums, 0, nums.length - 1);
        return pair.getKey() - pair.getValue() >= 0;
    }

    Pair<Integer, Integer> score2(int[] nums, int i, int j) {
        if (i == j) {
            return new Pair<>(nums[i], 0);
        }

        if (i + 1 == j) {
            return new Pair<>(Math.max(nums[i], nums[j]), Math.min(nums[i], nums[j]));
        }

        String key = String.format("%d#%d", i, j);
        if (map.containsKey(key)) {
            return map.get(key);
        }

        Pair<Integer, Integer> scoreI1J = score2(nums, i+1, j);
        Pair<Integer, Integer> scoreIJ1 = score2(nums, i, j-1);
        Pair<Integer, Integer> result;
        if (nums[i] + scoreI1J.getValue() > nums[j] + scoreIJ1.getValue()) {
            result = new Pair<>(nums[i] + scoreI1J.getValue(), scoreI1J.getKey());
        } else {
            result = new Pair<>(nums[j] + scoreIJ1.getValue(), scoreIJ1.getKey());
        }
        map.put(key, result);
        return result;
    }

    // 思路和上面score2的递归类似
    private boolean PredictTheWinner_solution_dp(int[] nums) {
        int n = nums.length;
        Pair<Integer, Integer>[][] dp = new Pair[n][n];
        for (int i = 0; i < n; ++i) {
            dp[i][i] = new Pair<>(nums[i], 0);
        }

        for (int i = n-2; i >= 0; --i) {
            for (int j = i+1; j < n; ++j) {
                int left = nums[i] + dp[i+1][j].getValue();
                int right = nums[j] + dp[i][j-1].getValue();
                if (left > right) {
                    dp[i][j] = new Pair<>(left, dp[i+1][j].getKey());
                } else {
                    dp[i][j] = new Pair<>(right, dp[i][j-1].getKey());
                }
            }
        }

        return dp[0][n-1].getKey() - dp[0][n-1].getValue() >= 0;
    }
}


