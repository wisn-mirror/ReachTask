package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.entity.Moment;
import com.wisn.mainmodule.http.HttpApi;
import com.wisn.mainmodule.http.HttpApiService;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.IMomentModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.presenter.HttpCallback;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Wisn
 * @time 2018/2/1 11:37
 */


public class MomentModel implements IMomentModel {
    private IUserModel userModel;
    private final HttpApi httpApi;

    public MomentModel(){
        userModel=new UserModel();
        httpApi = HttpApiService.getHttpApi();

    }
    @Override
    public List<Moment> getMoments(int offset, int limit) {
        /*String tokenByActiveUser = getTokenByActiveUser();
        if (tokenByActiveUser == null) {
            callback.onError("请登录");
            callback.onFinsh();
        }

        Call<HttpResponse<List<User>>> getUsers = httpApi.getUsers(tokenByActiveUser, offset, limit);
        getUsers.enqueue(new Callback<HttpResponse<List<User>>>() {
            @Override
            public void onResponse(Call<HttpResponse<List<User>>> call, retrofit2.Response<HttpResponse<List<User>>> response) {
                HttpResponse<List<User>> body1 = response.body();
                if (body1 != null && body1.getCode() == 200) {
                    callback.onSuccess(body1);
                } else {
                    callback.onError(body1.getMessage());
                }
                callback.onFinsh();
            }

            @Override
            public void onFailure(Call<HttpResponse<List<User>>> call, Throwable t) {
                callback.onError(t.getMessage());
                callback.onFinsh();
            }
        });*/
        return null;
    }

    @Override
    public void sendMoment(String content, String location, List<String> imagepath, List<String> videopath, final HttpCallback<String> callback) {
        String tokenByActiveUser = userModel.getTokenByActiveUser();
        if (tokenByActiveUser == null) {
            callback.onError("请登录");
            callback.onFinsh();
        }
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content",content)
                .addFormDataPart("location",location);
        for(String imagePath:imagepath){
            File file=new File(imagePath);
            builder.addFormDataPart("imageres",file.getName(),RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }
        for(String videoPath:videopath){
            File file=new File(videoPath);
            builder.addFormDataPart("videores",file.getName(),RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }
        MultipartBody build = builder.build();
        Call<HttpResponse<String>> sendMents = httpApi.sendMents(tokenByActiveUser, build);
        sendMents.enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(Call<HttpResponse<String>> call, retrofit2.Response<HttpResponse<String>> response) {
                HttpResponse<String> body1 = response.body();
                if (body1 != null && body1.getCode() == 200) {
                    callback.onSuccess(body1);
                } else {
                    callback.onError(body1.getMessage());
                }
                callback.onFinsh();
            }

            @Override
            public void onFailure(Call<HttpResponse<String>> call, Throwable t) {
                callback.onError(t.getMessage());
                callback.onFinsh();
            }
        });
    }
}
