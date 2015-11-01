package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.function.RequestErrorHelper;
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
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        layout = View.inflate(this, R.layout.activity_landingpage, null);
        setContentView(layout);


        /**
         *
         *
         */
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final JSONObject params = new JSONObject();
                //Toast.makeText(LandingPageActivity.this, "注册测试用户!", Toast.LENGTH_SHORT).show();
                try {
                    params.put("phone", new Random().nextInt(1000));
                    params.put("password", "123456");
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                PostRequest request = new PostRequest(FrServerConfig.getRegisterUrl(), null, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            Toast.makeText(LandingPageActivity.this, params.getInt("phone")+"", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        RequestErrorHelper.toast(LandingPageActivity.this, volleyError);
                    }
                });
                FrRequest.getInstance().request(request);
            }
        });

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
                startActivity(intent);
                break;
            case R.id.register_button:
                Intent intent2 = new Intent(this, RegisterActivity.class);
                startActivity(intent2);
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

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再一次将退出健食记", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
        }
    }
}
