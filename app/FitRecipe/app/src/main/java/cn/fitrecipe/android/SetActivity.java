package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetActivity extends Activity implements View.OnClickListener {

    private RelativeLayout set_account_btn;
    private RelativeLayout set_meal_btn;
    private RelativeLayout set_alarm_btn;
    private RelativeLayout set_about_btn;
    private RelativeLayout set_share_btn;
    private RelativeLayout set_feedback_btn;
    private RelativeLayout set_checkupdate_btn;
    private RelativeLayout set_protocol_btn;

    private ImageView back_btn;
    private ImageView right_btn;

    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        //Sina
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        //QQ weibo
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        //QQ空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468", "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        //QQ好友
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468","c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
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

    private void initEvent() {
        back_btn.setOnClickListener(this);
        set_account_btn.setOnClickListener(this);
        set_meal_btn.setOnClickListener(this);
        set_alarm_btn.setOnClickListener(this);
        set_about_btn.setOnClickListener(this);
        set_share_btn.setOnClickListener(this);
        set_feedback_btn.setOnClickListener(this);
        set_checkupdate_btn.setOnClickListener(this);
        set_protocol_btn.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        right_btn.setVisibility(View.GONE);
        set_account_btn = (RelativeLayout) findViewById(R.id.set_account_btn);
        set_meal_btn = (RelativeLayout) findViewById(R.id.set_meal_btn);
        set_alarm_btn = (RelativeLayout) findViewById(R.id.set_alarm_btn);
        set_about_btn = (RelativeLayout) findViewById(R.id.set_about_btn);
        set_share_btn = (RelativeLayout) findViewById(R.id.set_share_btn);
        set_feedback_btn = (RelativeLayout) findViewById(R.id.set_feedback_btn);
        set_checkupdate_btn = (RelativeLayout) findViewById(R.id.set_checkupdate_btn);
        set_protocol_btn = (RelativeLayout) findViewById(R.id.set_protocol_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.set_account_btn:
                if(FrApplication.getInstance().isLogin()){
                    Intent set_account_btn = new Intent(this,SetAccountActivity.class);
                    startActivity(set_account_btn);
                }else{
                    Toast.makeText(this, "请先登录账号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.set_meal_btn:
                Intent set_meal_btn = new Intent(this,SetMealActivity.class);
                startActivity(set_meal_btn);
                break;
            case R.id.set_alarm_btn:
                Intent set_alarm_btn = new Intent(this,SetAlarmActivity.class);
                startActivity(set_alarm_btn);
                break;
            case R.id.set_about_btn:
                Intent set_about_btn = new Intent(this, SetAboutActivity.class);
                startActivity(set_about_btn);
                break;
            case R.id.set_share_btn:
                share();
                break;
            case R.id.set_feedback_btn:
                FeedbackAgent agent = new FeedbackAgent(this);
                agent.startFeedbackActivity();
                break;
            case R.id.set_checkupdate_btn:
                break;
            case R.id.set_protocol_btn:
                break;
        }
    }

    private void share() {
        // 设置分享内容
        mController.setShareContent("下载健食记，选择属于自己的健身饮食计划。讲究自己的每一餐，因为健身从不将就。http://fitrecipe.cn");
        //mController.
        mController.openShare(this, false);
    }
}
