package cn.fitrecipe.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ProcedureCardAdapter;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.ProcedureCard;
import pl.tajchert.sample.DotsTextView;

public class RecipeProcedureActivity extends Activity implements View.OnClickListener{

    private RecyclerView procedureRecyclerView;
    private ProcedureCardAdapter procedureCardAdapter;
    private RecyclerViewLayoutManager procedureLayoutManager;
    List<ProcedureCard> procedureCards;
    private TextView title_view;
    private ImageView left_btn;
    private ImageView right_btn;

    private JSONArray procedure_set;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_procedure);
        title = getIntent().getStringExtra("recipe_title");
        try {
            procedure_set = new JSONArray(getIntent().getStringExtra("procedure_set"));
            initView();
            initData();
            initEvent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initData() throws JSONException {
        procedureCards = getRecipeCards();
        procedureCardAdapter = new ProcedureCardAdapter(this, procedureCards);
        procedureRecyclerView.setAdapter(procedureCardAdapter);

    }

    private List<ProcedureCard> getRecipeCards() throws JSONException {
        List<ProcedureCard> result = new ArrayList<ProcedureCard>();
        for (int i = 0; i < procedure_set.length(); i++){
            JSONObject procedure = procedure_set.getJSONObject(i);
            int num = procedure.getInt("num");
            String text = procedure.getString("content");
            String img = FrServerConfig.getImageCompressed(procedure.getString("img"));
            ProcedureCard pc = new ProcedureCard(num, text, img);
            result.add(pc);
        }
        return result;
    }

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
        System.out.println("play video!!!");
        Intent intent = new Intent(this, RecipeVideoActivity.class);
        intent.putExtra("video_url", "http://player.youku.com/embed/XMTI0OTc5MzEyNA==");
        startActivity(intent);
    }
}
