package cn.fitrecipe.android.Http;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wk on 2015/7/23.
 */
public class FrServerConfig {

    //server host
    public final static String HOST = "https://42.121.108.228";


    //login
    public static String getLoginUrl() {
        return HOST + "/api/accounts/login/";
    }

    //register
    public static String getRegisterUrl() {
        return HOST + "/api/accounts/register/";
    }

    // thirty party login

    public static String getThirtyPartyLoginUrl() {
        return HOST + "/api/accounts/thirdparty/";
    }

    //get Recipe
    public static String getRecipeDetails(String id) {
        return HOST + "/api/recipe/" + id;
    }

    //get Home data
    public static String getHomeDataUrl() {
        return HOST + "/api/recommend/";
    }

    //get compressed image data
    public static String getImageCompressed(String url) {
        String scale = "?imageMogr2/thumbnail/350000@";
        return url + scale;
    }

    //get Theme Details
    public static String getThemeDetailsUrl(String id) {
        return HOST + "/api/theme/" + id + "/recipes/";
    }

    //get Category
    public static String getRecipeListByCategory(JSONObject params) throws JSONException {
        StringBuilder sb = new StringBuilder(HOST + "/api/recipe/list?");
        if(params.has("meat"))
            sb.append("meat=" + params.getString("meat") +"&");
        if(params.has("effect"))
            sb.append("effect=" + params.getString("effect") + "&");
        if(params.has("time"))
            sb.append("time=" + params.getString("time") + "&");
        if(params.has("order"))
            sb.append("order=" + params.getString("order") + "&");
        if(params.has("desc"))
            sb.append("desc=" + params.getBoolean("desc") + "&");
        if(params.has("start"))
            sb.append("start=" + params.getInt("start") + "&");
        if(params.has("num"))
            sb.append("num=" + params.getInt("num") + "&");
        return sb.toString();
    }
}
