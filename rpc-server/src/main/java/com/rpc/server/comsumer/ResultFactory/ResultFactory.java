package com.rpc.server.comsumer.ResultFactory;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: SC19002999
 * @Description:
 * @Date: 2020/12/31 15:57
 * @Version: 1.0
 */
@Component
public class ResultFactory {
    private static final Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
    public static void put(String uuid, Integer result) {
        map.put(uuid, result);
    }

    public static Object get(String uuid) {
        return map.get(uuid);
    }
}
