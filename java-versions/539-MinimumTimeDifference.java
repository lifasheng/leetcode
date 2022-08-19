/*
Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any two time-points in the list.

Example 1:
Input: timePoints = ["23:59","00:00"]
Output: 1

Example 2:
Input: timePoints = ["00:00","23:59","00:00"]
Output: 0

Constraints:
2 <= timePoints.length <= 2 * 104
timePoints[i] is in the format "HH:MM".
*/

class Solution {
    public int findMinDifference(List<String> timePoints) {
        List<Integer> times = timePoints.stream().map(t -> timeStrToInt(t)).collect(Collectors.toList());
        Collections.sort(times, (t1, t2) -> {return t1 - t2;});
        
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < times.size(); ++i) {
            if (i == 0) {
                int diff = timeDiff(times.get(0), times.get(times.size() - 1));
                minDiff = Math.min(minDiff, diff);
            } else {
                int diff = timeDiff(times.get(i - 1), times.get(i));
                minDiff = Math.min(minDiff, diff);
            }
        }
        return minDiff;
    }
    
    private int timeStrToInt(String s) {
        String[] hm = s.split(":");
        int h = Integer.valueOf(hm[0]);
        int m = Integer.valueOf(hm[1]);
        return h * 60 + m;
    }
    
    private int timeDiff(int t1, int t2) {
        int t3 = 24 * 60;
        return Math.min(t2 - t1, t1 + t3 - t2);
    }
}

