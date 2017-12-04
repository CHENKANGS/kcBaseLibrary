package com.library.base.global;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * 全局常量统一在此保存，如文件路径等
 *
 * @author shishuyao
 */
public class GlobalConstant {

//    public static final String API_HOST = "http://claim.uu139.com/";
//    public final static String API_HOST = "http://192.168.1.77:81/";
    public final static String API_HOST = "http://cm.uu139.com";
//    public final static String API_HOST = "http://192.168.1.123:100";
//    public final static String API_HOST = "http://59.173.111.99:10000";

    public static final String IMG_HOST = "http://120.24.16.77:8081/";

    public static final String GETSIGN = API_HOST + "app/pay/alipay/sign";
    /**
     * 服务器返回成功
     */
//    public static final String REQUEST_SUCCESS = "0";
    public static final String REQUEST_SUCCESS = "00000";

    public static final int IMAGE_SIZE = 480;

    public static final int PERSON_IMAGE_SIZE = 320;

    public static final int DEFAULT_PAGES_NUMBERS = 10;


    public static final String PROJECT_NAME = "KcMVP";
    /**
     * 路径
     */
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + PROJECT_NAME + File.separator + "clue" + File.separator;

    /**
     * 网络缓存路径
     */
    public static final String NET_CATCH_DIR = BASE_PATH + File.separator + ".netcatch";
    /**
     * 网络缓存大小
     */
    public static final int NET_CATCH_SIZE_52428800 = 52428800;

    public static final String SPF_NAME_USER = "UserInfo";

    public static final String SPF_NAME_COMMON = PROJECT_NAME;
    public static final String LOGON_ACTION = "JESMVP_LOGIN_ACTION";
    /**
     * 数字常量把值包含在名字之中
     */
    public static final int NET_TIMEOUT_30 = 30;
    public static final int NET_TIMEOUT_60 = 60;
    public static final int NET_TIMEOUT_120 = 120;
    public static final int NET_TIMEOUT_600 = 600;
    /**
     * 网络状态码
     */
    public static final int NET_CODE_SUCCESS = 10000;
    //重新登录
    public static final int NET_CODE_RE_LOGIN = 10089;
    //500
    public static final int NET_CODE_INTERNAL_ERROR = 10500;


    // 微信APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxc0078b1212139196";

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }


}
