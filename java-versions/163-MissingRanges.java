/*
You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.

A number x is considered missing if x is in the range [lower, upper] and x is not in nums.

Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.

Each range [a,b] in the list should be output as:

"a->b" if a != b
"a" if a == b
 
Example 1:
Input: nums = [0,1,3,50,75], lower = 0, upper = 99
Output: ["2","4->49","51->74","76->99"]
Explanation: The ranges are:
[2,2] --> "2"
[4,49] --> "4->49"
[51,74] --> "51->74"
[76,99] --> "76->99"

Example 2:
Input: nums = [-1], lower = -1, upper = -1
Output: []
Explanation: There are no missing ranges since there are no missing numbers.

Constraints:
-109 <= lower <= upper <= 109
0 <= nums.length <= 100
lower <= nums[i] <= upper
All the values of nums are unique.
*/

class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        if (nums.length == 0) {
            return Arrays.asList(formatRange(lower, upper));
        }
        
        List<String> result = new ArrayList<>();
        int prev = lower - 1;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] - prev > 1) {
                result.add(formatRange(prev + 1, nums[i] - 1));
            }
            prev = nums[i];
        }
        
        if (upper > prev) {
            result.add(formatRange(prev + 1, upper));
        }
        
        return result;
    }
    
    private String formatRange(int lower, int upper) {
        if (lower == upper) {
            return String.format("%d", lower);
        } else {
            return String.format("%d->%d", lower, upper);
        }
    }
}

