package com.library.base.entity.basemodel;


import com.library.base.global.GlobalConstant;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class ResponseModel<T> {

    public String ret;
    public String msg;
    public T result;


    public boolean isSuccess() {
        return ret.equals( GlobalConstant.REQUEST_SUCCESS);
    }
//    public String resultCode;
//    public String resultMessage;
//    public T resultData;
//
//
//    public boolean isSuccess() {
//        return resultCode.equals(GlobalConstant.REQUEST_SUCCESS);
//    }


    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public ResponseModel setResult(T result) {
        this.result = result;
        return this;
    }
}
