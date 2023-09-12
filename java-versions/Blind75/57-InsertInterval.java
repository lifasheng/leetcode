/*
very very good!

You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval newInterval = [start, end] that represents the start and end of another interval.

Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return intervals after the insertion.

Example 1:
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

Example 2:
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 

Constraints:

0 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 105
intervals is sorted by starti in ascending order.
newInterval.length == 2
0 <= start <= end <= 105
*/

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        return insert_mergeDirectly(intervals, newInterval);   
    }

    // 方法1: 利用#56 mergeInterval的思路，先把newInterval加到intervals中，然后merge就行。
    // 思路很清晰， 时间复杂度是O(n)
    private int[][] insert_mergeInterval(int[][] intervals, int[] newInterval) {        
        List<int[]> newIntervals = new ArrayList<>();
        boolean added = false;
        for (int[] interval : intervals) {
            if (!added && interval[0] > newInterval[0]) {
                newIntervals.add(newInterval);
                added = true;
            } 
            newIntervals.add(interval);
        }

        if (!added) {
            newIntervals.add(newInterval);
        }

        List<int[]> res = mergeInterval(newIntervals);
        return res.toArray(new int[res.size()][2]);
    }

    private List<int[]> mergeInterval(List<int[]> intervals) {
        List<int[]> res = new ArrayList<>();
        res.add(intervals.get(0));
        for (int i = 1; i < intervals.size(); ++i) {
            int[] cur = intervals.get(i);
            int[] last = res.get(res.size() - 1);

            if (cur[0] > last[1]) {
                res.add(cur);
            } else {
                last[1] = Math.max(cur[1], last[1]);
            }
        }
        return res;
    }

    /*
     方法2: 边遍历边分类，边合并。

    (1) cur 在左边
    cur:        ------
    newInterval         -------

    (2) cur 在右边
    cur:                   ------
    newInterval   -------

    （3） cur和newInterval 有overlap

    cur:             ------
    newInterval   -------


    cur:        ------
    newInterval     -------

    这两种情况都有overlap，合并逻辑是相同的。
    
     */
    private int[][] insert_mergeDirectly(int[][] intervals, int[] newInterval) {
        List<int[]> left = new ArrayList<>();
        List<int[]> right = new ArrayList<>();

        int start = newInterval[0];
        int end = newInterval[1];
        for (int i = 0; i < intervals.length; ++i) {
            int[] cur = intervals[i];
            if (cur[1] < start) { // 左边
                left.add(cur);
            } else if (cur[0] > end) { // 右边
                right.add(cur);
            } else { // 合并
                start = Math.min(start, cur[0]);
                end = Math.max(end, cur[1]);
            }
        }

        List<int[]> res = new ArrayList<>();
        res.addAll(left);
        res.add(new int[]{start, end});
        res.addAll(right);
        return res.toArray(new int[res.size()][2]);
    }
    
}


