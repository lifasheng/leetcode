class Solution {
public:
    uint32_t reverseBits(uint32_t n) {
        uint32_t high_mask = 0x80000000;
        uint32_t low_mask  = 0x00000001;
        while (high_mask > low_mask) {
            bool h1 = (n & high_mask) == high_mask;
            bool l1 = (n & low_mask) == low_mask;
            
            n &= ~high_mask;
            n &= ~low_mask;
            n |= l1 ? high_mask : 0;
            n |= h1 ? low_mask : 0;
            
            high_mask >>= 1;
            low_mask <<= 1;
        }
        return n;
    }
};
