public class LongestPalindromeSubsequence{
public int longestPalindromeSubseq(String s) {
        int n = s.length();
        String reverse = "";
        
        for (int i = n - 1;  i >= 0; i--) {
            reverse += s.charAt(i) + "";
        }
        
        int[][] dp = new int[n+1][n+1];
        
        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < n+1; j++) {
                if (s.charAt(i-1) == reverse.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        
        return dp[n][n];
    }

}
