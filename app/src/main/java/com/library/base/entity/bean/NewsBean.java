package com.library.base.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ChenKang on 2017/11/14.
 */

public class NewsBean  implements Serializable {

    /**
     * id : 1
     * url : http://claim.manage.cc/webview/news/2
     * title : 法国恢复供货
     * pic : http://claim.manage.cc/uploads/20170322/deb721a554799611c6164d9005f7999b.jpg
     * created_at : 2017-03-22
     */

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("pic")
    private String pic;
    @SerializedName("created_at")
    private String created_at;
    /**
     * id : 3
     * excerpt : 中华保险和中国中车签署战略合作协议
     */

    @SerializedName("excerpt")
    private String excerpt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

}
