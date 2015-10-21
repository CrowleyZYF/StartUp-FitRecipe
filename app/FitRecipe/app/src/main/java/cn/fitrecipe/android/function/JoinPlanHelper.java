package cn.fitrecipe.android.function;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlanItem;

/**
 * Created by wk on 2015/10/12.
 */
public class JoinPlanHelper {

    private Context context;

    public JoinPlanHelper(Context context) {
        this.context = context;
    }

    public void joinPersonalPlan(final CallBack callBack, String date) throws JSONException {
        JSONObject params = new JSONObject();
        JSONArray dish = new JSONArray();
        List<DatePlanItem> items = Common.generateDatePlan(date);
        for(int i = 0; i < items.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("type", i);
            JSONArray ingredient = new JSONArray();
            JSONArray recipe = new JSONArray();
//            JSONObject obj1 = new JSONObject();
//            obj1.put("id", 1);
//            obj1.put("amount", 200);
//            ingredient.put(obj1);
            obj.put("ingredient", ingredient);
            obj.put("recipe", recipe);
            dish.put(obj);
        }
        params.put("dish", dish);
        params.put("joined_date", date);
        PostRequest request = new PostRequest(FrServerConfig.getUpdatePlanUrl(), FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                Toast.makeText(context, "创建或更新自定义计划成功！", Toast.LENGTH_SHORT).show();
                if (callBack != null)
                    try {
                        callBack.handle(res.getJSONObject("data").getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null && volleyError.networkResponse != null) {
                    int statusCode = volleyError.networkResponse.statusCode;
                    Toast.makeText(context, statusCode+"", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
        FrRequest.getInstance().request(request);
        FrDbHelper.getInstance(context).clearBasket();
        FrApplication.getInstance().setIsBasketEmpty(true);
    }

    public void joinOfficalPlan(int id, final CallBack callBack) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("plan", id);
        params.put("joined_date", Common.getDate());
        PostRequest request = new PostRequest(FrServerConfig.getJoinPlanUrl(), FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    try {
                        if(res.getString("data").equals("ok"))
                            callBack.handle();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null && volleyError.networkResponse != null) {
                    int statusCode = volleyError.networkResponse.statusCode;
                    //Toast.makeText(context, statusCode+"", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
        FrRequest.getInstance().request(request);
        FrDbHelper.getInstance(context).clearBasket();
        FrApplication.getInstance().setIsBasketEmpty(true);
    }

    public interface CallBack {
         void handle(Object... res);
    }
}
