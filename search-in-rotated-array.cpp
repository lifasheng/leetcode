class Solution {
public:
    int search(int A[], int n, int target) {
        if (!A || n <=0)
            return -1;
        
        int low=0, high = n-1, mid;
        while(low <= high) {
            mid = (low+high) >> 1;
            if (A[mid] == target) {
                return mid;
            }
            
            if (A[low] <= A[mid]) { // left part is ascending 左边是递增的
                if (A[low] <= target && target <= A[mid]) 
                    high = mid-1;
                else
                    low = mid+1;
            }
            else { // right part is ascending 右边是递增的
                if (A[mid] <= target && target <= A[high])
                    low = mid+1;
                else
                    high = mid-1;
            }
        }
        
        return -1;
    }
};
