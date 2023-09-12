/*
very very very good!


Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

Example 1:
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]

Example 2:
Input: nums = [1], k = 1
Output: [1]
 
Constraints:
1 <= nums.length <= 105
-104 <= nums[i] <= 104
k is in the range [1, the number of unique elements in the array].
It is guaranteed that the answer is unique.
 
Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
*/


// https://www.cnblogs.com/xugenpeng/p/9950007.html
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        return solution3(nums, k);
    }


    // 使用排序算法对元素按照频率由高到低进行排序，然后取前 𝑘 个元素。但是这样做的时间复杂度是 𝑂(𝑛log𝑛)的， 不满足题目要求。
    // 时间复杂度： 主要是排序，最坏情况是每个元素都不同，O(nlogn)， n是nums的数组大小
    /*
    时间复杂度：𝑂(𝑛log𝑛)，其中 𝑛 表示数组的长度。
    空间复杂度：𝑂(𝑛)，最极端的情况下（每个元素都不同），用于存储元素及其频率的 Map 需要存储 𝑛 个键值对
    */
    private int[] solution1(int[] nums, int k) {

        // 1. 统计每个数字出现的次数；
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int i : nums) {
            freqMap.put(i, freqMap.getOrDefault(i, 0) + 1);
        }

        // 2. 将出现的次数从大到小排序
        // freqMap.entrySet() 返回的是Set<Map.Entry<K, V>> 
        List<Map.Entry<Integer, Integer>> freqList = new ArrayList<>(freqMap.entrySet());
        Collections.sort(freqList, (entry1, entry2) -> {
            int freq1 = entry1.getValue();
            int freq2 = entry2.getValue();
            return freq2 - freq1;
        });

        // 4. 按照出现次数从大到小取数就行了。
        int[] result = new int[k];
        for (int i = 0; i < k; ++i) {
            result[i] = freqList.get(i).getKey();
        }
        return result;

    }


    // 哈希表 + 堆 时间复杂度O(k * log k)，其中k为独立元素的个数。 比上面的方法好
    /*
    时间复杂度：𝑂(𝑛log𝑘)，其中 𝑛表示数组的长度。
    首先，遍历一遍数组统计元素的频率，这一系列操作的时间复杂度是 𝑂(𝑛)的；
    接着，遍历用于存储元素频率的 map，如果元素的频率大于最小堆中顶部的元素，则将顶部的元素删除并将该元素加入堆中，
    这一系列操作的时间复杂度是 𝑂(𝑛log𝑘)的；
    最后，弹出堆中的元素所需的时间复杂度是 𝑂(𝑘log𝑘)的。因此，总的时间复杂度是 𝑂(𝑛log𝑘)的。

    空间复杂度：𝑂(𝑛)，最坏情况下（每个元素都不同），map 需要存储 𝑛 个键值对，优先队列需要存储 𝑘 个元素，
    因此，空间复杂度是 (𝑛) 的。
    */
    private int[] solution2(int[] nums, int k) {

        // 1. 统计每个数字出现的次数；
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int i : nums) {
            freqMap.put(i, freqMap.getOrDefault(i, 0) + 1);
        }

        // 2. 将countMap的entry加入priorityQueue 最大堆，并按出现次数排序
        Queue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((p1, p2) -> p2.getValue() - p1.getValue());
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            pq.offer(entry);
        }

        // 3. 从堆中取出元素，加入结果集中。
        int[] result = new int[k];
        for (int i = 0; i < k; ++i) {
            Map.Entry<Integer, Integer> entry = pq.poll();
            result[i] = entry.getKey();
        }
        return result;
    }

    /*
    最后，为了进一步优化时间复杂度，可以采用桶排序（bucket sort），即用空间复杂度换取时间复杂度。

    第一步和解法二相同，也是统计出数组中元素的频次。接着，将数组中的元素按照出现频次进行分组，
    即出现频次为 𝑖 的元素存放在第 𝑖 个桶。最后，从桶中逆序取出前 𝑘 个元素。
    最多有n+1个桶， n为nums的大小。

    时间复杂度：𝑂(𝑛)，其中 𝑛 表示数组的长度。
    空间复杂度：𝑂(𝑛)
    */
    private int[] solution3(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int i : nums) {
            freqMap.put(i, freqMap.getOrDefault(i, 0) + 1);
        }

        List<Integer>[] bucket = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()) {
            int key = entry.getKey();
            int freq = entry.getValue();

            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(key);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = nums.length; i >=0; --i) {
            if (result.size() >= k) break;
            if (bucket[i] != null) {
                result.addAll(bucket[i]);
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; ++i) {
            res[i] = result.get(i);
        }
        return res;
    }
}


