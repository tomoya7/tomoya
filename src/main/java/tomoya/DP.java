package tomoya;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DP {
//    连续子数组的最大和
    public int maxSubArray(Integer[] nums){
        Integer[] dp=new Integer[nums.length];
        dp[0]=nums[0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i-1]>0)dp[i]=dp[i-1]+nums[i];
            else dp[i]=nums[i];
        }
        return Collections.max(Arrays.asList(dp));
    }
//    数字翻译成字符串的种类
    public Integer translateNum(Integer num){
        String s=String.valueOf(num);
        if(s.length()==1){
            return 1;
        }
        Integer[] dp=new Integer[s.length()];
        dp[0]=1;
        if ((s.charAt(0)+"").equals("1") || ((s.charAt(0)+"").equals("2") && s.charAt(1)<'6')){
            dp[1]=2;
        }
        else {dp[1]=1;}
        for (int i = 2; i < dp.length; i++) {
            if ((s.charAt(i-1)+"").equals("1") || ((s.charAt(i-1)+"").equals("2") && s.charAt(i)<'6')){
                dp[i]=dp[i-1]+dp[i-2];
            }
            else{
                dp[i]=dp[i-1];
            }
        }
        return dp[dp.length-1];
    }
//    无重复最长子串
    public Integer lengthOfLongestSubstring(String s){
        Integer[] dp=new Integer[s.length()];
        dp[0]=1;
        HashMap<Character,Integer> map=new HashMap<>();
        map.put(s.charAt(0),0);
        int j;
        for (int i = 1; i<dp.length; i++) {
            j = map.getOrDefault(s.charAt(i), -100);
            map.put(s.charAt(i),i);
            if (dp[i-1]<i-j) dp[i]=dp[i-1]+1;
            else dp[i]=i-j;
        }
        return Collections.max(Arrays.asList(dp));
    }
//n个骰子的点数和概率
    public double[] twoSum(int n){
        double[][] dp=new double[n+1][n*6+1];
        Arrays.fill(dp,0.0);
        for (int i = 1; i < 7; i++) {
            dp[1][i]=(double) 1/6;
        }
        for (int i = 2; i <n+1 ; i++) {
            for (int j = i; j < i*6+1; j++) {
                for (int k = 1; k < 7; k++) {
                    if (j-k>=i-1) {
                        dp[i][j] += dp[i - 1][j - k] /6;
                    }
                }
            }
        }
//        return dp[n];
        List<Double> list=new ArrayList<>();
        for (double i:dp[n]) {
            if (i!=0.0){
                list.add(i);
            }
        }
        double[] res=new double[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i]=list.get(i);
        }
        return res;
    }
//    剪绳子
    public Integer cuttingRope(Integer n){
        Integer[] dp=new Integer[n+1];
        for (int i = 1; i < dp.length; i++) {
               dp[i]=i;
        }
        for (int i = 0; i < dp.length; i++) {
            for (int j = 1; j < i; j++) {
                dp[i]=Math.max(dp[i],dp[j]*dp[i-j]);
            }
        }
        if (n==2){
            return 1;
        }
        else  if (n==3){
            return 2;
        }
        else {
            return dp[n];
        }
    }
//    最长回文子串
    public String longestPalindrome(String s){
        Boolean[][] dp=new Boolean[s.length()][s.length()];
        int max=0;
        int t=0;
        for (int d = 0; d < s.length(); d++) {
            for (int i = 0; i < s.length(); i++) {
                if (d==0) dp[i][i+d]=true;
                if (d==1 && (i+d)<s.length()) dp[i][i+d]= s.charAt(i) == s.charAt(i + d);

                if ((i+d)<s.length()&& (i+1)<s.length()&& (i+d-1)>=0 && (i+1)<=(i+d-1)) {
                    dp[i][i+d]= s.charAt(i) == s.charAt(i + d) && dp[i+1][i+d-1];
                }
                if (max<d && (i+d)<s.length()&&dp[i][i + d]){
                    max=d;
                    t=i;
                }
            }
        }
        return s.substring(t,t+max+1);
    }
//    最长回文子序列
    public int longestPalindromeSubseq(String s) {
        Integer[][] dp=new Integer[s.length()][s.length()];
        for (Integer[] d:dp){
            Arrays.fill(d,1);
        }
        for (int l=0;l<s.length();l++){
            for (int i = 0; i < s.length(); i++) {
                int j=i+l;
                if (j>s.length()-1){
                    break;
                }
                if (i==j) continue;
                else if (i==j-1){
                    if (s.charAt(i)==s.charAt(j)){
                        dp[i][j]=2;
                    }
                }
                else {
                    if (s.charAt(i)==s.charAt(j)){
                        dp[i][j]=dp[i+1][j-1]+2;
                    }
                    else {
                        dp[i][j]=Math.max(dp[i][j-1],dp[i+1][j]);
                    }
                }
            }
        }
        return dp[0][s.length()-1];
    }
//    最长公共子序列
    public int longestCommonSubsequence(String text1, String text2){
        Integer[][] dp=new Integer[text1.length()+1][text2.length()+1];
        for (Integer[] d: dp)Arrays.fill(d,0);
        for (int i = 1; i < text1.length()+1; i++) {
            for (int j = 1; j < text2.length()+1; j++) {
                if (text1.charAt(i-1)==text2.charAt(j-1))dp[i][j]=dp[i-1][j-1]+1;
                else dp[i][j]=Math.max(dp[i][j-1],dp[i-1][j]);
            }
        }
        return  dp[text1.length()][text2.length()];
    }
//    最长上升子序列
    public int lengthOfLIS(int[] nums){
        if (nums.length==0)return  0;
        Integer[] dp=new Integer[nums.length];
        Arrays.fill(dp,1);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < i; j++) if (nums[i]>nums[j]) dp[i]=Math.max(dp[i],1+dp[j]);
        }
        return Collections.max(Arrays.asList(dp));
    }
//    礼物最大价值
    public int maxValue(int[][] grid){
        int[][] dp=new int[grid.length][grid[0].length];
        dp[0][0]=grid[0][0];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (j-1>=0 && i-1 >=0) dp[i][j]=grid[i][j]+Math.max(dp[i][j-1],dp[i-1][j]);
                else if (i-1>=0) dp[i][j]=grid[i][j]+dp[i-1][j];
                else if (j-1>=0)dp[i][j]=grid[i][j]+dp[i][j-1];
            }
        }
        return dp[grid.length-1][grid[0].length-1];
    }
//    吃橘子最少次数
public int[] minCount(int n) {
    int[] dp=new int[n+1];
    dp[1]=1;
    for (int i = 2; i < dp.length; i++) {
        if (i % 2 == 0) dp[i] = dp[i - i / 2] + 1;
        if (i % 3 == 0) dp[i] = dp[i - 2 * (i / 3)] + 1;
        if (i % 3 == 0 && i % 2 == 0) dp[i] = Math.min(dp[i - i / 2], dp[i - 2 * (i / 3)]) + 1;
        if(!(i%3==0||i%2==0)) dp[i] = dp[i - 1]+1;
    }
    return dp;
}
//    分割等和子集
    public boolean canPartition(int[] nums) {
        int sum=0;
        for (int num:nums) sum+=num;
        if (sum%2==1) return false;
        int target=sum/2;
        boolean[][] dp= new boolean[nums.length][target+1];
        for (boolean[] booleans : dp) Arrays.fill(booleans, false);
        dp[0][0]=true;
        for (int i = 1; i <  nums.length; i++) {
            for (int j = 0; j < target+1; j++) {
                if (j-nums[i]>=0) dp[i][j]=dp[i-1][j] || dp[i-1][j-nums[i]];
                else dp[i][j]=dp[i-1][j];
            }
        }
        return dp[nums.length-1][target];
    }
    //    零钱兑换2
    public int change(int amount,int[] coins) {
        int[][] dp = new int[coins.length][amount + 1];
        for (int[] ints : dp) Arrays.fill(ints, 0);
        dp[0][0] = 1;
        for (int i = coins[0]; i < amount + 1; i += coins[0]) dp[0][i] = 1;
        for (int i = 1; i < coins.length; i++) {
            for (int j = 0; j < amount + 1; j++) {
                for (int k = 0; j - k * coins[i] >= 0; k++) {
                    dp[i][j] += dp[i - 1][j - k * coins[i]];
                }
            }
        }
        return dp[coins.length - 1][amount];
    }
    //零钱兑换1
    public int coinChange(int[] coins, int amount) {
        int [] dp=new int[amount+1];
        Arrays.fill(dp,100);
        dp[0]=0;
        for (int i = 1; i < dp.length; i++) for (int coin : coins) if (i - coin >= 0) dp[i] = Math.min(dp[i], dp[i - coin]+1) ;
        return dp[amount]>=100? -1:dp[amount];
    }
//  最大正方形
    public int maximalSquare(char[][] matrix) {
        int max=0;
        int[][] dp=new int[matrix.length][matrix[0].length];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (matrix[i][j]=='1'){
                    if (i==0|| j==0)dp[i][j]=1;
                    else dp[i][j]=Math.min(Math.min(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1])+1;
                    max=Math.max(max,dp[i][j]);
                }
            }
        }
        return  max;
    }
//    统计全为1的正方形子矩阵
    public int countSquares(int[][] matrix) {
        int sum=0;
        int[][] dp=new int[matrix.length][matrix[0].length];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (matrix[i][j]==1){
                    if (i==0|| j==0)dp[i][j]=1;
                    else dp[i][j]=Math.min(Math.min(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1])+1;
                    sum+=dp[i][j];
                }
            }
        }
        return  sum;
    }
    public static void main(String[] args){
        Main main=new Main();
    }
}
