package com.zc.qqclient.service;

import com.zc.qqcommon.Message;
import com.zc.qqcommon.MessageType;
import com.zc.qqcommon.User;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 8:44
 * 用户端连接到服务端验证用户的合法性并维护该条socket连接
 */
public class UserClientService {
    private User user = new User();
    private Socket socket;
    //检测用户合法性
    public boolean checkUser(String userId, String pwd){
        boolean b = false;
        user.setUserid(userId);
        user.setPassword(pwd);
        //连接服务端
        try{
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //读取服务器的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();
            if(message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//ok
                b = true;
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                //集合维护
                ManageClientConnectThread.add(userId, ccst);
            }else{
                //关闭Socket
                socket.close();
            }
        }catch (IOException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
    //获取在线用户列表
    public void onlineUsers(){
        //发送MESSAGE
        Message ms = new Message();
        ms.setSender(user.getUserid());
        ms.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectThread.get(user.getUserid()).getSocket().getOutputStream());
            oos.writeObject(ms);
        }catch (IOException e){

        }


    }
    //退出系统
    public void exciteSystem() {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserid());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectThread.get(user.getUserid()).getSocket().getOutputStream());
            oos.writeObject(message);
        }catch (IOException e){

        }
        System.exit(0);
    }

    //发送消息
    public void sendMessage(String getter, String sentence){
        Message message = new Message();
        message.setSender(user.getUserid());
        message.setGetter(getter);
        message.setContent(sentence);
        message.setMessageType(MessageType.MESSAGE_COM_MES);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectThread.get(user.getUserid()).getSocket().getOutputStream());
            oos.writeObject(message);
        }catch (IOException e){

        }
    }

    //群发消息
    public void sendMessageToAll(String sentence){
        Message message = new Message();
        message.setSender(user.getUserid());
        message.setContent(sentence);
        message.setMessageType(MessageType.MESSAGE_SEND_TO_ALL);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectThread.get(user.getUserid()).getSocket().getOutputStream());
            oos.writeObject(message);
        }catch (IOException e){

        }
    }

    //发送文件
    public void sendFile(String address, String dest, String getter){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_SEND_FILE);

    }
}
