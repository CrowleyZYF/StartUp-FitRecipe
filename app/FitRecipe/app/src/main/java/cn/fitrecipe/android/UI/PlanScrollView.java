package cn.fitrecipe.android.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by 奕峰 on 2015/8/29.
 */
public class PlanScrollView extends ScrollView {

    private OnBorderListener onBorderListener;
    private View contentView;

    public PlanScrollView(Context context) {
        super(context);
    }

    public PlanScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlanScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(y % 4 == 0)
            doOnBorderListener();
    }

    private void doOnBorderListener() {
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            if (onBorderListener != null) {
                onBorderListener.onBottom();
            }
        } else if (getScrollY() == 0) {
            if (onBorderListener != null) {
                onBorderListener.onTop();
            }
        } else {
            if (onBorderListener != null) {
                onBorderListener.onScroll();
            }
        }
    }

    public static interface OnBorderListener {

        public void onBottom();

        public void onTop();

        public void onScroll();
    }

    public void setOnBorderListener(final OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
    }
}
