/*
A binary watch has 4 LEDs on the top to represent the hours (0-11), and 6 LEDs on the bottom to represent the minutes (0-59). Each LED represents a zero or one, with the least significant bit on the right.

For example, the below binary watch reads "4:51".

Given an integer turnedOn which represents the number of LEDs that are currently on (ignoring the PM), return all possible times the watch could represent. You may return the answer in any order.

The hour must not contain a leading zero.

For example, "01:00" is not valid. It should be "1:00".
The minute must be consist of two digits and may contain a leading zero.

For example, "10:2" is not valid. It should be "10:02".
 

Example 1:

Input: turnedOn = 1
Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
Example 2:

Input: turnedOn = 9
Output: []
*/


class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();
        if (turnedOn < 0 || turnedOn >= 9) return result;


        //h : 1-12  0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100
        //m : 0-59

        Map<Integer, Integer> cache = new HashMap<>();

        for (int h = 0; h < 12; ++h) {
            for (int m = 0; m < 60; ++m) {
                
                if (countOne(h, cache) + countOne(m, cache) != turnedOn) continue;

                StringBuilder sb = new StringBuilder();
                sb.append(h);
                sb.append(":");
                if (m >= 10) {
                    sb.append(m);
                } else {
                    sb.append("0");
                    sb.append(m);
                }
                result.add(sb.toString());
            }
        }

        return result;
    }

    private int countOne(int number, Map<Integer, Integer> cache) {
        if (cache.containsKey(number)) return cache.get(number);
        int count = 0;
        int n = number;
        while (n > 0) {
            if ((n & 1) == 1) {
                ++ count;
            }
            n >>= 1;
        }
        cache.put(number, count);
        return count;
    }
}


