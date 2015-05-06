package cn.fitrecipe.android.UI;

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
public class rcPlanViewPagerAdapter extends PagerAdapter {

    private List<View> list;
    private Context context;

    public rcPlanViewPagerAdapter(List<View> list, Context context) {
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

        BlurLayout mSampleLayout4;
        mSampleLayout4 = (BlurLayout) v.findViewById(R.id.sample);
        View hover4 = LayoutInflater.from(context).inflate(R.layout.plan_recipe_card_hover,null);
        mSampleLayout4.setHoverView(hover4);
        mSampleLayout4.addChildAppearAnimator(hover4, R.id.cat, Techniques.SlideInLeft);
        mSampleLayout4.addChildAppearAnimator(hover4, R.id.mail, Techniques.FadeInDown);
        mSampleLayout4.addChildAppearAnimator(hover4, R.id.last, Techniques.SlideInRight);

        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.cat, Techniques.SlideOutLeft);
        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.mail, Techniques.FadeOutUp);
        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.last, Techniques.SlideOutRight);

        mSampleLayout4.addChildAppearAnimator(hover4, R.id.content, Techniques.BounceIn);
        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.content, Techniques.FadeOutUp);


        hover4.findViewById(R.id.cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia"));
                context.startActivity(getWebPage);
            }
        });

        hover4.findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"daimajia@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About AndroidViewHover");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a good idea about this project..");

                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
