package cn.fitrecipe.android.Http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wk on 2015/7/22.
 */
public class PostRequest extends JsonObjectRequest{

    private final int timeout = 10000;

    public PostRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public PostRequest(String url,JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
