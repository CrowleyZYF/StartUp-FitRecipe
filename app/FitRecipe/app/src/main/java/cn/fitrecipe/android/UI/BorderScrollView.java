package cn.fitrecipe.android.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.fitrecipe.android.R;


public class BorderScrollView extends ScrollView {

    private OnBorderListener onBorderListener;
    private View             contentView;

    // 加载更多视图（底部视图）
    private View footView;

    // 加载更多文字
    private TextView tvFootTitle;

    // 加载更多忙碌框
    private ProgressBar pbFootRefreshing;

    //whether add footview
    private boolean flag;
    //whether can scroll
    private boolean isScroll;

    public BorderScrollView(Context context) {
        super(context);
        flag = false;
        isScroll = true;
    }

    public BorderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        flag = false;
        isScroll = true;
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        flag = false;
        isScroll = true;
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        doOnBorderListener();
    }

    public void setOnBorderListener(final OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
        if (onBorderListener == null) {
            return;
        }

        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * OnBorderListener, Called when scroll to top or bottom
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-22
     */
    public static interface OnBorderListener {

        /**
         * Called when scroll to bottom
         */
        public void onBottom();

        /**
         * Called when scroll to top
         */
        public void onTop();
    }

    private void doOnBorderListener() {
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            synchronized (BorderScrollView.class) {
                if (onBorderListener != null && isScroll) {
                    onBorderListener.onBottom();
                    System.out.println("显示进度！");
                    getMore();
                }
            }
        } else if (getScrollY() == 0) {
            if (onBorderListener != null) {
                onBorderListener.onTop();
            }
        }
    }

    private void getMore() {
        isScroll = false;
        if(!flag) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.container_layout);
            footView = View.inflate(getContext(), R.layout.get_more_list_view_foot, null);
            tvFootTitle = (TextView) footView.findViewById(R.id.tv_foot_title);
            tvFootTitle.setText("正在加载...");
            pbFootRefreshing = (ProgressBar) footView.findViewById(R.id.pb_foot_refreshing);
            pbFootRefreshing.setVisibility(View.VISIBLE);
            layout.addView(footView);
            flag = true;
        }else
            footView.setVisibility(View.VISIBLE);
    }

    public void setCompleteMore() {
        isScroll = true;
        if (footView!=null)
            footView.setVisibility(View.GONE);
    }

    public void setNoMore() {
        isScroll = false;
    }
}