class Solution {
public:
#if 1
    // this version works!
    vector<int> twoSum(vector<int> &numbers, int target) {
        vector<int> result;
        unordered_multimap<int, int> m;
        
        for(int i=0; i<numbers.size(); ++i) {
            // m[numbers[i]] = i; // this is valid for unordered_map, but not for unordered_multimap
            m.emplace(numbers[i], i);
        }
        
        for(int i=0; i<numbers.size(); ++i) {
            int gap = target - numbers[i];
            if (gap == numbers[i]) {
                if (m.count(gap) >=2) {
                    result.push_back(i+1);
                    
                    typedef unordered_multimap<int, int>::iterator  Iterator;
                    std::pair<Iterator, Iterator> range = m.equal_range(gap);
                    for(Iterator iter = range.first; iter!= range.second; ++iter) {
                        if (iter->second != i) {
                            result.push_back(iter->second+1);
                            break;
                        }
                    }
                    break;
                }
            }
            else if (m.count(gap) >=1) // gap != numbers[i]
            {
                result.push_back(i+1);
                result.push_back(m.find(gap)->second+1);
                break;
            }
        }
        return result;
    }
#else
    // following code is from internet, it's wrong
    vector<int> twoSum(vector<int> &numbers, int target) {
        vector<int> result;
        unordered_map<int, int> m; // duplicate is not allowed
        
        for(int i=0; i<numbers.size(); ++i) {
            m[numbers[i]] = i;
        }
        
        for(int i=0; i<numbers.size(); ++i) {
            int gap = target - numbers[i];
            if (m.find(gap) != m.end())
            {
                result.push_back(i+1);
                result.push_back(m[gap]+1);
                break;
            }
        }
        return result;
    }
#endif
};
