package cn.fitrecipe.android.fragment;

import android.app.Fragment;
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
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.entity.Recipe;
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
                ((SelectRecipeActivity)getActivity()).obj_selected = data.get(position);
                ((SelectRecipeActivity)getActivity()).setFragment(1);
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
                        Toast.makeText(getActivity(), "食谱不存在！", Toast.LENGTH_SHORT).show();
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