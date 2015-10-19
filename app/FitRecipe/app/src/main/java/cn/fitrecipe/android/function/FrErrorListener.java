package cn.fitrecipe.android.function;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


/**
 * Created by wk on 2015/10/19.
 */
public class FrErrorListener implements Response.ErrorListener{

    private Context context;

    public FrErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(volleyError instanceof TimeoutError) {
            Toast.makeText(context, "访问超时,请检查网络!", Toast.LENGTH_SHORT).show();
        }
        else
        if((volleyError instanceof ServerError) || (volleyError instanceof AuthFailureError)) {
            NetworkResponse response = volleyError.networkResponse;
            if (response != null) {
                switch (response.statusCode) {
                    case 404:
                        Toast.makeText(context, "访问不存在！", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(context, "服务器错误！", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "服务器错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            if((volleyError instanceof NetworkError) || (volleyError instanceof NoConnectionError)) {
                Toast.makeText(context, "网络未连接！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
