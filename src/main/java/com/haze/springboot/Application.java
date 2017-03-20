package com.haze.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class Application {

    static Logger logger = LoggerFactory.getLogger(Application.class);

    public static String FILE_PATH = "/home/hadoop/testcase";

    public static String HOST_PATH = "hosts";

    public static String CASE_PATH = "testcase";

    public static void main(String[] args) {
       /* ApplicationContext ctx = SpringApplication.run(Application.class, args);*/
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBanner((environment, sourceClass, out) -> {
            out.println("Welcome use Spring Boot!");
        });
        ApplicationContext ctx = springApplication.run(args);
        for (String beanName : ctx.getBeanDefinitionNames()) {
            logger.debug(beanName);
        }
        ctx.getBean(StringRedisTemplate.class).opsForValue().set("aaa", "111");

        /*if (!Files.exists(Paths.get(FILE_PATH))) {
            try {
                Files.createDirectories(Paths.get(FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug("system start completed, now register host node to zookeeper......");
        ZooKeeperUtils zk = new ZooKeeperUtils();
        try {
            zk.connect("192.168.20.207,192.168.20.208");
            zk.create(HOST_PATH + "/" + InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getHostName(), CreateMode.EPHEMERAL);
            logger.debug("host node registered success!");
            //zk.start();
            zk.read("zoo", zk);
            zk.getChildNode("/zoo", zk);
        } catch (InterruptedException | IOException e) {
            logger.error("connected zookeeper failure!",e);
        } catch (KeeperException e) {
            e.printStackTrace();
        }*/

        //VideoTrans trans = new VideoTrans();
        //trans.init();
    }
}
