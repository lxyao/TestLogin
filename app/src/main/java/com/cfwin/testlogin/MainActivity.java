package com.cfwin.testlogin;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cfwin.base.activity.BaseActivityApi23;
import com.cfwin.base.http.ResponseCallback;
import com.cfwin.base.http.ResponseInfo;
import com.cfwin.base.utils.HttpUtil;
import com.cfwin.base.utils.LogUtil;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.lang.reflect.Type;

import static com.cfwin.base.utils.PrintWriteUtils.LOGPATH;

public class MainActivity extends BaseActivityApi23 implements View.OnClickListener {

    private TextView mTVLoginInfo;
    private TextView mTVLoginStateInfo;
    private final int MAX_COUNT = 5;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTVLoginInfo = findViewById(R.id.loginInfo);
        mTVLoginStateInfo = findViewById(R.id.loginStateInfo);
        mTVLoginStateInfo.setText(getString(R.string.login_state, ""));
        isGrant(201, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    public void permissionGrant(int requestCode, String[] permission) {
        super.permissionGrant(requestCode, permission);
        File file = new File(getExternalCacheDir().getParentFile().getAbsolutePath()+"/logs");
        if(!file.exists())file.mkdirs();
        LOGPATH = file.getAbsolutePath()+"/";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getUrl:
                //接口访问
                getUrl();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 201){
            String result = "";
            if(data != null) result = data.getStringExtra("result");
            LogUtil.e("MainActivity", "调用返回信息="+result);
            count = 0;
            getStateInfo();
        }
    }

    private void getUrl(){
        HttpUtil.getInstance().request("http://40.73.96.49:8084/api/Verify/GetData?", new AjaxParams(), new ResponseCallback<MainActivity, ResponseInfo>(this) {
            @Override
            protected Type parseType() {
                return null;
            }

            @Override
            protected void onError(Throwable e, String msg) {
                super.onError(e, msg);
                LogUtil.e("MainActivity", "信息  = "+e.getLocalizedMessage()+"  str="+msg);
            }

            @Override
            protected void resultOther(String msg) {
                super.resultOther(msg);
                LogUtil.e("MainActivity", "信息  = "+msg);
                MainActivity tmp = getContext();
                if(tmp != null && !TextUtils.isEmpty(msg)){
                    tmp.mTVLoginInfo.setText(msg);
                    tmp.startActivityForResult(new Intent("com.cfwin.cfwinblockchain.ACTION_SCAN_LOGIN")
                            .putExtra("loginInfo", msg), 201);
                }
            }

            @Override
            protected void onResult(MainActivity context, ResponseInfo result) {}
        });
    }

    private void getStateInfo(){
        String vcode = Uri.parse(mTVLoginInfo.getText().toString()).getQueryParameter("v");
        if(TextUtils.isEmpty(vcode)){
            Toast.makeText(this, "必要参数为空 vcode", Toast.LENGTH_LONG).show();
            return;
        }
        vcode = "http://40.73.96.49:8084/api/Verify/GetStatus?vcode="+vcode;
        HttpUtil.getInstance().request(vcode, new AjaxParams(), new ResponseCallback<MainActivity, ResponseInfo>(this) {
            @Override
            protected Type parseType() {
                return null;
            }

            @Override
            protected void resultOther(String msg) {
                super.resultOther(msg);
                LogUtil.e("MainActivity", "信息  = "+msg);
                MainActivity tmp = getContext();
                if(tmp != null){
                    if(!TextUtils.isEmpty(msg) && msg.toLowerCase().equals("true")){
                        tmp.mTVLoginStateInfo.setText(getString(R.string.login_state, msg));
                    }else{
                        if(tmp.count >= tmp.MAX_COUNT){
                            tmp.mTVLoginStateInfo.setText(getString(R.string.login_state, msg));
                            return;
                        }
                        tmp.count++;
                        tmp.getStateInfo();
                    }
                }
            }

            @Override
            protected void onResult(MainActivity context, ResponseInfo result) {}
        });
    }
}
