package com.haze.springboot.zookeeper;

import com.haze.springboot.Application;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Sofar on 2016/10/11.
 */
public class ZooKeeperUtils implements Watcher {

    private static final Logger logger = LoggerFactory
            .getLogger(ZooKeeperUtils.class);

    /**
     * ZooKeeper节点路径分隔符
     * <p>
     * ZooKeeper节点路径必须为绝对路径，使用/分隔符开头。
     * </p>
     */
    private static final String ZNODE_PATH_SEPERATOR = "/";
    /**
     * 每毫秒会话超时
     */
    private static final int SESSION_TIMEOUT = 50;

    private static final String CHARSET = "UTF-8";

    private ZooKeeper zk;

    private CountDownLatch connectedSignal = new CountDownLatch(1);

    /**
     * 创建zookeeper客户端到服务端之间的连接
     *
     * @param hosts
     *            ZooKeeper服务地址，默认端口为2181
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String hosts) throws IOException, InterruptedException {
        logger.debug("connecting zookeeper, host[{}]", hosts);
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this); // watcher对象接收ZooKeeper的响应，并通知它各种事件
        connectedSignal.await();
        logger.debug("connected zookeeper success!");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
        }
        logger.debug("receive zookeeper's event, event path=[{}], event type=[{}]", event.getPath(), event.getType());
        /*if (event.getType() == Event.EventType.NodeDataChanged) {
            String data = read(event.getPath(), this);
        }
        if (event.getType() == Event.EventType.NodeChildrenChanged) {
            start();
            System.out.println("子节点变化，节点路径信息" + event.getPath());
        }
        if (event.getType() == Event.EventType.NodeCreated) {
            start();
            System.out.println("新建子节点变化，节点路径信息" + event.getPath());
        }
        if (event.getType() == Event.EventType.NodeDeleted) {
            String path = event.getPath();
            Path p = Paths.get(FILE_PATH + path.replace(Application.CASE_PATH,""));
            if (Files.exists(p)) {
                try {
                    Files.walkFileTree(p, new FileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (file != null) {
                                Files.delete(file);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    });
                        Files.deleteIfExists(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("删除节点路径信息" + event.getPath());
        }*/
    }

    /**
     * 创建ZooKeeper组节点。
     *
     * <p>
     * <b>说明：</b>znodes可能是临时的或永久的。一个临时性znode在创建它的客户端断开连接后（可能是明确的断开连接，
     * 也可能是客户端由于其它原因中断）就会被 ZooKeeper服务删除。相反，一个永久性znode不会在客户端断开连接后被删除。
     * </p>
     *
     * @param groupNames
     *            组节点名称
     */
    public void create(String groupNames, String data, CreateMode createMode) {
        String path = ZNODE_PATH_SEPERATOR + groupNames;
        byte[] nodeData = null;
        try {
            if (data != null) nodeData = data.getBytes();
            String groupPath = zk.create(path, nodeData/* data */,
                    Ids.OPEN_ACL_UNSAFE, createMode); // 创建永久性znode
            logger.debug("创建组节点，组节点路径为：{}", groupPath);
        } catch (KeeperException | InterruptedException e) {
            logger.debug("创建组节点失败，错误信息为：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除ZooKeeper组节点以及组节点下的子节点，同时删除节点数据。
     *
     * <p>
     * <b>说明：</b>ZooKeeper没有递归删除操作，因此在删除组节点前必须首先删除该节点下子节点。
     * </p>
     *
     * @param groupNames 组节点名称
     */
    public void delete(String groupNames) {
        String groupPath = ZNODE_PATH_SEPERATOR + groupNames;
        try {
            List<String> childrenPaths = zk.getChildren(groupPath, false);
            for (String childrenPath : childrenPaths) {
                logger.debug("删除组节点下子节点，子节点路径为：{}", groupPath
                        + ZNODE_PATH_SEPERATOR + childrenPath);
                zk.delete(groupPath + ZNODE_PATH_SEPERATOR + childrenPath, -1);
            }
            logger.debug("删除组节点，组节点路径为：{}", groupPath);
            zk.delete(groupPath, -1);
        } catch (KeeperException | InterruptedException e) {
            logger.error("删除组节点失败，错误信息为：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭客户端和ZooKeeper服务器之间的连接
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        zk.close();
    }

    public void write(String path, String value, CreateMode createMode) {
        try {
            Stat stat = zk.exists(path, false);
            if (stat == null) {
                zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE,
                        createMode);
            }
            zk.setData(path, value.getBytes(CHARSET), -1);
        } catch (KeeperException | InterruptedException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String read(String path, Watcher watcher) {
        byte[] data;
        try {
            data = zk.getData("/" + path, watcher, null);
            return new String(data, CHARSET);
        } catch (KeeperException | InterruptedException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getChildNode(String path, Watcher watcher) throws KeeperException, InterruptedException {
        return zk.getChildren(path, watcher);
    }

    private static String FILE_PATH = Application.FILE_PATH;

    public void start() {
        try {
            List<String> nodes = getChildNode("/" + Application.CASE_PATH, this);
            nodes.forEach(s -> {
                //读取目录下文件信息
                if (!Files.exists(Paths.get(FILE_PATH + "/" + s))) {
                    try {
                        Files.createDirectories(Paths.get(FILE_PATH + "/" + s));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Path f = Paths.get(FILE_PATH + "/" + s + "/" + "testcase.txt");
                if (Files.exists(f)) {
                    try {
                        Files.delete(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String data = read(Application.CASE_PATH + "/" + s, this);
                try {
                    if (data != null) {
                        Files.copy(new ByteArrayInputStream(data.getBytes()), f);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}