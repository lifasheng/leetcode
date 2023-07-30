#include <iostream>
#include <cassert>
#include <string>
using namespace std;

/*
Find the total area covered by two rectilinear rectangles in a 2D plane.

Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.
Rectangle Area

Assume that the total area is never beyond the maximum possible value of int.
*/

struct Point {
    int x, y;
    Point(int xx, int yy): x(xx), y(yy) {
    }
};
struct Rectangle {
    Point bottomLeft, topRight; 
public:
    Rectangle(int A, int B, int C, int D): bottomLeft(A, B), topRight(C, D) {
    }
    int area() const {
        return (topRight.x - bottomLeft.x) * (topRight.y-bottomLeft.y);
    }
    void swap(Rectangle &rhs) {
        std::swap(bottomLeft.x, rhs.bottomLeft.x);
        std::swap(bottomLeft.y, rhs.bottomLeft.y);
        std::swap(topRight.x, rhs.topRight.x);
        std::swap(topRight.y, rhs.topRight.y);
    }
    friend Rectangle intersect(Rectangle A, Rectangle B);
    friend ostream& operator<<(ostream &out, const Rectangle &rect);
};

ostream& operator<<(ostream &out, const Rectangle &rect) {
    out << "(" << rect.bottomLeft.x << ", " << rect.bottomLeft.y << ") ";
    out << "(" << rect.topRight.x << ", " << rect.topRight.y << ") ";
    return out;
}
Rectangle intersect(Rectangle A, Rectangle B) {
    // 先保证A 在 B 的左边
    if (A.bottomLeft.x > B.bottomLeft.x) {
        A.swap(B);
    }

    //cout << A << "    " << B << endl;
    // 不相交，分开的
    // B在A的上面，下面，或右面
    if (A.topRight.y <= B.bottomLeft.y ||
            A.bottomLeft.y >= B.topRight.y ||
            A.topRight.x <= B.bottomLeft.x) {
        return Rectangle(0, 0, 0, 0);
    }

    // A完全包含B
    else if (B.bottomLeft.x >= A.bottomLeft.x && B.topRight.x <= A.topRight.x
            && B.bottomLeft.y >= A.bottomLeft.y && B.topRight.y <= A.topRight.y) {
        return B;
    }

    // A 和 B 重叠
    else {
        return Rectangle(max(A.bottomLeft.x, B.bottomLeft.x),
                         max(A.bottomLeft.y, B.bottomLeft.y),
                         min(A.topRight.x, B.topRight.x),
                         min(A.topRight.y, B.topRight.y));
    }
}


#if 1
class Solution {
public:
    int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        Rectangle ra(A,B,C,D), rb(E,F,G,H);
        return ra.area() + rb.area() - intersect(ra, rb).area();
    }
};

#else

class Solution {
public:
    int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int x = min(G, C) > max(E, A) ? (min(G, C) - max(E, A)) : 0;
        int y = min(D, H) > max(B, F) ? (min(D, H) - max(B, F)) : 0;
        return (D - B) * (C - A) + (G - E) * (H - F) - x * y;
    }
};

#endif

int main() {
    Solution solution;

    assert(solution.computeArea(-3, 0, 3, 4,  0, -1, 9, 2) == 45);
    assert(solution.computeArea(0, 0, 0, 0,  -1, -1, 1, 1) == 4);
    assert(solution.computeArea(-2, -2, 2, 2,  -3, -3, 3, -1) == 24);

    return 0;
}
