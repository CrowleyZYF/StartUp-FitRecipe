package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ProcedureCardAdapter;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Procedure;

public class RecipeProcedureActivity extends Activity implements View.OnClickListener{

    private RecyclerView procedureRecyclerView;
    private ProcedureCardAdapter procedureCardAdapter;
    private RecyclerViewLayoutManager procedureLayoutManager;
    List<Procedure> procedureCards;
    private TextView title_view;
    private ImageView left_btn;
    private ImageView right_btn;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_procedure);
        title = getIntent().getStringExtra("recipe_title");
        procedureCards = (ArrayList<Procedure>) getIntent().getSerializableExtra("procedure_set");
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initData() {
        procedureCardAdapter = new ProcedureCardAdapter(this, procedureCards);
        procedureRecyclerView.setAdapter(procedureCardAdapter);

    }

//    private List<ProcedureCard> getRecipeCards() throws JSONException {
//        List<ProcedureCard> result = new ArrayList<ProcedureCard>();
//        for (int i = 0; i < procedure_set.length(); i++){
//            JSONObject procedure = procedure_set.getJSONObject(i);
//            int num = procedure.getInt("num");
//            String text = procedure.getString("content");
//            String img = FrServerConfig.getImageCompressed(procedure.getString("img"));
//            ProcedureCard pc = new ProcedureCard(num, text, img);
//            result.add(pc);
//        }
//        return result;
//    }

    private void initView() {
        title_view = (TextView) findViewById(R.id.recipe_title);
        title_view.setText(title);
        left_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        procedureRecyclerView = (RecyclerView) this.findViewById(R.id.procedure_recycler_view);
        procedureLayoutManager = new RecyclerViewLayoutManager(this);
        procedureLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        procedureRecyclerView.setLayoutManager(procedureLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                this.finish();
                break;
            case R.id.right_btn:
                playVideo();
                break;
            default:
        }
    }

    private void playVideo() {
        Intent intent  = new Intent(RecipeProcedureActivity.this, PlayerActivity.class);
        intent.putExtra("vid", "XMTI0OTc5MzEyNA");
        startActivity(intent);
    }
}
