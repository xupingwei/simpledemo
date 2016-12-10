package com.cqcye.simpledemo.net;

import android.content.Context;

import com.cqcye.simpledemo.listener.HttpDoneListener;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;

import org.apache.http.Header;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by xupingwei on 2016/12/10.
 * 网络请求工具类
 */

public class HttpClientUtils {

    public static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com/facts.json";
    private static Context mContext;
    private static AsyncHttpClient httpClient = new AsyncHttpClient();
    private static final String FAILED = "啊哦，网络好像不给力\n\t请稍后再试~";
    private static final String NETWORK_FAILED = "啊哦，网络好像不给力\n\t请稍后再试~";
    private static final String DATA_FAILED = "啊哦，数据获取异常啦";

    static {
        httpClient.setTimeout(10 * 1000);
        httpClient.setConnectTimeout(15 * 1000);
    }

    /**
     * 取消所有请求
     *
     * @param mayInterruptIfRunning
     */
    public static void cancelAllRequests(boolean mayInterruptIfRunning) {
        httpClient.cancelAllRequests(mayInterruptIfRunning);
    }

    /**
     * @param _context
     * @param _subUrl  url地址
     * @param obj      传参对象
     * @param _action  本次操作名称
     * @param listener 回调函数
     */
    public static void httpGet(Context _context, String _subUrl, Object obj, final Class<?> cls,
                               final String _action, final HttpDoneListener listener) {
        mContext = _context;
        if (!NetworkHelper.isNetworkConnected(mContext)) {
            listener.requestFailed(-1, NETWORK_FAILED, _action);
            return;
        }
        final String requestUrl = BASE_URL + _subUrl;
        RequestParams params = getParams(obj);
        httpClient.get(_context, requestUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.requestFailed(-1, _action + "：" + throwable.getMessage(), _action);
                Logger.e(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Logger.e(_action + "返回字符串为:" + responseString);
                Logger.json(responseString);
                GsonBuilder gson = new GsonBuilder().serializeNulls();
                Object result = gson.create().fromJson(responseString, cls);
                listener.requestSuccess(result, _action);
            }

        });
    }


    /**
     * 生成请求的参数
     *
     * @param _obj
     * @return
     */
    private static RequestParams getParams(Object _obj) {
        RequestParams params = new RequestParams();
        Field[] fields = _obj.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                String name = f.getName();
                StringBuilder sb = new StringBuilder();

                Class fieldType = f.getType();
                String simpleName = fieldType.getSimpleName();
                if (simpleName.equalsIgnoreCase("boolean")) {
                    sb.append("is");
                    sb.append(name.substring(0, 1).toUpperCase());
                    sb.append(name.substring(1));
                } else {
                    sb.append("get");
                    sb.append(name.substring(0, 1).toUpperCase());
                    sb.append(name.substring(1));
                }

                Method m = _obj.getClass().getMethod(sb.toString());
//                LoggerUtils.e("变量名为:" + name + "类型为：" + fieldType.getSimpleName());
                if (simpleName.equalsIgnoreCase("byte[]")) {
                    byte[] byteValue = (byte[]) m.invoke(_obj);
                    params.put(name, byteValue);
                } else if (simpleName.equalsIgnoreCase("String")) {
                    String value = (String) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("int")) {
                    int value = (int) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("double")) {
                    double value = (double) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("boolean")) {
                    boolean value = (boolean) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("long")) {
                    long value = (long) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("short")) {
                    short value = (short) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("float")) {
                    float value = (float) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("char")) {
                    char value = (char) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName.equalsIgnoreCase("List")) {
                    List value = (List) m.invoke(_obj);
                    params.put(name, value);
                } else if (simpleName instanceof Object) {
                    String value = (String) m.invoke(_obj);
                    params.put(name, value);
                } else {
                    Logger.e("未处理的类型：" + simpleName);
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return params;
    }
}