package com.umeng.soexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.common.SocializeConstants;
import com.umeng.soexample.R;


public class WelActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.umeng_example_socialize_welcome);
		SocializeConstants.SHOW_ERROR_CODE = true;
		Intent intent = null;
		if(android.os.Build.VERSION.SDK_INT >= 14){
			intent = new Intent(this, NavigationActivity.class);
		}else{
			intent = new Intent(this, SocialHomeActivity.class);
		}
		WelActivity.this.startActivity(intent);
        WelActivity.this.finish();
	}
	
}
