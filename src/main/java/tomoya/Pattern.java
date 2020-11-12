package tomoya;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//单例模式
class Singleton{
    private volatile static Singleton singleton;
    public static Singleton getInstance(){
        if (null==singleton){
            synchronized (Singleton.class){
                if (null==singleton){
                    singleton=new Singleton() ;
                }
            }
        }
        return singleton;
    }
}
//LRU
class LRUCache {
    private int capacity;
    private LinkedHashMap<Integer,Integer> map;
    LRUCache(int capacity){
        this.capacity=capacity;
        map=new LinkedHashMap<>();
    }
    public int get(int key){
        if (map.containsKey(key)){
            int tem=map.remove(key);
            map.put(key,tem);
            return tem;
        }
        else return -1;
    }
    public  void put(int key ,int value){
        if (map.containsKey(key)){
            map.remove(key);
            map.put(key,value);
        }
        else if (map.size()<this.capacity) map.put(key,value);
        else {
            map.remove(map.entrySet().iterator().next().getKey());
            map.put(key,value);
        }
    }
}
//两个栈实现队列
class Cqueue {
    Deque<Integer> stack1;
    Deque<Integer> stack2;
    public Cqueue(){
        stack1=new LinkedList<>();
        stack2=new LinkedList<>();
    }
    public void offer(int val){
        stack1.push(val);
    }
    public int poll(){
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()) stack2.push(stack1.pop());
        }
        if (stack2.isEmpty()) return -1;
        else return stack2.pop();
    }
}
//实现min函数的栈
class MinStack {
    Deque<Integer> A;
    Deque<Integer> B;
    public MinStack() {
        A=new LinkedList<>();
        B=new LinkedList<>();
    }
    public void push(int x) {
        A.push(x);
        if (B.isEmpty() || B.peek()>=x)
            B.push(x);
    }
    public int pop() {
        int tem = A.pop();
        if (tem==B.peek()) B.pop();
        return tem;
    }
    public int top(){
        return A.peek();
    }
    public int getMin() {
        return B.peek();
    }
}
//生产者消费者模式
class Producer extends Thread{
    private BlockingQueue<Integer> blockingQueue;
    public Producer(BlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                blockingQueue.offer(i);
                System.out.println("生产了： " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Consumer extends Thread{
    private ArrayBlockingQueue<Integer> blockingQueue;
    public Consumer(ArrayBlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                System.out.println("消费了，值为：" +  blockingQueue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//排序栈
class SortedStack {
    LinkedList<Integer> A;
    LinkedList<Integer> B;
    public SortedStack() {
        A=new LinkedList<Integer>();
        B=new LinkedList<Integer>();
    }
    public void push(int val) {
        if (A.isEmpty()){
            A.push(val);
            return;
        }
        while (!A.isEmpty()&&A.peek()<val)B.push(A.pop());
        A.push(val);
        while (!B.isEmpty()) A.push(B.pop());
    }
    public void pop() {
        if (!A.isEmpty()) A.pop();
    }
    public int peek() {
        if (!A.isEmpty()) return A.peek();
        else return -1;
    }
    public boolean isEmpty() {
        return A.isEmpty();
    }
}
//实现max的队列
class MaxQueue {
    LinkedList<Integer> dq;
    LinkedList<Integer> B;
    public MaxQueue() {
        dq=new LinkedList<>();
        B=new LinkedList<>();
    }
    public int max_value() {
        if(B.isEmpty()) return -1;
        return B.getFirst();
    }
    public void push_back(int value) {
        dq.offer(value);
        Iterator<Integer> it=B.iterator();
        while (it.hasNext())if (it.next()<value) it.remove();
        B.offer(value);
    }
    public int pop_front() {
        if(dq.isEmpty()) return -1;
        int tem=dq.poll();
        if(tem==B.getFirst()) B.poll();
        return tem;
    }
}
//多线程之间的协调
class  MyService{
    private  Boolean flag=false;
    public synchronized void printA(int i)  {
        if (flag){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"_"+i);
        flag=true;
        notifyAll();
    }
    public synchronized void printB(int i)  {
        if (!flag){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"_"+i);
        flag=false;
        notifyAll();
    }
}
class A extends  Thread{
    private volatile MyService myService;
    A(MyService myService){
        this.myService=myService;
    }
    public void run() {
        for (int i = 1; i <= 9; i+=2) myService.printA(i);
    }
}
class B implements Runnable{
    private MyService myService;
    B(MyService myService){
        this.myService=myService;
    }
    public void run(){
        for (int i = 2; i <= 10; i+=2) myService.printB(i);
    }
}
class ThreadB extends Thread {
    T t;
    ThreadB (T t){
        this.t=t;
    }
    @Override
    public void run() {
        while (t.index <=10) {
            if (t.open) {
                System.out.println(Thread.currentThread().getName() + "-----" + t.index++);
                t.open = false;
            }
        }
    }
}

class ThreadA extends Thread {
    T t;
    ThreadA(T t){
        this.t=t;
    }
    @Override
    public void run() {
        while (t.index <= 10) {
            if (!t.open) {
                System.out.println(Thread.currentThread().getName() + "-----" + t.index++);
                t.open = true;
            }
        }
    }
}
class T{
    volatile static int index=1;
    volatile static boolean open=false;
}
public class Pattern {
//    栈的压入弹出序列
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        LinkedList<Integer> stack=new LinkedList<>();
        int j=0;
        for (int x:pushed) {
            stack.push(x);
            while (!stack.isEmpty()&&stack.peek()==popped[j]){
                j+=1;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args) throws InterruptedException {
//        MyService myService=new MyService();
//        A a=new A(myService);
//        a.start();
//        Thread b=new Thread(new B(myService));
//        b.start();
//        a.join();
//        b.join();
//        T t=new T();
//        new ThreadA(t).start();
//        new ThreadB(t).start();
        Pattern pattern=new Pattern();
        System.out.println(pattern.validateStackSequences(new int[]{6,5,4,3,2,1},new int[]{3,4,6,5,2,1}));
    }
}