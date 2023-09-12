/*
very good!

Given an array intervals where intervals[i] = [li, ri] represent the interval [li, ri), remove all intervals that are covered by another interval in the list.
The interval [a, b) is covered by the interval [c, d) if and only if c <= a and b <= d.
Return the number of remaining intervals.

Example 1:
Input: intervals = [[1,4],[3,6],[2,8]]
Output: 2
Explanation: Interval [3,6] is covered by [2,8], therefore it is removed.

Example 2:
Input: intervals = [[1,4],[2,3]]
Output: 1

Constraints:
1 <= intervals.length <= 1000
intervals[i].length == 2
0 <= li < ri <= 105
All the given intervals are unique.
*/


class Solution {

    /*
     思路：
     先按照起点升序排序，如果起点相同，则按照终点降序排序。
     这里为了处理以下起点相同的情况。
     -------
     ----

     按照起点排序之后，会出现以下几种情况

    （1）： 覆盖的情况
     -----------
       ------

    （2）： overlap的情况，这种情况不能remove

     ---------
          --------
    （3）： 完全分开的情况
     -------
              ----------  

    我们只需要统计第一种情况的个数就是被remove的interval的个数。
    */
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });


        int count = 0;
        int n = intervals.length;
        int right = intervals[0][1];
        for (int i = 1; i < n; ++i) {
            if (intervals[i][1] <= right) {
                ++ count;
            } else {
                right = intervals[i][1]; 
            }
        }
        return n - count;
    }
}


