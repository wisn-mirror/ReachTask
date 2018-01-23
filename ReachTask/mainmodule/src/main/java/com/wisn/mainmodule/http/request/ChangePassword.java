package com.wisn.mainmodule.http.request;

/**
 * @author Wisn
 * @time 2018/1/23 13:56
 */


public class ChangePassword {

    /**
     * oldPassword : 4444
     * newPassword : 4444
     */

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
