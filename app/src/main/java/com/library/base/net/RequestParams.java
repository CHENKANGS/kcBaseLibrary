package com.library.base.net;


import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by fullteem on 2016/4/26.
 */
public abstract class RequestParams {
    private LinkedHashMap<String, String> mParams;
    private LinkedHashMap<String, File> mFileParams;

    public RequestParams() {
        mParams = new LinkedHashMap<String, String>();
        mFileParams = new LinkedHashMap<String, File>();
    }

    public RequestParams( String key, Object value) {
        this();
        add(key, value);
    }

    public RequestParams add( String key, Object value) {
        if(key == null) return this;
        if(value == null) mParams.put(key, null);
        else if(value instanceof File ) mFileParams.put(key, (File) value);
        else mParams.put(key, value.toString());
        return this;
    }

    public LinkedHashMap<String, String> get() {
        return mParams;
    }
    public LinkedHashMap<String, File> files() {
        return mFileParams;
    }

    public abstract void put( String key, String value);

    public abstract void put( String key, int value);

    public abstract void put( String key, long value);

}
