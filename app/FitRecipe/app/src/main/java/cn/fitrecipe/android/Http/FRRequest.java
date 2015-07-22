package cn.fitrecipe.android.Http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by wk on 2015/7/18.
 */
public class FrRequest {

    private static FrRequest instance = null;
    private RequestQueue rq = null;

    private FrRequest() {
    }

    public static FrRequest getInstance() {
        synchronized (FrRequest.class) {
            if(instance == null)
                instance = new FrRequest();
        }
        return instance;
    }

    public void init(Context context) {
        rq = Volley.newRequestQueue(context);
    }

    public void request(JsonObjectRequest request) {
        rq.add(request);
    }

}
