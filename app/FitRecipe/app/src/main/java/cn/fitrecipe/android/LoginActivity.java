package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.function.Common;


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
    private String backActivity;

    private void loginSuccess(JSONObject data) throws JSONException {
        System.out.println(data);
        Author author = new Gson().fromJson(data.toString(), Author.class);
        author.setIsLogin(true);
        FrApplication.getInstance().setAuthor(author);
        Toast.makeText(getApplicationContext(), "欢迎："+ author.getNick_name(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(nowContext, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void accountError(){
        Common.errorDialog(nowContext, "登陆失败", "账户不存在").show();
    }

    private void passError(){
        Common.errorDialog(nowContext, "登陆失败", "密码错误").show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nowContext=this;
        
        initViews();
        initData();
        initEvents();
    }

    //初始化事件
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

    //初始化数据
    private void initData() {
        //backActivity
        Intent intent =getIntent();
        backActivity = intent.getStringExtra("back");

        //sina
        mController = UMServiceFactory.getUMSocialService("com.umeng.activity_login");
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //QQ
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, "1104537242","tDAlabTzuOR4Fvy3");
        qqSsoHandler.addToSocialSDK();

        //wechat
        //UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,appId,appSecret);
        //wxHandler.addToSocialSDK();
    }

    //初始化试图
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

    //正常登陆
    private void doLogin(String accountString, String passwordString) {
        if(!Common.isOpenNetwork(this)){
            Common.errorDialog(this, "登陆失败", "未连接网络").show();
        }else if(accountString.equals("")){
            Common.errorDialog(this, "登陆失败", "手机号不得为空").show();
        }else if(passwordString.equals("")){
            Common.errorDialog(this, "登陆失败", "密码不得为空").show();
        }else {
            Toast.makeText(LoginActivity.this, "phone:" + accountString + " password:" + passwordString, Toast.LENGTH_SHORT).show();
            // TODO @WangKun
            // 正常登陆，登陆成功后调用loginSuccess，账号不存在调用accountError，密码错误调用passError
            JSONObject params = new JSONObject();
            try {
                params.put("phone", accountString);
                params.put("password", passwordString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PostRequest request = new PostRequest(FrServerConfig.getLoginUrl(), null, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if(res.has("data")) {
                        try {
                            JSONObject data = res.getJSONObject("data");
                            loginSuccess(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(volleyError != null && volleyError.networkResponse != null) {
                        int statusCode = volleyError.networkResponse.statusCode;
                        if(statusCode == 401) {
                            passError();
                        }
                        if(statusCode == 402) {
                            accountError();
                        }
                    }
                }
            });
            FrRequest.getInstance().request(request);
        }
    }

    //第三方登录
    private void doOtherLogin(final String userId, final String userName, final String platform) {
        Toast.makeText(LoginActivity.this, "ID:" + userId + " Name:" + userName + " Source:" + platform, Toast.LENGTH_SHORT).show();
        // TODO @WangKun
        // 第三方登陆，登陆成功后调用loginSuccess，账号不存在调用accountError，密码错误调用passError
        JSONObject params = new JSONObject();
        try {
            params.put("external_id", userId);
            params.put("nick_name", userName);
            params.put("external_source", platform);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostRequest request = new PostRequest(FrServerConfig.getThirtyPartyLoginUrl(), null, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        loginSuccess(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        FrRequest.getInstance().request(request);
    }

    //第三方登录，qq
    public void login_qq(){
        mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                //Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(final Bundle bundle, SHARE_MEDIA share_media) {
                Toast.makeText(LoginActivity.this, "授权完成，正在跳转...", Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if(status == 200 && info != null){
                            String userId = bundle.getString("openid");
                            String platform = "qq";
                            String username = (String) info.get("screen_name");
                            doOtherLogin(userId,username,platform);
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

    //第三方登录，微信或者新浪
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
            public void onError(SocializeException e, SHARE_MEDIA share_media) {}

            @Override
            public void onCancel(SHARE_MEDIA share_media) {}
        });
    }

    //获取第三方登录的用户信息，微信或者新浪
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onComplete(int i, Map<String, Object> info) {
                Toast.makeText(LoginActivity.this, "授权完成，正在跳转...", Toast.LENGTH_SHORT).show();
                if ( info != null ) {
                    String userId = Long.toString((Long) info.get("uid"));
                    String platform = "sina";
                    String username = (String) info.get("screen_name");
                    doOtherLogin(userId,username,platform);
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
                if(backActivity != null && backActivity.equals("landingPage")){
                    startActivity(new Intent(this, LandingPageActivity.class));
                    finish();
                }else{
                    finish();
                }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(backActivity.equals("landingPage")){
                startActivity(new Intent(this, LandingPageActivity.class));
                finish();
            }else{
                finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
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
}
