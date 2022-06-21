/*
You are given two lists of closed intervals, firstList and secondList, where firstList[i] = [starti, endi] and secondList[j] = [startj, endj]. Each list of intervals is pairwise disjoint and in sorted order.

Return the intersection of these two interval lists.

A closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.

The intersection of two closed intervals is a set of real numbers that are either empty or represented as a closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].

Example 1:
Input: firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]

Example 2:
Input: firstList = [[1,3],[5,9]], secondList = []
Output: []
*/


class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> list = new ArrayList<>();
        
        int len1 = firstList.length;
        int len2 = secondList.length;
        
        int i = 0;
        int j = 0;
        while (i < len1 && j < len2) {
            int[] pair1 = firstList[i];
            int[] pair2 = secondList[j];
            int low = Math.max(pair1[0], pair2[0]);
            int high = Math.min(pair1[1], pair2[1]);
            if (low <= high) {
                list.add(new int[]{low, high});
            }
            
           if (pair1[1] < pair2[1]) {
                i ++;
            } else {
                j ++;
            }
        }
        
        int[][] result = new int[list.size()][];
        for (i = 0; i < list.size(); ++i) {
            result[i] = list.get(i);
        }
        return result;
    }
}


