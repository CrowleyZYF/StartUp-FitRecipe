package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;



public class RecipeVideoActivity extends Activity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_video);

        webview = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String video_url = intent.getStringExtra("video_url");
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(false);
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        webview.setWebChromeClient(new WebChromeClient());
        String ss = "<html>\n" +
                "<body  style=\"margin:0;padding:0}\">\n" +
                "<iframe id=\"iFrame1\" width=\"100%\" height=\"100%\" src=\""+ video_url +"\" frameborder=0 allowfullscreen></iframe>\n" +
                "</body>\n" +
                "</html>";
        webview.loadData(ss, "text/html; charset=UTF-8", null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        webview.resumeTimers();
    }

    @Override
    protected void onPause() {
        webview.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webview.destroy();
        super.onDestroy();
    }


}
