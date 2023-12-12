/*
In a special ranking system, each voter gives a rank from highest to lowest to all teams participating in the competition.

The ordering of teams is decided by who received the most position-one votes. If two or more teams tie in the first position, we consider the second position to resolve the conflict, if they tie again, we continue this process until the ties are resolved. If two or more teams are still tied after considering all positions, we rank them alphabetically based on their team letter.

You are given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams according to the ranking system described above.
Return a string of all teams sorted by the ranking system.

Example 1:

Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
Output: "ACB"
Explanation: 
Team A was ranked first place by 5 voters. No other team was voted as first place, so team A is the first team.
Team B was ranked second by 2 voters and ranked third by 3 voters.
Team C was ranked second by 3 voters and ranked third by 2 voters.
As most of the voters ranked C second, team C is the second team, and team B is the third.
Example 2:

Input: votes = ["WXYZ","XYZW"]
Output: "XWYZ"
Explanation:
X is the winner due to the tie-breaking rule. X has the same votes as W for the first position, but X has one vote in the second position, while W does not have any votes in the second position. 
Example 3:

Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
Explanation: Only one voter, so their votes are used for the ranking.
 

Constraints:

1 <= votes.length <= 1000
1 <= votes[i].length <= 26
votes[i].length == votes[j].length for 0 <= i, j < votes.length.
votes[i][j] is an English uppercase letter.
All characters of votes[i] are unique.
All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
*/



class Solution {

    public String rankTeams(String[] votes) {
        int m = votes.length;
        if (m == 1) return votes[0];
        int n = votes[0].length();

        // for each person, we need to calculate their ranking at each position.
        Map<Character, Map<Integer, Integer>> personToPosAndCountMap = calculatePersonToPosAndCountMap(votes);
        List<Map.Entry<Character, Map<Integer, Integer>>> personToPosAndCountEntries = new ArrayList<>(personToPosAndCountMap.entrySet());
        Collections.sort(personToPosAndCountEntries, (e1, e2) -> {
            Map<Integer, Integer> m1 = e1.getValue();
            Map<Integer, Integer> m2 = e2.getValue();
            for (int i = 0; i < n; ++i) {
                int v1 = m1.getOrDefault(i, 0);
                int v2 = m2.getOrDefault(i, 0);
                if (v1 != v2) {
                    return v2 - v1;
                }
            }
            return e1.getKey() - e2.getKey();
        });
        return getSortedTeams(personToPosAndCountEntries);
        
    }

    private Map<Character, Map<Integer, Integer>> calculatePersonToPosAndCountMap(String[] votes) {
        int m = votes.length;
        int n = votes[0].length();
        Map<Character, Map<Integer, Integer>> personToPosAndCountMap = new HashMap<>();

        for (String vote : votes) {
            for (int i = 0; i < n; ++i) {
                char c = vote.charAt(i);
                personToPosAndCountMap.putIfAbsent(c, new HashMap<>());
                Map<Integer, Integer> posToCountMap = personToPosAndCountMap.get(c);
                posToCountMap.put(i, posToCountMap.getOrDefault(i, 0) + 1);
            }
        }
        return personToPosAndCountMap;
    }

    private String getSortedTeams(List<Map.Entry<Character, Map<Integer, Integer>>> personToPosAndCountEntries) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Map<Integer, Integer>> entry : personToPosAndCountEntries) {
            sb.append(entry.getKey());
        }
        return sb.toString();
    }
}







// 更好的方法， 思路类似 : https://learnku.com/articles/41355
class Solution {

    public String rankTeams(String[] votes) {
        int m = votes.length;
        if (m == 1) return votes[0];
        int n = votes[0].length();

        Map<Character, int[]> personToPosAndCountMap = calculatePersonToPosAndCountMap(votes);
        PriorityQueue<Character> maxPQ = new PriorityQueue<>((a, b) -> {
            int[] rankA = personToPosAndCountMap.get(a);
            int[] rankB = personToPosAndCountMap.get(b);
            for (int i = 0; i < n; ++i) {
                if (rankA[i] != rankB[i]) {
                    return rankB[i] - rankA[i];
                }
            }
            return a - b;
        });

        for (int i = 0; i < n; ++i) {
            maxPQ.offer(votes[0].charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        while (!maxPQ.isEmpty()) {
            sb.append(maxPQ.poll());
        }
        
        return sb.toString();
    }

    private Map<Character, int[]> calculatePersonToPosAndCountMap(String[] votes) {
        int m = votes.length;
        int n = votes[0].length();
        Map<Character, int[]> personToPosAndCountMap = new HashMap<>();

        for (String vote : votes) {
            for (int i = 0; i < n; ++i) {
                char c = vote.charAt(i);
                personToPosAndCountMap.putIfAbsent(c, new int[n]);
                personToPosAndCountMap.get(c)[i]++;
            }
        }
        return personToPosAndCountMap;
    }
}


