package tomoya;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Shuzu {
    //    二分查找
    public int binarySearch(int[] arr,int k){
        if(arr.length==0)return -1;
        int mid=arr.length/2;
        if (arr[mid]==k)return mid;
        if (arr.length==1)return -1;
        else if(arr[mid]>k)arr=Arrays.copyOfRange(arr,0,mid);
        else arr=Arrays.copyOfRange(arr,mid+1,arr.length);
        return  binarySearch(arr,k);
    }
    //    二维数组中的查找
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        for (int [] arr:matrix) {
            if (binarySearch(arr,target)!=-1) return true;
        }
        return false;
    }
    //    前k个高频元素
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer,Integer> map=new HashMap<>();
    for (Integer num:nums) map.put(num,map.getOrDefault(num,0)+1);
    PriorityQueue<Integer> max=new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return map.get(o2)-map.get(o1);
        }
    });
    for (Map.Entry<Integer,Integer> entry:map.entrySet()) max.add(entry.getKey());
    int[] res=new int[k];
    for (int i = 0; i < k; i++) res[i]=max.poll();
    return res;
}
    //数组中的第K个最大元素
    public int findKthLargest(int[] nums, int k) {
        int p=nums[0];
        List<Integer> l=new ArrayList<>();
        List<Integer> r=new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i]<p) l.add(nums[i]);
            else r.add(nums[i]);
        }
        int[] left=new int[l.size()];
        for (int i = 0; i < left.length; i++) left[i]=l.get(i);
        int[] right=new int[r.size()];
        for (int i = 0; i < right.length; i++) right[i]=r.get(i);
        if (k==right.length+1) return p;
        else if (k<right.length+1) return findKthLargest(right,k);
        else return findKthLargest(left,k-right.length-1);
    }
    //    有序矩阵中第k小的元素
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int n = matrix.length;
        for (int i = 0; i < n; i++) pq.offer(new int[]{matrix[i][0], i, 0});
        for (int i = 0; i < k - 1; i++) {
            int[] now = pq.poll();
            if (now[2] != n - 1) pq.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
        }
        return pq.poll()[0];
    }
//    中位数
    public double findMedian(Integer[] arr) {
        PriorityQueue<Integer> max=new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        PriorityQueue<Integer> min = new PriorityQueue<>();
        max.addAll(Arrays.asList(arr));
        for (int i = 0; i < arr.length/2; i++) min.offer(max.poll());
        if (max.size()>min.size()) return max.peek();
        else if (max.size()<min.size()) return min.peek() ;
        else return  ((double)max.peek()+(double)min.peek())/2;
    }
//    堆排序(小顶堆升序)
    public Integer[] heapSort(Integer[] arr){
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(arr));
        Integer[] res=new Integer[arr.length];
        for (int i = 0; i < res.length; i++) res[i]=pq.poll();
        return  res;
    }
//    快排
    public Integer[] fastSort(Integer[] arr){
        if (arr.length<=1) return arr;
        Integer[] result=new Integer[arr.length];
        int tem=arr[0];
        List<Integer> left=new ArrayList<>();
        List<Integer> right=new ArrayList<>();
        for (int i=1;i<arr.length;i++) {
            if (arr[i]<=tem) left.add(arr[i]);
            if (arr[i]>tem) right.add(arr[i]);
        }
        Integer[] l=fastSort(left.toArray(new Integer[0]));
        Integer[] r=fastSort(right.toArray(new Integer[0]));
        System.arraycopy(l, 0, result, 0, l.length);
        result[l.length]=tem;
    //        System.arraycopy(l, 0, result, l.length+1, arr.length);
        for (int i = 0; i <r.length ; i++) result[l.length+1+i]=r[i];
        return result;
    }
//    归并排序
    public Integer[] mergeSort(Integer[] arr){
        if (arr.length==1)return arr;
        Integer[] left=mergeSort(Arrays.copyOfRange(arr,0,arr.length/2));
        Integer[] right=mergeSort(Arrays.copyOfRange(arr,arr.length/2,arr.length));
        return  merge(left,right);
        }
        public Integer[] merge(Integer[] left,Integer[] right){
            List<Integer> list=new ArrayList<>();
            int i=0;
            int j=0;
            while (i< left.length && j< right.length){
                if (left[i]<right[j]){
                    list.add(left[i]);
                    i+=1;
                }
                else{
                    list.add(right[j]);
                    j+=1;
                }
            }
            if (i==left.length){
                while (j< right.length){
                    list.add(right[j]);
                    j+=1;
                }
            }
            else if (j==right.length){
                while (i< left.length){
                    list.add(left[i]);
                    i+=1;
                }
            }
            return list.toArray(new Integer[0]);
        }
//    约瑟夫环
    public int lastRemaining(int n, int m){
        int i=0;
        List<Integer> list=new ArrayList<>();
        for (int j = 0; j < n; j++) {
            list.add(j);
        }
        while (list.size()>1){
            i=(i+m-1)%list.size();
            list.remove(i);
        }
        return list.get(0);
    }
//    验证回文串
    public boolean isPalindrome(String s){
        StringBuilder sgood= new StringBuilder();
        for (Character c:s.toCharArray()) {
            if (Character.isDigit(c) || Character.isLetter(c)){
                sgood.append(Character.toLowerCase(c));
            }
        }
        int i=0;
        int j=sgood.length()-1;
        while (i<j){
            if ((sgood.charAt(i)+"").equals(sgood.charAt(j)+"")){
                i+=1;
                j-=1;
            }
            else return false;
        }
        return true;
    }
//    最长公共前缀
    public String longestCommonPrefix(String[] strs){
        if (strs.length==0) return "";
        boolean flag=false;
        for (int i=0;i<strs[0].length();i++){
            char p = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if ( i==strs[j].length()||strs[j].charAt(i)!=p ){
                    flag=true;
                    break;
                }
            }
            if (flag) return strs[0].substring(0,i);
        }
        return strs[0];
    }
//    和为target的三个数
public List<List<Integer>> threeSum(int[] nums){
    Arrays.sort(nums);
    List<String> list=new ArrayList<>();
    List<List<Integer>> res=new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
        int j=i+1;
        int k=nums.length-1;
        while (j<k){
            if (nums[j]+nums[k]== -nums[i]) {
                if (!list.contains(String.valueOf(nums[i]) + nums[j] + nums[k])) {
                    list.add(String.valueOf(nums[i]) + nums[j] + nums[k]);
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                }
                j += 1;
            }
            else if(nums[j]+nums[k]< -nums[i]) j+=1;
            else k-=1;
        }
    }
    return res;
}
    //    股票最大利润
    public Integer maxProfit(int[] prices){
        Integer[] dp=new Integer[prices.length];
        int cost=Integer.MAX_VALUE-1;
        for (int i = 0; i < dp.length; i++) {
            if (prices[i]<cost) cost=prices[i];
            dp[i]=prices[i]-cost;
        }
        return Collections.max(Arrays.asList(dp));
    }
//    旋转矩阵
    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                int tem=matrix[i][j];
                matrix[i][j]= matrix[j][i];
                matrix[j][i]=tem;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length/2; j++) {
                int tem=matrix[i][j];
                matrix[i][j]=matrix[i][matrix.length-1-j];
                matrix[i][matrix.length-1-j]=tem;
            }
        }
    }
//    和为s的连续正数序列
    public int[][] findContinuousSequence(int target) {
        int l=1;
        int r=2;
        List<int[]> res=new ArrayList<>();
        while (r<=target/2+1){
            int sum=0;
            for (int i = l; i < r+1; i++) sum+=i;
            if (sum==target){
                int[] tem=new int[r-l+1];
                for (int i = l; i < r+1; i++) tem[i-l]=i;
                l+=1;
                res.add(tem);
            }
            else if(sum<target)r+=1;
            else l+=1;
        }
        return res.toArray(new int[0][]);
    }
//    翻转单词顺序
    public String reverseWords(String s) {
        s=s.strip();
        int i=s.length()-1;
        int j=s.length()-1;
        List<String> res=new ArrayList<>();
        while (i>=0){
            while (i>=0&&s.charAt(i)!=' ')i-=1;
            res.add(s.substring(i+1,j+1));
            while (i>=0&&s.charAt(i)==' ') i-=1;
            j=i;
        }
        return String.join(" ",res);
    }
//    构建乘积数组
    public int[] constructArr(int[] a) {
        int[] l=new int[a.length];
        int[] r=new int[a.length];
        Arrays.fill(l,1);
        Arrays.fill(r,1);
        for (int i = 1; i < l.length; i++) l[i]=l[i-1]*a[i-1];
        for (int i = r.length-2 ;i > -1; i--) r[i]=r[i+1]*a[i+1];
        int[] res=new int[a.length];
        for (int i = 0; i < res.length; i++) res[i]=l[i]*r[i];
        return res;
    }
//    两个数组的交集
    public Integer[] intersection(int[] nums1, int[] nums2) {
        List<Integer> list=new ArrayList<>();
        if (nums1.length<=nums2.length) {
            for (int num:nums1) if (IntStream.of(nums2).anyMatch(x->x==num)) list.add(num);
        }
        else for (int num:nums2) if (IntStream.of(nums1).anyMatch(x->x==num)) list.add(num);
        HashSet<Integer> res=new HashSet<>(list);
        return res.toArray(new Integer[0]);
    }
//    把数组排成最小的数
public String minNumber(int[] nums) {
    String[] arr=new String[nums.length];
    for (int i = 0; i < nums.length; i++) arr[i]=String.valueOf(nums[i]);
    arr=sort(arr);
    return String.join("",arr);
}
    public String[] sort(String[] arr){
        if (arr.length<=1) return arr;
        String[] result=new String[arr.length];
        String tem=arr[0];
        List<String> left=new ArrayList<>();
        List<String> right=new ArrayList<>();
        for (int i=1;i<arr.length;i++) {
            if (valid(tem,arr[i])) left.add(arr[i]);
            if (!valid(tem,arr[i])) right.add(arr[i]);
        }
        String[] l=sort(left.toArray(new String[0]));
        String[] r=sort(right.toArray(new String[0]));
        System.arraycopy(l, 0, result, 0, l.length);
        result[l.length]=tem;
        //        System.arraycopy(l, 0, result, l.length+1, arr.length);
        for (int i = 0; i <r.length ; i++) result[l.length+1+i]=r[i];
        return result;
    }
    public boolean valid(String tem,String a){
        return (tem + a).compareTo(a + tem) >= 0;
    }
//    两地调度
    public int twoCitySchedCost(int[][] costs) {
        int res = 0;
        int[] temp = new int[costs.length];
        for (int i = 0; i < costs.length; i++) {
            temp[i] = costs[i][1] - costs[i][0];
            res += costs[i][0];
        }
        Arrays.sort(temp);
        for (int i = 0; i < temp.length/2; i++) res += temp[i];
        return res;
    }
//  数组中数字出现的次数
    public int[] singleNumbers(int[] nums) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int num:nums)map.put(num,map.getOrDefault(num,0)+1);
        int [] ans=new int[2];
        int count=0;
        for(Map.Entry<Integer,Integer>entry:map.entrySet())if(entry.getValue()==1)ans[count++]=entry.getKey();
        return ans;
    }
//    一次编辑
    public boolean oneEditAway(String first, String second) {
        if (first.length()<second.length()) {
            String p=first;
            first=second;
            second=p;
        }
        StringBuffer p=new StringBuffer(second);
        for (int i = 0; i < first.length(); i++) {
            if (i>=p.length()||first.charAt(i)!=p.charAt(i)){
                if (first.length()==p.length()){
                    p.replace(i,i+1,first.charAt(i)+"");
                    break;
                }
                else{
                    p.insert(i,first.charAt(i));
                    break;
                }
            }
        }
        return first.equals(p.toString());
    }
//    鸡兔同笼
    public String jitu(int[] arr){
        int head=arr[0];
        int foot=arr[1];
        for (int i = 1; i < head; i++) if (2*i+4*(head-i)==foot) return i+","+(400-i);
        return "WRONG";
    }
    public static void main(String[] args){
        Shuzu shuzu=new Shuzu();
//        System.out.println(shuzu.threeSum(new int[]{-2,-9,11,15}));
//        System.out.println(shuzu.lastRemaining(5,3));
//        System.out.println(shuzu.isPalindrome(new String("A man, a plan, a canal: Panama")));
//        System.out.println(shuzu.longestCommonPrefix(new String[]{"flower","flow","flight"}));
//        System.out.println(Arrays.toString(shuzu.fastSort(new Integer[]{2, 1, 5, 3})));
        System.out.println(shuzu.binarySearch(new int[]{5,7,7,8,8,10},8));
//        System.out.println(Arrays.toString(shuzu.heapSort(new Integer[]{4, 2, 3, 5, 8, 1})));
//        System.out.println(shuzu.findMedian(new Integer[]{1,2,3,4,5,6,7,8,9}));
    }
}
