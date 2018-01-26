package com.wisn.mainmodule.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.wisn.mainmodule.protocal.protobuf.beans.EMessageMudule;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * `messageid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息id',
 * `fromuserid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发送者用户ID',
 * `targetuserid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '接收者用户ID',
 * `type` int(4) NOT NULL COMMENT '消息类型',
 * `status` int(2) NOT NULL COMMENT '消息状态',
 * `content` varchar(1000) NOT NULL COMMENT '手机号',
 * `createtime` bigint(13) NOT NULL COMMENT '创建时间',
 * `receivetime` bigint(13) NOT NULL COMMENT '接收时间'
 */
@Entity
public class Message implements Parcelable {
    @Property(nameInDb = "_id")
    @Id
    private Long messageid;
    private long fromuserid;
    private long targetuserid;
    private int messagetype;
    private int status;
    private long contactid;
    private String token;
    private String content;
    private long createtime;
    private long receivetime;

    public Message() {

    }

    public long getContactid() {
        return contactid;
    }

    public void setContactid(long contactid) {
        this.contactid = contactid;
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            return " ";
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getMessageid() {
        if(messageid==null){
            return System.nanoTime();
        }
        return messageid;
    }

    public void setMessageid(Long messageid) {
        this.messageid = messageid;
    }

    public long getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(long fromuserid) {
        this.fromuserid = fromuserid;
    }

    public long getTargetuserid() {
        return targetuserid;
    }

    public void setTargetuserid(long targetuserid) {
        this.targetuserid = targetuserid;
    }

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        if (TextUtils.isEmpty(content)) {
            return " ";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(long receivetime) {
        this.receivetime = receivetime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageid=" + messageid +
                ", fromuserid=" + fromuserid +
                ", targetuserid=" + targetuserid +
                ", type=" + messagetype +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", receivetime=" + receivetime +
                '}';
    }


    public EMessageMudule.EMessage buildEMessage() {
        EMessageMudule.EMessage eMessage = EMessageMudule.EMessage.newBuilder()
                .setMessageid(this.getMessageid())
                .setFromuserid(this.getFromuserid())
                .setTargetuserid(this.getTargetuserid())
                .setMessagetype(this.getMessagetype())
                .setStatus(this.getStatus())
                .setContent(this.getContent())
                .setToken(this.getToken())
                .setCreatetime(System.currentTimeMillis())
                .setReceivetime(-1).build();
        return eMessage;
    }

    public  Message valueOf(EMessageMudule.EMessage emessage) {
        this.setMessageid(emessage.getMessageid());
        this.setFromuserid(emessage.getFromuserid());
        this.setTargetuserid(emessage.getTargetuserid());
        this.setMessagetype(emessage.getMessagetype());
        this.setToken(emessage.getToken());
        this.setStatus(emessage.getStatus());
        this.setContent(emessage.getContent());
        this.setCreatetime(emessage.getCreatetime());
        this.setReceivetime(System.currentTimeMillis());
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.messageid);
        dest.writeLong(this.fromuserid);
        dest.writeLong(this.targetuserid);
        dest.writeInt(this.messagetype);
        dest.writeInt(this.status);
        dest.writeString(this.token);
        dest.writeString(this.content);
        dest.writeLong(this.createtime);
        dest.writeLong(this.receivetime);
    }

    protected Message(Parcel in) {
        this.messageid = (Long) in.readValue(Long.class.getClassLoader());
        this.fromuserid = in.readLong();
        this.targetuserid = in.readLong();
        this.messagetype = in.readInt();
        this.status = in.readInt();
        this.token = in.readString();
        this.content = in.readString();
        this.createtime = in.readLong();
        this.receivetime = in.readLong();
    }

    @Generated(hash = 2047741352)
    public Message(Long messageid, long fromuserid, long targetuserid,
            int messagetype, int status, long contactid, String token,
            String content, long createtime, long receivetime) {
        this.messageid = messageid;
        this.fromuserid = fromuserid;
        this.targetuserid = targetuserid;
        this.messagetype = messagetype;
        this.status = status;
        this.contactid = contactid;
        this.token = token;
        this.content = content;
        this.createtime = createtime;
        this.receivetime = receivetime;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
