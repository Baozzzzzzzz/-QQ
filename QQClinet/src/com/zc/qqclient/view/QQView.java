package com.zc.qqclient.view;

import com.zc.qqclient.service.FileClientService;
import com.zc.qqclient.service.UserClientService;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/21 11:21
 */
public class QQView {
    private UserClientService userClientService = new UserClientService();
    private Boolean loop = true;
    private FileClientService fileClientService = new FileClientService();
    String key = "";
    Scanner sc = new Scanner(System.in);
    private void mainMenu() throws IOException {
        while (loop){
            System.out.println("=================欢迎登录网络通信系统=================");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择：");
            key = sc.nextLine();
            switch(key){
                case "1":
                    System.out.print("请输入用户号：");
                    String userid = sc.nextLine();
                    System.out.print("请输入密码：");
                    String pwd = sc.nextLine();
                    if(userClientService.checkUser(userid, pwd)){
                        while (loop){
                            System.out.println("===============网络通讯系统二级菜单("+userid+")===================");
                            System.out.println("\t\t 1 显示在线列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = sc.nextLine();
                            switch (key){
                                case "1":
                                    userClientService.onlineUsers();
                                    break;
                                case "2":
                                    System.out.print("请输入群发的消息：");
                                    String sentence1 = sc.nextLine();
                                    userClientService.sendMessageToAll(sentence1);
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    System.out.print("请输入想聊天的用户号（在线）：");
                                    String id = sc.nextLine();
                                    System.out.println("请输入想说的话：");
                                    String sentence = sc.nextLine();
                                    userClientService.sendMessage(id, sentence);
                                    break;
                                case "4":
                                    System.out.println("请输入要发送文件的用户（在线）：");
                                    String id3 = sc.nextLine();
                                    System.out.println("请输入文件的路径：");
                                    String address = sc.nextLine();
                                    System.out.println("请输入对方文件的路径：");
                                    String dest = sc.nextLine();
                                    fileClientService.sendFileToOne(address, dest, userid, id3);
                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.exciteSystem();
                                    break;
                            }
                        }
                    }else
                    {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    System.out.println("退出系统");
                    loop = false;

                    break;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        QQView qqView = new QQView();
        qqView.mainMenu();

    }
}
