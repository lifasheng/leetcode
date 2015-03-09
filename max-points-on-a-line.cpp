/**
 * Definition for a point.
 * struct Point {
 *     int x;
 *     int y;
 *     Point() : x(0), y(0) {}
 *     Point(int a, int b) : x(a), y(b) {}
 * };
 */
class Solution {
public:
#define M2
#ifdef M1
/*
// 暴力枚举法,以边为中心,时间复杂度 O(n^3),空间复杂度 O(1)
斜率计算：
ky - iy       jy - iy
--------  == ---------
kx - ix       jx - ix
由于用除法不仅有浮点数判断问题，还有除零判断等特殊情况。
而改用乘法来判断就可以避免这些问题，这种方法对于垂直或水平的点也能处理。
(ky-iy) * (jx-ix) == (kx-ix) * (jy-iy)
但是我们要对垂直或水平这两种情况中的一种单独处理，不然 L行的三个点也会被认为在一条线上。
其实他们是两个x坐标相同，另外两个y坐标相同。
*/
    int maxPoints(vector<Point> &points) {
        const int n = points.size();
        if (n<=2) return n;
        
        int result = 0;
        for(int i=0; i<n; ++i) {
            for(int j=i+1; j<n; ++j) {
                int count = 2; // i and j are in a same line
                
                // 这里对垂直线进行特殊处理。
                if (points[i].x == points[j].x) { // 垂直线
                    for(int k=0; k<n; ++k) {
                        if (k!=i && k!=j && points[k].x == points[i].x) {
                            ++count;
                        }
                    }
                    result = max(result, count);
                }
                else { // 水平线或斜线，这里不用除法计算斜率，而改用乘法。
                    int jiy = points[j].y - points[i].y;
                    int jix = points[j].x - points[i].x;
                    for(int k=0; k<n; ++k) {
                        if (k!=i && k!=j) {
                            int kiy = points[k].y - points[i].y;
                            int kix = points[k].x - points[i].x;
                            long long kiy_jix = kiy * jix;
                            long long kix_jiy = kix * jiy;
                            if (kiy_jix == kix_jiy) {
                                ++count;
                            }
                        }
                    }
                    result = max(result, count);
                }
            }
        }
        
        return result;
    }
#endif
#ifdef M2
/*
// 暴力枚举,以点为中心,时间复杂度 O(n^2),空间复杂度 O(n)
注意：我们不能简单地先两两计斜率，再统计每个斜率上有多少点，因为一条直线除了斜率外，还得有一个点来最终确定这条线。
斜率相同的不一定是同一条线，也可能是平行线。
我们要对每个点，计算该点和其它各点的斜率，这样斜率相同的就是在同一直线上。
对于重合点，需要特殊考虑，虽然它的slope为无穷大，但我们不能将这种情况归为slope=无穷大这个斜率中。
因为它可以加任何经过该点的其它斜率中去。
*/
    int maxPoints(vector<Point> &points) {
        const int n = points.size();
        if (n<=2) return n;
        
        int result = 0;
        unordered_map<double, int> slope_count;
        for(int i=0; i<n; ++i) {
            slope_count.clear(); 
            int samePointNum = 0; // 与i重合的点
            int point_max = 1;    // 与i共线的点
            
            for(int j=i+1; j<n; ++j) {
                double slope;
                if (points[i].x == points[j].x) {
                    slope = std::numeric_limits<double>::infinity();
                    
                    // 对重合点，特殊处理。
                    if (points[i].y == points[j].y) {
                        ++samePointNum;
                        continue;
                    }
                }
                else {
                    slope = 1.0 * (points[j].y - points[i].y) / (points[j].x - points[i].x);
                }
                
                int count = 0;
                if (slope_count.find(slope) != slope_count.end()) {
                    count = ++slope_count[slope];
                }
                else {
                    count = slope_count[slope] = 2;
                }
                
                point_max = max(point_max, count);
            }
            result = max(result, point_max + samePointNum);
        }
        return result;
    }
#endif
};
