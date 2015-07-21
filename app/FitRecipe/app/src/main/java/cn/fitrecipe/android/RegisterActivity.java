package cn.fitrecipe.android;

import static cn.smssdk.framework.utils.R.getStringRes;

import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Http.HttpUtils;
import cn.fitrecipe.android.function.Common;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity implements OnClickListener {

    private ImageView back_btn;
    private TextView login_btn;
    private LinearLayout login_linearlayout;
    private LinearLayout code_linearlayout;

    private EditText account;
    private EditText code;
    private EditText password;
    private Button register_btn;

    private JSONObject result = null;
    private Context nowContext;
    private String backActivity;
    private int countdown;
    private TextView get_enable;
    private TextView get_disable;
    private Runnable countdownRunnable;


    // 填写从短信SDK应用后台注册得到的APPKEY
	private static String APPKEY = "6ce1a80942cd";//463db7238681  27fe7909f8e8
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "c11bc27f315a877a649e980716145ca6";
	public String phString;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

        nowContext=this;

        initViews();
        initData();
        initEvents();
		
		//System.loadLibrary("OSbase");
		SMSSDK.initSDK(this,APPKEY,APPSECRET);
		EventHandler eh=new EventHandler(){

			@Override
			public void afterEvent(int event, int result, Object data) {
				
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
			
		};
		SMSSDK.registerEventHandler(eh);
	}

    private void initEvents() {
        back_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        login_linearlayout.setOnClickListener(this);
        code_linearlayout.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    private void initData() {
        countdown=60;
        //backActivity
        Intent intent =getIntent();
        backActivity = intent.getStringExtra("back");
    }

    private void initViews() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        login_btn = (TextView) findViewById(R.id.login_btn);
        login_linearlayout = (LinearLayout) findViewById(R.id.login_linearlayout);
        code_linearlayout = (LinearLayout) findViewById(R.id.get_code);

        account = (EditText) findViewById(R.id.account);
        code = (EditText) findViewById(R.id.code);
        password = (EditText) findViewById(R.id.password);
        register_btn = (Button) findViewById(R.id.register_btn);

        get_enable = (TextView) findViewById(R.id.get_enable);
        get_disable = (TextView) findViewById(R.id.get_disable);
    }

    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.back_btn:
            if(backActivity.equals("landingPage")){
                startActivity(new Intent(this, LandingPageActivity.class));
                finish();
            }else{
                finish();
            }
            break;
        case R.id.login_btn:
            startActivity(new Intent(this, LoginActivity.class));
            break;
        case R.id.login_linearlayout:
            startActivity(new Intent(this, LoginActivity.class));
            break;
		case R.id.get_code://获取验证码
            if(get_enable.getVisibility()!=View.GONE){
                if(!TextUtils.isEmpty(account.getText().toString())){
                    SMSSDK.getVerificationCode("86",account.getText().toString());
                    phString=account.getText().toString();
                    get_enable.setVisibility(View.GONE);
                    get_disable.setVisibility(View.VISIBLE);
                    countdownRunnable=new ThreadShow();
                    new Thread(countdownRunnable).start();
                }else {
                    Common.errorDialog(this, "注册失败", "电话不能为空").show();
                }
            }
			break;
		case R.id.register_btn://校验验证码
            if(!Common.isOpenNetwork(this)){
                Common.errorDialog(this, "注册失败", "未连接网络").show();
            }else if(account.getText().toString().equals("")){
                Common.errorDialog(this, "注册失败", "手机号不得为空").show();
            }else if(password.getText().toString().equals("")){
                Common.errorDialog(this, "注册失败", "密码不得为空").show();
            }else if(code.getText().toString().equals("")){
                Common.errorDialog(this, "注册失败", "验证码不能为空").show();
            }else{
                SMSSDK.submitVerificationCode("86", phString, code.getText().toString());
            }
			break;
		/*case R.id.button3://国家列表
			SMSSDK.getSupportedCountries();
			//SMSSDK.getCountry(arg0);
			SMSSDK.getGroupedCountryList();
			//SMSSDK.getCountryByMCC(arg0);
			break;*/
		default:
			break;
		}
	}

	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
					//Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    doRegister(account.getText().toString(),password.getText().toString());
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
					Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(RegisterActivity.this, "smssdk_network_error");
				//Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                Common.errorDialog(nowContext, "注册失败", "验证码错误").show();
				if (resId > 0) {
					Toast.makeText(RegisterActivity.this, resId, Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

    private void doRegister(String accountString, String passwordString) {
        if(!Common.isOpenNetwork(this)){
            Common.errorDialog(this, "注册失败", "未连接网络").show();
        }else if(accountString.equals("")){
            Common.errorDialog(this, "注册失败", "手机号不得为空").show();
        }else if(passwordString.equals("")){
            Common.errorDialog(this, "注册失败", "密码不得为空").show();
        }else {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("account", accountString);
            params.put("password", passwordString);
            // TODO @WangKun
            // 注册，注册成功后调用registerSuccess，账号已存在调用accountError
        }
    }


    private class ThreadShow implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (get_enable.getVisibility()==View.GONE) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    countdownHandler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler countdownHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                if (countdown>0){
                    countdown--;
                    get_disable.setText("("+countdown+")重新获取");
                    if(countdown==0){
                        get_enable.setVisibility(View.VISIBLE);
                        get_disable.setVisibility(View.GONE);
                        countdown=61;
                    }
                }
            }
        }
    };

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

    private void registerSuccess(String platform, String account, String username){
        SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogined", true);
        editor.putString("platform", platform);
        editor.putString("account", account);
        editor.putString("username", username);
        editor.commit();
        Toast.makeText(getApplicationContext(), "欢迎："+ username, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(nowContext, MainActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    private void accountError(){
        Common.errorDialog(nowContext, "注册失败", "账户已存在").show();
    }
}
