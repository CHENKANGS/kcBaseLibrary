package com.library.base.net;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by ChenKang on 2017/8/3.
 */
public interface uploadfileApi {

    /**
     * 上传一张图片
     * @param
     * @param imgs
     * @return
     */
    @Multipart
    @POST("/api/tool/addImage")
    Call<String > uploadImage( @Part("appkey") RequestBody appkey, @Part("appsecret") RequestBody appsecret,
                               @Part("file\"; filename=\"image.png\"") RequestBody imgs );

    /**
     * 上传三张图片
     * @param description
     * @param imgs
     * @param imgs1
     * @param imgs3
     * @return
     */
    @Multipart
    @POST("/upload")
    Call<String > uploadImage( @Part("fileName") String description,
                               @Part("file\"; filename=\"image.png\"") RequestBody imgs,
                               @Part("file\"; filename=\"image.png\"") RequestBody imgs1,
                               @Part("file\"; filename=\"image.png\"") RequestBody imgs3 );



    @Multipart
    @POST("/api/tool/addImage")
    Call<String > upload( @Part("appkey") String appkey, @Part("appsecret") String appsecret,
                          @Part("image") RequestBody file );
//    Observable<DataResponse<UploadFile>> uploadFile(@Part("file\"; filename=\"avatar.png\"") RequestBody file，@Part("nickName") RequestBody nickName)

    @Multipart
    @POST("/api/attachment/quickcase")
    Call<String > upload_2( @PartMap Map< String, RequestBody > params );
}