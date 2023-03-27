package com.zc.qqclient.service;

import java.util.HashMap;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 9:17
 * 集合维护用户线程
 */
public class ManageClientConnectThread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void add(String userid, ClientConnectServerThread clientConnectServerThread){
        hm.put(userid, clientConnectServerThread);
    }

    public static ClientConnectServerThread get(String userid){
        return hm.get(userid);
    }
}
