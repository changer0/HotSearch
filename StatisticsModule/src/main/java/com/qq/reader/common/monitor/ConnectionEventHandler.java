package com.qq.reader.common.monitor;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by liuxiaoyang on 2017/6/14.
 * <p>
 * 用于维护ConnectionEvent
 */

public class ConnectionEventHandler {

    int index = 1;
    HashMap<Integer, ConnectionEvent> map = new HashMap<>();

    public synchronized int addEvent(ConnectionEvent event) {
        index++;
        map.put(index, event);
        return index;
    }

    public ConnectionEvent popEvent(int i) {
//        System.out.println("ConnectionEventHandler map.size = " + map.size());
        ConnectionEvent event = map.get(i);
        map.remove(i);
        return event;
    }
}
