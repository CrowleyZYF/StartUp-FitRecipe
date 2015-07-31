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
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(false);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadWithOverviewMode(false);//<==== 一定要设置为false，不然有声音没图像
        settings.setUseWideViewPort(true);
        webview.setWebChromeClient(new WebChromeClient());
        String ss = "<html>\n" +
                "<body  style=\"margin:0;padding:0}\">\n" +
                "<iframe id=\"iFrame1\" width=\"100%\" height=\"100%\" src=\""+ video_url +"\" frameborder=0 allowfullscreen></iframe>\n" +
                "</body>\n" +
                "</html>";
        webview.loadData(ss, "text/html; charset=UTF-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //恢复播放
        webview.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停播放
        webview.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要销毁，否则无法停止播放
        webview.destroy();
    }


}
