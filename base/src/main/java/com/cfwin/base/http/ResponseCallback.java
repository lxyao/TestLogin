package com.cfwin.base.http;

import android.content.ComponentCallbacks;
import android.text.TextUtils;

import com.cfwin.base.BuildConfig;
import com.cfwin.base.utils.LogUtil;
import com.cfwin.base.utils.PrintWriteUtils;
import com.google.gson.JsonSyntaxException;

import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.conn.HttpHostConnectException;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

/**
 * 该抽象类，作为http回调，用于统一处理数据
 * @param <T>
 * @param <O>
 */
public abstract class ResponseCallback<T extends ComponentCallbacks, O extends ResponseInfo> extends AjaxCallBack<String> {

    private WeakReference<T> mContext;
    public ResponseCallback(T context){
        mContext = new WeakReference<>(context);
    }

    /**
     * @return 获取当前上线文，解决内存泄漏
     */
    public T getContext(){
        return mContext != null ? mContext.get() : null;
    }

    /**
     * @return 是否显示服务器端返回的错误信息 默认显示
     */
    public boolean isToast(){
        return true;
    }

    /**
     * @return 是否获取源串 默认false
     */
    protected boolean getSource(){
        return false;
    }

    @Override
    public void onFailure(Throwable t, int errorNo, String strMsg) {
        super.onFailure(t, errorNo, strMsg);
        String str = strMsg;
        T tmp = getContext();
        if(t instanceof HttpHostConnectException){
            str = "连接服务器异常，";
        }
        try{
//            if(tmp instanceof BaseActivity){
//                BaseActivity base = (BaseActivity) tmp;
//                base.endLoadingView();
//                if(base.isConnected(base)){
//                    if(strMsg.contains("refused") || strMsg.contains("timed out"))
//                        str = "服务器无响应";
//                }else{
//                    str = str.concat("建议请先检查本地网络");
//                }
//                base.toastMsg(str);
                PrintWriteUtils.writeErrorLog("服务器连接出错 :"+strMsg);
//            }
        }catch (Exception e){
            e.printStackTrace();
            PrintWriteUtils.writeErrorLog("异常 :"+strMsg);
        }
        onError(t, null);
    }

    @Override
    public void onSuccess(String s) {
        super.onSuccess(s);
        T tmp = getContext();
        try{
            //fix me :fragment dont deal with
//            if(tmp instanceof BaseActivity){
//                BaseActivity base = (BaseActivity) tmp;
//                base.endLoadingView();
//                if(!TextUtils.isEmpty(s)){
//                    O t = new Gson().fromJson(s, parseType());
//                    if(getSource() || BuildConfig.DEBUG)t.setSource(s);
//                    //服务端返回错误信息
//                    if(t.getResult() == 0 && isToast()){
//                        base.toastMsg(t.getMessage());
//                    }
//                    onResult(tmp, t);
//                }else{
//                    resultOther("");
//                }
//            }else {
                resultOther(s);
//            }
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            onError(e, null);
            if(!BuildConfig.DEBUG)PrintWriteUtils.writeErrorLog("gson解析语法异常 :"+e.getLocalizedMessage());
        }catch (Exception e){
            e.printStackTrace();
            onError(e, null);
            if(!BuildConfig.DEBUG)PrintWriteUtils.writeErrorLog("异常 :"+e.getLocalizedMessage());
        }
    }

    protected void onError(Throwable e, String msg){
        msg = (e == null ? "" : "Throwable ="+e.getLocalizedMessage()+"\r\n")+"  msg ="+msg;
        LogUtil.e(this.getClass().getSimpleName(), msg, true);
    }

    protected void resultOther(String msg){}

    /**
     * 解析对象泛型
     * @return
     */
    protected abstract Type parseType();

    /**
     * 解析结果对象
     * @param context 当前上下文（可能是Activity|Fragment），解决内存泄漏
     * @param result 结果
     */
    protected abstract void onResult(T context, O result);
}
