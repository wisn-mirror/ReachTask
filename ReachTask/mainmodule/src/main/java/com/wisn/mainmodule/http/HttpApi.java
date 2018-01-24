package com.wisn.mainmodule.http;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.response.HttpResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * @author Wisn
 * @time 2018/1/23 14:01
 */


public interface HttpApi {

    @Headers({"Content-Type:application/json"})
    @POST("user/register")
    Call<HttpResponse<String>> register(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json"})
    @POST("user/login")
    Call<HttpResponse<User>> login(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json"})
    @PUT("userdata/changpassword")
    Call<HttpResponse<String>> changePassword(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @Headers({"Content-Type:application/json"})
    @DELETE("userdata/loginout")
    Call<HttpResponse<String>> loginOut(@Header("Authorization") String authorization);

    @Headers({"Content-Type:application/json"})
    @GET("userdata/getusers")
    Call<HttpResponse<List<User>>> getUsers(@Header("Authorization") String authorization, @Query("offset") int offset, @Query("limit") int limit);
}
