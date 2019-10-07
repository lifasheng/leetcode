/*
Given a array of N strings, find the longest common prefix among all strings present in the array.

Input:
The first line of the input contains an integer T which denotes the number of test cases to follow. Each test case contains an integer N. Next line has space separated N strings.

Output:
Print the longest common prefix as a string in the given array. If no such prefix exists print "-1"(without quotes).

Constraints:
1 <= T <= 103
1 <= N <= 103
1 <= |S| <= 103

Example:
Input:
2
4
geeksforgeeks geeks geek geezer
3
apple ape april

Output:
gee
ap

Explanation:
Testcase 1: Longest common prefix in all the given string is gee.
*/


// https://practice.geeksforgeeks.org/problems/longest-common-prefix-in-an-array/0


// https://www.geeksforgeeks.org/longest-common-prefix-using-character-by-character-matching/
// try it on https://www.onlinegdb.com/

#include <iostream>
#include <vector>
#include <sstream>
#include <string>

using namespace std;

string getStringFromCommandLine() {
    std::string line;
	std::getline(std::cin, line);
	return line;
}

int getIntFromCommandLine() {
    std::string line = getStringFromCommandLine();
	istringstream iss(line);
	int res;
	iss >> res;
	return res;
}

vector<string> splitString(const string &input) {
    vector<string> vs;
    std::istringstream iss(input);
    std::string token;

    while ( getline(iss, token, ' ') ) {
        //cout << "token: "<< token<<endl;
        vs.push_back(token);
    }
    return vs;
}

bool isSameCharAtPos(int pos, const vector<string> &vs) {
    for (int j=1; j<vs.size(); ++j) {
        if (vs[j].size() <= pos) return false;
        if (vs[j][pos] != vs[0][pos]) return false;
    }
    return true;
}

string findCommonPrefix(const vector<string> &vs) {
    if (vs.empty() || vs[0].empty()) return "";
    
    int commonLen = 0;
    const string & s0 = vs[0];
    for(int i=0; i<s0.size(); ++i) {
        if (isSameCharAtPos(i, vs)) {
            ++commonLen;
        } else {
            break;
        }
    }
    if (commonLen > 0) {
        //cout << "commonLen = " << commonLen <<endl;
        return vs[0].substr(0, commonLen);
    } else {
        return "-1";
    }
}

int main() {

	int T = getIntFromCommandLine();
	// cout << "T: " << T << endl;
	
	while(T) {
	    --T;
	    int N = getIntFromCommandLine();
	    // cout << "N: " << N << endl;
	    string line = getStringFromCommandLine();
	    // cout << "line: " << line << endl;
	    vector<string> vs = splitString(line);
	    
	    cout << findCommonPrefix(vs) << endl;
	}
	
	return 0;
}




/////////////////////////////////////
// https://www.geeksforgeeks.org/longest-common-prefix-using-word-by-word-matching/


// https://www.geeksforgeeks.org/longest-common-prefix-using-linked-list/



// https://www.geeksforgeeks.org/longest-common-prefix-using-binary-search/


// https://www.geeksforgeeks.org/longest-common-prefix-using-trie/



