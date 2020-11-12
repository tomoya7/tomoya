package tomoya;

import java.util.*;

class ListNode{
    ListNode next=null;
    int val;
    ListNode random=null;
    ListNode (int val){
        this.val=val;
    }
    public  void show(){
        ListNode tem=this;
        while (tem !=null){
            System.out.println(tem);
            tem=tem.next;
        }
    }
    public String toString(){
        return String.valueOf(this.val);
    }
    public Integer[] asArray(){
        ListNode tem=this;
        List<Integer> list=new ArrayList<Integer>();
        while (tem!=null){
            list.add(tem.val);
            tem=tem.next;
        }
        return  list.toArray(new Integer[list.size()]);
    }
}
public class LinkList {
//    反转链表
    public ListNode reverseList(ListNode head){
        if (head==null || head.next==null) return head;
        ListNode reverseHead=reverseList(head.next);
        head.next.next=head;
        head.next=null;
        return reverseHead;
    }
//    删除节点
    public ListNode deleteNode(ListNode head, int val){
        if(head.val==val) return head.next;
        ListNode tem=head;
        while (tem.next!=null){
            if (tem.next.val==val) {
                tem.next=tem.next.next;
                break;
            }
            tem=tem.next;
        }
        return head;
    }
//    链表拷贝
    public ListNode deepcopy(ListNode head){
        HashMap<ListNode,ListNode> hashMap=new HashMap();
        ListNode cur=head;
        while (cur!=null){
            hashMap.put(cur,new ListNode(cur.val+1));
            cur=cur.next;
        }
        cur=head;
        while (cur !=null){
            hashMap.get(cur).next=hashMap.get(cur.next);
            cur=cur.next;
        }
        return hashMap.get(head);
    }
//    获得链表第一个公共节点
    public ListNode getIntersectionNode(ListNode head1,ListNode head2){
        ListNode node1=head1;
        ListNode node2=head2;
        while (node1!=node2){
            if (node1!=null)node1=node1.next;
            else node1=head2;
            if (node2!=null)node2=node2.next;
            else node2=head1;
        }
        return node1;
    }
//    链表中倒数第k个结点
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast=head;
        ListNode slow=head;
        for (int i = 0; i < k; i++) fast=fast.next;
        while (fast!=null){
            fast=fast.next;
            slow=slow.next;
        }
        return slow;
    }
    public  ListNode Create(Integer[] arr){
        ListNode head=new ListNode(arr[0]);
        ListNode tem=head;
        for (int i=1;i<arr.length;i++){
            tem.next=new ListNode(arr[i]);
            tem=tem.next;
        }
        return head;
    }
    //    链表插入排序
    public ListNode insertionSortList(ListNode head) {
        if(head==null) return null;
        ListNode dummy=new ListNode(0);
        ListNode pre=head;
        ListNode cur=head.next;
        dummy.next=head;
        while (cur !=null) {
            cur=pre.next;
            ListNode p=dummy;
            while (cur!=null&&pre.val <= cur.val) {
                pre = cur;
                cur = cur.next;
            }
            if (cur==null) break;
            while (p.next!=null&&p.next.val <= cur.val) p = p.next;
            pre.next = cur.next;
            cur.next = p.next;
            p.next = cur;
        }
        return dummy.next;
    }
//    链表归并排序
    public ListNode sortList(ListNode head) {
        if (head==null||head.next==null) return  head;
        ListNode fast=head.next;
        ListNode slow=head;
        while (fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
        ListNode mid=slow.next;
        slow.next=null;
        ListNode left=sortList(head);
        ListNode right=sortList(mid);
        return merge(left,right);
    }
    private ListNode merge(ListNode left,ListNode right){
        ListNode head=new ListNode(-1);
        ListNode tem=head;
        while (left!=null&&right!=null) {
            if (left.val < right.val) {
                tem.next = left;
                left = left.next;
            }
            else{
                tem.next=right;
                right=right.next;
            }
            tem=tem.next;
        }
        if (left==null) tem.next=right;
        else tem.next=left;
        return head.next;
    }
//    分割链表(快速排序)
    public ListNode partition(ListNode head, int x) {
        ListNode l=new ListNode(0);
        ListNode lt=l;
        ListNode r=new ListNode(0);
        ListNode rt=r;
        while (head!=null){
            if (head.val<x) {
                lt.next=new ListNode(head.val);
                lt=lt.next;
            }
            else {
                rt.next=new ListNode(head.val);
                rt=rt.next;
            }
            head=head.next;
        }
        lt.next=r.next;
        return l.next;
    }
//    链表求和
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head=new ListNode(0);
        int count=0;
        ListNode tem=head;
        while (l1!=null&& l2!=null){
            int sum=l1.val+l2.val+count;
            count=sum/10;
            tem.next=new ListNode(sum%10);
            tem=tem.next;
            l1=l1.next;
            l2=l2.next;
        }
        if (l1==null){
            while (l2!=null) {
                int sum = l2.val + count;
                count = sum / 10;
                tem.next = new ListNode(sum % 10);
                tem = tem.next;
                l2 = l2.next;
            }
        }
        else{
            while (l1!=null){
                int sum=l1.val+count;
                count=sum/10;
                tem.next=new ListNode(sum%10);
                tem=tem.next;
                l1=l1.next;
            }
        }
        if (count!=0){
            tem.next=new ListNode(count);
            tem=tem.next;
        }
        tem.next=null;
        return head.next;
    }
    //    环路检测
    public ListNode detectCycle(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&& fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                slow=head;
                while(slow!=fast){
                    slow=slow.next;
                    fast=fast.next;
                }
                return fast;
            }
        }
        return null;
    }
//奇偶链表
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;
            even.next = even.next.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
    public static void main(String[] args){
        LinkList linkedList=new LinkList();
    }
}
