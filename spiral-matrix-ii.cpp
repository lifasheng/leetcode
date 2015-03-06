class Solution {
public:
    vector<vector<int> > generateMatrix(int n) {
        vector<vector<int> > matrix;
        if (n<=0) return matrix;
        
        matrix.resize(n);
        for(int i=0;i<n;++i) { matrix[i].resize(n); }
        
        int k=0;
        int beginX=0, endX=n-1;
        int beginY=0, endY=n-1;
        while(1) {
            for(int j=beginX; j<=endX; ++j) matrix[beginY][j] = ++k;
            ++beginY;
            if (beginY > endY) break;
            
            for(int i=beginY; i<=endY; ++i) matrix[i][endX] = ++k;
            --endX;
            if (beginX > endX) break;
            
            for(int j=endX; j>=beginX; --j) matrix[endY][j] = ++k;
            --endY;
            if (beginY > endY) break;
            
            for(int i=endY; i>=beginY; --i) matrix[i][beginX] = ++k;
            ++beginX;
            if (beginX > endX) break;
        }
        
        assert(k == n*n);
        
        return matrix;
    }
};
