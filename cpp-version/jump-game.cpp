class Solution {
public:
#define M2
#ifdef M1
/*
DP，设状态为f(i), 表示第i层剩余的步数。
对前n-1层，只要每一层剩余的步数大于0即可。
*/
    bool canJump(int A[], int n) {
        vector<int> f(n, 0);
        for(int i=0; i<n; ++i) {
            if (i==0) {
                f[0] = A[0];
            }
            else {
                f[i] = max(f[i-1]-1, A[i]);
            }
            if (i<n-1 && f[i] <=0) return false;
        }
        return true;
    }
#endif
#ifdef M2
// 顺序扫描数组，记录当前能够到达的最远位置。
    bool canJump(int A[], int n) {
        int canArrive = 0; //当前能到达的最远位置
        for(int i=0; i<=canArrive && canArrive<n-1; ++i) {
            canArrive = max(i+A[i], canArrive);
        }
        return canArrive >= n-1;
    }
#endif
};

class Solution {
public:
// DP, f(i)表示从第i个位置所能到达的最远距离。
// 当i<n-1是，如果f[i]<=i，则说明到不了最后一个位置。
// 注意，我们只需要判断[0, n-1]这些位置，对于第n个位置，不用判断。
// 所以如果A=[0], 也返回true。
    bool canJump(int A[], int n) {
        if (n<=0) return false;
        vector<int> f(n);
        for(int i=0; i<n-1; ++i) {
            if (i==0) {
                f[i] = A[i];
            }
            else {
                f[i] = max(A[i]+i, f[i-1]);
            }
            if (f[i] <= i) return false;
        }
        return true;
    }
};
// 我一开始重写的代码是上面（34-53行）。
// 看了最初的M2之后，发现f数组其实是可以省掉的，所以有了下面的代码。
// 个人觉得这个代码理解起来比M2更容易，虽然很相似:-)。
class Solution {
public:
// DP, f(i)表示从第i个位置所能到达的最远距离。
// 当i<n-1是，如果f[i]<=i，则说明到不了最后一个位置。
// 注意这里f(i)其实不用一个数组，而是用一个变量即可。
// 因为f(i)的求值只依赖f(i-1)，类似于滚动数组，可以省去一个数组。
    bool canJump(int A[], int n) {
        if (n<=0) return false;
        int canArrive = 0;
        for(int i=0; i<n-1; ++i) {
            canArrive = max(A[i]+i, canArrive);
            if (canArrive <= i) return false;
        }
        return true;
    }
};
