package com.zc.qqcommon;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/20 14:51
 * 消息类型
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//登陆成功
    String MESSAGE_LOGIN_FAIL = "2";//登陆失败
    String MESSAGE_COM_MES = "3";//普通信息
    String MESSAGE_GET_ONLINE_FRIEND = "4";//要求获取在线用户信息
    String MESSAGE_RET_ONLINE_FRIEND = "5";//返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";//用户请求退出
    String MESSAGE_SEND_TO_ALL = "7";//群发消息
    String MESSAGE_SEND_FILE = "8";//发文件
}
