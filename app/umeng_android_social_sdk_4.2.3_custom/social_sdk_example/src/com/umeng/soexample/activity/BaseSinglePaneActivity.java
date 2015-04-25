/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.umeng.soexample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.SocialHomeActivity;
import com.umeng.soexample.commons.Constants;
import com.umeng.soexample.widget.ActionBar;
import com.umeng.soexample.widget.ActionBar.IntentAction;

/**
 * A {@link BaseActivity} that simply contains a single fragment. The intent
 * used to invoke this activity is forwarded to the fragment as arguments during
 * fragment instantiation. Derived activities should only need to implement
 * {@link com.google.android.apps.iosched.ui.BaseSinglePaneActivity#onCreatePane()}
 * .
 */
public abstract class BaseSinglePaneActivity extends FragmentActivity {
    private Fragment mFragment;
    protected ActionBar mActionBar;
    protected UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane_empty);
        mActionBar = (ActionBar) findViewById(R.id.actionbar1);
        mActionBar.setTitle(getTitle());
        mActionBar.setHomeAction(new IntentAction(this, getHomeIntent(this),
                R.drawable.ic_title_home_default));

        if (savedInstanceState == null) {
            mFragment = onCreatePane();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.root_container, mFragment)
                    .commit();
        }
    }
    
    private Intent getHomeIntent(Context context) {
        Intent intent = new Intent(context, SocialHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }
    
    protected abstract Fragment onCreatePane();

}
