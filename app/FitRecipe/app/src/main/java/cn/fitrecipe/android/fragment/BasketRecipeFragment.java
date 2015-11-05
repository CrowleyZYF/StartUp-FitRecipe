package cn.fitrecipe.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import cn.fitrecipe.android.Adpater.BasketAdapter;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class BasketRecipeFragment extends Fragment {

    LinearLayoutForListView recipe_list_view;
    BasketAdapter basketAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_ingredient_2, null);
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BasketRecipe");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BasketRecipe");
    }

    private void initView(View v) {
        recipe_list_view = (LinearLayoutForListView) v.findViewById(R.id.recipe_list_view);
        basketAdapter = new BasketAdapter(getActivity(), ((IngredientActivity) getActivity()).getData());
        recipe_list_view.setAdapter(basketAdapter);
    }

    public void fresh() {
        basketAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
            fresh();
        super.onHiddenChanged(hidden);
    }
}