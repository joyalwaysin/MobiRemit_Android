package com.nagainfo.mobiremit.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://182.72.164.246:91/MobileRemit/api/admin/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            // time out is the api response wait time for the app(upto 20 seconds api wait for the response-used in slow internet connection)
            httpBuilder.connectTimeout(20, TimeUnit.SECONDS);
            httpBuilder.readTimeout(20, TimeUnit.SECONDS);
            httpBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient okClient = httpBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
