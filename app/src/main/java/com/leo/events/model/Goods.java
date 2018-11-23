package com.leo.events.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by spf on 2018/10/23.
 */

public class Goods extends BaseModel{


    public Goods() {
        type = TYPE_ADAPTER_ONE;
    }

    /**
     * id : 221
     * images : []
     * comments : []
     * publishDate : 2018-10-23 13:13:45
     * text : null
     * smallImage : /uploads/1/image/public/201810/20181023131342_8p3u0rjrm8.jpeg
     * title : 这个一个完整商品标题
     */

    @SerializedName("id")
    public int id;
    @SerializedName("publishDate")
    public String publishDate;
    @SerializedName("text")
    public String text;
    @SerializedName("smallImage")
    public String smallImage;
    @SerializedName("title")
    public String title;
    @SerializedName("images")
    public List<?> images;
    @SerializedName("comments")
    public List<?> comments;

}
