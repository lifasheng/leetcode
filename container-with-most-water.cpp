class Solution {
public:
    int maxArea(vector<int> &height) {
        const int n = height.size();
        
        int result = INT_MIN;
        int left = 0, right = n-1;
        while(left<right) {
            int area = min(height[left], height[right])*(right-left);
            result = max(result, area);
            if (height[left] <= height[right]) {
                ++left;
            }
            else {
                --right;
            }
        }
        
        return result;
    }
};
