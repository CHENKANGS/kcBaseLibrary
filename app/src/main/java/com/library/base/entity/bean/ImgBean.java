package com.library.base.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ChenKang on 2017/11/14.
 */

public class ImgBean implements Serializable {

    /**
     * id : 1
     * path : uploads/20161121/eff5aba47155bd99df9d5d64edeacf6b.jpg
     * image_url : http://claim.cc/uploads/20161121/eff5aba47155bd99df9d5d64edeacf6b.jpg
     */

    @SerializedName("id")
    private String id;
    @SerializedName("path")
    private String path;
    @SerializedName("image_url")
    private String image_url;

    public ImgBean() {
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url( String image_url ) {
        this.image_url = image_url;
    }
}
