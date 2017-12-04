package com.yongchun.library.utils;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.yongchun.library.model.LocalMedia;
import com.yongchun.library.model.LocalMediaFolder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dee on 15/11/19.
 */
public class LocalMediaLoader {
    // load type
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID};

    private final static String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION};

    private int type = TYPE_IMAGE;
    private FragmentActivity activity;

    private Handler handler=null;

    public LocalMediaLoader(FragmentActivity activity, int type) {
        this.activity = activity;
        this.type = type;
        //创建属于主线程的handler
        handler=new Handler();
    }

    HashSet<String> mDirPaths = new HashSet<String>();

    public void loadAllImage(final LocalMediaLoadListener imageLoadListener) {
        activity.getSupportLoaderManager().initLoader(type, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = null;
                if (id == TYPE_IMAGE) {
                    //只查询jpeg和png的图片
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                            new String[]{"image/jpeg", "image/png"}, MediaStore.MediaColumns.DATE_ADDED + " DESC");
//                    cursorLoader = new CursorLoader(
//                            activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            IMAGE_PROJECTION, MediaStore.Images.Media.MIME_TYPE + "=? or "
//                            + MediaStore.Images.Media.MIME_TYPE + "=?",
//                            new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
                } else if (id == TYPE_VIDEO) {
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[2] + " DESC");
                }
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                final ArrayList<LocalMediaFolder> imageFolders = new ArrayList<LocalMediaFolder>();
                LocalMediaFolder allImageFolder = new LocalMediaFolder();
                List<LocalMedia> allImages = new ArrayList<LocalMedia>();

//                if (data.moveToFirst()) {
                while (data != null && data.moveToNext()) {

                        // 获取图片的路径
                        String path = data.getString(data
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        File file = new File(path);
                        if (!file.exists())
                            continue;
                        // 获取该图片的目录路径名
                        File parentFile = file.getParentFile();
                        if (parentFile == null || !parentFile.exists())
                            continue;

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path, options);
//        BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
                    int imageHeight = options.outHeight;
                    int imageWidth = options.outWidth;
                    if (imageWidth > 200 && imageHeight>200){

                        String dirPath = parentFile.getAbsolutePath();
                        // 利用一个HashSet防止多次扫描同一个文件夹
                        if (mDirPaths.contains(dirPath)) {
                            continue;
                        } else {
                            mDirPaths.add(dirPath);
                        }

                        if (parentFile.list() == null)
                            continue;
                        LocalMediaFolder localMediaFolder = getImageFolder(path, imageFolders);

                        File[] files = parentFile.listFiles(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                if (filename.endsWith(".jpg")
                                        || filename.endsWith(".png")
                                        || filename.endsWith(".jpeg"))
                                    return true;
                                return false;
                            }
                        });

                        ArrayList<LocalMedia> images = new ArrayList<>();
                        for (int i = 0; i < files.length; i++) {
                            File f = files[i];
                            LocalMedia localMedia = new LocalMedia(f.getAbsolutePath());
                            localMedia.setLastUpdateAt(f.lastModified());
                            allImages.add(localMedia);
                            images.add(localMedia);
                        }
                        Collections.sort(images, new FileComparator());//通过重写Comparator的实现类FileComparator来实现

                        if (images.size() > 0) {
                            localMediaFolder.setImages(images);
                            localMediaFolder.setImageNum(localMediaFolder.getImages().size());
                            imageFolders.add(localMediaFolder);
                        }
                    }

                    }
                Collections.sort(allImages, new FileComparator());//通过重写Comparator的实现类FileComparator来实现
                    allImageFolder.setImages(allImages);
                    allImageFolder.setImageNum(allImageFolder.getImages().size());
                    allImageFolder.setFirstImagePath(allImages.get(0).getPath());
                    allImageFolder.setName(activity.getString(com.yongchun.library.R.string.all_image));
                    imageFolders.add(allImageFolder);
                    sortFolder(imageFolders);
//                    imageLoadListener.loadComplete(imageFolders);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageLoadListener.loadComplete(imageFolders);
                    }
                });
//                }

                if (data != null) data.close();
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
            }
        });
    }


    //按照时间排序
    public class FileComparator implements Comparator<LocalMedia> {
        public int compare(LocalMedia file1, LocalMedia file2) {
            if(file1.getLastUpdateAt() > file2.getLastUpdateAt()) {
                return -1;
            }else if (file1.getLastUpdateAt()==file2.getLastUpdateAt()) {
                return 0;
            }else {
                return 1;
            }
        }
    }


    private void sortFolder(List<LocalMediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            @Override
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() == null || rhs.getImages() == null) {
                    return 0;
                }
                int lsize = lhs.getImageNum();
                int rsize = rhs.getImageNum();
                return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
            }
        });
    }

    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();

        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        return newFolder;
    }

    public interface LocalMediaLoadListener {
        void loadComplete(List<LocalMediaFolder> folders);
    }

}
