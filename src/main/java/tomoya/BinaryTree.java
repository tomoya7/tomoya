package tomoya;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

class TreeNode {
    TreeNode left=null;
    TreeNode right=null;
    int val;
    TreeNode(int val){
        this.val=val;
    }
}
public class BinaryTree {
//    检查子树
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A==null || B==null) return false;
        return check(A,B) || isSubStructure(A.right,B) || isSubStructure(A.left,B);
    }
        public boolean check(TreeNode A,TreeNode B){
            if (B==null) return true;
            if (A==null) return false;
            return A.val== B.val && check(A.left,B.left)&& check(A.right,B.right);
        }
//    二叉搜索树与双向链表
    TreeNode pre;
    TreeNode head;
    public TreeNode treeToDoublyList(TreeNode root) {
        pre=null;
        inorder(root);
        head.left=pre;
        pre.right=head;
        return head;
    }
    public void inorder(TreeNode root){
        if (root!=null){
            inorder(root.left);
            if (pre==null)head=root;
            else{
                root.left=pre;
                pre.right=root;
            }
            pre=root;
            inorder(root.right);
        }
    }
//    二叉树中序遍历
    LinkedList<Integer> listinOrder=new LinkedList<>();
    public void inOrder(TreeNode root){
        if (root!=null){
            inOrder(root.left);
            listinOrder.add(root.val);
            inOrder(root.right);
        }
    }
//    二叉树层序遍历
public List<List<Integer>> levelOrder(TreeNode root) {
    Deque<TreeNode> dq=new ArrayDeque<>();
    List<Integer> list=new ArrayList<>();
    List<List<Integer>> res=new ArrayList<>();
    dq.offer(root);
    while (!dq.isEmpty()){
        int len=dq.size();
        for (int i = 0; i < len; i++) {
            TreeNode node = dq.poll();
            list.add(node.val);
            if (node.left != null) dq.offer(node.left);
            if (node.right != null) dq.offer(node.right);
        }
        res.add(new ArrayList<>(list));
        list.clear();
    }
    return res;
}
//    验证二叉搜索树的后序遍历序列
public boolean verifyPostorder(int[] postorder){
    if (postorder.length==0) return true;
    int root=postorder[postorder.length-1];
    int i=0;
    while (root>postorder[i]) i+=1;
    int p=i;
    while (root<postorder[i]) i+=1;
    return root==postorder[i] && verifyPostorder(Arrays.copyOfRange(postorder,0,p)) && verifyPostorder(Arrays.copyOfRange(postorder,p,postorder.length-1));
}
//    二叉树镜像
    public TreeNode mirrorTree(TreeNode root) {
        TreeNode tem=root.left;
        root.left=mirrorTree(root.right);
        root.right=mirrorTree(tem);
        return root;
    }
//    合法二叉搜索树
    public boolean isValidBST(TreeNode root) {
        listinOrder=new LinkedList<>();
        inOrder(root);
        for (int i = 0; i < listinOrder.size()-1; i++) if (listinOrder.get(i)>=listinOrder.get(i+1)) return false;
        return true;
    }
//    后继者
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root==null) return null;
        if (root.val>p.val){
            TreeNode node =inorderSuccessor(root.left,p);
            if (node==null) return root;
            else return node;
        }
        else return inorderSuccessor(root.right,p);
    }
//    二叉树最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==null) return null;
        int left=find(root.left,p,q);
        int right=find(root.right,p,q);
        if (left==2) return lowestCommonAncestor(root.left,p,q);
        else if(right==2) return lowestCommonAncestor(root.right,p,q);
        else return root;
    }
    public int find(TreeNode root,TreeNode p,TreeNode q){
        int count=0;
        if (root==null) return 0;
        if (root.val==p.val||root.val==q.val) count+=1;
        return count+find(root.left,p,q)+find(root.right,p,q);
    }
//    对称二叉树
    public boolean isSymmetric(TreeNode root) {
        if (root==null) return true;
        return help(root.left,root.right);
    }
    public boolean help(TreeNode left,TreeNode right){
        if (left==null || right==null) {
            if (left==null && right==null) return true;
            else return false;
        }
        else return left.val==right.val && help(left.left,right.right) && help(left.right,right.left);
    }
//    二叉树的直径
    int max;
    public int diameterOfBinaryTree(TreeNode root){
        max=0;
        deep(root);
        return max;
    }
    public int deep(TreeNode root){
        if (root==null) return 0;
        int left=deep(root.left);
        int right=deep(root.right);
        this.max=Math.max(max,left+right);
        return 1+Math.max(left,right);
    }
    //根据前序遍历反序列化二叉树
    static int index=-1;
    public static TreeNode preCreatTree(int[] nums){
        index+=1;
        if (index>= nums.length||nums[index]==-1) return null;
        TreeNode root=new TreeNode(nums[index]);
        root.left=preCreatTree(nums);
        root.right=preCreatTree(nums);
        return root;
    }
//   根据层序遍历反序列化二叉树
    public static TreeNode levelCreatTree(int[] nums,int p){
        TreeNode root=new TreeNode(nums[0]);
        LinkedList<TreeNode> dq=new LinkedList<>();
        dq.offer(root);
        int k=0;
        while (!dq.isEmpty()){
            int size=dq.size();
            for (int i = 0; i < size; i++) {
                TreeNode node=dq.poll();
                k++;
                if (k>=nums.length) break;
                node.left=nums[k]==p? null: new TreeNode(nums[k]);
                k++;
                if (k>=nums.length) break;
                node.right=nums[k]==p? null:new TreeNode(nums[k]);
                if (node.left!=null) dq.offer(node.left);
                if (node.right!=null) dq.offer(node.right);
            }
        }
        return root;
    }
    //    根据前序和中序构建二叉树
    public TreeNode create(int[] pre,int[] in){
        if (pre.length==0){
            return null;
        }
        TreeNode root=new TreeNode(pre[0]);
        Integer[] in1=Arrays.stream(in).boxed().toArray(Integer[]::new);
        int index=Arrays.asList(in1).indexOf(pre[0]);
        int[] left_in=Arrays.copyOfRange(in,0,index);
        int[] right_in=Arrays.copyOfRange(in,index+1,in.length);
        int[] left_pre=Arrays.copyOfRange(pre,1,index+1);
        int[] right_pre=Arrays.copyOfRange(pre,pre.length-(in.length-index-1),pre.length);
        root.left=create(left_pre,left_in);
        root.right=create(right_pre,right_in);
        return root;
    }
    public static void main(String[] args){
//        BinaryTree binaryTree=new BinaryTree();
//        TreeNode root=binaryTree.create(new int[]{3,9,20,15,7},new int[]{9,3,15,20,7});
//        binaryTree.inOrder(root);
//        System.out.println(binaryTree.listinOrder);
    }
}
