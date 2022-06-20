package com.example.appmusicmp3.data.datasources.remote;

import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.RetrofitClient;

public class AppConstant {
    public static String BASE_URL = "https://phidev777.000webhostapp.com/Server/";

    public static APIService getService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
