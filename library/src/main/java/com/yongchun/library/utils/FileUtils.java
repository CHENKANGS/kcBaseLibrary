package com.yongchun.library.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dee on 15/11/20.
 */
public class FileUtils {
    public static final String POSTFIX = ".JPEG";
    public static final String APP_NAME = "ImageSelector";
    public static final String CAMERA_PATH = "/claimCamera/";
    public static final String CROP_PATH = "/claimPicture/Picture/";
//    public static final String CAMERA_PATH = "/" + APP_NAME + "/CameraImage/";
//    public static final String CROP_PATH = "/" + APP_NAME + "/CropImage/";

    public static File createCameraFile(Context context) {
        return createMediaFile(context,CAMERA_PATH);
    }
    public static File createCropFile(Context context) {
        return createMediaFile(context,CROP_PATH);
    }

    private static File createMediaFile(Context context,String parentPath){
       String externalStorage_camer = Environment.getExternalStorageDirectory().getAbsolutePath()+parentPath;
        File tempFileCamer = new File(externalStorage_camer);
        if(!tempFileCamer.exists()){
            tempFileCamer.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgName = File.separator + "IMG_" + timeStamp + ".jpg" ;
        File mediaFile = new File(tempFileCamer.getPath() + imgName);
//        cameraPath = mediaFile.getAbsolutePath();

//        String state = Environment.getExternalStorageState();
//        File rootDir = state.equals(Environment.MEDIA_MOUNTED)?Environment.getExternalStorageDirectory():context.getCacheDir();
//        File folderDir = new File(rootDir.getAbsolutePath() + parentPath);
//        if (!folderDir.exists() && folderDir.mkdirs()){
//        }
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
//        String fileName = APP_NAME + "_" + timeStamp + "";
//        File tmpFile = new File(folderDir, fileName + POSTFIX);


        return mediaFile;
    }
}
