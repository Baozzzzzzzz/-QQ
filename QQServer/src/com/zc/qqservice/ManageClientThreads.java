package com.zc.qqservice;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 17:32
 * 管理和客户端通讯的线程
 */
public class ManageClientThreads {
    public static HashMap<String, ServerConnectThread> hm = new HashMap<>();

    public static void put(String userid, ServerConnectThread serverConnectThread){
        hm.put(userid, serverConnectThread);
    }

    public static ServerConnectThread get(String userid){
        return hm.get(userid);
    }
    //获取在线用户列表
    public static  String getOnlineUser(){
        Iterator<String> iterator = hm.keySet().iterator();
        String list = "";
        while (iterator.hasNext()){
            list += iterator.next()+" ";
        }
        return list;
    }

    //退出
    public static void remove(String userid){
        hm.remove(userid);
    }

    //返回hashmap'
    public static HashMap rethm(){
        return hm;
    }
}
