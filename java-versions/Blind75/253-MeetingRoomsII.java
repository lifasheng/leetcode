/*
very good!

Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.

Example 1:
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2

Example 2:
Input: intervals = [[7,10],[2,4]]
Output: 1
 
Constraints:
1 <= intervals.length <= 104
0 <= starti < endi <= 106
*/

class Solution {
    /*
    思路： 先按照开始时间排序，然后用贪心法 greedy 来将每个会议分组。
    怎么分组呢？ 找比当前会议早结束的会议，将他们放在一组。
    所以就是要在前面的会议中找到结束时间比当前的开始时间小或者相等的。

    用priorityQueue显然会比较方便。
    */
    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        Queue<Integer> priorityQueue = new PriorityQueue<>();
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) return a[1]-b[1];
            else return a[0] - b[0];
        });

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            if (priorityQueue.isEmpty()) {
                priorityQueue.offer(end);
            } else {
                if (priorityQueue.peek() <= start) {
                    priorityQueue.poll();
                    priorityQueue.offer(end);
                } else {
                    priorityQueue.offer(end);
                }
            }
        }
        return priorityQueue.size();
    }

}

