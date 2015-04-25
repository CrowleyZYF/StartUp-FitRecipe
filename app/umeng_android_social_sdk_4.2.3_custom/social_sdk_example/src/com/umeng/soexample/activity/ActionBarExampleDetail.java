package com.umeng.soexample.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.umeng.soexample.activity.BaseSinglePaneActivity;
import com.umeng.soexample.fragments.DetailPageFragment;
import com.umeng.soexample.utils.MockDataHelper.TV;



public class ActionBarExampleDetail extends BaseSinglePaneActivity {
	@Override
	protected Fragment onCreatePane() {
		TV tv = getIntent().getExtras().getParcelable("TV");
		return new DetailPageFragment(tv);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


}
