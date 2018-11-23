package com.cfwin.base.utils;

import android.content.ComponentCallbacks;
import android.text.TextUtils;

import com.cfwin.base.BuildConfig;
import com.cfwin.base.http.ResponseCallback;
import com.cfwin.base.http.ResponseInfo;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

/**
 * http请求工具类
 * created by Yao on 2018-06-13
 */
public class HttpUtil {

    private FinalHttp mFinalHttp;
    private static String baseUrl;
    private HttpUtil(){
        mFinalHttp = new FinalHttp();
        mFinalHttp.configTimeout(30000);
        LogUtil.d(this.getClass().getSimpleName(), "httpUtil init");
    }

    private static HttpUtil util;
    public static HttpUtil getInstance(){
        synchronized (HttpUtil.class){
            if(util == null)util = new HttpUtil();
        }
        return util;
    }

    /**
     * 提供请求对象，方便添加其他参数
     * @return
     */
    public FinalHttp getFinalHttp() {
        return mFinalHttp;
    }

    /**
     * 设置http请求的基本路径
     * @param baseUrl
     * @return
     */
    public HttpUtil setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 异步post请求
     * @param url 接口路径
     * @param params 访问参数
     * @param callback 回调
     * @param <T>
     * @param <O>
     */
    public <T extends ComponentCallbacks, O extends ResponseInfo> void request(String url, AjaxParams params, ResponseCallback<T, O> callback){
        if(!TextUtils.isEmpty(url)){
            String lower = url.toLowerCase();
            if(lower.startsWith("http:") || lower.startsWith("https")){ }
            else{
                if(TextUtils.isEmpty(baseUrl)){
                    if(!BuildConfig.DEBUG)PrintWriteUtils.writeErrorLog("httputil baseurl is null");
                    else LogUtil.e(this.getClass().getSimpleName(), "baseUrl dont null");
                }
                url = baseUrl+url;
            }
        }else{
            if(!TextUtils.isEmpty(baseUrl)){
                //基础地址为空
                url = baseUrl;
            }
        }
        if(!url.toLowerCase().startsWith("http://")
                && !url.toLowerCase().startsWith("https://")){
            //默认http
            url = "http://"+url;
        }
        LogUtil.e(this.getClass().getSimpleName(), "request baseUrl="+url);
        if(url.contains("?")){
            mFinalHttp.get(url, params, callback);
        }else
            mFinalHttp.post(url, params, callback);
    }
}
