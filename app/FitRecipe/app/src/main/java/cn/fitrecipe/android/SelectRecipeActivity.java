package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.SearchRecipeAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Recipe;
import pl.tajchert.sample.DotsTextView;

public class SelectRecipeActivity extends Activity implements View.OnClickListener{

    Fragment[] fragments;
    FragmentTransaction transaction;
    int last = -1;
    List<Object> objects;
    Object obj_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        fragments = new Fragment[2];
        setFragment(0);
    }

    private void setFragment(int i) {
        transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if(fragments[i] == null) {
            if (i == 0) fragments[i] = new Fragment1();
            if (i == 1) fragments[i] = new Fragment2();
            transaction.add(R.id.fragment_container, fragments[i]);
        }
        if(last != -1)
            transaction.hide(fragments[last]);
        transaction.show(fragments[i]);
        last = i;
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    class Fragment1 extends Fragment implements View.OnClickListener{

        private View view;
        private TextView search_cancel, search_btn;
        private LinearLayoutForListView search_content;
        private EditText search_input;
        private ImageView clear_btn;
        private SearchRecipeAdapter adapter;
        private List<Object> data;
        private LinearLayout loadingInterface;
        private DotsTextView dotsTextView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = View.inflate(getActivity(), R.layout.fragment_select_recipe_1, null);
            initView();
            initEvent();

            return view;
        }

        private void initEvent() {
            search_cancel.setOnClickListener(this);
            clear_btn.setOnClickListener(this);
            search_btn.setOnClickListener(this);
            search_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    obj_selected = data.get(position);
                    setFragment(1);
                }
            });
        }

        private void initView() {
            search_cancel = (TextView) view.findViewById(R.id.search_cancel);
            search_content = (LinearLayoutForListView) view.findViewById(R.id.search_content);
            search_input = (EditText) view.findViewById(R.id.search_input);
            clear_btn = (ImageView) view.findViewById(R.id.clear_btn);
            search_btn = (TextView) view.findViewById(R.id.search_btn);
            loadingInterface = (LinearLayout) view.findViewById(R.id.loading_interface);
            dotsTextView = (DotsTextView) view.findViewById(R.id.dots);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_cancel:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                case R.id.search_next:
                    setFragment(1);
                    break;
                case R.id.clear_btn:
                    search_input.setText("");
                    break;
                case R.id.search_btn:
                    search(search_input.getText().toString());
                    break;
                default:;
            }
        }

        private void search(String text) {
            if(data == null)
                data = new ArrayList<>();
            else
                data.clear();
            getData();
        }

        private void getData() {
            showLoading();
            GetRequest request = new GetRequest(FrServerConfig.getRecipeDetails("8"), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if(res != null && res.has("data")) {
                        try {
                            JSONObject data = res.getJSONObject("data");
                            hideLoading(false, "");
                            processData(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    hideLoading(true, getResources().getString(R.string.network_error));
                    if(volleyError != null && volleyError.networkResponse != null) {
                        int statusCode = volleyError.networkResponse.statusCode;
                        if(statusCode == 404) {
                            Toast.makeText(SelectRecipeActivity.this, "食谱不存在！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            FrRequest.getInstance().request(request);
        }

        private void processData(JSONObject json) throws JSONException {
            Recipe recipe = Recipe.fromJson(json.toString());
            data.add(recipe);
            if(adapter == null) {
                adapter = new SearchRecipeAdapter(getActivity(), data);
                search_content.setAdapter(adapter);
            }else
                adapter.notifyDataSetChanged();
        }

        private void hideLoading(boolean isError, String errorMessage){
            loadingInterface.setVisibility(View.INVISIBLE);
            dotsTextView.stop();
            if(isError){
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
            }else{
                search_content.setVisibility(View.VISIBLE);
            }
        }

        private void showLoading() {
            loadingInterface.setVisibility(View.VISIBLE);
            dotsTextView.start();
            search_content.setVisibility(View.INVISIBLE);
        }
    }

    class Fragment2 extends Fragment implements View.OnClickListener{
        private View view;
        private TextView search_pre,search_finish;
        private TextView recipe_title, recipe_weight, plan_num_dash, plan_num_sure;
        private TextView[] nums;
        private int[] ids = {R.id.plan_num_00, R.id.plan_num_01, R.id.plan_num_02, R.id.plan_num_03, R.id.plan_num_04, R.id.plan_num_05, R.id.plan_num_06,
                R.id.plan_num_07, R.id.plan_num_08,  R.id.plan_num_09};
        private StringBuilder weight;
        private PieChartView piechartview;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = View.inflate(getActivity(), R.layout.fragment_select_recipe_2, null);
            nums = new TextView[10];
            weight = new StringBuilder();
            initView();
            initEvent();

            return view;
        }

        private void initEvent() {
            search_pre.setOnClickListener(this);
            search_finish.setOnClickListener(this);
            for(int  i = 0; i < 10; i++) {
                final String tmp = String.valueOf(i);
                nums[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((weight.toString().length() == 0 || weight.toString().equals("0")) && tmp.equals("0")) {
                            weight.setLength(0);
                        } else {
                            if (weight.toString().length() < 4)
                                weight.append(tmp);
                            else
                                Toast.makeText(getActivity(), "重量不能超过10 000克!", Toast.LENGTH_SHORT).show();
                            recipe_weight.setText(weight.toString());
                        }

                    }
                });
            }
            plan_num_dash.setOnClickListener(this);
            plan_num_sure.setOnClickListener(this);
        }

        private void initView() {
            for(int i = 0; i < 10; i++) {
                nums[i] = (TextView) view.findViewById(ids[i]);
            }
            search_finish = (TextView) view.findViewById(R.id.search_finish);
            search_pre = (TextView) view.findViewById(R.id.search_pre);
            recipe_title = (TextView) view.findViewById(R.id.recipe_title);
            recipe_weight = (TextView) view.findViewById(R.id.recipe_weight);
            plan_num_dash = (TextView) view.findViewById(R.id.plan_num_dash);
            plan_num_sure = (TextView) view.findViewById(R.id.plan_num_sure);
            piechartview = (PieChartView) view.findViewById(R.id.piechartview);
            fresh();
        }


        @Override
        public void onHiddenChanged(boolean hidden) {
            if(!hidden) {
                fresh();
            }
            super.onHiddenChanged(hidden);
        }

        public void fresh() {
            if(obj_selected instanceof Recipe) {
                Recipe recipe = ((Recipe) obj_selected);
                recipe_title.setText(recipe.getTitle());
//                double sum = recipe.getNutrition_set().get(1).getAmount() + recipe.getNutrition_set().get(2).getAmount() + recipe.getNutrition_set().get(3).getAmount();
//                int a = (int) Math.round(recipe.getNutrition_set().get(3).getAmount() * 100 / sum);
//                int b = (int) Math.round(recipe.getNutrition_set().get(1).getAmount() * 100 / sum);
//                int c = 100 - a - b;
                // a 碳水 b 蛋白质 c 脂类
                int b = (int) Math.round(recipe.getProtein_ratio());
                int c = (int) Math.round(recipe.getFat_ratio());
                int a = 100 - b - c;
                piechartview.setValue(new float[]{a, b, c}, true, false, false);
            }else {
                Component component = (Component) obj_selected;
                recipe_title.setText(component.getIngredient().getName());
                piechartview.setValue(new float[]{33, 33, 34}, true, false, false);
            }
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_pre:
                    setFragment(0);
                    break;
                case R.id.search_finish:
                    String w = recipe_weight.getText().toString();
                    if(!w.equals("0")) {
                        Intent intent = new Intent();
                        PlanComponent component_selected = new PlanComponent();
                        int weight = Integer.parseInt(recipe_weight.getText().toString());
                        if(obj_selected instanceof Recipe) {
                            Recipe recipe = (Recipe) obj_selected;
                            component_selected.setAmount(weight);
                            component_selected.setName(recipe.getTitle());
                            component_selected.setType(1);
                            component_selected.setId(recipe.getId());
                            for(int i = 0; i < recipe.getNutrition_set().size(); i++)
                                recipe.getNutrition_set().get(i).setAmount(recipe.getNutrition_set().get(i).getAmount() * weight / 100);
                            ArrayList<PlanComponent> components = new ArrayList<>();
                            for(int i = 0; i < recipe.getComponent_set().size(); i++) {
                                PlanComponent component = new PlanComponent();
                                component.setName(recipe.getComponent_set().get(i).getIngredient().getName());
                                component.setType(0);
                                component.setAmount(Math.round(recipe.getComponent_set().get(i).getAmount() * weight * 1.0f/ recipe.getTotal_amount()));
                                component.setCalories(100);
                                components.add(component);
                            }
                            component_selected.setComponents(components);
                            component_selected.setNutritions(recipe.getNutrition_set());
                            component_selected.setCalories(recipe.getCalories() * weight / 100);

                        }else {
                            Component component = (Component) obj_selected;
                            component_selected.setName(component.getIngredient().getName());
                            component_selected.setType(0);
                            component_selected.setAmount(weight);
                            component_selected.setCalories(100);
                            //TODO @wk
                        }
                        intent.putExtra("component_selected", component_selected);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else
                        Toast.makeText(getActivity(), "重量不能为0", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.plan_num_dash:
                    recipe_weight.setText("0");
                    weight.setLength(0);
                    break;
                case R.id.plan_num_sure:
                    if(weight.toString().length() > 0) {
                        weight.deleteCharAt(weight.toString().length() - 1);
                        if(weight.toString().length() == 0)
                            recipe_weight.setText("0");
                        else
                            recipe_weight.setText(weight.toString());
                    }
                    break;
            }
        }
    }
}
