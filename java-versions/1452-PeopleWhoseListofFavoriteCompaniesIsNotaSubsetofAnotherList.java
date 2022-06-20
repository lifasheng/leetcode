/*
Given the array favoriteCompanies where favoriteCompanies[i] is the list of favorites companies for the ith person (indexed from 0).

Return the indices of people whose list of favorite companies is not a subset of any other list of favorites companies. You must return the indices in increasing order.

 

Example 1:

Input: favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],["google"],["amazon"]]
Output: [0,1,4] 
Explanation: 
Person with index=2 has favoriteCompanies[2]=["google","facebook"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] corresponding to the person with index 0. 
Person with index=3 has favoriteCompanies[3]=["google"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] and favoriteCompanies[1]=["google","microsoft"]. 
Other lists of favorite companies are not a subset of another list, therefore, the answer is [0,1,4].
Example 2:

Input: favoriteCompanies = [["leetcode","google","facebook"],["leetcode","amazon"],["facebook","google"]]
Output: [0,1] 
Explanation: In this case favoriteCompanies[2]=["facebook","google"] is a subset of favoriteCompanies[0]=["leetcode","google","facebook"], therefore, the answer is [0,1].
Example 3:

Input: favoriteCompanies = [["leetcode"],["google"],["facebook"],["amazon"]]
Output: [0,1,2,3]
*/



class Solution {
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        Map<String, Integer> m = new HashMap<>();

        // transform string to integer, use set to check intersection
        List<Set<Integer>> indexes = new ArrayList<>();
        int index = 1;
        for (List<String> stringList : favoriteCompanies) {
            Set<Integer> integerSet = new HashSet<>();
            for (String str : stringList) {
                if (!m.containsKey(str)) {
                    m.put(str, index);
                    ++index;
                }
                integerSet.add(m.get(str));
            }
            
            indexes.add(integerSet);
        }
        
        // favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],["google"],["amazon"]]
        // indexes = [[1, 2, 3], [2, 4], [2, 3], [2], [5]]
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < indexes.size(); ++i) {
            boolean isISubsetAny = false;
            for (int j = 0; j < indexes.size(); ++j) {
                // if len(i) > len(j), i can not be subset of j
                if (i == j || indexes.get(i).size() > indexes.get(j).size()) { 
                    continue;
                }
                
                boolean isISubSetOfJ = true;
                for (int pi : indexes.get(i)) {
                    if (!indexes.get(j).contains(pi)) {
                        isISubSetOfJ = false;
                    }
                }
                if (isISubSetOfJ) {
                    isISubsetAny = true;
                }
            }
            
            if (!isISubsetAny) {
                result.add(i);
            }
        }
        
        return result;
        
        
    }
}


