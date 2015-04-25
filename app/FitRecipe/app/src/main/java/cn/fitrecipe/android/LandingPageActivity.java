package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LandingPageActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{
    private ViewPager frViewPager;
    private int[] frImgIds = new int[]{R.drawable.landing_intro1,R.drawable.landing_intro2,R.drawable.landing_intro3};
    private List<ImageView> mImages = new ArrayList<ImageView>();
    boolean misScrolled = false;
    private Button frLoginButton;
    private Button frRegisterButton;
    private Button frIndexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        frViewPager = (ViewPager) findViewById(R.id.landing_page);
        frLoginButton = (Button) findViewById(R.id.login_button);
        frRegisterButton = (Button) findViewById(R.id.register_button);
        frIndexButton = (Button) findViewById(R.id.index_button);
        frViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(LandingPageActivity.this);
                imageView.setImageResource(frImgIds[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImages.add(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImages.get(position));
            }

            @Override
            public int getCount() {
                return frImgIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        });
        frViewPager.setOnPageChangeListener(this);
        frLoginButton.setOnClickListener(this);
        frRegisterButton.setOnClickListener(this);
        frIndexButton.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        /*if(i==frImgIds.length-1){
            Intent mainIntent = new Intent(LandingPageActivity.this, MainActivity.class);
            LandingPageActivity.this.startActivity(mainIntent);
            LandingPageActivity.this.finish();
        }*/
    }

    @Override
    public void onPageSelected(int i) {

    }

    /*@Override
    public void onPageScrollStateChanged(int i) {

    }*/

    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                misScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (frViewPager.getCurrentItem() == frViewPager.getAdapter().getCount() - 1 && !misScrolled) {
                    startActivity(new Intent(this, MainActivity.class));
                    LandingPageActivity.this.finish();
                }
                misScrolled = true;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                startActivity(new Intent(this, LoginActivity.class));
                LandingPageActivity.this.finish();
                break;
            case R.id.register_button:
                startActivity(new Intent(this, RegisterActivity.class));
                LandingPageActivity.this.finish();
                break;
            case R.id.index_button:
                startActivity(new Intent(this, MainActivity.class));
                LandingPageActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
