package com.example.usuario.tasker.remote;


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
    Call<Boolean> userExists(@Part("username") String username, @Part("pass") String pass);

    @Multipart
    @POST("create_user.php")
    Call<Boolean> createUser(@Part("username") String username, @Part("pass") String pass, @Part("name") String name);

}
