package cn.fitrecipe.android.Http;

import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class RecipeListHttp extends Thread{
    private String url;
    private Handler handler;
    private View view;

    public void run(){
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
