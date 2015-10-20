package cn.fitrecipe.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.HomeData;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Recommend;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.function.Common;

public class GetHomeDataService extends Service {

    final String action = "cn.fitrecipe.android.homedataready";

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
            Toast.makeText(this, getResources().getString(R.string.network_close), Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent();
                        intent.setAction(action);
                        sendBroadcast(intent);
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
                String error_info = getResources().getString(R.string.network_error);
                if(!Common.isOpenNetwork(GetHomeDataService.this))
                    error_info = getResources().getString(R.string.network_close);
                //Toast.makeText(GetHomeDataService.this, error_info, Toast.LENGTH_SHORT).show();
                System.out.println("get Home data " + volleyError.getMessage());
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONObject data) throws JSONException {
        //save data to Application
        HomeData homeData = HomeData.fromJson(data.toString());
        FrApplication.getInstance().setHomeData(homeData);

        Set<String> urls = new HashSet<String>();
        List<Theme> themes = homeData.getTheme();
        for(int i = 0; i < themes.size(); i++) {
            String img = themes.get(i).getThumbnail();
            urls.add(img);
        }

        List<Recipe> updates = homeData.getUpdate();
        for (int i = 0;i < updates.size(); i++){
            String img = updates.get(i).getThumbnail();
            urls.add(img);
        }

        List<Recommend> recommends = homeData.getRecommend();
        for(int i = 0;i < recommends.size(); i++){
            String img = recommends.get(i).getRecipe().getRecommend_img();
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
