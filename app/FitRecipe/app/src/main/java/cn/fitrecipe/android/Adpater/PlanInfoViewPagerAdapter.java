package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 奕峰 on 2015/9/1.
 */
public class PlanInfoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private Map<String, Object> data;
    private List<View> introLinearLayout = new ArrayList<View>();

    public PlanInfoViewPagerAdapter(Context context, Map<String, Object> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View planInfoContainer = null;
        if(position==0){
            planInfoContainer = LayoutInflater.from(context).inflate(R.layout.activity_plan_choice_intro_1, null);
            TextView choice_name = (TextView) planInfoContainer.findViewById(R.id.choice_name);
            choice_name.setText(data.get("choice_name").toString());
            TextView choice_intro = (TextView) planInfoContainer.findViewById(R.id.choice_intro);
            choice_intro.setText(data.get("choice_intro").toString());
            ImageView choice_hard_rank_02 = (ImageView) planInfoContainer.findViewById(R.id.choice_hard_rank_02);
            ImageView choice_hard_rank_03 = (ImageView) planInfoContainer.findViewById(R.id.choice_hard_rank_03);
            switch (Integer.parseInt(data.get("choice_hard").toString())){
                case 1:
                    choice_hard_rank_02.setVisibility(View.GONE);
                    choice_hard_rank_03.setVisibility(View.GONE);
                    break;
                case 2:
                    choice_hard_rank_03.setVisibility(View.GONE);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            ImageView choice_delicious_rank_02 = (ImageView) planInfoContainer.findViewById(R.id.choice_delicious_rank_02);
            ImageView choice_delicious_rank_03 = (ImageView) planInfoContainer.findViewById(R.id.choice_delicious_rank_03);
            switch (Integer.parseInt(data.get("choice_delicious").toString())){
                case 1:
                    choice_delicious_rank_02.setVisibility(View.GONE);
                    choice_delicious_rank_03.setVisibility(View.GONE);
                    break;
                case 2:
                    choice_delicious_rank_03.setVisibility(View.GONE);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            TextView choice_join = (TextView) planInfoContainer.findViewById(R.id.choice_join);
            choice_join.setText(data.get("choice_join").toString()+"人已采用");
            TextView choice_label = (TextView) planInfoContainer.findViewById(R.id.choice_label);
            if(Integer.parseInt(data.get("choice_label").toString())==0){
                choice_label.setText(R.string.muscle);
            }else{
                choice_label.setText(R.string.fat);
            }
            TextView choice_type = (TextView) planInfoContainer.findViewById(R.id.choice_type);
            if(Integer.parseInt(data.get("choice_type").toString())==0){
                choice_type.setText(R.string.plan_food);
            }else{
                choice_type.setText(R.string.plan_recipe);
            }
            TextView choice_days = (TextView) planInfoContainer.findViewById(R.id.choice_days);
            choice_days.setText(data.get("choice_days").toString());
        }else{
            planInfoContainer = LayoutInflater.from(context).inflate(R.layout.activity_plan_choice_intro_2, null);
            CircleImageView author_avatar = (CircleImageView) planInfoContainer.findViewById(R.id.author_avatar);
            author_avatar.setImageResource(R.drawable.author_header);
            TextView author_name = (TextView) planInfoContainer.findViewById(R.id.author_name);
            author_name.setText(data.get("author_name").toString());
            TextView author_type = (TextView) planInfoContainer.findViewById(R.id.author_type);
            LinearLayout author_type_01 = (LinearLayout) planInfoContainer.findViewById(R.id.author_type_01);
            LinearLayout author_type_02 = (LinearLayout) planInfoContainer.findViewById(R.id.author_type_02);
            if (data.get("author_type").toString().equals("0")){
                author_type.setText("健身达人");
                author_type_01.setVisibility(View.VISIBLE);
                author_type_02.setVisibility(View.GONE);
                TextView fit_year = (TextView) planInfoContainer.findViewById(R.id.fit_year);
                fit_year.setText(data.get("fit_year").toString());
                TextView fit_fat = (TextView) planInfoContainer.findViewById(R.id.fit_fat);
                fit_fat.setText(data.get("fit_fat").toString()+"%");
            }else {
                author_type.setText("营养师");
                author_type_01.setVisibility(View.GONE);
                author_type_02.setVisibility(View.VISIBLE);
                TextView fit_title = (TextView) planInfoContainer.findViewById(R.id.fit_title);
                fit_title.setText(data.get("fit_title").toString());
            }
            TextView author_intro = (TextView) planInfoContainer.findViewById(R.id.author_intro);
            author_intro.setText("简介："+data.get("author_intro").toString());
            TextView choice_intro = (TextView) planInfoContainer.findViewById(R.id.plan_intro);
            choice_intro.setText(data.get("plan_intro").toString());
        }
        container.addView(planInfoContainer);
        introLinearLayout.add(planInfoContainer);
        return planInfoContainer;
    }

    @Override
    public int getCount() {
//        return introLinearLayout.size()
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
