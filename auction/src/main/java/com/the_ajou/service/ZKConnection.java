package com.the_ajou.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

import static java.util.Objects.requireNonNull;

@Slf4j
public class ZKConnection {

    private String host;
    private ZooKeeper zoo;
    private boolean connected;

    public ZKConnection(String host) {
        this.host = requireNonNull(host, "host");
    }

    public ZooKeeper connect() throws Exception {
        if (isConnected()) {
            throw new IllegalStateException("Already connected");
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);
        zoo = new ZooKeeper(host, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Success to connect {}" +  host);
                    updateConnectionState(true);
                    countDownLatch.countDown();
                } else {
                    System.out.println("Receive new watched event : {}" +  event.getState());
                }
            }
        });

        countDownLatch.await();
        return zoo;
    }

    public boolean isConnected() {
        synchronized (this) {
            return connected;
        }
    }

    public void close() throws Exception {
        synchronized (this) {
            if (zoo != null) {
                zoo.close();
                zoo = null;
                connected = false;
            }
        }
    }

    private void updateConnectionState(boolean state) {
        synchronized (this) {
            connected = state;
        }
    }
}