package cn.fitrecipe.android.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Created by 奕峰 on 2015/5/9.
 */
public class LinearLayoutForListView extends ListView {
    public LinearLayoutForListView(Context context) {
        super(context);
    }
    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public LinearLayoutForListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
