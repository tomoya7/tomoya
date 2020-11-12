package tomoya;
import java.util.*;
public class DFS {
    //    岛屿数量
    char[][] grid1;
    LinkedList<String> list1;
    public int numIslands(char[][] grid) {
        this.grid1=grid;
        this.list1=new LinkedList<>();
        int count=0;
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[0].length; j++) {
                if (grid1[i][j]=='1'){
                    dfs1(i,j);
                    count+=1;
                    //    System.out.println(list1);
//                    list1.clear();
                }
            }
        }
        return count;
    }
    public void dfs1(int i,int j){
        for (int[] t:new int[][]{{i+1,j},{i-1,j},{i,j+1},{i,j-1}}) {
            if (valid(t)){
                grid1[t[0]][t[1]]='0';
                dfs1(t[0],t[1]);
            }
        }
    }
    public boolean valid(int[] t){
        int i=t[0];
        int j=t[1];
        return i>=0&&i<grid1.length&&j>=0&&j<grid1[0].length&&grid1[i][j]=='1'&&!list1.contains(i+"-"+j);
    }
    //    求和路径
    LinkedList<Integer> list2;
    int target2;
    List<List<Integer>> res2;
    public int pathSum(TreeNode root, Integer sum) {
        res2=new ArrayList<>();
        if (root==null) return  res2.size();
        list2=new LinkedList<>();
        target2=sum;
        dfs2(root);
        return res2.size()+pathSum(root.left, sum)+pathSum(root.right, sum);
    }
    public void dfs2(TreeNode root){
        if (sum2()==target2) res2.add(new ArrayList<>(list2));
        for (TreeNode node:new TreeNode[]{root.left,root.right}){
            if (node!=null){
                list2.add(node.val);
                dfs2(node);
                list2.removeLast();
            }
        }
    }
    public int sum2(){
        int sum=0;
        for (int i:list2) sum+=i;
        return sum;
    }
//    幂集
    List<List<Integer>> res3;
    LinkedList<Integer> list3;
    int[] nums3;
    public List<List<Integer>> subsets(int[] nums) {
        res3=new ArrayList<>();
        list3=new LinkedList<>();
        this.nums3=nums;
        for (int i = 0; i < nums.length+1; i++) {
            dfs(0,i);
        }
        return res3;
    }
    public void dfs(int row,int len){
        if (row==len){
            List<Integer> l=new ArrayList<>(list3);
            for (int i = 0; i < l.size(); i++) l.set(i,nums3[l.get(i)]);
            res3.add(new LinkedList<>(l));
            return;
        }
        for (int i=0;i<nums3.length;i++) {
            if (list3.isEmpty()||list3.getLast()<i){
                list3.add(i);
                dfs(row+1,len);
                list3.removeLast();
            }
        }
    }
    //节点间通路
    List<Integer>[] arr4;
    LinkedList<Integer> list4;
    int target4;
    boolean flag;
    public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
        arr4=new ArrayList[n];
        Arrays.fill(arr4,null);
        for (int[] tem:graph) {
            if (arr4[tem[0]]==null) arr4[tem[0]]=new ArrayList<>();
            arr4[tem[0]].add(tem[1]);
        }
        list4=new LinkedList<>();
        this.target4=target;
        flag=false;
        dfs4(start);
        return flag;
    }
    public void dfs4(int start){
        if (start==target4||arr4[start]==null){
            if (start==target4) flag=true;
            return;
        }
        for (Integer i: arr4[start]){
            if(!list4.contains(i)){
                list4.add(i);
                dfs4(i);
                list4.removeLast();
            }
        }
    }
//    顺时针打印矩阵
    LinkedList<String> list5;
    int[][] matrix5;
    public List spiralOrder(int[][] matrix) {
        list5=new LinkedList<>();
        this.matrix5=matrix;
        list5.add(0+"-"+0);
        while (list5.size()<matrix5.length*matrix5[0].length){
            dfs5(Integer.parseInt(list5.getLast().split("-")[0]),Integer.parseInt(list5.getLast().split("-")[1]),1);
            dfs5(Integer.parseInt(list5.getLast().split("-")[0]),Integer.parseInt(list5.getLast().split("-")[1]),2);
            dfs5(Integer.parseInt(list5.getLast().split("-")[0]),Integer.parseInt(list5.getLast().split("-")[1]),3);
            dfs5(Integer.parseInt(list5.getLast().split("-")[0]),Integer.parseInt(list5.getLast().split("-")[1]),4);
        }
        List<Integer> res=new ArrayList<>();
        for (String s:list5) {
            int j=Integer.parseInt(s.split("-")[0]);
            int k=Integer.parseInt(s.split("-")[1]);
            res.add(this.matrix5[j][k]);
        }
        return res;
    }
    public void  dfs5(int i ,int j,int flag){
        if (flag==1) j+=1;
        if (flag==2) i+=1;
        if (flag==3) j-=1;
        if (flag==4) i-=1;
        if (i>=0 && i<matrix5.length &&j>=0 && j<matrix5[0].length && !list5.contains(i+"-"+j)){
            list5.add(i+"-"+j);
            dfs5(i,j,flag);
//            list10.removeLast();
        }
    }
//    字符串的排列
    String s6;
    Set<String> res6;
    LinkedList<Integer> list6;
    public String[] permutation(String s) {
        this.s6=s;
        list6=new LinkedList<>();
        res6=new HashSet<>();
        dfs6(0);
        return res6.toArray(new String[0]);
    }
    public void dfs6(int row){
        if (row==s6.length()){
            StringBuilder s=new StringBuilder();
            for (int i:list6) s.append(s6.charAt(i));
            res6.add(s.toString());
            return;
        }
        for (int i=0;i<s6.length();i++) {
            if (!list6.contains(i)){
                list6.push(i);
                dfs6(row+1);
                list6.pop();
            }
        }
    }
//    机器人的运动范围
    LinkedList<String> list7;
    int m7;
    int n7;
    int k7;
    public int movingCount(int m,int n,int k){
        this.list7=new LinkedList<>();
        this.m7=m;
        this.n7=n;
        this.k7=k;
        dfs7(0,0);
        list7.addFirst(0+"-"+0);
//        System.out.println(list7);
        return list7.size();
    }
    public  void dfs7(int i,int j){
        for (Integer[] nums:new Integer[][]{{i+1,j},{i,j+1}}) {
            if (valid7(nums[0],nums[1])){
                list7.add(nums[0]+"-"+nums[1]);
                dfs7(nums[0],nums[1]);
//                list.removeLast();
            }
        }
    }
    public boolean valid7(int i,int j){
        return i>=0 && i<m7&&j>=0 && j<n7 && !list7.contains(i+"-"+j)&&assist7(i,j)<=k7;
    }
    public int assist7(int i,int j){
        int sum=0;
        for (char c:String.valueOf(i).toCharArray()) sum+=Integer.parseInt(c+"");
        for (char c:String.valueOf(j).toCharArray()) sum+=Integer.parseInt(c+"");
        return sum;
    }
//    二叉树前序遍历
    LinkedList<Integer> list8;
    public List<Integer> preOrder(TreeNode root){
        this.list8=new LinkedList<>();
        dfs8(root);
        list8.addFirst(root.val);
        return list8;
    }
    public void dfs8(TreeNode root){
        for (TreeNode node:new TreeNode[]{root.left,root.right}){
            if (node != null) {
                list8.add(node.val);
                dfs8(node);
//                list.removeLast();
            }
        }
    }
//    二叉树根节点到每一个叶子节点的路径
    LinkedList<Integer> list9;
    List<LinkedList<Integer>> res9;
    public List<LinkedList<Integer>> rootToLeaf(TreeNode root){
        list9=new LinkedList<>();
        res9=new ArrayList<>();
        dfs9(root);
        for (LinkedList<Integer> r:res9) r.addFirst(root.val);
        return res9;
    }
    public  void  dfs9(TreeNode root){
        if (root.right==null && root.left==null){
            res9.add(new LinkedList<>(list9));
            return;
        }
        for (TreeNode node:new TreeNode[]{root.left,root.right}) {
            if (node != null) {
                list9.add(node.val);
                dfs9(node);
                list9.removeLast();
            }
        }
    }
//    括号生成
    int n10;
    LinkedList<Character> list10;
    List<List<Character>> res10;
    public List<List<Character>> generateParenthesis(int n){
        list10=new LinkedList<>();
        res10=new ArrayList<>();
        this.n10=n;
        dfs10(0);
        return res10;
    }
    public  void dfs10(int row){
        if (row==n10*2){
            res10.add(new LinkedList<>(list10));
            return;
        }
        for (char c:"()".toCharArray()){
            if (valid10(c)){
                list10.add(c);
                dfs10(row+1);
                list10.removeLast();
            }
        }
    }
    public Boolean valid10(char c){
        int left=0;
        int right=0;
        for (char ch:list10) {
            if (ch=='(') left+=1;
            else right+=1;
        }
        if (c=='(') left+=1;
        else right+=1;
        return left>=right&& left<=n10;
    }
    //    n皇后
    int n11;
    LinkedList<Integer> list11=new LinkedList<>();
    List<List<Integer>> res11=new ArrayList<>();
    public List<List<Integer>> solveNQueens(int n){
        this.n11=n;
        dfs11(0);
        return res11;
    }
    public void dfs11(int row){
        if (row==n11) {
            res11.add(new LinkedList<>(list11));
            return;
        }
        for (int i = 0; i < n11; i++) {
            if(valid11(i,row)){
                list11.add(i);
                dfs11(row+1);
                list11.removeLast();
            }
        }
    }
    public Boolean valid11(int i,int row){
        for (int j=0;j<list11.size();j++) if (i==list11.get(j)||Math.abs(row-j)==Math.abs(list11.get(j)-i)) return false;
        return true;
    }
    //    矩阵中的路径
    String word12;
    LinkedList<String> list12;
    List<List<String>> res12;
    char[][] board12;
    public boolean exist(char[][] board, String word){
        this.word12=word;
        this.board12=board;
        list12=new LinkedList<>();
        res12=new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j]==word.charAt(0)) {
                    list12.add(i+"-"+j);
                    dfs12(i,j,1);
                    if (res12.size()!=0) return true;
                    list12.clear();
                    res12.clear();
                }
            }
        }
        return false;
    }
    public void dfs12(int i,int j,int row){
        if (row==word12.length()){
            res12.add(new LinkedList<>(list12));
            return;
        }
        for (Integer[] nums:new Integer[][]{{i+1, j},{i,j+1},{i-1, j},{i,j-1}}) {
            if(valid12(nums[0],nums[1],row)) {
                list12.add(nums[0]+"-"+nums[1]);
                dfs12(nums[0],nums[1],row+1);
                list12.removeLast();
            }
        }
    }
    public Boolean valid12(int i,int j,int row){
        return 0<=i && i<board12.length && 0<=j && j<board12[0].length && !list12.contains(i+"-"+j)&&board12[i][j]==word12.charAt(row);
    }
//    朋友圈
    LinkedList<Integer> list13;
    int[][] M3;
    public int findCircleNum(int[][] M) {
        list13=new LinkedList<>();
        this.M3=M;
        int count=0;
        for (int i = 0; i < M.length; i++) {
            if (!list13.contains(i)) {
                dfs13(i);
                count+=1;
            }
        }
        return count;
    }
    public void dfs13(int start){
        for (int i=0;i<M3.length;i++){
            if (M3[start][i]==1 && i!=start && !list13.contains(i)){
                list13.add(i);
                dfs13(i);
//                list1.removeLast();
            }
        }
    }
//    矩阵中的最长递增路径
    int[][] matrix14;
    LinkedList<Integer> list14;
    List<Integer> res14;
    public int longestIncreasingPath(int[][] matrix) {
        this.matrix14=matrix;
        this.list14=new LinkedList<>();
        this.res14=new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                this.list14.add(matrix[i][j]);
                dfs14(i,j);
                this.list14.clear();
            }
        }
        return res14.size();
    }
    public  void  dfs14(int i,int j){
        if (assist14(i+1,j) && assist14(i-1,j) && assist14(i,j+1) && assist14(i,j+1)){
            if (res14.size() <list14.size())res14=new ArrayList<>(list14);
            return;
        }
        for (int [] t:new int[][]{{i+1,j},{i-1,j},{i,j-1},{i,j+1}}) {
            if (valid14(t)){
                list14.add(matrix14[t[0]][t[1]]);
                dfs14(t[0],t[1]);
                list14.removeLast();
            }
        }
    }
    public  boolean valid14(int[] t){
        int i=t[0];
        int j=t[1];
        return  i>=0 && i<matrix14.length && j>=0 && j<matrix14[0].length && list14.getLast() < matrix14[i][j];
    }
    public  boolean assist14(int i,int j){
        return  i < 0 || i>= this.matrix14.length || j<0 || j>=matrix14[0].length || list14.getLast()>=matrix14[i][j];
    }
    public static void main(String[] args){
        DFS dfs=new DFS();
    }
}
