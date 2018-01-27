package com.wisn.mainmodule.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class User implements Parcelable{
    @Property(nameInDb = "_id")
    @Id
    private Long userid;
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

    @Generated(hash = 832569860)
    public User(Long userid, String nickname, String nameid, String iconurl, String password, String encryption, String phonenumber, String token, long expired, long registertime, long lastlogintime, boolean isactive) {
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
        this.isactive = isactive;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
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

    /**
     * 过期返回true,
     * @return
     */
    public boolean isExpired(){
        return this.getExpired()<System.currentTimeMillis();
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

    public boolean getIsactive() {
        return this.isactive;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userid);
        dest.writeString(this.nickname);
        dest.writeString(this.nameid);
        dest.writeString(this.iconurl);
        dest.writeString(this.password);
        dest.writeString(this.encryption);
        dest.writeString(this.phonenumber);
        dest.writeString(this.token);
        dest.writeLong(this.expired);
        dest.writeLong(this.registertime);
        dest.writeLong(this.lastlogintime);
        dest.writeByte(this.isactive ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.userid = (Long) in.readValue(Long.class.getClassLoader());
        this.nickname = in.readString();
        this.nameid = in.readString();
        this.iconurl = in.readString();
        this.password = in.readString();
        this.encryption = in.readString();
        this.phonenumber = in.readString();
        this.token = in.readString();
        this.expired = in.readLong();
        this.registertime = in.readLong();
        this.lastlogintime = in.readLong();
        this.isactive = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
