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

    private final int timeout = 3000;
    private String token;

    public PostRequest(String url, String token, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.token = token;
    }


    public PostRequest(String url, String token, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.token = token;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        if(token != null && token.length() > 0) {
            headers.put("Authorization", "Token " + token);
            System.out.println("Authorization" + " : Token " + token);
        }
        return headers;
    }
}
