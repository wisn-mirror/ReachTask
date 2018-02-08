package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.MineFragmentView;

/**
 * @author Wisn
 * @time 2018/2/8 10:58
 */


public class MineFragmentPresenter {
    private  MineFragmentView mineFragmenView;
    private UserModel  userModel;
    public MineFragmentPresenter(MineFragmentView mineFragmentView){
        this.mineFragmenView=mineFragmentView;
        userModel=new UserModel();
    }
    public void load(){
        User activeUser = userModel.getActiveUser();
        mineFragmenView.updateInfo(activeUser);
    }
}
