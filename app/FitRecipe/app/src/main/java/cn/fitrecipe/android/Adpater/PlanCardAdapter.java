package cn.fitrecipe.android.Adpater;

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
import cn.fitrecipe.android.PlanChoiceInfoActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LabelView;
import cn.fitrecipe.android.entity.SeriesPlan;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class PlanCardAdapter extends RecyclerView.Adapter<PlanCardAdapter.PlanCardViewHolder>{

    private List<SeriesPlan> planCardsList;
    private Context context;


    public PlanCardAdapter(Context context, List<SeriesPlan> planCardsList) {
        this.context = context;
        this.planCardsList = planCardsList;
    }

    @Override
    public PlanCardAdapter.PlanCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_plan_choice_item_2, viewGroup, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String id = ((TextView) v.findViewById(R.id.choice_id)).getText().toString();
                Intent intent=new Intent(context,PlanChoiceInfoActivity.class);
                //intent.putExtra("id", id);
                //TODO
                intent.putExtra("plan", planCardsList.get(i));
                context.startActivity(intent);
            }
        });

        return new PlanCardViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(PlanCardAdapter.PlanCardViewHolder contactViewHolder, int i) {
        SeriesPlan pc = planCardsList.get(i);
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
        contactViewHolder.choice_days.setText(pc.getDays()+"天");
        contactViewHolder.choice_join.setText(pc.getJoin() + "人");
//        contactViewHolder.choice_background.setBackground(this.context.getResources().getDrawable(pc.getBackground()));
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.choice_background, pc.getBackground());
        contactViewHolder.author_name.setText(pc.getAuthor_name()+"");
        if(pc.getAuthor_type()==0){
            contactViewHolder.author_type.setText("健身达人");
        }else{
            contactViewHolder.author_type.setText("营养师");
        }
//        contactViewHolder.author_avatar.setImageResource(R.drawable.author_header);
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.author_avatar, pc.getAuthor_avatar());
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
        protected TextView author_name;
        protected TextView author_type;
        protected CircleImageView author_avatar;
        protected Button isUsed;

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
            author_name = (TextView) itemView.findViewById(R.id.author_name);
            author_type = (TextView) itemView.findViewById(R.id.author_type);
            author_avatar = (CircleImageView) itemView.findViewById(R.id.author_avatar);
            isUsed = (Button) itemView.findViewById(R.id.isUsed);
        }
    }
}
