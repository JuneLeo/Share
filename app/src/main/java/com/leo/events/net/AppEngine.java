package com.leo.events.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leo.events.BuildConfig;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wsl on 16-1-6.
 */
public final class AppEngine {

    private static final String BASE_URL = "http://120.78.206.49:8080/";

    public static final int HTTP_CONNECTION_TIMEOUT = 15;
    public static final int HTTP_READ_TIMEOUT = 0;
    public static final int HTTP_WRITE_TIMEOUT = 15;
    
    private AppService appService;

    private static class AppEngineHolder {
        private static final AppEngine INSTANCE = new AppEngine();
    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder()
                .create();

        appService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(defaultOkHttpClient())
                .build()
                .create(AppService.class);
    }

    private static OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(new LoggingInterceptor.Builder()
//                    .loggable(true)
//                    .setLevel(Level.BASIC)
//                    .log(Platform.INFO)
//                    .request("Request")
//                    .response("Response")
//                    .addHeader("version", BuildConfig.VERSION_NAME)
//                    .build());
//            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }

    public static AppEngine getInstance() {
        return AppEngineHolder.INSTANCE;
    }

    public AppService getAppService() {
        if (appService == null) {
            initRetrofit();
        }
        return appService;
    }
}