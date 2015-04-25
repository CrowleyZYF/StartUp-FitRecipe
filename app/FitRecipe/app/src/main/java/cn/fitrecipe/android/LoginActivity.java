package cn.fitrecipe.android;

import static cn.smssdk.framework.utils.R.getStringRes;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;
import java.util.Set;


public class LoginActivity extends Activity implements View.OnClickListener {

    UMSocialService mController;
    private LinearLayout sina_login;
    private LinearLayout wechat_login;
    private LinearLayout qq_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        initViews();
        initData();
        initEvents();
    }

    private void initEvents() {
        sina_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
    }

    private void initData() {
        //sina
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //QQ
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, "1104537242","tDAlabTzuOR4Fvy3");
        qqSsoHandler.addToSocialSDK();

        //wechat
        //UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,appId,appSecret);
        //wxHandler.addToSocialSDK();
    }

    private void initViews() {
        sina_login = (LinearLayout) findViewById(R.id.sina_login);
        wechat_login = (LinearLayout) findViewById(R.id.wechat_login);
        qq_login = (LinearLayout) findViewById(R.id.qq_login);
    }

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(com.umeng.socialize.bean.SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                String uid = bundle.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(platform);
                } else {
                    Toast.makeText(LoginActivity.this, "授权失败...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }

    /**
     * 获取授权平台的用户信息</br>
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform, new SocializeListeners.UMDataListener() {


            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int i, Map<String, Object> info) {
                if ( info != null ) {
                    Toast.makeText(LoginActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Nothing", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sina_login:
                login(SHARE_MEDIA.SINA);
                break;
            case R.id.wechat_login:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qq_login:
                login_qq();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void login_qq(){
        mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                Toast.makeText(LoginActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if(status == 200 && info != null){
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for(String key : keys){
                                sb.append(key+"="+info.get(key).toString()+"\r\n");
                            }
                            Log.d("TestData", sb.toString());
                            Toast.makeText(LoginActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("TestData","发生错误："+status);
                        }
                    }
                });
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                Toast.makeText(LoginActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
