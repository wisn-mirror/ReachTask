package com.wisn.mainmodule.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author Wisn
 * @time 2018/1/26 15:00
 */

@Entity
public class Contact {
    @Property(nameInDb = "_id")
    @Id
    private Long contactid;
    private Long fromuserid;
    private Long targetuserid;
    private String icon;
    private String name;
    private String lastmessage;
    private Long lastcontacttime;
    private boolean isremind;

    public Contact() {
    }
    @Generated(hash = 805730274)
    public Contact(Long contactid, Long fromuserid, Long targetuserid, String icon, String name, String lastmessage, Long lastcontacttime, boolean isremind) {
        this.contactid = contactid;
        this.fromuserid = fromuserid;
        this.targetuserid = targetuserid;
        this.icon = icon;
        this.name = name;
        this.lastmessage = lastmessage;
        this.lastcontacttime = lastcontacttime;
        this.isremind = isremind;
    }
    public Long getContactid() {
        return contactid;
    }

    public void setContactid(Long contactid) {
        this.contactid = contactid;
    }

    public Long getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Long fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Long getTargetuserid() {
        return targetuserid;
    }

    public void setTargetuserid(Long targetuserid) {
        this.targetuserid = targetuserid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public Long getLastcontacttime() {
        return lastcontacttime;
    }

    public void setLastcontacttime(Long lastcontacttime) {
        this.lastcontacttime = lastcontacttime;
    }

    public boolean isIsremind() {
        return isremind;
    }

    public void setIsremind(boolean isremind) {
        this.isremind = isremind;
    }
    public boolean getIsremind() {
        return this.isremind;
    }
}
