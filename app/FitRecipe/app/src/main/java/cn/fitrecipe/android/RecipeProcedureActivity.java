package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ProcedureCardAdapter;
import cn.fitrecipe.android.Adpater.ProcedurePagerAdapter;
import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.ProcedureCard;
import cn.fitrecipe.android.model.RecipeCard;

public class RecipeProcedureActivity extends Activity{

    private RecyclerView procedureRecyclerView;
    private ProcedureCardAdapter procedureCardAdapter;
    private RecyclerViewLayoutManager procedureLayoutManager;
    List<ProcedureCard> procedureCards;

    private int[] imgs = { R.drawable.ztest001, R.drawable.ztest002, R.drawable.ztest003,
            R.drawable.ztest004, R.drawable.ztest005, R.drawable.ztest006};

    private String[] texts = {
            "鸡蛋煮熟后，切成条状待用",
            "紫甘蓝切丝后，用沸水滚30秒软化捞出沥干",
            "生菜，洋葱，洗干净切丝待用",
            "用一个大碗，将紫甘蓝，鸡蛋，洋葱少许，金枪鱼肉，淋上番茄酱，芝麻酱，橄榄油，混合搅拌",
            "菠菜卷饼用平底锅加热1分钟，放入生菜垫底，将搅拌后的食材倒入卷饼",
            "裹上卷饼后，即可食用"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_procedure);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
        procedureCards = getRecipeCards();
        procedureCardAdapter = new ProcedureCardAdapter(this,procedureCards);
        procedureRecyclerView.setAdapter(procedureCardAdapter);

    }

    private List<ProcedureCard> getRecipeCards() {
        List<ProcedureCard> result = new ArrayList<ProcedureCard>();
        for (int i=0;i<6;i++){
            ProcedureCard pc = new ProcedureCard(i,texts[i],imgs[i]);
            result.add(pc);
        }
        return result;
    }

    private void initView() {
        procedureRecyclerView = (RecyclerView) this.findViewById(R.id.procedure_recycler_view);
        procedureLayoutManager = new RecyclerViewLayoutManager(this);
        procedureLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        procedureRecyclerView.setLayoutManager(procedureLayoutManager);
    }

}
