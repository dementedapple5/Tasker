package com.example.usuario.tasker.remote;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    Call<ResponseBody> select_tasks(@Part("username") RequestBody username);

    @Multipart
    @POST("create_tasks.php")
    Call<Void> createTask(@Part("username") String username, @Part("title") String title, @Part("comment") String comment, @Part("des") String desc,@Part("prior") Integer prior);



    @Multipart
    @POST("create_user.php")
    Call<Void> createUser(@Part("username") String username, @Part("pass") String pass, @Part("name") String name);

}
