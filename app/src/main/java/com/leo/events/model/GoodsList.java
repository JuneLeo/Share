package com.leo.events.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by spf on 2018/10/23.
 */

public class GoodsList {


    /**
     * success : true
     * info : null
     * result : null
     * entity : {"totalPage":1,"pageSize":5,"totalCount":1,"currentPage":1,"entityData":[{"id":221,"images":[],"comments":[],"publishDate":"2018-10-23 13:13:45","text":null,"smallImage":"/uploads/1/image/public/201810/20181023131342_8p3u0rjrm8.jpeg","title":"这个一个完整商品标题"}]}
     */

    @SerializedName("success")
    public boolean success;
    @SerializedName("info")
    public Object info;
    @SerializedName("result")
    public Object result;
    @SerializedName("entity")
    public EntityBean entity;

    public static class EntityBean {
        /**
         * totalPage : 1
         * pageSize : 5
         * totalCount : 1
         * currentPage : 1
         * entityData : [{"id":221,"images":[],"comments":[],"publishDate":"2018-10-23 13:13:45","text":null,"smallImage":"/uploads/1/image/public/201810/20181023131342_8p3u0rjrm8.jpeg","title":"这个一个完整商品标题"}]
         */

        @SerializedName("totalPage")
        public int totalPage;
        @SerializedName("pageSize")
        public int pageSize;
        @SerializedName("totalCount")
        public int totalCount;
        @SerializedName("currentPage")
        public int currentPage;
        @SerializedName("entityData")
        public List<Goods> entityData;
    }

}
