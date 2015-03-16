class Solution {
public:
#define M0
#ifdef M0
/*
两遍扫描法，类似于candy。
对于某一个柱子，如果它左右两边的最大柱子高度的较小者比自己的柱子高度要大，这该柱子上面就可以容纳水。
对于任何一个柱子，检查其左右的最大柱子高度，该柱子能容纳的面积就是min(max_left, max_right) - height.
可以从左右各扫一遍，存入一个数组；O(n)time and space
*/
    int trap(int A[], int n) {
        if (!A || n <= 0) return 0;

        int *left = new int[n];
        int *right = new int[n]; // note that this array is not necessary, but we use it here for simplicity.
        int result = 0;

        // 找到该柱子左边最大的柱子高度
        int imax = 0;
        for(int i=0; i<n; ++i)  {
            left[i] = imax;
            imax = max(imax, A[i]);
        }

        // 找到该柱子右边最大的柱子高度
        // 由于已经找到该柱左右两边的最大柱子高度，所以可以计算出该柱子所能容纳的水量。
        imax = 0;
        for(int i=n-1; i>=0; --i) {
            right[i] = imax;
            imax = max(imax, A[i]);

            int minMax = min(left[i], right[i]);
            if (minMax > A[i]) {
                result += minMax - A[i];
            }
        }

        delete []left;
        delete []right;

        return result;
    }
#endif
#ifdef M1
/*
这是我的解法：
思路是从左到右扫描数组，一个容器一个容器的计算。
* 如果当前元素大于0,并且它的下一个元素小于它，则在它右边找大于等于它的数，两者夹一下，就是一个可以盛水的容器;
* 如果当前元素的右边的元素都小于它，比如[3, 0, 2]，则找到它右边最大的元素，两者夹一下;
* 计算完一个容器之后，要跳过该容器的右边元素;
* test case: [2, 0, 1, 3, 2, 3, 1, 0, 2]
*            [3, 0, 2]
*            [2, 0, 1, 0, 2]
*/
    int trap(int A[], int n) {
        int result = 0;
        
        for (int i=0; i<n; ++i) {
            if (A[i] > 0 && A[i+1] < A[i]) {
                int j = i+1;
                int mi = j; // index of the biggest one in A[i] right part (only used when there is no number bigger than A[i] in its right)
                int mv = 0; // value of the biggest one in A[i] right part (only used when there is no number bigger than A[i] in its right)
                while(j<n && A[j] < A[i]) {
                    if (A[j] > mv) {
                        mi = j;
                        mv = A[j];
                    }
                    ++j;
                }
                
                if (j<n) { // A[j] > A[i]
                    int t = min(A[i], A[j]); // 容器的高以小的一边为准
                    for(int k=i+1; k !=j; ++k) {
                        result += (t-A[k]);
                    }
                    i = j-1; // [i, j] has been calculated, next scan starts from j (since there is ++i, so i=j-1)
                }
                else { // j == n
                    int t = min(A[i], mv);
                    for(int k=i+1; k!= mi; ++k) {
                        result += (t-A[k]);
                    }
                    i = mi-1; // [i, mi] has been calculated, next scan starts from mi (since there is ++i, so i=mi-1)
                }
            }
        }
        
        return result;
    }
#endif
#ifdef M2
    int trap(int A[], int n) {
        int result = 0;
        
        //找到最大的元素的下标
        int max = 0;
        for(int i=0; i<n; ++i) {
            if (A[i] > A[max]) {
                max = i;
            }
        }
        
        // 从左往右扫描，直到遇到最大元素
        // 对每个位置，用到目前为止所遇到的左边的最高值和右边的最高值夹逼，就是当前位置可以容纳的水量。
        // 这里右边的最高值就是上面的最大元素，它肯定是大于等于左边的最高值，所以计算时以左边的最高值来计算。
        int height = 0;
        for (int i=0; i<max; ++i) {
            if (A[i] > height)
                height = A[i]; // 遇到一个更大的容器高度
            else
                result += (height - A[i]);
        }
        
        // 从右往左扫描，直到遇到最大元素
        height = 0;
        for(int i=n-1; i>max; --i) {
            if (A[i] > height)
                height = A[i];
            else
                result += (height - A[i]);
        }
        
        return result;
    }
#endif
#ifdef M3
/*
思路是：用一个栈来辅助，从左到右扫描数组。
* 如果当前元素小于栈顶元素则入栈;
* 如果当前元素大于等于栈顶元素，则把栈顶元素出栈，并计算面积，这个过程持续到当前元素小于栈顶元素;
* 计算面积时，需要记住一个高度值，这个高度值代表容器中已经计算完面积的较低部分;
* test case: [2, 0, 1, 0, 2]
* log:
i: 0
push 2
i: 1
bar:2, pos:0, height:0, water:0, i-pos-1:0
bar:2, pos:0, height:2, water:0
break
push 0
i: 2
bar:0, pos:1, height:0, water:0, i-pos-1:0
bar:0, pos:1, height:0, water:0
pop
bar:2, pos:0, height:0, water:0, i-pos-1:1
bar:2, pos:0, height:2, water:1
break
push 1
i: 3
bar:1, pos:2, height:0, water:1, i-pos-1:0
bar:1, pos:2, height:1, water:1
break
push 0
i: 4
bar:0, pos:3, height:0, water:1, i-pos-1:0
bar:0, pos:3, height:0, water:1
pop
bar:1, pos:2, height:0, water:1, i-pos-1:1
bar:1, pos:2, height:1, water:2
pop
bar:2, pos:0, height:1, water:2, i-pos-1:3
bar:2, pos:0, height:2, water:5
pop
push 2
5
*/
    int trap(int a[], int n) {
        stack<pair<int, int> > s;
        int water = 0;
        for (int i = 0; i <n; ++i) {
            int height = 0;
            // Ribi will be the current element low or high element disposed of all
            while (!s.empty()) {
                int bar = s.top().first;
                int pos = s.top().second;
                // bar, height, a[i] The three clip into depression
                water += (min(bar, a[i]) - height) * (i - pos - 1);
                height = bar;
                if (a[i] <bar) // Met than the current element high, out of the loop
                    break;
                else
                s.pop(); // Pop up the top of the stack, since this element is done, no longer necessary
            }
            s.push(make_pair(a[i], i));
        }
        return water;
    }
#endif
};
