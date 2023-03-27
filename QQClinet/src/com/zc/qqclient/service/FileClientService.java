package com.zc.qqclient.service;

import com.zc.qqcommon.Message;
import com.zc.qqcommon.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/27 11:02
 */
public class FileClientService {
    public void sendFileToOne(String src, String dest, String senderid, String getterid) throws IOException {
        FileInputStream fileInputStream = null;
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_SEND_FILE);
        message.setSender(senderid);
        message.setGetter(getterid);
        message.setSrc(src);
        message.setDest(dest);

        byte[] fileBytes = new byte[(int)new File(src).length()];

            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);
            message.setFileBytes(fileBytes);
            fileInputStream.close();

        System.out.println("\n"+senderid+"给"+getterid+"发送文件"+src+"到"+dest);

        ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectThread.get(senderid).getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
