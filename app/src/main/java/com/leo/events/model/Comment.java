package com.leo.events.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by spf on 2018/11/28.
 */
public class Comment {

    /**
     * id : 176
     * creator : {"username":"admin","realName":"管理员","avatarSmall":"/uploads/users/1/avatar_small.jpg"}
     * parent : null
     * fid : 231
     * creationDate : 2018-10-09 17:05:15
     * text : g
     */

    @SerializedName("id")
    public int id;
    @SerializedName("creator")
    public CreatorBean creator;
    @SerializedName("parent")
    public String parent = "";
    @SerializedName("fid")
    public int fid;
    @SerializedName("creationDate")
    public String creationDate;
    @SerializedName("text")
    public String text = "";

    public static class CreatorBean {
        /**
         * username : admin
         * realName : 管理员
         * avatarSmall : /uploads/users/1/avatar_small.jpg
         */

        @SerializedName("username")
        public String username = "";
        @SerializedName("realName")
        public String realName;
        @SerializedName("avatarSmall")
        public String avatarSmall;
    }
}
