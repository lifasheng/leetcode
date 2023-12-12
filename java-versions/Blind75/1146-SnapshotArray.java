/*
very very good!

Implement a SnapshotArray that supports the following interface:

SnapshotArray(int length) initializes an array-like data structure with the given length. Initially, each element equals 0.
void set(index, val) sets the element at the given index to be equal to val.
int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 
Example 1:
Input: ["SnapshotArray","set","snap","set","get"]
[[3],[0,5],[],[0,6],[0,0]]
Output: [null,null,0,null,5]
Explanation: 
SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
snapshotArr.set(0,5);  // Set array[0] = 5
snapshotArr.snap();  // Take a snapshot, return snap_id = 0
snapshotArr.set(0,6);
snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
 
Constraints:
1 <= length <= 5 * 104
0 <= index < length
0 <= val <= 109
0 <= snap_id < (the total number of times we call snap())
At most 5 * 104 calls will be made to set, snap, and get.
*/

// https://www.cnblogs.com/grandyang/p/14934158.html
// 第一种解法，每次snap都copy一份数据。Memory Limit Exceeded
/*
class SnapshotArray {
    private int snapId;
    private Map<Integer, int[]> snapshots;
    private int[] cur;

    public SnapshotArray(int length) {
        snapId = 0;
        snapshots = new HashMap<>();
        cur = new int[length];
    }
    
    public void set(int index, int val) {
        cur[index] = val;
    }
    
    public int snap() {
        snapshots.put(snapId, cur);
        cur = cur.clone();
        return snapId++;
    }
    
    public int get(int index, int snap_id) {
        int[] arr = snapshots.get(snap_id);
        return arr[index];
    }
}
*/


/*
思路：上面那种每次snapshot都全部复制一份的方法会消耗太多的内存而导致Memory Limit Exceeded

我们可以对每个index维持一个list，list的每个item是【snapid，value】的pair。
这样只有真正被修改过的位置才会增加一个新的copy。

这里需要注意的是：
1. 我们需要将每个index初始化为0，这样就保证了初始值为0，又简化了代码，不用担心某些index从来没有被set过。
2. snap()会导致整个arraysnapId增加，而我们不会为每个index增加新的版本值；
    比如
    SnapshotArray snapshotArr = new SnapshotArray(3); 
    snapshotArr.snap()
    snapshotArr.set(1,9);
    snapshotArr.snap()
    snapshotArr.set(0, 4)
    snapshotArr.get(0,1) => 0
    snapshotArr.get(1, 2) => 9
     snapshotArr.get(2, 2) => 0

    对应的内部状态为：
    snapId=0, snapshotArr = {0: [0,0], 1: [0,0], 2: [0,0]]}
    snapId=1, snapshotArr = {0: [0,0], 1: [0,0], 2: [0,0]]}
    snapId=1, snapshotArr = {0: [0,0], 1: [0,0][1,9], 2: [0,0]]}
    snapId=2, snapshotArr = {0: [0,0], 1: [0,0][1,9], 2: [0,0]]}
    snapId=2, snapshotArr = {0: [0,0][2,4], 1: [0,0][1,9], 2: [0,0]]}


*/
class SnapshotArray_1 {
    private int snapId;
    private Map<Integer, List<int[]>> snapshots;

    public SnapshotArray_1(int length) {
        this.snapId = 0;
        this.snapshots = new HashMap<>();
        for(int i = 0; i < length; ++i) {
            snapshots.putIfAbsent(i, new ArrayList<>());
            snapshots.get(i).add(new int[]{0, 0});
        }
    }
    
    public void set(int index, int val) {
        List<int[]> list = snapshots.get(index);

        int[] p = list.get(list.size() - 1);
        if (p[0] < snapId) {
            list.add(new int[]{snapId, val});
        } else {
            p[1] = val;
        }
    }
    
    public int snap() {
        return snapId++;
    }
    
    // O(n) 也能通过
    // public int get(int index, int snap_id) {
    //     List<int[]> list = snapshots.get(index);
    //     int prev = 0;
    //     for (int[] p: list) {
    //         if (p[0] == snap_id) {
    //             return p[1];
    //         }
    //         if (p[0] < snap_id) {
    //             prev = p[1];
    //         }
    //     }
    //     return prev;
    // }

    // O(logN) using upper_bound
    public int get(int index, int snap_id) {
        List<int[]> list = snapshots.get(index);
        int upperIndex = upper_bound(list, snap_id);
        return list.get(upperIndex - 1)[1];
    }

    private int upper_bound(List<int[]> list, int val) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low)/2;
            if (list.get(mid)[0] <= val) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}

/*
上面的方法用的是List<int[]>，这样的话就需要自己实现upper_bound函数。
如果用TreeMap的话，就不用自己实现了。同时它也是有序的。
*/
class SnapshotArray {
    private int snapId;
    private TreeMap<Integer, Integer>[] snapshots;

    public SnapshotArray(int length) {
        this.snapId = 0;
        this.snapshots = new TreeMap[length];
        for(int i = 0; i < length; ++i) {
            snapshots[i] = new TreeMap<>();
            snapshots[i].put(0, 0);
        }
    }
    
    public void set(int index, int val) {
        snapshots[index].put(snapId, val);
    }
    
    public int snap() {
        return snapId++;
    }

    public int get(int index, int snap_id) {
        // floorEntry: Returns a key-value mapping associated with the greatest key 
        // less than or equal to the given key, or null if there is no such key.
        return snapshots[index].floorEntry(snap_id).getValue();
    }
}


/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */



