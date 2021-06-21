package com.example.myapplication.network.base;
import android.util.Log;

/**
 * 网络请求返回码统一监听类
 * Create by xuqunxing on  2019/4/2
 */
public class NetConnectionIntercepter extends ServerResultInterceptor {
    @Override
    public void onIntercept(final String message, int code) {
        Log.e("======", "======NetIntercepter-code:" + code);
        if (code == 10013) {

        } else if (code == 10033) {

        } else if (code == 10032) {

        }
    }
}
