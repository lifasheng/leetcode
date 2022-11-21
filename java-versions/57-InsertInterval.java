/*
Medium
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
        return insert_oneScan(intervals, newInterval);
    }
    
    // 思路很简单，从左到右扫描一遍intervals，和newInterval对比，小的直接加到结果中，再把newIntervl加到结果中。
    // 这里需要考虑加的时候是否有overlap
    public int[][] insert_oneScan(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        int i = 0;
        
        // 先把小的加入结果
        while (i < n && intervals[i][0] <= newInterval[0]) {
            result.add(intervals[i]);
            ++ i;
        }
        
        // 加入newInterval
        if (result.isEmpty() || newInterval[0] > result.get(result.size() - 1)[1]) {
            result.add(newInterval);
        } else if (newInterval[1] > result.get(result.size() - 1)[1]) {
            result.get(result.size() - 1)[1] = newInterval[1];
        }
        
        // 加入剩下的
        while (i < n) {
            // 这里为什么可以和newInterval[1]， 而不是result.get(result.size() - 1)[1]呢？
            // 因为原始的intervals已经保证了intervals[i][0] > intervals[i-1][1]，就是没有overlap
            if (intervals[i][0] > newInterval[1]) {
                result.add(intervals[i]);
            } else {
                if (intervals[i][1] > newInterval[1]) {
                    result.get(result.size() - 1)[1] = intervals[i][1];
                }
            }
            
            ++ i;
        }
        
        return result.toArray(new int[result.size()][2]);
        
    }
}

