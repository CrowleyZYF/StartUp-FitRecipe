package cn.fitrecipe.android.Adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.PlanChoiceActivity;
import cn.fitrecipe.android.PlanChoiceInfoActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.SeriesPlan;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class PlanCardAdapter extends RecyclerView.Adapter<PlanCardAdapter.PlanCardViewHolder>{

    private List<SeriesPlan> planCardsList;
    private Activity context;


    public PlanCardAdapter(Activity context, List<SeriesPlan> planCardsList) {
        this.context = context;
        this.planCardsList = planCardsList;
    }

    @Override
    public PlanCardAdapter.PlanCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_plan_choice_item_2, viewGroup, false);

        return new PlanCardViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(PlanCardAdapter.PlanCardViewHolder contactViewHolder, final int i) {

        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PlanChoiceInfoActivity.class);
                intent.putExtra("plan_id", planCardsList.get(i).getId());
                intent.putExtra("isUsed", planCardsList.get(i).isUsed());
                context.startActivity(intent);
            }
        });

        SeriesPlan pc = planCardsList.get(i);
        contactViewHolder.choice_id.setText(pc.getId()+"");
        contactViewHolder.choice_name.setText(pc.getTitle());
        switch (pc.getDifficulty()){
            case 1:
                contactViewHolder.choice_hard_rank_02.setVisibility(View.GONE);
                contactViewHolder.choice_hard_rank_03.setVisibility(View.GONE);
                break;
            case 2:
                contactViewHolder.choice_hard_rank_03.setVisibility(View.GONE);
                break;
            case 3:
                break;
            default:
                break;
        }
        switch (pc.getDelicious()){
            case 1:
                contactViewHolder.choice_delicious_rank_02.setVisibility(View.GONE);
                contactViewHolder.choice_delicious_rank_03.setVisibility(View.GONE);
                break;
            case 2:
                contactViewHolder.choice_delicious_rank_03.setVisibility(View.GONE);
                break;
            case 3:
                break;
            default:
                break;
        }
        if (pc.getBenifit()==0){//增肌
            contactViewHolder.choice_type.setText(R.string.muscle);
        }else{
            contactViewHolder.choice_type.setText(R.string.fat);
        }
        contactViewHolder.choice_days.setText(pc.getTotal_days()+"天");
        contactViewHolder.choice_join.setText(pc.getDish_headcount() + "人");
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.choice_background, pc.getImg());
        contactViewHolder.author_name.setText(pc.getAuthor().getName()+"");
        if(pc.getAuthor().getType()==0){
            contactViewHolder.author_type.setText("健身达人");
        }else{
            contactViewHolder.author_type.setText("营养师");
        }
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.author_avatar, pc.getAuthor().getAvatar());
        if(pc.isUsed()){
            contactViewHolder.isUsed.setVisibility(View.VISIBLE);
        }else{
            contactViewHolder.isUsed.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(planCardsList == null)
            return 0;
        else
            return planCardsList.size();
    }

    public static class PlanCardViewHolder extends RecyclerView.ViewHolder {

        protected View itemView;
        protected TextView choice_id;
        protected TextView choice_name;
        protected ImageView choice_hard_rank_01;
        protected ImageView choice_hard_rank_02;
        protected ImageView choice_hard_rank_03;
        protected ImageView choice_delicious_rank_01;
        protected ImageView choice_delicious_rank_02;
        protected ImageView choice_delicious_rank_03;
        protected TextView choice_days;
        protected TextView choice_type;
        protected TextView choice_join;
        protected RelativeLayout choice_background;
        protected TextView author_name;
        protected TextView author_type;
        protected CircleImageView author_avatar;
        protected Button isUsed;

        public PlanCardViewHolder(View itemView, Context context) {
            super(itemView);

            this.itemView = itemView;

            choice_id =  (TextView) itemView.findViewById(R.id.choice_id);
            choice_name = (TextView) itemView.findViewById(R.id.choice_name);
            choice_hard_rank_01 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_01);
            choice_hard_rank_02 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_02);
            choice_hard_rank_03 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_03);
            choice_delicious_rank_01 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_01);
            choice_delicious_rank_02 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_02);
            choice_delicious_rank_03 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_03);
            choice_days = (TextView)  itemView.findViewById(R.id.choice_days);
            choice_type = (TextView)  itemView.findViewById(R.id.choice_type);
            choice_join = (TextView) itemView.findViewById(R.id.choice_join);
            choice_background = (RelativeLayout) itemView.findViewById(R.id.choice_background);
            author_name = (TextView) itemView.findViewById(R.id.author_name);
            author_type = (TextView) itemView.findViewById(R.id.author_type);
            author_avatar = (CircleImageView) itemView.findViewById(R.id.author_avatar);
            isUsed = (Button) itemView.findViewById(R.id.isUsed);
        }
    }
}
