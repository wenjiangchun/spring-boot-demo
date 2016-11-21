package com.haze.springboot.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Sofar on 2016/11/18.
 */
@Component
public class ZooKeeperConfig {

    @Value("${zookeeper.host}")
    private String host;

    @Value("${zookeeper.sessionTimeout}")
    private int sessionTimeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
