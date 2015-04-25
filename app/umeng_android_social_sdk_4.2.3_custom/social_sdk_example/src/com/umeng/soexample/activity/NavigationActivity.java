
package com.umeng.soexample.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.soexample.R;
import com.umeng.soexample.commons.Constants;
import com.umeng.soexample.utils.NavigationHelper;

/**
 * @功能描述 : Demo主Activity
 * @author :
 * @version: [版本号, Aug 3, 2013]
 */
@SuppressLint("NewApi")
public class NavigationActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    // public static final String ARG_SECTION_POSITION = "section_number";
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    protected ActionBar actionBar;
    private Animation showAnim, hideAnim;
    private Handler mPagerTitleHander;
    protected ViewPager mViewPager;
    protected PagerTitleStrip mPagerTitle;
    protected SectionsPagerAdapter mSectionsPagerAdapter;
    private boolean isShowTitle = false;

    final UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.DESCRIPTOR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.umeng_example_socialize_action_icon);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String[] titles = NavigationHelper.getTitles();
        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(actionBar.getThemedContext(),
                        R.layout.navigation_list_item,
                        android.R.id.text1,
                        titles),
                this);

        mPagerTitle = getPagerTitle();
        initFlickerAnimations();

        mPagerTitleHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 1:// init show
                        sendEmptyMessage(3);
                        // sendEmptyMessageDelayed(2, 2000);
                        break;
                    case 2:// hide page title
                        // if (isShowTitle ) {
                        // mPagerTitle.startAnimation(hideAnim);
                        // }
                        break;
                    case 3:// show page title
                        if (!isShowTitle) {
                            isShowTitle = true;
                            ViewParent parent = mPagerTitle.getParent();
                            if (parent == null) {
                                mViewPager.addView(mPagerTitle);
                                mPagerTitle.startAnimation(showAnim);
                            }
                        }
                        break;
                }
            };
        };

        mPagerTitleHander.sendEmptyMessageDelayed(1, 100);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                actionBar.setSelectedNavigationItem(pos);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                // mPagerTitleHander.removeMessages(2);
                // } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                // mPagerTitleHander.sendEmptyMessageDelayed(2, 2000);
                // }
            }
        });
    }

    private PagerTitleStrip getPagerTitle() {
        PagerTabStrip pagerTabStrip = (PagerTabStrip) View.inflate(this,
                R.layout.pager_tab_title,
                null);
        android.support.v4.view.ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        pagerTabStrip.setLayoutParams(lp);
        return pagerTabStrip;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            mViewPager.setCurrentItem(0, true);
        }
        return true;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        mViewPager.setCurrentItem(position, true);
        if (!isShowTitle)
            mPagerTitleHander.sendEmptyMessage(1);
        return true;
    }

    public Fragment onGetFragmentItem(int position) {
        String title = NavigationHelper.getTitle(position);
        Fragment fragment = NavigationHelper.getFragment(title);
        return fragment;
    }

    private void initFlickerAnimations() {
        hideAnim = new AlphaAnimation(1.0f, 0.0f);
        hideAnim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPager.removeView(mPagerTitle);
            }
        });
        hideAnim.setDuration(500);

        showAnim = new AlphaAnimation(0.5f, 1.0f);
        showAnim.setDuration(150);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        String[] titles;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            titles = NavigationHelper.getTitles();
        }

        @Override
        public Fragment getItem(int position) {

            return onGetFragmentItem(position);
        }

        @Override
        public int getCount() {
            return NavigationHelper.getItemCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("########", "###########share board is open : " + mController.isOpenShareBoard());
//        if (keyCode == KeyEvent.KEYCODE_BACK && mController.isOpenShareBoard()) {
//            mController.dismissShareBoard();
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
            Log.d("", "#### ssoHandler.authorizeCallBack");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
