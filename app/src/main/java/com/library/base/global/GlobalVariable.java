package com.library.base.global;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 全局变量统一在此保存，如文件路径等
 *
 * @author shishuyao
 */
public class GlobalVariable {

    public static String msg = "连接服务器失败，请检查网络";
    public static String response_ret = "ret";//表示返回的code
    public static String response_msg = "msg";//表示返回的msg
    public static String response_result = "result";//表示返回的数据
    public static String ret_code = "00000";//表示成功
    public static String login_error = "9000"; //登录失效
    public static String login_error_key = "login_error"; //登录失效
    public static String thumb = ".thumb.jpg" ;  // 给图片加上缩略图的后缀
    public static String externalStorageDirectory  ;  // 压缩图片的路径
    public static File tempFileImage ;
    public static String image_name = "tempCompress.jpg"  ;  // 图片名字

    public static String user_id_yk = "2" ;//游客id
    public static String realname_yk = "游客" ;//游客名称
    public static String token_yk = "1yq7moup8JKybW0Kdfc183jjSjTYkaRjcMewTZB5zxuH3fxM3fn0DQekNhb2" ;//游客固定token

    public static int localVersionCode;//版本号
    public static String localVersionName;//版本号名称

    public static int width;//屏幕的宽
    public static int height;//屏幕的高

    public static double latitude ;//经纬度
    public static double longitude ;
    public static String mapAddress ; //根据经纬度编译出来的地址

    //判断公司的key
    public static String appkey = "lvX0jaeZBIdwtHyz";
    public static String appsecret = "e125579c07bad67d2bde9b3a79cdb6c2";
    //阳光的
//    public static String appkey = "1mSAen2CDwBSOyZS";
//    public static String appsecret = "f6f782dd49649a1997fe557583043d6d";

    public static String company_id ;//保险公司id
    public static String mCheckUrl = "http://cm.uu139.com/api/version/android?appkey="+appkey+"&appsecret="+appsecret;//更新


    public static boolean isDebug = true;
    public static boolean isBackPress;
    public static String token;
    public static String user_id;
    public static String user_name;
    public static String realname;
    public static String jobno;
    public static String user_image;


    public static String DS_Print = "D";
    public static String CK_Print = "C";


}
