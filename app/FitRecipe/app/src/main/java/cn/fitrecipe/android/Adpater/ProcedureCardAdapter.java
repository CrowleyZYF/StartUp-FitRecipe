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

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.model.ProcedureCard;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ProcedureCardAdapter extends RecyclerView.Adapter<ProcedureCardAdapter.ProcedureCardViewHolder>{

    private List<ProcedureCard> procedureCardsList;
    private Context context;


    public ProcedureCardAdapter(Context context, List<ProcedureCard> procedureCardsList) {
        this.context = context;
        this.procedureCardsList = procedureCardsList;
    }

    @Override
    public ProcedureCardAdapter.ProcedureCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_recipe_procedure_item, viewGroup, false);

        return new ProcedureCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProcedureCardAdapter.ProcedureCardViewHolder contactViewHolder, int i) {
        ProcedureCard pc = procedureCardsList.get(i);
        contactViewHolder.procedure_num.setText(pc.getProcedure_num());
        contactViewHolder.procedure_content.setText(pc.getProcedure_content());
        contactViewHolder.procedure_img.setBackground (this.context.getResources().getDrawable(pc.getProcedure_img()));
    }

    @Override
    public int getItemCount() {
        return procedureCardsList.size();
    }

    public static class ProcedureCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView procedure_num;
        protected TextView procedure_content;
        protected ImageView procedure_img;

        public ProcedureCardViewHolder(View itemView) {
            super(itemView);
            procedure_num =  (TextView) itemView.findViewById(R.id.procedure_num);
            procedure_content = (TextView) itemView.findViewById(R.id.procedure_content);
            procedure_img = (ImageView)  itemView.findViewById(R.id.procedure_img);
        }
    }
}
