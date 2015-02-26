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
