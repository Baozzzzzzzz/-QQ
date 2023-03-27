package com.zc.qqclient.service;

import com.zc.qqcommon.Message;
import com.zc.qqcommon.MessageType;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/22 9:08
 * 维护socket连接
 */
public class ClientConnectServerThread extends Thread{
    private Socket socket;
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void run(){
        //一直通讯
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();//没有发送Message，阻塞
                //判断Message类型，处理
                //获取服务端发送的用户列表
                if(ms.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    //取出
                   String[] str =  ms.getContent().split(" ");
                    System.out.println("\n=============在线用户列表==============");
                for(int i=0;i<str.length;i++){
                        System.out.println(str[i]);
                    }
                }
                //结束信息
                else if(ms.getMessageType().equals(MessageType.MESSAGE_COM_MES)){
                    System.out.println("\n"+ms.getSender()+"对你说："+ms.getContent());
                }
                else if (ms.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ALL)){
                    System.out.println("\n"+ms.getSender()+"对所有人说："+ms.getContent());
                }
                else if (ms.getMessageType().equals(MessageType.MESSAGE_SEND_FILE)){
                    System.out.println(ms.getSender()+"发送给你"+ms.getSrc()+"文件写入到"+ms.getDest());
                    FileOutputStream fos = new FileOutputStream(ms.getDest());
                    fos.write(ms.getFileBytes());
                    fos.close();
                }
            }catch (IOException e){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
