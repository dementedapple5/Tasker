package com.example.usuario.tasker.remote;

/**
 * Created by Samuel on 02/11/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://centraldemascotas.com/aplicaciones/tasker/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
