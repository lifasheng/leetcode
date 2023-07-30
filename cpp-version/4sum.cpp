class Solution {
public:
// both version are OK, just different style!
#define M1
#ifdef M1
    vector<vector<int> > fourSum(vector<int> &num, int target) {
        vector<vector<int> > result;
        int size = num.size();
        if (size < 4) return result;
        
        sort(num.begin(), num.end());
        
        for(int i=0; i<size-3; ++i) {
            for(int j=i+1; j<size-2; ++j) {
                int k = j+1;
                int t = size-1;
                
                while(k < t) {
                    int sum = num[i] + num[j] + num[k] + num[t];
                    if (sum < target) {
                        ++k;
                    }
                    else if (sum > target) {
                        --t;
                    }
                    else { // sum == target
                        result.push_back({num[i], num[j], num[k], num[t]});
                        ++k;
                        --t;
                    }
                }
            }
        }
        
        sort(result.begin(), result.end());
        result.erase(unique(result.begin(), result.end()), result.end());
        
        return result;
#endif
#ifdef M2
    vector<vector<int> > fourSum(vector<int> &num, int target) {
        vector<vector<int> > result;
        int size = num.size();
        if (size < 4) return result;
        
        sort(num.begin(), num.end());
        
        typedef vector<int>::iterator Iterator;
        Iterator end = num.end();
        for(Iterator i=num.begin(); i<prev(end,3); ++i) {
            for(Iterator j=next(i); j<prev(end,2); ++j) {
                Iterator k = next(j);
                Iterator t = prev(end,1);
                
                while(k < t) {
                    int sum = *i + *j + *k + *t;
                    if (sum < target) {
                        ++k;
                    }
                    else if (sum > target) {
                        --t;
                    }
                    else { // sum == target
                        result.push_back({*i, *j, *k, *t});
                        ++k;
                        --t;
                    }
                }
            }
        }
        
        sort(result.begin(), result.end());
        result.erase(unique(result.begin(), result.end()), result.end());
        
        return result;
#endif
#ifdef M3 // 思路很好，但是超时了
    vector<vector<int> > fourSum(vector<int> &num, int target) {
        vector<vector<int> > result;
        int size = num.size();
        if (size < 4) return result;
        
        sort(num.begin(), num.end());
        
        unordered_multimap<int, pair<int, int> >m;
        
        typedef vector<int>::iterator Iterator;
        for(int i=0; i<size-1; ++i) {
            for(int j=i+1; j<size; ++j) {
                m.emplace(num[i] + num[j], std::make_pair(i, j));
            }
        }
        
        typedef unordered_multimap<int, pair<int, int> >::iterator MapIterator;
        for(MapIterator i= m.begin(); i!=m.end(); ++i) {
            int gap = target - i->first;
            
            std::pair<MapIterator, MapIterator> range = m.equal_range(gap);
            for(MapIterator j=range.first; j!= range.second; ++j) {
                int a = i->second.first;
                int b = i->second.second;
                int c = j->second.first;
                int d = j->second.second;
                
                if (a!=c && a!=d && b!=c && b!=d) {
                    vector<int> vec = {num[a], num[b], num[c], num[d]};
                    sort(vec.begin(), vec.end());
                    result.push_back(vec);
                }
            }
        }
        
        sort(result.begin(), result.end());
        result.erase(unique(result.begin(), result.end()), result.end());
        
        return result;
#endif
    }
};
