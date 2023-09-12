/*
Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

Example 1:
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

Example 2:
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.

Constraints:
1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 104
*/

class Solution {
    
    // https://labuladong.github.io/algo/di-san-zha-24031/jing-dian--a94a0/yi-ge-fang-93124/
    public int[][] merge(int[][] intervals) {
        // 按区间的 start 升序排列
        Arrays.sort(intervals, (a, b)-> a[0] - b[0]);

        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; ++i) {
            int[] cur = intervals[i];
            // res 中最后一个元素的引用
            int[] last = res.get(res.size() - 1);

            if (cur[0] <= last[1]) {
                last[1] = Math.max(cur[1], last[1]); // 有overlap，合并
            } else {
                res.add(cur); // 没有overlap，直接加入
            }
        }
        return res.toArray(new int[res.size()][2]);
    }
}


