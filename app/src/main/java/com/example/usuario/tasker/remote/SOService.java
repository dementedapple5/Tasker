package com.example.usuario.tasker.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Samuel on 02/11/2017.
 */

public interface SOService {


    @Multipart
    @POST("check_user.php")
    Call<Boolean> userExists(@Part("username") String username, @Part("pass") String pass);

/*    @GET("getresiscercanas.php")
    Call<List<Residencias» getResisCercanas(@Query("lat") String lat, @Query("lng") String lng);

    @GET("getfavresis.php")
    Call<List<Residencias» getFavResis(@Query("username") String username);

    @Multipart
    @POST("check-resi-favorita.php")
    Call<ResponseBody> checkResiFavorita(@Part("nombreResi") String nombreResi);

    @Multipart
    @POST("update-resi-favorita.php")
    Call<Void> updateResiFavorita(@Part("username") String username, @Part("name") String accion);*/
}
