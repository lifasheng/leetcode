class Solution {
public:
#if 0
/*
主要思路： 
 * 处理复杂度为O(n2)，不过其中获取符合条件三元组时对重复元素也进行了计算，最后又进行了排序来同一去重使得效率很低 
 * 1、为了选出的三元组是非递减的，先对输入数组进行排序 
 * 2、枚举法选取符合条件的三元组，由于每个元素只能取一次，所以当前循环的起始为上次循环起始之后 
 * 3、由于原始输入数组本身可能包含重复元素，所以需要对选取到的三元组进行筛选移除重复三元组 
 */
    vector<vector<int> > threeSum(vector<int> &num) {
        vector<vector<int> >result;
        if (num.size() < 3) return result;
        
        sort(num.begin(), num.end());
        
        const int target = 0;
        
        typedef vector<int>::iterator Iterator;
        Iterator end = num.end();
        for(Iterator a=num.begin(); a!=prev(end, 2); ++a) {
            if (*a > target) // 因为已经排序，所以如果第一个a>0,则c>b>a>0,和肯定大于0
                break;
            Iterator b = next(a);
            Iterator c = prev(end);
            
            while(b < c) {
                int sum = *a+*b+*c;
                if (sum < target) {
                    ++b;
                }
                else if (sum > target) {
                    --c;
                }
                else {
                    //vector<int> temp;
                    //temp.push_back(*a);
                    //temp.push_back(*b);
                    //temp.push_back(*c);
                    //result.push_back(temp);
                    result.push_back({*a, *b, *c});
                    
                    ++b;
                    --c;
                }
            }
        }
        
        sort(result.begin(), result.end());
        
        result.erase(unique(result.begin(), result.end()), result.end());
        
        return result;
    }
#else
/** 整理的提交代码 
 * 主要思路： 
 * 处理复杂度为O(n2)，这次是在获取符合条件的三元组时就进行了“实时”去重 
 * 1、为了选出的三元组是非递减的，先对输入数组进行排序 
 * 2、符合条件的三元组的第一个元素选取范围为[0...n-3]， 
 *    第二个元素和第三个元素的获取就如求“一个数组中的两个数的和等于某一目标值”，从第一个元素之后开始考察 
 *     如果找到符合条件的三元组则第二个向前移动，否则根据与目标值的关系来向前移动第二个元素（<目标值）或者 
 *     向后移动第三个元素（>目标值） 
 * 3、由于原始输入数组本身可能包含重复元素，所以需要对选取到的三元组进行筛选移除重复三元组
 *
 *
 * 最重要的是：在处理第一个a和第二个元素b时，遇到相邻且相同的元素要跳 过。
 * 比如：-1 -1 0 0 1 2, 对于a，当a=-1时，b=-1, c=2符合条件，但是下一个循环，a还是-1,这时就要跳过这个a了.
 * 同样，a=-1, b=0, c=1符合条件，但是第二个b=0要跳过。
 * 下面代码中带有注释 // important! 的部分是关键，就是这两个去重代替了上面的sort+unique+erase.
 */
    vector<vector<int> > threeSum(vector<int> &num) {
        vector<int> triplet;
        vector<vector<int> > result;
        sort(num.begin(), num.end());
        size_t size = num.size();
        if (size < 3)
        {
            return result;
        }
        
        size_t i, j, k;
        int sum;
        // (i<j<k && num[i]<=num[j]<=num[k]) 分别为三元组的三个元素，先取得二元组然后寻找另外一个
        for (i = 0; i < size-2; i++)
        {
            // 如果第一个考察的元素已经大于目标0，则直接返回
            if (num[i] > 0)
            {
                break;
            }  
            // 首次去重，如果当前元素考察的第一个元素与上一次考察的第一个元素相同，则跳过这次
            // 必须先判断i>0，否则在i=0时内存违规访问runtime error
            // important!
            if (i > 0 && num[i] == num[i-1])
            {
                continue;
            }
            
            // 第二个考察元素大于等于第一个考察元素，从当前考察的第一个元素之后开始考察
            j = i+1;
            // 第三个考察元素则是从数组末尾开始进行考察
            k = size - 1;
            while (j < k)
            {
                sum = num[i] + num[j] + num[k];
                if (sum == 0)
                {
                    // 如果当前元素考察的第二个元素与上一次考察的第二个元素相同，则跳过这次
                    // 限制的是符合条件的三元组中的第二个元素，这里需要判断j>i+1，以避免与第一个元素比较
                    // ！！！ 这里的去重很重要，不然在最后去重的话就超时了。 ！！！
                    // important!
                    if (num[j] == num[j-1] && j > i+1)
                    {
                        ++j;
                        continue;
                    }
                    triplet.push_back(num[i]);  // 三元组第一个元素
                    triplet.push_back(num[j]);  // 三元组第二个元素
                    triplet.push_back(num[k]);  // 三元组第三个元素
                    result.push_back(triplet);
                    triplet.clear();
                    j++;        // 第二个考察元素移动
                    k--;        // 第三个考察元素移动  // 网上的代码没有这一行，其实这一行不加也能通过，但是加上会更好。
                                // 原因是：如果num[k-1] == num[k], 则k--并不会加入重复的三元组，因为如果num[j++] = num[j]，则会上面会对j去重。
                                //         如果num[k-1] != num[k]，则即使num[j++] = num[j]，三元组的和也不为0,可以提高一点效率。
                }
                else if (sum > 0)    //
                {
                    --k;
                    // 避免重复计算，当前考察的三元组第三个元素与上一次考察的相等则跳过这个元素，
                    // 其实这时只是避免考察它，因为如果当前考察元素与上个考察元素相同，
                    // 下次的求和结果还是大于0。
                    // 所以其实它不会出现在结果中，只是为了避免下次重复计算和以及判断
                    // 可以不用判断，只是影响了一点效率，结果正确
                    //if (num[k] == num[k+1])
                    //{
                    //    --k;
                    //}
                }
                else
                {
                    ++j;
                    // 避免重复计算，这里的考虑与上面k的考虑原理相同
                    // 可以不用判断，只是影响了一点效率，结果正确
                    //if (num[j] == num[j-1])
                    //{
                    //    ++j
                    //}
                }
            }
  
        }
  
//      vector<vector<int> >::iterator end = result.end();
//      sort(result.begin(), result.end(), less<vector<int> >());   // 此处需要重新排序，比较元素为vector
//      vector<vector<int> >::iterator new_end = unique(result.begin(), end);
//      result.erase(new_end, end);
          
        return result;  
    }  
#endif
};
