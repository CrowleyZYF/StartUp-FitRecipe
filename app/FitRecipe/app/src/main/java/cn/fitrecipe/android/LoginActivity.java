package cn.fitrecipe.android;

import static cn.smssdk.framework.utils.R.getStringRes;

import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Http.HttpUtils;
import cn.fitrecipe.android.function.Common;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class LoginActivity extends Activity implements View.OnClickListener {

    UMSocialService mController;
    private LinearLayout sina_login;
    private LinearLayout wechat_login;
    private LinearLayout qq_login;

    private ImageView back_btn;
    private TextView register_btn;
    private LinearLayout register_linearlayout;

    private EditText account;
    private EditText password;
    private Button login_btn;
    private LinearLayout forget_password;

    private JSONObject result = null;
    private Context nowContext;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                try {
                    switch (Integer.parseInt((String) result.get("result"))) {
                        case HttpUrl.LOGIN_SUCCESS:{
                            SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isLogined", true);
                            editor.putString("account", (String) result.get("account"));
                            editor.putString("username", (String) result.get("username"));
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "欢迎："+ ((String) result.get("username")), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(nowContext, MainActivity.class));
                            LoginActivity.this.finish();
                            break;
                        }
                        case HttpUrl.NOT_EXIST:{
                            Common.errorDialog(nowContext, "登陆失败", "账户不存在").show();
                            break;
                        }
                        case HttpUrl.PASS_ERROR:{
                            Common.errorDialog(nowContext, "登陆失败", "密码错误").show();
                            break;
                        }
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        nowContext=this;
        
        initViews();
        initData();
        initEvents();
    }

    private void initEvents() {
        sina_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);

        back_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        register_linearlayout.setOnClickListener(this);

        login_btn.setOnClickListener(this);
        forget_password.setOnClickListener(this);
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

        back_btn = (ImageView) findViewById(R.id.back_btn);
        register_btn = (TextView) findViewById(R.id.register_btn);
        register_linearlayout = (LinearLayout) findViewById(R.id.register_linearlayout);

        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);
        forget_password = (LinearLayout) findViewById(R.id.forget_password);
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
            case R.id.back_btn:
                finish();
                break;
            case R.id.register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.register_linearlayout:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                String accountString=account.getText().toString();
                String passwordString=password.getText().toString();
                doLogin(accountString, passwordString);
                break;
            default:
                break;
        }
    }

    private void doLogin(String accountString, String passwordString) {
        if(!Common.isOpenNetwork(this)){
            Common.errorDialog(this, "登陆失败", "未连接网络").show();
        }else if(accountString.equals("")){
            Common.errorDialog(this, "登陆失败", "手机号不得为空").show();
        }else if(passwordString.equals("")){
            Common.errorDialog(this, "登陆失败", "密码不得为空").show();
        }else {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("account", accountString);
            params.put("password", passwordString);
            new Thread(){
                public void run(){
                    String teString = HttpUtils.submitPostData(HttpUrl.LOGIN_URL, params, "utf-8");
                    //test
                    if(teString.equals("")){
                        teString = HttpUrl.LOGIN_URL_JSON;
                    }
                    try {
                        result=new JSONObject(teString);
                        handler.sendEmptyMessage(0x123);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
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
