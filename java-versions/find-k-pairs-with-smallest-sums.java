/*
373. Find K Pairs with Smallest Sums
Medium

You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
Define a pair (u, v) which consists of one element from the first array and one element from the second array.
Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.


Example 1:

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

Example 2:

Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [[1,1],[1,1]]
Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

Example 3:

Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [[1,3],[2,3]]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
*/


class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
        
        for(int i=0; i<nums1.length; ++i) {
            for(int j=0; j<nums2.length; ++j) {
                int sum = nums1[i] + nums2[j];
                if (!map.containsKey(sum)) {
                    pq.add(sum);
                }
                map.putIfAbsent(sum, new ArrayList<>());
                map.get(sum).add(Arrays.asList(nums1[i], nums2[j]));
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            int t =  pq.poll();
            List<List<Integer>> list = map.get(t);
            for(int i=0; i<list.size(); ++i) {
                result.add(list.get(i));
                if (result.size() == k) {
                    return result;
                }
            }
        }
        
        return result;
    }
}







