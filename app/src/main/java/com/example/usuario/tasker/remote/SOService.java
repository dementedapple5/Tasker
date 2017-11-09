package com.example.usuario.tasker.remote;


import com.example.usuario.tasker.pojoObjects.TaskPojo;
import com.example.usuario.tasker.pojoObjects.UserPojo;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Samuel on 02/11/2017.
 */

public interface SOService {


    @Multipart
    @POST("check_user.php")
    Call<ResponseBody> userExists(@Part("username") RequestBody username, @Part("pass") RequestBody pass);

    @Multipart
    @POST("select_tasks.php")
    Call<List<TaskPojo>> select_tasks(@Part("username") String username);

    @Multipart
    @POST("create_tasks.php")
    Call<Void> createTask(@Part("username") String username, @Part("title") String title, @Part("comment") String comment, @Part("des") String desc,@Part("prior") Integer prior);


    @POST("select_users.php")
    Call<List<UserPojo>> select_users();

    @Multipart
    @POST("task_done.php")
    Call<Void> taskDone(@Part("username") String username, @Part("title") String title, @Part("fecha") String fecha);


    @Multipart
    @POST("create_user.php")
    Call<Void> createUser(@Part("username") String username, @Part("pass") String pass, @Part("name") String name);

}
