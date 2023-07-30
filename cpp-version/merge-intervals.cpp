/**
 * Definition for an interval.
 * struct Interval {
 *     int start;
 *     int end;
 *     Interval() : start(0), end(0) {}
 *     Interval(int s, int e) : start(s), end(e) {}
 * };
 */
class Solution {
public:
    vector<Interval> merge(vector<Interval> &intervals) {
        vector<Interval> result;
        for(int i=0;i<intervals.size(); ++i) {
            insert(result, intervals[i]);
        }
        return result;
    }
    vector<Interval> insert(vector<Interval> &intervals, Interval newInterval) {
        vector<Interval>::iterator iter = intervals.begin();
        while(iter != intervals.end()) {
            if (newInterval.end < iter->start) {
                intervals.insert(iter, newInterval);
                return intervals;
            }
            else if (iter->end < newInterval.start) {
                ++iter;
            }
            else {
                newInterval.start = min(newInterval.start, iter->start);
                newInterval.end   = max(newInterval.end, iter->end);
                iter = intervals.erase(iter); // erase will return iterator of next item before iter is removed.
            }
        }
        intervals.insert(intervals.end(), newInterval);
        return intervals;
    }
};
