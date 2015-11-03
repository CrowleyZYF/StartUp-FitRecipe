package cn.fitrecipe.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.SearchRecipeAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.function.JsonParseHelper;
import cn.fitrecipe.android.function.RequestErrorHelper;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class SelectStageFirstFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView search_cancel, search_btn;
    private LinearLayoutForListView search_content;
    private EditText search_input;
    private ImageView clear_btn;
    private SearchRecipeAdapter adapter;
    private List<PlanComponent> data;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private BorderScrollView scrollView;
    private int start , num = 15;
    private String search;

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
        clear_btn.setVisibility(View.GONE);
        search_btn.setOnClickListener(this);
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (search_input.getText().toString().length() == 0) {
                    clear_btn.setVisibility(View.GONE);
                } else {
                    clear_btn.setVisibility(View.VISIBLE);
                }

            }
        });
        search_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SelectRecipeActivity) getActivity()).obj_selected = data.get(position);
                ((SelectRecipeActivity) getActivity()).setFragment(1);
            }
        });
        scrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getData();
            }

            @Override
            public void onTop() {

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
        scrollView = (BorderScrollView) view.findViewById(R.id.scrollView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:
                getActivity().setResult(getActivity().RESULT_CANCELED);
                getActivity().finish();
                break;
            case R.id.search_next:
                ((SelectRecipeActivity)getActivity()).setFragment(1);
                break;
            case R.id.clear_btn:
                search_input.setText("");
                break;
            case R.id.search_btn:
                search(search_input.getText().toString());
                break;
            default:
        }
    }

    private void search(String text) {
//        if(text == null || text.length() == 0) {
        if(text == null || text.length() == 0) {
            Toast.makeText(getActivity(), "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            search = text;
            if (data == null)
                data = new ArrayList<>();
            else
                data.clear();
            start = 0;
            getData();
        }
    }

    private void getData() {
        if(start == 0)
            showLoading();
        GetRequest request = null;
        try {
            request = new GetRequest(FrServerConfig.getSearchFoodUrl(URLEncoder.encode(search, "utf-8"), start, num), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                    @Override
                public void onResponse(JSONObject res) {
                    if(res != null && res.has("data")) {
                        try {
                            JSONArray data = res.getJSONArray("data");
                            processData(data);
                            if(start == 0) {
                                hideLoading(false);
                                if(data.length() == 0)
                                    Toast.makeText(getActivity(), "没有找到符合条件的食谱/食材！", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                scrollView.setCompleteMore();
                                if(data.length() == 0)
                                    Toast.makeText(getActivity(), "没有多余的搜索结果了!", Toast.LENGTH_SHORT).show();
                            }
                            if(data.length() > 0)
                                start += num;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    hideLoading(true);
                    RequestErrorHelper.toast(getActivity(), volleyError);

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray json) throws JSONException {

        if(json.length() == 0)
            Toast.makeText(getActivity(), "搜索内容为空", Toast.LENGTH_SHORT).show();

        for(int i = 0; i <json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            PlanComponent component = new PlanComponent();
            component.setId(obj.getInt("id"));
            component.setType(obj.getInt("type"));
            component.setName(obj.getString("name"));
            component.setAmount((int) obj.getDouble("amount"));
            component.setNutritions(JsonParseHelper.getNutritionSetFromJson(obj.getJSONObject("nutrition_set")));
            component.setCalories(obj.getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount"));
            if(component.getType() == 1) {
                ArrayList<PlanComponent> components = new ArrayList<>();
                JSONArray array = obj.getJSONArray("component_set");
                for(int j = 0; j < array.length(); j++) {
                    PlanComponent component1 = new PlanComponent();
                    component1.setType(0);
                    component1.setId(array.getJSONObject(j).getJSONObject("ingredient").getInt("id"));
                    component1.setName(array.getJSONObject(j).getJSONObject("ingredient").getString("name"));
                    component1.setAmount(array.getJSONObject(j).getInt("amount"));
                    components.add(component1);
                }
                component.setComponents(components);
            }
            data.add(component);
        }
        if(adapter == null) {
            adapter = new SearchRecipeAdapter(getActivity(), data);
            search_content.setAdapter(adapter);
        }else
            adapter.notifyDataSetChanged();
    }

    private void hideLoading(boolean isError){
        loadingInterface.setVisibility(View.INVISIBLE);
        dotsTextView.stop();
        if(isError){
//            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
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