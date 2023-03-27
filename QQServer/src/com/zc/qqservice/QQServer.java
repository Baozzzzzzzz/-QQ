package com.zc.qqservice;

import com.zc.qqcommon.Message;
import com.zc.qqcommon.MessageType;
import com.zc.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.HashMap;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 9:28
 * 服务端
 */
public class QQServer {
    private ServerSocket ss;
    private static HashMap<String, User> users = new HashMap<>();
    static {
        users.put("至尊宝",new User("至尊宝","123456"));
        users.put("紫霞仙子", new User("紫霞仙子","123456"));
        users.put("菩提老祖", new User("菩提老祖", "123456"));
    }
    private boolean checkUser(String userid, String passwd){
        User user = users.get(userid);
        if(user==null){
            return false;
        }
        if(!user.getPassword().equals(passwd)){
            return false;
        }
        return true;
    }
    public QQServer() {
        System.out.println("服务端正在监听...");
        try {
            ss = new ServerSocket(9999);
            while (true){//持续监听
                Socket socket = ss.accept();//没有客户端，阻塞
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User) ois.readObject();
                Message ms = new Message();
                if(checkUser(u.getUserid(), u.getPassword())){
                    //创建Message对象
                    ms.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(ms);
                    //创建线程
                    ServerConnectThread serverConnectThread = new ServerConnectThread(socket, u.getUserid());
                    serverConnectThread.start();
                    ManageClientThreads.put(u.getUserid(), serverConnectThread);
                }else {
                    ms.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    System.out.println("id="+u.getUserid()+"  "+"pwd="+u.getPassword()+"验证不通过");
                    oos.writeObject(ms);
                    socket.close();
                }
            }
        }catch (IOException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new QQServer();
    }
}
