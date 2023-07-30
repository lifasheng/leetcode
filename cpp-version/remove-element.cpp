class Solution {
public:
#define M0
#ifdef M0 // O(n), best one
    int removeElement(int A[], int n, int elem) {
        int index = 0;
        for(int i=0; i<n; ++i) {
            if (A[i] != elem) {
                A[index++] = A[i];
            }
        }
        return index;
    }
#endif
#ifdef M1 // O(nlogn), 先排序O(nlogn),再去除指定元素O(n)
    int removeElement(int A[], int n, int elem) {
        sort(A, A+n);
        
        int newLen = n;
        for(int i=0; i<n; ++i) {
            if (A[i] == elem) {
                int j = i+1;
                while (j<n && A[j] == elem) {
                    ++j;
                }
                
                newLen = n - (j-i); // elem共有j-i个
                
                while(j<n) {
                    A[i++] = A[j++];
                }
                
                break;
            }
        }
        
        return newLen;
    }
#endif
#ifdef M2 // 用hash map来辅助，O(n)
    int removeElement(int A[], int n, int elem) {
        unordered_map<int, int> m;
        
        for(int i=0; i<n; ++i) {
            m[A[i]]++;
        }
        
        typedef unordered_map<int, int>::iterator Iterator;
        int k = 0;
        for(Iterator iter=m.begin(); iter!=m.end(); ++iter) {
            if(iter->first != elem) {
                for(int j=0; j<iter->second; ++j) {
                    A[k++] = iter->first;
                }
            }
        }
        
        return k;
    }
#endif
};
