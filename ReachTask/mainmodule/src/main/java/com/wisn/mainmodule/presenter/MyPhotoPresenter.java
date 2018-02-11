package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.MyPhotoView;


/**
 * @author Wisn
 * @time 2018/2/11 8:22
 */


public class MyPhotoPresenter implements HttpCallback<String> {

    UserModel model=null;
    MyPhotoView  myPhotoView=null;
    public MyPhotoPresenter(MyPhotoView  myPhotoView){
         model=new UserModel();
         this.myPhotoView=myPhotoView;
        User activeUser = model.getActiveUser();
        myPhotoView.updatePhoto(activeUser.getIconurl());
    }
    public void  updateIcon(String path){
        model.updateIcon(path, this );
    }

    @Override
    public void onSuccess(HttpResponse<String> response) {
        String data = response.getData();
        User activeUser = model.getActiveUser();
        activeUser.setIconurl(data);
        model.updateUser(activeUser);
        myPhotoView.updatePhoto(data);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onFinsh() {

    }
}
