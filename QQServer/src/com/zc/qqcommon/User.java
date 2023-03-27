package com.zc.qqcommon;

import java.io.Serializable;

/**
 * @author zc
 * @version 1.0
 * @create 2023/3/20 14:41
 * 用户信息
 */
public class User implements Serializable {
    private static final long serialVersionUID =1L;
    private String userid;
    private String password;
    public User(String userid, String password){
        this.userid = userid;
        this.password = password;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
