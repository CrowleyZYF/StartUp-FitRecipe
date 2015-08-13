package cn.fitrecipe.android.UI;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 奕峰 on 2015/8/12.
 */
public class TestViewPager extends ViewPager {

    private boolean noScroll = false;

    public TestViewPager(Context context) {
        super(context);
    }

    public TestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
