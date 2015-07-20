package cn.fitrecipe.android.Http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by wk on 2015/7/18.
 */
public class FRRequest {

    private static FRRequest instance = null;
    private RequestQueue rq = null;

    private FRRequest() {
    }

    public static FRRequest getInstance() {
        synchronized (FRRequest.class) {
            if(instance == null)
                instance = new FRRequest();
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
