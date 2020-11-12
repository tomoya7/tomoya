package tomoya;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
public class Recursion {
//    求i的m次方
    public int qiumi(int i ,int m){
        if (m==1){
            return i;
        }
        int tem=qiumi(i,m/2);
        return tem*tem*qiumi(i,m%2);
    }
//    1出现的次数
    public int num1Count(int n){
        if (String.valueOf(n).length()==1)return n==0? 0:1;
        String s=String.valueOf(n);
        int tem=Integer.parseInt(s.charAt(0)+"");
        int pow=(int)Math.pow(10,s.length()-1);
        int num=(n-tem*pow);
        if (tem==1) return num1Count(num)+tem*num1Count(pow-1)+num+1;
        return num1Count(num)+tem*num1Count(pow-1)+pow;
    }
//    正则匹配
    public Boolean isMatch(String s,String p) {
        if (p.length()==0) return s.length() == 0;
        if (p.length()==1) {
            char c=p.charAt(0);
            return s.length() == 1 && (c == '.' || c == s.charAt(0));
        }
        if (s.length()==0) {
            if (p.length()%2==1) return false;
            for (int i = 0; i < p.length()/2; i++) if (p.charAt(i*2+1)!='*') return false;
            return true;
        }
        Boolean first_match=Arrays.asList(new String[]{".",s.charAt(0)+""}).contains(p.charAt(0)+"");
        if (p.charAt(1)=='*') return first_match && isMatch(s.substring(1),p) || isMatch(s,p.substring(2));
        else return  first_match && isMatch(s.substring(1),p.substring(1));
    }
//    青蛙跳
    public int numWays(int n) {
        if(n==0) return 1;
        if(n==1||n==2) return n;
        return numWays(n-2)+numWays(n-1);
    }
//  递归乘法
    public int multiply(int A, int B) {
        if (B==1)return A;
        return A+multiply(A,B-1);
    }
    public static  void main(String[] args){
        Recursion recursion=new Recursion();
//        log.debug(String.valueOf(recursion.qiumi(4,3)));
        log.debug(String.valueOf(recursion.num1Count(12)));
//        System.out.println(recursion.isMatch("aab","c*a*b"));
//        String[][] matrix=recursion.createMatrix();
//        String s=new String("abcced");
//        System.out.println(recursion.exist(matrix,s));
    }
}
