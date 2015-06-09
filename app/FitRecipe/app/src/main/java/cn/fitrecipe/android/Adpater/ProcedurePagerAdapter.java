package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;

import java.util.List;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/5/6.
 */
public class ProcedurePagerAdapter extends PagerAdapter {

    private List<View> list;
    private Context context;

    public ProcedurePagerAdapter(List<View> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = list.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
