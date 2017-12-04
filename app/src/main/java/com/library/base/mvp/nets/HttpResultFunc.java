package com.library.base.mvp.nets;



import com.library.base.entity.basemodel.ResponseModel;
import com.library.base.global.GlobalConstant;
import com.library.base.mvp.JesException;

import rx.functions.Func1;

/**
 * Created by Allen on 2017/9/19.
 */

public class HttpResultFunc<T> implements Func1<ResponseModel<T>,T> {
    @Override
    public T call(ResponseModel<T> jesResponse) {
        if (!jesResponse.getRet() .equals(GlobalConstant.REQUEST_SUCCESS) ) {
            throw new JesException(jesResponse.getMsg(),Integer.parseInt(jesResponse.getRet()));
        }
        return jesResponse.getResult();
    }
}
