class Solution {
public:
#define M2
 
#ifdef M1  // counting sort, two pass
    void sortColors(int A[], int n) {
        int count[3] = {0}; // 记录每种颜色出现的次数
        for(int i=0; i<n; ++i) {
            count[A[i]] ++;
        }
        
        for(int i=0, index=0; i<3; ++i) {
            for(int j=0; j<count[i]; ++j) {
                A[index++] = i;
            }
        }
    }
#endif
#ifdef M2 
/*
考虑到题目要求one pass。这就意味着类似于链表的双指针问题，这里也要track两个index，一个是red的index，一个是blue的index，两边往中间走。
i从0到blue index扫描，
遇到0，放在red index位置，red index后移, i后移；
遇到2，放在blue index位置，blue index前移；
遇到1，i后移。
扫描一遍得到排好序的数组。时间O(n)，空间O(1)，
// 其中red指针标识red区域最大下标，而blue标识blue区域的最小下标
// testcase： [0] [1] [2] [1 0] [0 0] [1 1] [2 2] [1 0 2] [1 2 1 0]
*/
    void sortColors(int A[], int n) {
        int red = 0, blue = n-1;
        // 注意这里的判断条件：i <= blue, 
        // （1） 如果只是i<blue, 则[1, 0]这种case就出错了。因为如果blue这个下标指向的是blue元素的话，<就够了， 
        //       但如果数组中没有blue元素，则要用<=来扫描完整个数组。
        // （2） 如果是i<n的话， 则只要数组中有2，就会出现i不变，而blue下标越界的情况。
        for(int i=0; i<=blue; ) {
            if (A[i] == 0) {
                swap(A[i++], A[red++]);
            }
            else if (A[i] == 2) {
                // 注意，这里没有++i，因为很有可能交换之后A[i] = 0, ie [1 2 1 0]
                // 因此，需要再循环一次来判断一下。
                swap(A[i], A[blue--]);
            }
            else {
                ++i;
            }
        }
    }
#endif
#ifdef M3
/*
    // 这种划分方法好像不太适合这个问题。
    // 因为这个划分是将数分为[< pivot][pivot][>=pivot]三部分。
    // 而且pivot是未知的。
    // 这种方法对于已经排好序的数组来说，效率是O(n2)的。
    int partition(int A[], int low, int high) {
        int location = low-1;
        int pivot = A[high];
        for(int i=low; i<high; ++i) {
            if (A[i] < pivot) {
                ++location;
                swap(A[i], A[location]);
            }
        }
        
        swap(A[location+1], A[high]);
        return location+1;
    }
*/
    // std::partition的一种可能实现
    // partition将符合条件的元素放在前面，不符合的放在后面,并返回第一个不符合的位置。
    template <typename ForwardIterator, typename UnaryPredicate>
    ForwardIterator mypartition(ForwardIterator first, ForwardIterator last, UnaryPredicate pred) {
        ForwardIterator pos = first;
        for(; first != last; ++first) {
            if (pred(*first)) {
                swap(*first, *pos);
                ++pos;
            }
        }
        return pos;
    }
    void sortColors(int A[], int n) {
        mypartition(mypartition(A, A+n, bind1st(equal_to<int>(), 0)), 
            A+n, bind1st(equal_to<int>(), 1));
    }

    // rewrite mypartition
    template <class ForwardIterator, class UnaryPredicate>
    ForwardIterator mypartition(ForwardIterator first, ForwardIterator last, UnaryPredicate pred) {
        ForwardIterator pos = first;
        while(first != last) {
            if (pred(*first)) {
                swap(*pos, *first);
                ++pos;
            }
            ++first;
        }
        return pos;
    }
    void sortColors(int A[], int n) {
        mypartition(mypartition(A, A+n, [](int i){return i==0;}), A+n, [](int j){return j==1;});
    }
#endif
};
