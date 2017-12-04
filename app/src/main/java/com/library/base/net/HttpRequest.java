package com.library.base.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.base.entity.bean.NewsBean;
import com.library.base.global.GlobalVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpRequest extends RetrofitUtil {

    private static HttpRequest instance;

    private Context context;

    //获取单例
    public static HttpRequest getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpRequest INSTANCE = new HttpRequest();
    }


    public static HttpRequest getInstance(Context context) {
        if (instance == null) {
            instance = new HttpRequest();
        }
        instance.context = context;
        return instance;
    }


    /**
     * 资讯
     * @param appkey
     * @param appsecret
     * @param callback
     */
    public void getHomeZX(String appkey,String appsecret ,ResponseCallback<List<NewsBean>> callback) {
//        PostHomeZxModel model = new PostHomeZxModel();
//        model.setToken(GlobalVariable.token);
//        model.setAppkey(appkey);
//        model.setAppsecret(appsecret);

        HashMap<String, String> params = new HashMap<>();
        params.put("appkey", appkey);
        params.put("appsecret", appsecret);
        getService().getZX(params).enqueue(callback);
    }


















//    /**
//     * 获取版本
//     * @param appkey
//     * @param appsecret
//     * @param callback
//     */
//    public void getVersion( String appkey, String appsecret , ResponseCallback<UpdateModel> callback) {
////        PostHomeZxModel model = new PostHomeZxModel();
////        model.setToken(GlobalVariable.token);
////        model.setAppkey(appkey);
////        model.setAppsecret(appsecret);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("appkey", appkey);
//        params.put("appsecret", appsecret);
//        getService().getVersionCode(params).enqueue(callback);
//    }
//
//
//    /**
//     * 违章查询区域
//     */
//    public void getWZQY( String appkey, String appsecret , String pid, ResponseCallback<List<WZCX_QY_Model> > callback) {
//
//    HashMap<String, String> params = new HashMap<>();
//    params.put("appkey", appkey);
//    params.put("appsecret", appsecret);
//    params.put("token", GlobalVariable.token);
//        getService().xeqy(pid , params).enqueue(callback);
//    }




    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    public String post( String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }



    public void uploadImage( String appkeys, String appsecrets, File file) {

//        Map<String,RequestBody> params = new HashMap<>();
//        params.put("token", GlobalVariable.token);
//        // 创建 RequestBody，用于封装构建RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        Retrofit retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://192.168.1.77:81")
                .baseUrl("http://cm.uu139.com")
                .build();
        uploadfileApi service =retrofit.create(uploadfileApi.class);
//        File file = new File(Environment.getExternalStorageDirectory() + "/" + "1.txt");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        RequestBody appkey = RequestBody.create(MediaType.parse("appkey"), GlobalVariable.appkey);
        RequestBody appsecret = RequestBody.create(MediaType.parse("appsecret"), GlobalVariable.appsecret);
        Call<String > model = service.upload( GlobalVariable.appkey,GlobalVariable.appsecret,requestBody);
        model.enqueue(new Callback<String >() {
            @Override
            public void onResponse( Call<String > call, Response<String > response) {
                Log.e("==uploadfileApi==", "onResponse: " + response.body().toString() );
            }
            @Override
            public void onFailure( Call<String > call, Throwable t) {
                Log.e("==uploadfileApi==", "Throwable: " + t );
            }
        });


//        HashMap<String, String> params = new HashMap<>();
//        params.put("appkey", appkey);
//        params.put("appsecret", appsecret);
//        // 创建 RequestBody，用于封装构建RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//
//        // 添加描述
//        String descriptionString = "hello, 这是文件描述";
//        RequestBody description =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), descriptionString);
//
//        getService().uploadFile(params,body).enqueue(callback);

//        // 执行请求
//        Call<ResponseBody> call = service.upload(description, body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call,
//                                   Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
    }




    /**
     * 将文件路径数组封装为{@link List <MultipartBody.Part>}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts( String key,
                                                        String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }


    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 参数名（name属性）
     * @param value 文本内容
     * @param position 插入的位置
     */
    public static void addTextPart( List<MultipartBody.Part> parts,
                                    String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     * @param builder 用于构建MultipartBody的Builder
     * @param key 参数名（name属性）
     * @param value 文本内容
     */
    public static MultipartBody.Builder addTextPart( MultipartBody.Builder builder,
                                                     String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key, null, requestBody);
        return builder;
    }



}
