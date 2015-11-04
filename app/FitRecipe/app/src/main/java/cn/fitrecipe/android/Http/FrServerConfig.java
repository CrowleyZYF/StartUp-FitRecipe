package cn.fitrecipe.android.Http;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wk on 2015/7/23.
 */
public class FrServerConfig {

    //server host
    //public final static String HOST = "https://fitrecipe.cn";
    public final static String HOST = "http://121.41.41.13";


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
        return HOST + "/api/recipe/" + id + "/";
    }

    //get Home data
    public static String getHomeDataUrl() {
        return HOST + "/api/recommend/";
    }

    //get compressed image data
    public static String getImageCompressed(String url) {
        String scale = "?imageMogr2/thumbnail/400000@";
        return url + scale;
    }

    //get compressed avatar data
    public static String getAvatarCompressed(String url) {
        if (url.contains("7xk6xp.com1.z0.glb.clouddn.com")){
            String scale = "?imageMogr2/thumbnail/35000@";
            return url + scale;
        }else {
            return url;
        }
    }

    public static String getArticleImageCompressed(String url) {
        String scale = "?imageMogr2/thumbnail/35000@";
        return url + scale;
    }

    //get Theme Details
    public static String getThemeDetailsUrl(JSONObject params) throws JSONException {
        StringBuilder sb = new StringBuilder(HOST + "/api/theme/");
        if(params.has("id")) {
            sb.append(params.getString("id") + "/recipes/");
            sb.append("?");
            if (params.has("start"))
                sb.append("start=" + params.getInt("start") + "&");
            if (params.has("num"))
                sb.append("num=" + params.getInt("num") + "&");
        }
        return  sb.toString();
    }

    public static String getThemeInfo(int id) {
        return HOST + "/api/theme/" +id+ "/";
    }

    //get Category
    public static String getRecipeListByCategory(JSONObject params) throws JSONException {
        StringBuilder sb = new StringBuilder(HOST + "/api/recipe/list/?");
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

    //get article series
    public static String getSeriesByType(boolean a, boolean b, boolean c) {
        StringBuilder sb = new StringBuilder(HOST + "/api/article/series/list/?type=");
        if(a) sb.append("1,");
        if(b) sb.append("2,");
        if(c) sb.append("3,");
        if(sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static String getArticleInSeries(int id) {
        return HOST + "/api/article/series/" +id;
    }

    public static String getArticleById(int id) {
        return HOST + "/api/article/" + id;
    }

    public static String getCreateCommentUrl() {
        return HOST + "/api/comment/create/";
    }

    public static String getCommentByRecipe(int recipeid, int lastid) {
        String str =  HOST + "/api/comment/"+ recipeid + "/list/";
        if(lastid < 0)
            return str;
        else
            return str + "?lastid=" + lastid;
    }

    public static String getCreateCollectionUrl() {
        return HOST + "/api/collection/create/";
    }

    public static String getCollectionsUrl(String type, int lastid) {
        String str = HOST + "/api/collection/list/" + type + "/";
        if(lastid < 0)
            return str;
        else
            return str + "?lastid=" + lastid;
    }

    public static String getDeleteCollectionUrl(String type, int id) {
        return HOST + "/api/collection/delete/" + type + "/" + id + "/";
    }

    public static String getReportUrl() {
        return HOST + "/api/accounts/upload_evaluation/";
    }

    public static String getQiNiuHost() {
        return "http://7xk6xp.com1.z0.glb.clouddn.com/";
    }

    public static String getOfficalPlanUrl() {
        return HOST + "/api/plan/list/";
    }

    public static String getPlanDetails(int id) {
        return HOST + "/api/plan/" + id + "/";
    }

    public static String getPunchListUrl(String start, String end) {
        return HOST + "/api/plan/punch/?start="+ start +"&end=" + end;
    }

    public static String getDatePlanUrl(String start, String end) {
        return HOST + "/api/plan/calendar/?start=" + start + "&end=" + end;
    }

    public static String getPunchCountUrl() {
        return HOST + "/api/plan/count/";
    }

    public static String getSearchFoodUrl(String keywords, int start, int num) {
        return HOST + "/api/recipe/search/food/?keyword=" + keywords + "&start=" + start + "&num=" + num;
    }

    public static String getInUsePlanUrl() {
        return HOST + "/api/plan/current/";
    }

    public static String getUpdatePlanUrl() {
        return HOST + "/api/plan/list/";
    }

    public static String getJoinPlanUrl() {
        return HOST + "/api/plan/calendar/";
    }

    public static String getDownloadReportUrl() {
        return HOST + "/api/accounts/download_evaluation/";
    }

    public static String getPunchDeleteUrl(int id) {
        return HOST + "/api/plan/punch/" + id + "/delete";
    }


    public static String getAllRecipes(int start, int num) {
        return HOST + "/api/recipe/update/list/?start=" + start + "&num=" + num;
    }
    public static String getAllTheme(int start, int num) {
        return HOST + "/api/theme/list/?start=" + start + "&num=" + num;
    }

    public static String getSearchRecipeUrl(String keyword, int start, int num) {
        return HOST + "/api/recipe/search/?keyword=" + keyword + "&start=" + start + "&num=" + num;
    }

    public static String getUpdateUserInfoUrl() {
        return HOST + "/api/accounts/edit/";
    }
}
