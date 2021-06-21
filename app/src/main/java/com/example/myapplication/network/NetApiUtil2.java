package com.example.myapplication.network;

import android.content.Context;
import android.text.TextUtils;

import com.example.myapplication.bean.postMessageBean;
import com.example.myapplication.network.base.HttpCommon;
import com.example.myapplication.network.base.HttpRequestParam;
import com.example.myapplication.network.base.NetConnectionIntercepter;
import com.example.myapplication.network.base.ServerResult;
import com.example.myapplication.network.base.ServerResultHeader;
import com.example.myapplication.util.Md5Util;
import com.example.myapplication.util.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

//网络数据请求工具类
public class NetApiUtil2 {

    //--------------------------------GET请求 方法部分↓-----------------------------------------------------------

    //采购入库列表数量
//    public static final ServerResult<ResutlDataIntBean> getInventoryCount() {
//        HashMap<String, String> paramsMap = new HashMap<>();
//        HttpRequestParam.addCommmonGetRequestValue(Global.getApplicationContext(), paramsMap);
//        String url = ApiUrlManager.API_INVENT_LIST_COUNT;
//        HttpCommon httpCommon = new HttpCommon(getRequestUrl(url), new NetConnectionIntercepter());
//        ServerResultHeader csResult = httpCommon.getResponseAsCsResultGet(paramsMap, null);
//        ServerResult<ResutlDataIntBean> resTagList = new ServerResult<>();
//        if (csResult != null) {
//            String responseStr = csResult.getResponseJson();
//            resTagList.setCsResult(csResult);
//            if (!TextUtils.isEmpty(responseStr)) {
//                try {
//                    ResutlDataIntBean searchHotKeyBean = new Gson().fromJson(responseStr, ResutlDataIntBean.class);
//                    resTagList.setResultBean(searchHotKeyBean);
//                    resTagList.itemList.add(searchHotKeyBean);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return resTagList;
//    }


    // TODO:  xuqunxing post
    //--------------------------------post请求 方法部分↓-----------------------------------------------------------

    //待自提订单 - - 待自提订单
    public static final ServerResult<postMessageBean> PostWaitOrder(Context context, String shortMsg, String msgSender, double orderScore) {


        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceKey", ApiUrlManager.DEVICE_KEY + "");
        paramMap.put("shortMsg", shortMsg + "");
        paramMap.put("msgSend", msgSender + "");
        paramMap.put("orderScore", orderScore + "");
        String paramsStr = StringUtils.ascriAsc(paramMap);
        String sign = Md5Util.MD5Encode(paramsStr);


        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("deviceKey", ApiUrlManager.DEVICE_KEY + "");
        linkedHashMap.put("shortMsg", shortMsg + "");
        linkedHashMap.put("msgSend", msgSender + "");
        linkedHashMap.put("orderScore", orderScore + "");
        linkedHashMap.put("sign", sign + "");
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);

        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonPostRequestValue(context, paramsMap);
        HttpCommon httpCommon = new HttpCommon(getRequestUrl(ApiUrlManager.post_message), new NetConnectionIntercepter());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPostBody(paramsMap, httpStr);
        ServerResult<postMessageBean> resTagList = new ServerResult<>();
        if (csResult != null) {
            String responseStr = csResult.getResponseJson();
            resTagList.setCsResult(csResult);
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    postMessageBean orderStateBean = new Gson().fromJson(responseStr, postMessageBean.class);
                    resTagList.itemList.add(orderStateBean);
                    resTagList.setResultBean(orderStateBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }


    private static String getRequestUrl(String url) {
        if (url.indexOf("?") > -1) {
            url = url + "&time=" + System.currentTimeMillis();
        } else {
            url = url + "?time=" + System.currentTimeMillis();
        }
        return ApiUrlManager.BaseUrl_Test + url;
    }

    private static String getRequestUrlNoToken(String url) {////welcome页面不传token
        url = url + "?time=" + System.currentTimeMillis();
        return ApiUrlManager.post_message + url;
    }

}
