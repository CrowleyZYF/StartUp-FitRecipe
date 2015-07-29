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
 * Created by wk on 2015/7/23.
 */
public class GetRequest extends JsonObjectRequest{

    private String token;
    private final int timeout = 10000;

    public GetRequest(String url, String token, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, jsonRequest, listener, errorListener);
        this.token = token;
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GetRequest(String url, String token, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        this.token = token;
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("token", token);
        return headers;
    }


}
