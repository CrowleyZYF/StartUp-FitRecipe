package cn.fitrecipe.android.Adpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JoinPlanHelper;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 奕峰 on 2015/9/1.
 */
public class PlanInfoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private SeriesPlan plan;
    private List<View> introLinearLayout = new ArrayList<View>();

    public PlanInfoViewPagerAdapter(Context context, SeriesPlan plan){
        this.context = context;
        this.plan = plan;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View planInfoContainer = null;
        if(position==0){
            planInfoContainer = LayoutInflater.from(context).inflate(R.layout.activity_plan_choice_intro_1, null);
            TextView choice_name = (TextView) planInfoContainer.findViewById(R.id.choice_name);
            choice_name.setText(plan.getTitle());
            TextView choice_intro = (TextView) planInfoContainer.findViewById(R.id.choice_intro);
            choice_intro.setText(plan.getInrtoduce());
            ImageView choice_hard_rank_02 = (ImageView) planInfoContainer.findViewById(R.id.choice_hard_rank_02);
            ImageView choice_hard_rank_03 = (ImageView) planInfoContainer.findViewById(R.id.choice_hard_rank_03);
            switch (plan.getDifficulty()){
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
            switch (plan.getDelicious()){
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
            final TextView choice_join = (TextView) planInfoContainer.findViewById(R.id.choice_join);
            choice_join.setText(plan.getDish_headcount()+"人已采用");
            TextView choice_label = (TextView) planInfoContainer.findViewById(R.id.choice_label);
            if(plan.getBenifit() == 0){
                choice_label.setText(R.string.muscle);
            }else{
                choice_label.setText(R.string.fat);
            }
            TextView choice_type = (TextView) planInfoContainer.findViewById(R.id.choice_type);
            if(plan.getType() == 0){
                choice_type.setText(R.string.plan_food);
            }else{
                choice_type.setText(R.string.plan_recipe);
            }
            TextView choice_days = (TextView) planInfoContainer.findViewById(R.id.choice_days);
            choice_days.setText(plan.getTotal_days() + "");

            final Button choice_join_btn = (Button) planInfoContainer.findViewById(R.id.choice_join_btn);
            if(!plan.isUsed()) {
                choice_join_btn.setText("选用");
                choice_join_btn.setTextColor(context.getResources().getColor(R.color.white));
                choice_join_btn.setBackground(context.getResources().getDrawable(R.drawable.join_button));
            }
            else {
                choice_join_btn.setText("取消选用");
                choice_join_btn.setTextColor(context.getResources().getColor(R.color.gray));
                choice_join_btn.setBackground(context.getResources().getDrawable(R.drawable.join_button_disable));
            }

            choice_join_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plan.setIsUsed(!plan.isUsed());
                    final ProgressDialog pd = ProgressDialog.show(context, "", "选用计划...", true, false);
                    pd.setCanceledOnTouchOutside(false);
                    if(!plan.isUsed()) {
                        try {
                            new JoinPlanHelper(context).joinPersonalPlan(new JoinPlanHelper.CallBack() {
                                @Override
                                public void handle(Object... res) {
                                    choice_join_btn.setText("选用");
                                    choice_join_btn.setTextColor(context.getResources().getColor(R.color.white));
                                    choice_join_btn.setBackground(context.getResources().getDrawable(R.drawable.join_button));
                                    //
                                    FrDbHelper.getInstance(context).setPlanUnUsed(plan);
                                    pd.dismiss();
                                }
                            }, Common.getDate());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            new JoinPlanHelper(context).joinOfficalPlan(plan.getId(), new JoinPlanHelper.CallBack() {
                                @Override
                                public void handle(Object... res) {
                                    choice_join_btn.setText("取消选用");
                                    choice_join_btn.setTextColor(context.getResources().getColor(R.color.gray));
                                    choice_join_btn.setBackground(context.getResources().getDrawable(R.drawable.join_button_disable));
                                    FrDbHelper.getInstance(context).setPlanUsed(plan);
                                    pd.dismiss();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            planInfoContainer = LayoutInflater.from(context).inflate(R.layout.activity_plan_choice_intro_2, null);
            CircleImageView author_avatar = (CircleImageView) planInfoContainer.findViewById(R.id.author_avatar);
//            author_avatar.setImageResource(R.drawable.author_header);
            FrApplication.getInstance().getMyImageLoader().displayImage(author_avatar, plan.getAuthor().getAvatar());
            TextView author_name = (TextView) planInfoContainer.findViewById(R.id.author_name);
            author_name.setText(plan.getAuthor().getName());
            TextView author_type = (TextView) planInfoContainer.findViewById(R.id.author_type);
            LinearLayout author_type_01 = (LinearLayout) planInfoContainer.findViewById(R.id.author_type_01);
            LinearLayout author_type_02 = (LinearLayout) planInfoContainer.findViewById(R.id.author_type_02);
            if (plan.getAuthor().getType() == 0){
                author_type.setText("健身达人");
                author_type_01.setVisibility(View.VISIBLE);
                author_type_02.setVisibility(View.GONE);
                TextView fit_year = (TextView) planInfoContainer.findViewById(R.id.fit_year);
                fit_year.setText(plan.getAuthor().getFit_duration() + "");
                TextView fit_fat = (TextView) planInfoContainer.findViewById(R.id.fit_fat);
                fit_fat.setText(plan.getAuthor().getFat() + "%");
            }else {
                author_type.setText("营养师");
                author_type_01.setVisibility(View.GONE);
                author_type_02.setVisibility(View.VISIBLE);
                TextView fit_title = (TextView) planInfoContainer.findViewById(R.id.fit_title);
                fit_title.setText(plan.getAuthor().getJob_title());
            }
            TextView author_intro = (TextView) planInfoContainer.findViewById(R.id.author_intro);
            author_intro.setText("简介：" + plan.getAuthor().getIntroduce());
            TextView choice_intro = (TextView) planInfoContainer.findViewById(R.id.plan_intro);
            choice_intro.setText(plan.getDesc());
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
