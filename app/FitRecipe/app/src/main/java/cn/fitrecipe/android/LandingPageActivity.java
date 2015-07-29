package cn.fitrecipe.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

public class LandingPageActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{
    private ViewPager frViewPager;
    private int[] frImgIds = new int[]{R.drawable.landing_intro1,R.drawable.landing_intro2,R.drawable.landing_intro3};
    private List<ImageView> mImages = new ArrayList<ImageView>();
    boolean misScrolled = false;
    private Button frLoginButton;
    private Button frRegisterButton;
    private TextView frIndexButton;
    private View layout;
    private List<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        layout = View.inflate(this, R.layout.activity_landingpage, null);
        setContentView(layout);

        bitmaps = new ArrayList<Bitmap>();

        frViewPager = (ViewPager) findViewById(R.id.landing_page);
        frLoginButton = (Button) findViewById(R.id.login_button);
        frRegisterButton = (Button) findViewById(R.id.register_button);
        frIndexButton = (TextView) findViewById(R.id.index_button);
        frViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(LandingPageActivity.this);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), frImgIds[position]);
                imageView.setImageBitmap(bitmap);
                bitmaps.add(bitmap);
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

        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        defaultIndicator.setViewPager(frViewPager);

        defaultIndicator.setOnPageChangeListener(this);
        frLoginButton.setOnClickListener(this);
        frRegisterButton.setOnClickListener(this);
        frIndexButton.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
    }

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
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("back","landingPage");
                startActivity(intent);
                LandingPageActivity.this.finish();
                break;
            case R.id.register_button:
                Intent intent2 = new Intent(this, RegisterActivity.class);
                intent2.putExtra("back","landingPage");
                startActivity(intent2);
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

    @Override
    protected void onDestroy() {
        for(int i = 0; i < bitmaps.size(); i++)
            if(!bitmaps.get(i).isRecycled())
                bitmaps.get(i).recycle();;
        super.onDestroy();
    }
}
