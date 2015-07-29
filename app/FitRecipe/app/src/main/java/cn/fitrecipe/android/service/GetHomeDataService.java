package cn.fitrecipe.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.function.Common;

public class GetHomeDataService extends Service {
    public GetHomeDataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Common.isOpenNetwork(this))
            getDataFromNetwork();
        else{
            Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //get Data from Network
    private void getDataFromNetwork() {
        final GetRequest request = new GetRequest(FrServerConfig.getHomeDataUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res != null && res.has("data")) {
                    try {
                        System.out.println(this.getClass().getSimpleName() + "get data from network status 200 !");
                        JSONObject data = res.getJSONObject("data");
                        processData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("get Home data " + volleyError.getMessage());
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONObject data) throws JSONException {
        //save data to Application
        FrApplication.getInstance().saveData(data.toString());

        Set<String> urls = new HashSet<String>();
        JSONArray themes = data.getJSONArray("theme");
        for(int i = 0; i < themes.length(); i++) {
            JSONObject theme = themes.getJSONObject(i);
            String img = FrServerConfig.getImageCompressed(theme.getString("thumbnail"));
            urls.add(img);
        }

        JSONArray updates = data.getJSONArray("update");
        for (int i = 0;i < updates.length(); i++){
            JSONObject update = updates.getJSONObject(i);
            String img = FrServerConfig.getImageCompressed(update.getString("img"));
            urls.add(img);
        }

        JSONArray recommends = data.getJSONArray("recommend");
        for(int i = 0;i < recommends.length(); i++){
            JSONObject recommend = recommends.getJSONObject(i);
            String img = FrServerConfig.getImageCompressed(recommend.getString("img"));
            urls.add(img);
        }
        FrApplication.getInstance().getMyImageLoader().loadImages(urls, new ILoadingListener() {
            @Override
            public void loadComplete() {
                System.out.println(GetHomeDataService.class.getSimpleName() + "   stops ");
                stopSelf();
            }

            @Override
            public void loadFailed() {

            }
        });
    }
}
