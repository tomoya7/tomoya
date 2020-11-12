package zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socket.HelloServer;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // the server to connect to (can be a server list)
                .connectString("192.168.99.100:2181")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        try {
//            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/node1/00001");
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/node1/00001","java".getBytes());
            String path = "/node1";
            PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);
            PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
//                logger.debug(pathChildrenCacheEvent.getType().toString());
                System.out.println(pathChildrenCacheEvent.getType().toString());
            };
            pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
            pathChildrenCache.start();
            System.out.println(new String(zkClient.getData().forPath("/node1/00001")));//获取节点的数据内容
            zkClient.setData().forPath("/node1/00001","c++".getBytes());//更新节点数据内容
            System.out.println(new String(zkClient.getData().forPath("/node1/00001")));//获取节点的数据内容
//            logger.debug(zkClient.checkExists().forPath("/node1/00001").toString());//不为null的话，说明节点创建成功
            System.out.println(zkClient.checkExists().forPath("/node1/00001"));
            List<String> childrenPaths = zkClient.getChildren().forPath("/node1");
//            logger.debug(Arrays.toString(childrenPaths.toArray()));
            System.out.println(Arrays.toString(childrenPaths.toArray()));
            zkClient.delete().deletingChildrenIfNeeded().forPath("/node1");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
