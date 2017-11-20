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
    @POST("update_token.php")
    Call<ResponseBody> updateToken(@Part("username") RequestBody username, @Part("pass") RequestBody pass, @Part("token") RequestBody token);

    @Multipart
    @POST("select_tasks.php")
    Call<List<TaskPojo>> select_tasks(@Part("encargado") RequestBody username);

    @Multipart
    @POST("select_tasks_completed.php")
    Call<List<TaskPojo>> select_tasks_completed(@Part("encargado") RequestBody username);

    @Multipart
    @POST("insert_tasks.php")
    Call<Void> createTask(@Part("encargado") RequestBody username, @Part("titulo") RequestBody title, @Part("coments") RequestBody comment, @Part("contenido") RequestBody desc,@Part("prioridad") Integer prior,@Part("fecha") RequestBody date,@Part("estado") boolean state,@Part("visible") boolean visible);


    @POST("select_users.php")
    Call<List<UserPojo>> select_users();

    @Multipart
    @POST("task_done.php")
    Call<Void> taskDone(@Part("encargado") RequestBody username, @Part("titulo") RequestBody title, @Part("fecha") RequestBody fecha);


    @Multipart
    @POST("task_update.php")
    Call<Void> taskUpdate(@Part("encargado") RequestBody username, @Part("titulo") RequestBody title, @Part("coments") RequestBody fecha,@Part("prioridad") RequestBody prioridad,@Part("contenido") RequestBody contenido,@Part("encargadoBefore") RequestBody encargadoBefore,@Part("tituloBefore") RequestBody tituloBefore,@Part("fechaBefore") RequestBody fechaBefore);

    @Multipart
    @POST("create_user.php")
    Call<Void> createUser(@Part("username") String username, @Part("pass") String pass, @Part("name") String name,@Part("token") String token);

}
