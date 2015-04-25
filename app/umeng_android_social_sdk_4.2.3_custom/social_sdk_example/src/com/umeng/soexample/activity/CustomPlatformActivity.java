
package com.umeng.soexample.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.soexample.fragments.CustomPlatformFragment;

public class CustomPlatformActivity extends BaseSinglePaneActivity {

    @Override
    protected Fragment onCreatePane() {
        CustomPlatformFragment cp = new CustomPlatformFragment();
        return cp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
