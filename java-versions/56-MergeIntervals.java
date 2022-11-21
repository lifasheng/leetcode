/*
Medium

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
    // test case: [[1,4],[2,3]]
    // O(NlogN)
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        
        Arrays.sort(intervals, (a1, a2) -> {
            if (a1[0] == a2[0]) return a1[1] - a2[1];
            else return a1[0] - a2[0];
        });
        
        LinkedList<int[]> list = new LinkedList<>();
        list.add(intervals[0]);
        for (int i = 1; i < n; ++i) {
            int[] preInterval = list.getLast();
            int[] curInterval = intervals[i];
            if (curInterval[0] <= preInterval[1]) {
                //mergeInterval in place
                preInterval[1] = Math.max(preInterval[1], curInterval[1]);
            } else {
                list.add(curInterval);
            }
        }
        int[][] result = new int[list.size()][2];
        for (int i = 0; i < list.size(); ++i) {
            result[i] = list.get(i);
        }
        return result;
    }
}



class Solution {
    // 先排序， 再扫描一遍即可
    // result.get(result.size() - 1) 对LinkedList和ArrayList都适用。
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            return Integer.compare(a[0], b[0]);
        });
        
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; ++i) {
            if (intervals[i][0] <= result.get(result.size() - 1)[1]) {
                if (intervals[i][1] > result.get(result.size() - 1)[1]) {
                    result.get(result.size() - 1)[1] = intervals[i][1];
                }
            } else {
                result.add(intervals[i]);
            }
        }
        
        return result.toArray(new int[0][0]);
    }
}

