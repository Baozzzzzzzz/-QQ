package com.zc.qqservice;

import com.zc.qqcommon.Message;
import com.zc.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 17:23
 * 服务器端的线程，和某个客户端保持通信
 */
public class ServerConnectThread extends Thread{
    private Socket socket;
    private String userid;
    public ServerConnectThread(Socket socket, String userid){
        this.socket = socket;
        this.userid = userid;
    }

    public void run(){
        while (true){
            System.out.println("服务端正在读取"+userid+"...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();
                //获取在线用户列表
                if(ms.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    System.out.println(ms.getSender()+"请求拉取在线用户列表");
                    String onlineUsers = ManageClientThreads.getOnlineUser();
                    Message ms2 = new Message();
                    ms2.setMessageType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    ms2.setContent(onlineUsers);
                    ms2.setGetter(ms.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(ms2);

                }else if(ms.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    ManageClientThreads.remove(ms.getSender());
                    System.out.println(ms.getSender()+"退出系统");
                    break;
                }
                //接受并发送消息
                else if (ms.getMessageType().equals(MessageType.MESSAGE_COM_MES)){
                    try{
                        System.out.println(ms.getSender()+"对"+ms.getGetter()+"说："+ms.getContent());
                        ObjectOutputStream oos1 = new ObjectOutputStream(ManageClientThreads.get(ms.getGetter()).socket.getOutputStream());
                        oos1.writeObject(ms);
                    }catch (IOException e){

                    }

                }
                //群发消息
                else if(ms.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ALL)){
                    System.out.println(ms.getSender()+"对大家说："+ms.getContent());
                    HashMap<String, ServerConnectThread> hm = ManageClientThreads.rethm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()){
                        String online = iterator.next();
                        if (!online.equals(ms.getSender())) {
                            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreads.get(online).socket.getOutputStream());
                            oos.writeObject(ms);
                        }
                    }

                }
                else if (ms.getMessageType().equals(MessageType.MESSAGE_SEND_FILE)){
                    System.out.println(ms.getSender()+"给"+ms.getGetter()+"发送文件");
                    ServerConnectThread serverConnectThread = ManageClientThreads.get(ms.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectThread.socket.getOutputStream());
                    oos.writeObject(ms);
                }
            }catch (IOException e){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
