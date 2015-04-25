package com.umeng.soexample.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.MulStatusListener;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.ActionBarView;
import com.umeng.soexample.R;
import com.umeng.soexample.utils.MockDataHelper.TV;

@SuppressLint("ValidFragment")
public class DetailPageFragment extends Fragment {
	Context mContext;
	TV mTv;

	private UMAuthListener mOatuthListener;
	private SnsPostListener mSnsListener;
	private MulStatusListener mComListener;
	UMSocialService service;

	@SuppressLint("ValidFragment")
	public DetailPageFragment(TV tv) {
		mTv = tv;
		Log.d("TestData", mTv.des);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		initCallbackListeners();
	}

	private void initCallbackListeners() {
		mOatuthListener = new UMAuthListener() {
			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				Toast.makeText(mContext, "ActionBarExample callback on error",
						0).show();
				Log.d("TestData", "OauthCallbackListener onError");
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				Toast.makeText(mContext,
						"ActionBarExample callback  onComplete", 0).show();
				Log.d("TestData", "OauthCallbackListener onComplete");
			}

			@Override
			public void onCancel(SHARE_MEDIA arg0) {
				Toast.makeText(mContext, "ActionBarExample callback  onCancel",
						0).show();
				Log.d("TestData", "OauthCallbackListener onCancel");
			}

			@Override
			public void onStart(SHARE_MEDIA arg0) {
				Toast.makeText(mContext, "ActionBarExample callback  onStart",
						0).show();
				Log.d("TestData", "OauthCallbackListener onStart");
			}
		};

		mSnsListener = new SnsPostListener() {

			@Override
			public void onStart() {
				Toast.makeText(mContext,
						"ActionBarExample callback on onStart", 0).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				Toast.makeText(mContext,
						"ActionBarExample callback on onComplete", 0).show();
			}
		};

		mComListener = new MulStatusListener() {

			@Override
			public void onStart() {
				Toast.makeText(mContext,
						"ActionBarExample callback on onStart", 0).show();
			}

			@Override
			public void onComplete(MultiStatus multiStatus, int st,
					SocializeEntity entity) {
				Toast.makeText(mContext,
						"ActionBarExample callback on onComplete", 0).show();
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(
				R.layout.umeng_example_socialize_actionbar_detail, container,
				false);
		TextView textView = (TextView) root.findViewById(R.id.textview);
		textView.setText(mTv.des);
		service = UMServiceFactory.getUMSocialService(mTv.name);
		// 用于集成ActionBar 的ViewGroup
		ViewGroup parent = (ViewGroup) root.findViewById(R.id.root);
		// 创建ActionBar des参数是ActionBar的唯一标识，请确保不为空
		ActionBarView socializeActionBar = new ActionBarView(mContext, mTv.name);

		LayoutParams layoutParams = new ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		socializeActionBar.setLayoutParams(layoutParams);
		// 添加ActionBar
		parent.addView(socializeActionBar);

		service.registerListener(mComListener);
		service.registerListener(mOatuthListener);
		service.registerListener(mSnsListener);

		return root;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		service.unregisterListener(mComListener);
		service.unregisterListener(mOatuthListener);
		service.unregisterListener(mSnsListener);
	}
}
