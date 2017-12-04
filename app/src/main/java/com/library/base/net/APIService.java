package com.library.base.net;


import com.library.base.entity.bean.ImgBean;
import com.library.base.entity.basemodel.ResponseModel;
import com.library.base.entity.bean.NewsBean;
import com.library.base.entity.bean.UserBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface APIService {


    /**
     * 首页资讯
     * @return
     */
    @GET("/api/news")
    Call<ResponseModel<List<NewsBean>>> getZX(@QueryMap HashMap<String, String> params);


    @GET("/api/news")
    Observable<ResponseModel<List<NewsBean>>> getZXh(@QueryMap HashMap<String, String> params);
    @GET("/api/news")
    Observable<ResponseModel<List<NewsBean>>> getZXss(@Query("appkey") String userName, @Query("appsecret") String password);


    @DELETE("/api/news")
    Observable<ResponseModel<List<NewsBean>>> getZXhDE(@QueryMap HashMap<String, String> params);
    @PUT("/api/news")
    Observable<ResponseModel<List<NewsBean>>> getZXhPT(@QueryMap HashMap<String, String> params);



















//    /**
//     * 登录
//     *
//     * @param postLoginModel
//     * @return
//     */
//    @POST("api/user/login")
//    Call<ResponseModel<UserModel>> login( @Body PostLoginModel postLoginModel );
//    /**
//     * 违章查询
//     * @param postWZCXModel
//     * @return
//     */
//    @POST("api/traffic/offence")
//    Call<ResponseModel<WZCX_DetailModel>> wzcx( @Body PostWZCXModel postWZCXModel );
//    /**
//     * 违章查询里面区域查询
//     * @param
//     * @return
//     */
//    @GET("api/traffic/bureau/{id}")
//    Call<ResponseModel<List<WZCX_QY_Model> >> xeqy( @Path("id") String id, @QueryMap HashMap< String, String > params );
//    /**
//     * 行驶证查询里面区域查询
//     * @param
//     * @return
//     */
//    @GET("api/traffic/tarea/{id}")
//    Call<ResponseModel<List<WZCX_QY_Model> >> xsz_qy( @Path("id") String id, @QueryMap HashMap< String, String > params );
//    /**
//     * 获取版本
//     * @param params
//     * @return
//     */
//    @GET("/api/version/android")
//    Call<ResponseModel<UpdateModel>> getVersionCode( @QueryMap HashMap< String, String > params );


    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意：使用{@code @Multipart}注解方法，必须使用{@code @Part}/<br>
     * {@code @PartMap}注解其参数<br>
     * 本接口中将文本数据和文件数据分为了两个参数，是为了方便将封装<br>
     * {@link MultipartBody.Part}的代码抽取到工具类中<br>
     * 也可以合并成一个{@code @Part}参数
     *
     * @param params 用于封装文本数据
     * @param parts  用于封装文件数据
     * @return BaseResp为服务器返回的基本Json数据的Model类
     */
    @Multipart
    @POST("/api/tool/addImage")

    Call<ResponseModel<ImgBean>> requestUploadWork(@PartMap Map<String, RequestBody> params,
                                                   @Part List<MultipartBody.Part> parts);

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意2：使用{@code @Body}注解参数，则不能使用{@code @Multipart}注解方法了<br>
     * 直接将所有的{@link MultipartBody.Part}合并到一个{@link MultipartBody}中
     */
    @POST("/api/tool/addImage")
    Call<ResponseModel<ImgBean>> requestUploadWork(@Body MultipartBody body);


    @Multipart
    @POST("/api/tool/addImage")
    Call<ResponseModel<ImgBean>> uploadFile(@QueryMap HashMap<String, String> params, @Body MultipartBody.Part body);

}
