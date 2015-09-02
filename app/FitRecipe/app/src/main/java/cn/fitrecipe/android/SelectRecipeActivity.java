package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.fitrecipe.android.Adpater.SearchRecipeAdapter;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Recipe;

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

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onProgressUpdate(Void... values) {
                Toast.makeText(SelectRecipeActivity.this, "获取菜谱"+ (objects!=null?objects.size():-1), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                List<Recipe> recipes = FrDbHelper.getInstance(SelectRecipeActivity.this).getAllRecipe();
                objects = new ArrayList<Object>();
                for (int i =0; i < recipes.size(); i++)
                    objects.add(recipes.get(i));

                Ingredient ingredient = new Ingredient();
                ingredient.setId(13);
                ingredient.setName("胡萝卜");
                Component component = new Component();
                component.setIngredient(ingredient);
                component.setAmount(100 + "");
                objects.add(component);
                publishProgress();
                return null;
            }
        }.execute();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            for(int i = 0; i < objects.size(); i++) {
                Object o = objects.get(i);
                if(isMatch(text, o)) {
                    data.add(o);
                }
            }

            if(adapter == null) {
                adapter = new SearchRecipeAdapter(getActivity(), data);
                search_content.setAdapter(adapter);
            }else
                adapter.notifyDataSetChanged();
        }

        boolean isMatch(String match, Object o) {
            return true;
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
                double sum = recipe.getNutrition_set().get(1).getAmount() + recipe.getNutrition_set().get(2).getAmount() + recipe.getNutrition_set().get(3).getAmount();
                int a = (int) Math.round(recipe.getNutrition_set().get(3).getAmount() * 100 / sum);
                int b = (int) Math.round(recipe.getNutrition_set().get(1).getAmount() * 100 / sum);
                int c = 100 - a - b;
                piechartview.setValue(new float[]{a, b, c});
            }else {
                Component component = (Component) obj_selected;
                recipe_title.setText(component.getIngredient().getName());
                piechartview.setValue(new float[]{33, 33, 34});
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
                        if(obj_selected instanceof Recipe) {
                            ((Recipe) obj_selected).setIncreWeight(Integer.parseInt(recipe_weight.getText().toString()));
                            intent.putExtra("obj_selected", (Recipe)obj_selected);
                        }else {
                            ((Component) obj_selected).setAmount(recipe_weight.getText().toString());
                            intent.putExtra("obj_selected", (Component) obj_selected);
                        }
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
