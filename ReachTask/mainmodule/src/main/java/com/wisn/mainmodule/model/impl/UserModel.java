package com.wisn.mainmodule.model.impl;

import android.util.Log;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.mainmodule.entity.DaoSession;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.entity.UserDao;
import com.wisn.mainmodule.http.HttpApi;
import com.wisn.mainmodule.http.HttpApiService;
import com.wisn.mainmodule.http.request.ChangePassword;
import com.wisn.mainmodule.http.request.Login;
import com.wisn.mainmodule.http.request.Register;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.presenter.HttpCallback;
import com.wisn.mainmodule.utils.GsonTool;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Wisn
 * @time 2018/1/24 14:56
 */


public class UserModel implements IUserModel {

    private final HttpApi httpApi;


    public UserModel() {
        httpApi = HttpApiService.getHttpApi();

    }

    @Override
    public void updatePassword(ChangePassword changePassword, final HttpCallback<String> callback) {
        String tokenByActiveUser = getTokenByActiveUser();
        if (tokenByActiveUser == null) {
            callback.onError("请登录");
            callback.onFinsh();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), GsonTool.toJson(changePassword));
        Call<HttpResponse<String>> updatepassword = httpApi.changePassword(tokenByActiveUser, body);
        updatepassword.enqueue(new Callback<HttpResponse<String>>() {
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

    @Override
    public void register(Register user, final HttpCallback<String> callback) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), GsonTool.toJson(user));
        Call<HttpResponse<String>> register = httpApi.register(body);
        register.enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(Call<HttpResponse<String>> call, retrofit2.Response<HttpResponse<String>> response) {
                HttpResponse<String> body1 = response.body();
                Log.e("register", body1.toString());
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

    @Override
    public void login(Login user, final HttpCallback<User> callback) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), GsonTool.toJson(user));
        Call<HttpResponse<User>> login = httpApi.login(body);
        login.enqueue(new Callback<HttpResponse<User>>() {
            @Override
            public void onResponse(Call<HttpResponse<User>> call, retrofit2.Response<HttpResponse<User>> response) {
                HttpResponse<User> body1 = response.body();

                Log.e("login", body1.toString());
                if (body1 != null && body1.getCode() == 200) {
                    callback.onSuccess(body1);
                } else {

                    callback.onError(body1.getMessage());
                }
                callback.onFinsh();
            }

            @Override
            public void onFailure(Call<HttpResponse<User>> call, Throwable t) {
                callback.onError(t.getMessage());
                callback.onFinsh();
            }
        });
    }

    @Override
    public void getUser(final HttpCallback<List<User>> callback, int offset, int limit) {
        String tokenByActiveUser = getTokenByActiveUser();
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
        });
    }

    @Override
    public void loginOut(final HttpCallback<String> callback) {
        Call<HttpResponse<String>> loginout = httpApi.loginOut(getTokenByActiveUser());
        loginout.enqueue(new Callback<HttpResponse<String>>() {
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

    @Override
    public boolean isLogin(String phoneNumber) {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        Query<User> build = userDao.queryBuilder().where(UserDao.Properties.Phonenumber.eq(phoneNumber)).build();
        List<User> list = build.list();
        if (list == null || list.size() == 0) return false;
        if (list.size() == 1 && list.get(0) != null && list.get(0).getPhonenumber().equals(phoneNumber) && list.get(0).isIsactive())
            return true;
        return false;
    }

    @Override
    public void saveUser(final User user, boolean isActive) {
        user.setIsactive(isActive);
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        userDao.insertOrReplace(user);
    }

    public User getUserbyUserid(long userid) {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        Query<User> query = userDao.queryBuilder().where(UserDao.Properties.Userid.eq(userid))
                .orderDesc(UserDao.Properties.Userid).build();
        return query.unique();
    }

    @Override
    public void saveUsers(final List<User> user) {
        DaoSession daoSession = MApplication.getInstance().getDaoSession();
        final UserDao userDao1 = daoSession.getUserDao();
        daoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (User usertemp : user) {
                    userDao1.insertOrReplace(usertemp);
                }
            }
        });
    }

    @Override
    public void deleteUser(User user) {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        userDao.delete(user);
    }

    @Override
    public void updateUser(User user) {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        userDao.update(user);
    }

    @Override
    public User getActiveUser() {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        Query<User> query = userDao.queryBuilder().where(UserDao.Properties.Isactive.eq(true))
                .orderDesc(UserDao.Properties.Userid).build();
        User user = query.unique();
        if (user != null) {
            System.err.println(user.getToken());
            return user;
        }
        System.err.println(user);
        return null;
    }

    @Override
    public String getTokenByActiveUser() {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        Query<User> query = userDao.queryBuilder().where(UserDao.Properties.Isactive.eq(true))
                .orderDesc(UserDao.Properties.Userid).build();
        User user = query.unique();
        if (user != null) {
            System.err.println(user.getToken());
            return user.getToken();
        }
        System.err.println(user);
        return null;
    }

    @Override
    public List<User> getUsers(String phoneNumber) {
        UserDao userDao = MApplication.getInstance().getDaoSession().getUserDao();
        List<User> users = userDao.loadAll();
        for (User userone : users) {
            System.err.println("  ddd:" + userone);
        }
        System.err.println("  dddddd:" + users);
        return users;
    }
}
