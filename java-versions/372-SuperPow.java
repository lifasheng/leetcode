/*
Medium

Your task is to calculate ab mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.

Example 1:
Input: a = 2, b = [3]
Output: 8

Example 2:
Input: a = 2, b = [1,0]
Output: 1024

Example 3:
Input: a = 1, b = [4,3,3,8,5,2]
Output: 1
 
Constraints:
1 <= a <= 231 - 1
1 <= b.length <= 2000
0 <= b[i] <= 9
b does not contain leading zeros.
*/


class Solution {
    
    // very good!
    // (a * b) % k = (a % k)(b % k) % k
    private static final int BASE = 1337;
    
    public int superPow(int a, int[] b) {
        return superPow_2(a, b);
    }
    
    // solution 1: recursive, 即使用了arraycopy也是最慢， 因为b[0]==0时，要重新分配一个新数组， 5%
    // time: let N is b's length. The max number of b is 10 ^ N, the recursive times is log(10^N) = N * log10
    // for each recursive, it will do the divide 2 operation, which is O(N), so overall it's O(log10 * N^2) = O(N^2)
    public int superPow_recursive(int a, int[] b) {
        a %= BASE;
        
        if (a == 1) return 1;
        if (b.length == 1 && b[0] == 0) return 1;
        
        if (b[b.length - 1] % 2 == 1) {
            b[b.length - 1] -= 1;
            return (a * superPow(a, b)) % BASE;
        }
        
        int r = superPow(a, divide2(b)) % BASE;
        return (r * r) % BASE;
    }
    
    private int[] divide2(int[] b) {
        for (int i = 0; i < b.length; ++i) {
            int bi = b[i];
             b[i] /= 2;
            if (bi % 2 == 1) {
                b[i + 1] += (bi % 2) * 10;
            } 
        }
        
        if (b[0] != 0) {
            return b;
        }
        else {
            int[] c = new int[b.length - 1];
            // for (int i = 1; i < b.length; ++i) {
            //     c[i - 1] = b[i];
            // }
            System.arraycopy(b, 1, c, 0, c.length);
            return c;
        }
    }
    
    
//     // solution 1: recursive, optimized with ArrayList，
//     // 将数组b 倒过来存，但好像也没什么改进
//     public int superPow_recursive_optimized_2(int a, int[] b) {
//         ArrayList<Integer> list = new ArrayList<>();
//         for (int i = b.length - 1; i >= 0; --i) {
//             list.add(b[i]);
//         }
//         return superPow_recursive_optimized_list(a, list);
//     }
    
//     public int superPow_recursive_optimized_list(int a, ArrayList<Integer> list) {
//         a %= BASE;
        
//         if (a == 1) return 1;
//         if (list.size() == 0) return 1;
        
//         int last = list.get(0);
//         if (list.size() == 1 && last == 0) return 1;
        
//         if (last % 2 == 1) {
//             list.set(0, last - 1);
//             return (a * superPow_recursive_optimized_list(a, list)) % BASE;
//         }
        
//         int r = superPow_recursive_optimized_list(a, divide2_list(list)) % BASE;
//         return (r * r) % BASE;
//     }
    
//     private ArrayList<Integer> divide2_list(ArrayList<Integer> list) {
//         for (int i = 0; i < list.size(); ++i) {
//             int num = list.get(i);
//             list.set(i, num / 2);
//             if (num % 2 == 1) {
//                 list.set(i - 1, list.get(i - 1) + 5);
//             }
//         }
        
//         if (list.get(list.size() - 1) == 0) {
//             list.remove(list.size() - 1);
//         }
        
//         return list;
//     }
    
    
    
    // solution 2: a ^ [ 1 3 4 5] = a ^5 * (a ^[ 1 3 4]) ^ 10
    // faster than solution 1
    // time: O(log10 * N), N is the length of b, and myPow is log10 because b in myPow is at most 10， 这个是47%
    public int superPow_2(int a, int[] b) {
        // use ArrayDeque so it can dynamically change its size.
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : b) {
            list.add(i);
        }
        
        return superPow(a, list);
    }
    
    private int superPow(int a, ArrayList<Integer> list) {
        if (a == 1) return 1;
        if (list.size() == 0) return 1;
        
        int last = list.remove(list.size() - 1);
        return (myPow(a, last) * myPow(superPow(a, list), 10)) % BASE;
    }
    
    private int myPow(int a, int b) {
        a %= BASE; // this should be done at first, otherwise, when b == 1, it will return a, which should also % BASE.
        
        if (a == 1 || b == 1) return a;
        if (b == 0) return 1;
        
        if (b % 2 == 1) {
            return (a * myPow(a, b - 1)) % BASE;
        } else {
            int r = myPow(a, b / 2) % BASE;
            return (r * r) % BASE;
        }
    }
    
}


