package com.leo.events.net;


import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
    *
 */
public interface AppService {

    @GET("jspxcms-9.0.0/simpleinfos")
    Observable<JsonObject> getSimpleInfos();

    @GET("jspxcms-9.0.0/simpleinfo/{id}")
    Observable<String> getSimpleInfo(@Path("id")int id);
}
