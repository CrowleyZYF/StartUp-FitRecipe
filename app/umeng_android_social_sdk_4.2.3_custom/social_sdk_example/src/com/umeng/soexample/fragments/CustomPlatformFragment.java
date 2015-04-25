package com.umeng.soexample.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.scrshot.UMScrShotController.OnScreenshotListener;
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;
import com.umeng.socialize.sensor.beans.ShakeMsgType;
import com.umeng.socialize.sensor.controller.UMShakeService;
import com.umeng.socialize.sensor.controller.impl.UMShakeServiceFactory;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.soexample.R;
import com.umeng.soexample.commons.Constants;

/**
 * @功能描述 : 自定义平台Fragment, 包含添加微信和QQ平台
 * @原 作 者 :
 * @版 本 号 : [版本号, Aug 8, 2013]
 * @修 改 人 : mrsimple
 * @修改内容 :
 */
public class CustomPlatformFragment extends Fragment implements OnClickListener {

    // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.DESCRIPTOR);
    /**
     * 摇一摇控制器
     */
    private UMShakeService mShakeController = UMShakeServiceFactory
            .getShakeService(Constants.DESCRIPTOR);

    private ImageView mImageView = null;
    // private Button mWeiXinBtn = null;
    // private Button mQQBtn = null;
    // private Button mYiXinBtn = null;
    // private Button mShakeOpenShareBtn = null;
    // private Button mShakeShareBtn = null;
    // private Button mShakeScrShotBtn = null;
    // private Button mGotoFacebookBtn = null;
    // private Button mGotoLaiWangBtn = null;
    // private Button mInstagramBtn = null;

//    private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;

    /**
     * @功能描述 :
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // 初始化视图
        View rootView = initViews(inflater, container);
        addQZoneQQPlatform();
        return rootView;
    }

    /**
     * @Title: initViews
     * @Description: 初始化views
     * @return View
     * @throws
     */
    private View initViews(LayoutInflater inflater, ViewGroup container) {
        // patent容器
        final View root = inflater.inflate(
                R.layout.umeng_example_socialize_customplatform_example,
                container, false);
        mImageView = (ImageView) root.findViewById(R.id.scrshot_imgview);
        // 注册登录sina平台点击事件
        root.findViewById(R.id.login_sina).setOnClickListener(this);
        // 注册sina平台注销事件
        root.findViewById(R.id.logout_sina).setOnClickListener(this);
        
        // 注册登录QQ平台点击事件
        root.findViewById(R.id.login_qq).setOnClickListener(this);
        // 注册QQ平台注销事件
        root.findViewById(R.id.logout_qq).setOnClickListener(this);
        
        // 注册登录QZone平台点击事件
        root.findViewById(R.id.login_qzone).setOnClickListener(this);
        // 注册QZone平台注销事件
        root.findViewById(R.id.logout_qzone).setOnClickListener(this);
        
        // 注册摇一摇打开分享面板事件
        root.findViewById(R.id.shake_openshare).setOnClickListener(this);
        // 注册摇一摇分享事件
        root.findViewById(R.id.shake_share).setOnClickListener(this);
        // 注册摇一摇截屏事件
        root.findViewById(R.id.shake_scrshot).setOnClickListener(this);

        // // 打开分享面板
        // mWeiXinBtn = (Button) root.findViewById(R.id.share_by_weixin);
        // mWeiXinBtn.setOnClickListener(this);
        //
        // // 添加QQ平台
        // mQQBtn = (Button) root.findViewById(R.id.share_by_qq);
        // mQQBtn.setOnClickListener(this);
        //
        // // 添加易信平台
        // mYiXinBtn = (Button) root.findViewById(R.id.share_by_yixin);
        // mYiXinBtn.setOnClickListener(this);
        //
        // // 摇一摇打开分享面板
        // mShakeOpenShareBtn = (Button)
        // root.findViewById(R.id.shake_openshare);
        // mShakeOpenShareBtn.setOnClickListener(this);
        //
        // // 摇一摇截图、分享
        // mShakeShareBtn = (Button) root.findViewById(R.id.shake_share);
        // mShakeShareBtn.setOnClickListener(this);
        //
        // // 摇一摇截图
        // mShakeScrShotBtn = (Button) root.findViewById(R.id.shake_scrshot);
        // mShakeScrShotBtn.setOnClickListener(this);
        //
        // mGotoFacebookBtn = (Button)
        // root.findViewById(R.id.goto_facebook_btn);
        // mGotoFacebookBtn.setOnClickListener(this);
        //
        // mGotoLaiWangBtn = (Button) root.findViewById(R.id.goto_laiwang_btn);
        // mGotoLaiWangBtn.setOnClickListener(this);
        //
        // mInstagramBtn = (Button)
        // root.findViewById(R.id.share_by_instagram_btn);
        // mInstagramBtn.setOnClickListener(this);
        //
        // root.findViewById(R.id.share_by_pocket).setOnClickListener(this);
        // root.findViewById(R.id.share_by_linkedin).setOnClickListener(this);
        // root.findViewById(R.id.share_by_ynote).setOnClickListener(this);
        // root.findViewById(R.id.share_by_evernote_btn).setOnClickListener(this);
        // root.findViewById(R.id.share_by_pinterest).setOnClickListener(this);
        return root;
    }

    

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_sina:
                login(SHARE_MEDIA.SINA);
                break;
            case R.id.logout_sina:
                logout(SHARE_MEDIA.SINA);
                break;
            case R.id.login_qq:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.logout_qq:
                logout(SHARE_MEDIA.QQ);
                break;
            case R.id.login_qzone:
                login(SHARE_MEDIA.QZONE);
                break;
            case R.id.logout_qzone:
                logout(SHARE_MEDIA.QZONE);
                break;
            case R.id.shake_openshare:
                // 摇一摇打开分享面板
                mShakeController
                        .registerShakeToOpenShare(getActivity(), 2000, true);
                break;
            case R.id.shake_share:
                // 注册摇一摇截图分享
                registerShakeToShare();
                Toast.makeText(getActivity(), "注册摇一摇分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shake_scrshot:
                // 摇一摇截图
                mShakeController.registerShakeToScrShot(getActivity(),
                        new UMAppAdapter(getActivity()), 1500, true,
                        mScreenshotListener);
                Toast.makeText(getActivity(), "注册摇一摇截图", Toast.LENGTH_SHORT).show();
                break;
        }

        // if (v == mWeiXinBtn) {
        // // 添加微信支持, 并且打开平台选择面板
        // addWXPlatform();
        // } else if (v == mQQBtn) {
        // // 添加QQ平台,并且打开平台选择面板
        // addQQPlatform();
        //
        // // mController.doOauthVerify(getActivity(), SHARE_MEDIA.QQ,null);
        // } else if (v == mShakeOpenShareBtn) {
        // mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
        // // 摇一摇打开分享面板
        // mShakeController
        // .registerShakeToOpenShare(getActivity(), 2000, true);
        // } else if (v == mShakeShareBtn) {
        // // 注册摇一摇截图分享
        // registerShakeToShare();
        // Toast.makeText(getActivity(), "注册摇一摇分享", Toast.LENGTH_SHORT).show();
        // } else if (v == mShakeScrShotBtn) {
        // // 摇一摇截图
        // mShakeController.registerShakeToScrShot(getActivity(),
        // new UMAppAdapter(getActivity()), 1500, true,
        // mScreenshotListener);
        // Toast.makeText(getActivity(), "注册摇一摇截图", Toast.LENGTH_SHORT).show();
        // } else if (v == mYiXinBtn) { // 添加易信平台
        // // 添加易信平台
        // addYXPlatform();
        // mController.openShare(getActivity(), false);
        // } else if (v == mGotoFacebookBtn) {
        // addFacebook();
        // } else if (v == mGotoLaiWangBtn) {
        // // 添加来往平台的支持
        // addLaiWang();
        // } else if (v == mInstagramBtn) {
        // // 添加Instagram平台的支持
        // addInstagram();
        // } else if (v.getId() == R.id.share_by_pocket) {
        // addPocket();
        // } else if (v.getId() == R.id.share_by_linkedin) {
        // addLinkedIn();
        // } else if (v.getId() == R.id.share_by_ynote) {
        // addYNote();
        // } else if (v.getId() == R.id.share_by_evernote_btn) {
        // addEverNote();
        // } else if (v.getId() == R.id.share_by_pinterest) {
        // addPinterest();
        // }
    }

    /**
     * 注销本次登录</br>
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(getActivity(), platform, new SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除"+platform.toString()+"平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除"+platform.toString()+"平台授权失败[" + status + "]";
                }
                Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(getActivity(), platform, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(platform);
                } else {
                    Toast.makeText(getActivity(), "授权失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
            }
        });
    }

    /**
     * 获取授权平台的用户信息</br>
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(getActivity(), platform, new UMDataListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
//                String showText = "";
//                if (status == StatusCode.ST_CODE_SUCCESSED) {
//                    showText = "用户名：" + info.get("screen_name").toString();
//                    Log.d("#########", "##########" + info.toString());
//                } else {
//                    showText = "获取用户信息失败";
//                }
                if ( info != null ) {
                    Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void addQZoneQQPlatform() {
        String appId = "100424468";
        String appKey = "c7394704798a158208a74ab60104f0ba";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @Title: registerShakeToShare
     * @Description:
     * @throws
     */
    private void registerShakeToShare() {
        /**
         * 摇一摇截图,直接分享 参数1: 当前所属的Activity 参数2: 截图适配器 参数3: 要用户可选的平台,最多支持五个平台 参数4:
         * 传感器监听器，包括摇一摇完成后的回调函数onActionComplete, 可在此执行类似于暂停游戏、视频等操作;
         * 还有分享完成、取消的回调函数onOauthComplete、onShareCancel。
         */
        UMAppAdapter appAdapter = new UMAppAdapter(getActivity());
        // 配置平台
        List<SHARE_MEDIA> platforms = new ArrayList<SHARE_MEDIA>();
        // 通过摇一摇控制器来设置文本分享内容
        mShakeController.setShareContent("美好瞬间，摇摇分享——来自友盟社会化组件");
        mShakeController.setShakeMsgType(ShakeMsgType.PLATFORM_SCRSHOT);

        mShakeController.registerShakeListender(getActivity(), appAdapter,
                2000, false, platforms, mSensorListener);
        mShakeController.enableShakeSound(false);
    }

    /**
     * 传感器监听器
     */
    private OnSensorListener mSensorListener = new OnSensorListener() {

        @Override
        public void onStart() {

        }

        /**
         * 分享完成后回调 (non-Javadoc)
         * 
         * @see com.umeng.socialize.controller.listener.SocializeListeners.DirectShareListener#onOauthComplete(java.lang.String,
         *      com.umeng.socialize.bean.SHARE_MEDIA)
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int eCode,
                SocializeEntity entity) {
            Toast.makeText(getActivity(), "分享完成 000000", Toast.LENGTH_SHORT)
                    .show();
        }

        /**
         * (非 Javadoc)
         * 
         * @Title: onActionComplete
         * @Description: 摇一摇动作完成后回调 (non-Javadoc)
         * @param event
         * @see com.umeng.socialize.sensor.UMSensor.OnSensorListener#onActionComplete(android.hardware.SensorEvent)
         */
        @Override
        public void onActionComplete(SensorEvent event) {
            Toast.makeText(getActivity(), "游戏暂停", Toast.LENGTH_SHORT).show();
        }

        /**
         * (非 Javadoc)
         * 
         * @Title: onButtonClick
         * @Description: 用户点击分享窗口的取消和分享按钮触发的回调
         * @param button
         * @see com.umeng.socialize.sensor.UMSensor.OnSensorListener#onButtonClick(com.umeng.socialize.sensor.UMSensor.WhitchButton)
         */
        @Override
        public void onButtonClick(WhitchButton button) {
            if (button == WhitchButton.BUTTON_CANCEL) {
                Toast.makeText(getActivity(), "取消分享,游戏重新开始", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // 分享中
            }
        }
    };

    /**
     * 截图监听器，返回屏幕截图
     */
    private OnScreenshotListener mScreenshotListener = new OnScreenshotListener() {

        @Override
        public void onComplete(Bitmap bmp) {
            if (bmp != null && mImageView != null) {
                mImageView.setImageBitmap(bmp);
            }
        }
    };

    @Override
    public void onResume() {
        Log.d("", "fragment onResume");
        // 注册摇一摇截图分享
        registerShakeToShare();
        super.onResume();

    }

    @Override
    public void onStop() {
        Log.d("", "fragment onStop");
        mShakeController.unregisterShakeListener(getActivity());
        super.onStop();
    }
}
