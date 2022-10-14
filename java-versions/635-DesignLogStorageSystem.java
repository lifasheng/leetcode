/*
Medium
You are given several logs, where each log contains a unique ID and timestamp. Timestamp is a string that has the following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. All domains are zero-padded decimal numbers.

Implement the LogSystem class:
LogSystem() Initializes the LogSystem object.
void put(int id, string timestamp) Stores the given log (id, timestamp) in your storage system.
int[] retrieve(string start, string end, string granularity) Returns the IDs of the logs whose timestamps are within the range from start to end inclusive. start and end all have the same format as timestamp, and granularity means how precise the range should be (i.e. to the exact Day, Minute, etc.). For example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", and granularity = "Day" means that we need to find the logs within the inclusive range from Jan. 1st 2017 to Jan. 2nd 2017, and the Hour, Minute, and Second for each log entry can be ignored.
 
Example 1:
Input
["LogSystem", "put", "put", "put", "retrieve", "retrieve"]
[[], [1, "2017:01:01:23:59:59"], [2, "2017:01:01:22:59:59"], [3, "2016:01:01:00:00:00"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour"]]
Output
[null, null, null, null, [3, 2, 1], [2, 1]]

Explanation
LogSystem logSystem = new LogSystem();
logSystem.put(1, "2017:01:01:23:59:59");
logSystem.put(2, "2017:01:01:22:59:59");
logSystem.put(3, "2016:01:01:00:00:00");

// return [3,2,1], because you need to return all logs between 2016 and 2017.
logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year");

// return [2,1], because you need to return all logs between Jan. 1, 2016 01:XX:XX and Jan. 1, 2017 23:XX:XX.
// Log 3 is not returned because Jan. 1, 2016 00:00:00 comes before the start of the range.
logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour");
 
Constraints:
1 <= id <= 500
2000 <= Year <= 2017
1 <= Month <= 12
1 <= Day <= 31
0 <= Hour <= 23
0 <= Minute, Second <= 59
granularity is one of the values ["Year", "Month", "Day", "Hour", "Minute", "Second"].
At most 500 calls will be made to put and retrieve.
*/

// // O(N) get, O(1) put
// class LogSystem {
//     static Map<String, Integer> GRAN = new HashMap<>();
//     static {
//         GRAN.put("Year", 0);
//         GRAN.put("Month", 1);
//         GRAN.put("Day", 2);
//         GRAN.put("Hour", 3);
//         GRAN.put("Minute", 4);
//         GRAN.put("Second", 5);
//     }
//     static final int N = 6; 
//     class TimeStamp {
//         int[] parts = new int[N];
//         public TimeStamp(String timestamp) {
//             String[] arr = timestamp.split(":");
//             for (int i = 0; i < N; ++i) {
//                 parts[i] = Integer.valueOf(arr[i]);
//             }
//         }
        
//         public int compareTo(TimeStamp timestamp, String granularity) {
//             int index = GRAN.get(granularity);
//             for (int i = 0; i <= index; ++i) {
//                 if (parts[i] < timestamp.parts[i]) {
//                     return -1;
//                 } else if (parts[i] > timestamp.parts[i]) {
//                     return 1;
//                 }
//             }
//             return 0;
//         }
//     }

//     private Map<TimeStamp, List<Integer>> map;
//     public LogSystem() {
//         map = new HashMap<>();
//     }
    
//     public void put(int id, String timestamp) {
//         TimeStamp ts = new TimeStamp(timestamp);
//         map.putIfAbsent(ts, new ArrayList<>());
//         map.get(ts).add(id);
//     }
    
//     public List<Integer> retrieve(String start, String end, String granularity) {
//         TimeStamp startTS = new TimeStamp(start);
//         TimeStamp endTS = new TimeStamp(end);
        
//         List<Integer> result = new ArrayList<>();
//         for (TimeStamp timestamp : map.keySet()) {
//             if (timestamp.compareTo(startTS, granularity) >= 0 && timestamp.compareTo(endTS, granularity) <= 0) {
//                 result.addAll(map.get(timestamp));
//             }
//         }
//         return result;
//     }
// }


/*
1. 定义 TimeStamp class
2. compareTo method
3. lowerBound
4. upperBound

O(logN) put, and O(logN) get
*/
class LogSystem {

    private static final Map<String, Integer> GRAN = new HashMap<>();
    private static final String YEAR = "Year";
    private static final String MONTH = "Month";
    private static final String DAY = "Day";
    private static final String HOUR = "Hour";
    private static final String MINUTE = "Minute";
    private static final String SECOND = "Second";
    static {
        GRAN.put(YEAR, 0);
        GRAN.put(MONTH, 1);
        GRAN.put(DAY, 2);
        GRAN.put(HOUR, 3);
        GRAN.put(MINUTE, 4);
        GRAN.put(SECOND, 5);
    }
    static final int N = 6;
    class TimeStamp {
        int[] parts = new int[N];
        public TimeStamp(String timestamp) {
            String[] arr = timestamp.split(":");
            for (int i = 0; i < N; ++i) {
                parts[i] = Integer.valueOf(arr[i]);
            }
        }

        public int compareTo(TimeStamp timestamp, String granularity) {
            int index = GRAN.get(granularity);
            for (int i = 0; i <= index; ++i) {
                if (parts[i] < timestamp.parts[i]) {
                    return -1;
                } else if (parts[i] > timestamp.parts[i]) {
                    return 1;
                }
            }
            return 0;
        }
    }

    private Map<TimeStamp, List<Integer>> map;
    private List<TimeStamp> timestampList;
    public LogSystem() {
        map = new HashMap<>();
        timestampList = new ArrayList<>();
    }

    public void put(int id, String timestamp) {
        TimeStamp ts = new TimeStamp(timestamp);
        map.putIfAbsent(ts, new ArrayList<>());
        map.get(ts).add(id);
        timestampList.add(lowerBound(ts), ts);
    }

    private int lowerBound(TimeStamp target) {
        return lowerBound(target, SECOND);
    }
    private int lowerBound(TimeStamp target, String granularity) {
        int low = 0, high = timestampList.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            int ret = timestampList.get(mid).compareTo(target, granularity);
            if (ret < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int upperBound(TimeStamp target, String granularity) {
        int low = 0, high = timestampList.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            int ret = timestampList.get(mid).compareTo(target, granularity);
            if (ret <= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }


    public List<Integer> retrieve(String start, String end, String granularity) {
        TimeStamp startTS = new TimeStamp(start);
        TimeStamp endTS = new TimeStamp(end);

        List<Integer> result = new ArrayList<>();

        int lower = lowerBound(startTS, granularity);
        int upper = upperBound(endTS, granularity);
        for (int i = lower; i < upper; ++i) {
            TimeStamp timestamp = timestampList.get(i);
            result.addAll(map.get(timestamp));
        }
        return result;
    }
}

/**
 * Your LogSystem object will be instantiated and called as such:
 * LogSystem obj = new LogSystem();
 * obj.put(id,timestamp);
 * List<Integer> param_2 = obj.retrieve(start,end,granularity);
 */

