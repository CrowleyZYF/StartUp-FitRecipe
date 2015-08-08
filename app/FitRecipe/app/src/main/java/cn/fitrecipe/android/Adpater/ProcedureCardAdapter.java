package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Procedure;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ProcedureCardAdapter extends RecyclerView.Adapter<ProcedureCardAdapter.ProcedureCardViewHolder>{

    private List<Procedure> procedureCardsList;
    private Context context;


    public ProcedureCardAdapter(Context context, List<Procedure> procedureCardsList) {
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
        Procedure pc = procedureCardsList.get(i);
        contactViewHolder.procedure_num.setText(pc.getNum());
        contactViewHolder.procedure_content.setText(pc.getContent());
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.procedure_img, pc.getImg());
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
