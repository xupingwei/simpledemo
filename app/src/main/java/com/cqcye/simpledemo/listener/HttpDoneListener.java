package com.cqcye.simpledemo.listener;

/**
 * Created by xupingwei on 2015/8/20.
 * 网络请求回调接口
 */
public interface HttpDoneListener {
    void requestSuccess(Object obj, String action);

    void requestFailed(int failedCode, String failedMessage, String action);
}
