/*
Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Example 1:
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.

Example 2:
Input: intervals = [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.

Example 3:
Input: intervals = [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.

Constraints:

1 <= intervals.length <= 105
intervals[i].length == 2
-5 * 104 <= starti < endi <= 5 * 104
*/



class Solution {
    /*
    https://labuladong.github.io/algo/di-er-zhan-a01c6/tan-xin-le-9bedf/tan-xin-su-c41e8/
    这个问题在生活中的应用广泛，比如你今天有好几个活动，每个活动都可以用区间 [start, end] 表示开始和结束的时间，
    请问你今天最多能参加几个活动呢？显然你一个人不能同时参加两个活动，所以说这个问题就是求这些时间区间的最大不相交子集。

    思路：
    不能按照最早开始，或者最长的，或者最短的，都有反例可以证明这些方法不对。

    1    4
       3   5
         4 5

    这个例子应该删除[3, 5], 这样就会有两个不重叠的【1，4】，【4，5】


    正确的思路是按照结束时间end排序，每次选end最小的，再删除和该区间重叠的其他区间。重复该步骤，留下的都是合理的区间。

    想法就是贪心算法，我们尽量选结束时间早的活动，这样就能尽量多参加一些活动。
    */
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b)-> a[1] - b[1]);


        int n = intervals.length;
        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for (int i = 1; i < n; ++i) {
            int[] cur = intervals[i];
            int[] last = res.get(res.size() - 1);
            if (cur[0] >= last[1]) {
                res.add(cur);
            }
        }

        return n - res.size();
    }
}


