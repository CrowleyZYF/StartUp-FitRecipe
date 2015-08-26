package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.PlanChoiceInfoActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LabelView;
import cn.fitrecipe.android.entity.Plan;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class PlanCardAdapter extends RecyclerView.Adapter<PlanCardAdapter.PlanCardViewHolder> implements View.OnClickListener {

    private List<Plan> planCardsList;
    private Context context;


    public PlanCardAdapter(Context context, List<Plan> planCardsList) {
        this.context = context;
        this.planCardsList = planCardsList;
    }

    @Override
    public PlanCardAdapter.PlanCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_plan_choice_item, viewGroup, false);

        itemView.setOnClickListener(this);

        return new PlanCardViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(PlanCardAdapter.PlanCardViewHolder contactViewHolder, int i) {
        Plan pc = planCardsList.get(i);
        contactViewHolder.choice_id.setText(pc.getId()+"");
        contactViewHolder.choice_name.setText(pc.getName());
        switch (pc.getHard_rank()){
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
        switch (pc.getDelicious_rank()){
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
        if (pc.getLabel()==0){//增肌
            contactViewHolder.choice_label.setText(R.string.muscle);
            contactViewHolder.choice_label.setBackgroundColor(this.context.getResources().getColor(R.color.perfect_background));
            contactViewHolder.choice_label.setPadding(0,10,0,10);
        }else{
            contactViewHolder.choice_label.setText(R.string.fat);
            contactViewHolder.choice_label.setBackgroundColor(this.context.getResources().getColor(R.color.lf_background));
            contactViewHolder.choice_label.setPadding(0,10,0,10);
        }
        if (pc.getType()==0){//食材
            contactViewHolder.choice_type.setText(R.string.plan_food);
        }else{
            contactViewHolder.choice_type.setText(R.string.plan_recipe);
        }
        contactViewHolder.choice_days.setText(pc.getDays()+"");
        contactViewHolder.choice_join.setText(pc.getJoin()+"");
        contactViewHolder.choice_background.setBackground (this.context.getResources().getDrawable(pc.getBackground()));
    }

    @Override
    public int getItemCount() {
        if(planCardsList == null)
            return 0;
        else
            return planCardsList.size();
    }

    @Override
    public void onClick(View v) {
        String id = ((TextView) v.findViewById(R.id.choice_id)).getText().toString();
        Intent intent=new Intent(context,PlanChoiceInfoActivity.class);
        intent.putExtra("id", id);
        //TODO
        context.startActivity(intent);
    }

    public static class PlanCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView choice_id;
        protected TextView choice_name;
        protected ImageView choice_hard_rank_01;
        protected ImageView choice_hard_rank_02;
        protected ImageView choice_hard_rank_03;
        protected ImageView choice_delicious_rank_01;
        protected ImageView choice_delicious_rank_02;
        protected ImageView choice_delicious_rank_03;
        //protected TextView choice_label;
        protected TextView choice_days;
        protected TextView choice_type;
        protected TextView choice_join;
        protected RelativeLayout choice_background;
        protected LabelView choice_label;

        public PlanCardViewHolder(View itemView, Context context) {
            super(itemView);
            choice_id =  (TextView) itemView.findViewById(R.id.choice_id);
            choice_name = (TextView) itemView.findViewById(R.id.choice_name);
            choice_hard_rank_01 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_01);
            choice_hard_rank_02 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_02);
            choice_hard_rank_03 = (ImageView) itemView.findViewById(R.id.choice_hard_rank_03);
            choice_delicious_rank_01 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_01);
            choice_delicious_rank_02 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_02);
            choice_delicious_rank_03 = (ImageView) itemView.findViewById(R.id.choice_delicious_rank_03);
            //choice_label = (TextView)  itemView.findViewById(R.id.choice_label);
            choice_days = (TextView)  itemView.findViewById(R.id.choice_days);
            choice_type = (TextView)  itemView.findViewById(R.id.choice_type);
            choice_join = (TextView) itemView.findViewById(R.id.choice_join);
            choice_background = (RelativeLayout) itemView.findViewById(R.id.choice_background);
            choice_label = new LabelView(context);
            choice_label.setBackgroundColor(0xff03a9f4);
            choice_label.setTargetView(choice_background, 5, LabelView.Gravity.RIGHT_TOP);
        }
    }
}
