class Solution {
public:
#define M2
#ifdef M1
// 时间复杂度 O(n^2),空间复杂度 O(n)
    vector<int> getRow(int rowIndex) {
        vector<int> prev, cur;
        if (rowIndex < 0) return cur;
        
        cur.push_back(1);
        for(int i=2; i<=rowIndex+1; ++i) {
            prev = cur;
            cur.resize(i);
            cur[0] = 1;
            for(int j=1; j<i-1; ++j) {
                cur[j] = prev[j-1] + prev[j];
            }
            cur[i-1] = 1;
        }
        return cur;
    }
#endif
#ifdef M2
/*
滚动数组， 时间复杂度 O(n^2),空间复杂度 O(n)
这里只用了一个必要的数组保存结果，比M1节省一个数组。
cur[j] = prev[j-1]+prev[j]，要用滚动数组的话，就只能从后往前处理。
*/
    vector<int> getRow(int rowIndex) {
        if (rowIndex <0) return vector<int>();
        vector<int> result(rowIndex+1, 1);
        
        for(int i=2; i<= rowIndex+1; ++i) {
            for(int j=i-2; j>=1; --j) {
                result[j] += result[j-1];
            }
        }
        return result;
    }
#endif
};
