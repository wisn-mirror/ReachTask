package com.wisn.mainmodule.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private long userid;
    private String nickname;
    private String nameid;
    private String iconurl;
    private String password;
    private String encryption;
    private String phonenumber;
    private String token;
    private long expired;
    private long registertime;
    private long lastlogintime;
    private boolean isactive;

    public User() {
    }

    public User(long userid, String nickname, String nameid, String iconurl, String password, String encryption, String phonenumber, long registertime, long lastlogintime) {
        this.userid = userid;
        this.nickname = nickname;
        this.nameid = nameid;
        this.iconurl = iconurl;
        this.password = password;
        this.encryption = encryption;
        this.phonenumber = phonenumber;
        this.registertime = registertime;
        this.lastlogintime = lastlogintime;
    }

    @Generated(hash = 916199754)
    public User(long userid, String nickname, String nameid, String iconurl, String password, String encryption, String phonenumber, String token, long expired,
            long registertime, long lastlogintime) {
        this.userid = userid;
        this.nickname = nickname;
        this.nameid = nameid;
        this.iconurl = iconurl;
        this.password = password;
        this.encryption = encryption;
        this.phonenumber = phonenumber;
        this.token = token;
        this.expired = expired;
        this.registertime = registertime;
        this.lastlogintime = lastlogintime;
    }


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public long getRegistertime() {
        return registertime;
    }

    public void setRegistertime(long registertime) {
        this.registertime = registertime;
    }

    public long getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(long lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", nickname='" + nickname + '\'' +
                ", nameid='" + nameid + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", password='" + password + '\'' +
                ", encryption='" + encryption + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", token='" + token + '\'' +
                ", expired=" + expired +
                ", registertime=" + registertime +
                ", lastlogintime=" + lastlogintime +
                ", isactive=" + isactive +
                '}';
    }

}
