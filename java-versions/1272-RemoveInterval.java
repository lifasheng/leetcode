/*
Medium
A set of real numbers can be represented as the union of several disjoint intervals, where each interval is in the form [a, b). A real number x is in the set if one of its intervals [a, b) contains x (i.e. a <= x < b).

You are given a sorted list of disjoint intervals intervals representing a set of real numbers as described above, where intervals[i] = [ai, bi] represents the interval [ai, bi). You are also given another interval toBeRemoved.

Return the set of real numbers with the interval toBeRemoved removed from intervals. In other words, return the set of real numbers such that every x in the set is in intervals but not in toBeRemoved. Your answer should be a sorted list of disjoint intervals as described above.

Example 1:
Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
Output: [[0,1],[6,7]]

Example 2:
Input: intervals = [[0,5]], toBeRemoved = [2,3]
Output: [[0,2],[3,5]]

Example 3:
Input: intervals = [[-5,-4],[-3,-2],[1,2],[3,5],[8,9]], toBeRemoved = [-1,4]
Output: [[-5,-4],[-3,-2],[4,5],[8,9]]

Constraints:
1 <= intervals.length <= 104
-109 <= ai < bi <= 109
*/

// class Solution {
//     public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
//         List<List<Integer>> result = new ArrayList<>();
//         for (int i = 0; i < intervals.length; ++i) {
//             int[] curInterval = intervals[i];
//             if (curInterval[1] <= toBeRemoved[0] || curInterval[0] >= toBeRemoved[1]) { // no overlap
//                 result.add(Arrays.asList(curInterval[0], curInterval[1]));
//             } else { // 相交的四种情况
//                 if (curInterval[0] >= toBeRemoved[0] && curInterval[1] <= toBeRemoved[1]) { // 当前interval在toBeRemoved的里面
//                     // do nothing
//                 } else if (curInterval[0] < toBeRemoved[0] && toBeRemoved[1] < curInterval[1]) { // toBeRemoved在当前interval的里面
//                     result.add(Arrays.asList(curInterval[0], toBeRemoved[0]));
//                     result.add(Arrays.asList(toBeRemoved[1], curInterval[1]));
//                 } else if (curInterval[0] < toBeRemoved[0] && curInterval[1] >= toBeRemoved[0]) { // 左侧overlap
//                     result.add(Arrays.asList(curInterval[0], toBeRemoved[0]));
//                 } else if (curInterval[0] < toBeRemoved[1] && curInterval[1] >= toBeRemoved[1]) { // 右侧overlap
//                     result.add(Arrays.asList(toBeRemoved[1], curInterval[1]));
//                 } 
//             }
//         }
//         return result;
//     }
// }


// 简化版，主要是相交时可以简化
// 注意等号的处理，其实不用判断边界相等的情况，因为会被overlap的逻辑来处理。
class Solution {
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < intervals.length; ++i) {
            int[] curInterval = intervals[i];
            if (curInterval[1] < toBeRemoved[0] || curInterval[0] > toBeRemoved[1]) { // no overlap
                result.add(Arrays.asList(curInterval[0], curInterval[1]));
            } else { // overlap
                if (curInterval[0] < toBeRemoved[0]) {
                    result.add(Arrays.asList(curInterval[0], toBeRemoved[0]));
                }
            
                if (toBeRemoved[1] < curInterval[1]) {
                    result.add(Arrays.asList(toBeRemoved[1], curInterval[1]));
                } 
            }
        }
        return result;
    }
}

