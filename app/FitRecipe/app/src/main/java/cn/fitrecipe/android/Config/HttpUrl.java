package cn.fitrecipe.android.Config;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class HttpUrl {
    //URL
    public static final String API_HOST = "http://42.121.108.228/";
    //登陆 POST
    public static final String LOGIN_URL = API_HOST+"login";
    public static final String OTHER_LOGIN_URL = API_HOST+"login";
    public static final int LOGIN_SUCCESS = 0;
    public static final int NOT_EXIST = 1;
    public static final int PASS_ERROR = 2;
    //注册 POST
    public static final String REGISTER_URL = API_HOST+"register";
    public static final int REGISTER_SUCCESS = 0;
    public static final int ACCOUNT_EXIST = 1;
    //首页推荐内容 GET 参数int time: 0（早餐）/1（上午加餐）/2（午餐）/3（下午加餐）/4（晚餐）
    public static final String RECOMMEND_RECIPE_VIEWPAGER = API_HOST+"indexRecommendRecipe";
    //首页主题封面 GET
    public static final String THEME_RECIPE_VIEWPAGER = API_HOST+"indexThemeRecipe";
    //首页更新内容 GET
    public static final String UPDATE_RECIPE_VIEWPAGER = API_HOST+"indexUpdateRecipe";




    //TEST
    //登陆 POST
    public static final String LOGIN_URL_JSON = "{'result':'0','platform':'self','account':'13661846821','username':'HelloWorld'}";
    //首页推荐内容，菜谱id号，推荐特别封面图片url
    public static final String RECOMMEND_RECIPE_VIEWPAGER_JSON = "[{'id':'1','imgUrl':'http://ww3.sinaimg.cn/mw690/b5c47ffbgw1ergtmnxfdpj20ku0b1wih.jpg'},{'id':'2','imgUrl':'http://ww3.sinaimg.cn/mw690/b5c47ffbgw1ergtmnxfdpj20ku0b1wih.jpg'},{'id':'3','imgUrl':'http://ww3.sinaimg.cn/mw690/b5c47ffbgw1ergtmnxfdpj20ku0b1wih.jpg'}]";
    //首页主题封面，主题id号，主题特别封面图片url
    public static final String THEME_RECIPE_VIEWPAGER_JSON = "[{'id':'1','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmqetr5j20jo04yabg.jpg'},{'id':'2','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmqetr5j20jo04yabg.jpg'},{'id':'3','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmqetr5j20jo04yabg.jpg'},]";
    //首页更新内容，菜谱id号，菜谱封面url，菜谱名称，功效（0代表不限，1代表增肌，2代表减脂），烹饪时间，卡路里，收藏数
    public static final String UPDATE_RECIPE_VIEWPAGER_JSON = "["+
            "{'id':'1','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmulvpij20jq097jts.jpg','name':'牛油果鸡蛋三明治1','function':'1','time':'21','calorie':'210','like':'182'}"+
            "{'id':'2','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmulvpij20jq097jts.jpg','name':'牛油果鸡蛋三明治2','function':'2','time':'22','calorie':'220','like':'183'}"+
            "{'id':'3','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmulvpij20jq097jts.jpg','name':'牛油果鸡蛋三明治3','function':'0','time':'23','calorie':'230','like':'184'}"+
            "{'id':'4','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmulvpij20jq097jts.jpg','name':'牛油果鸡蛋三明治4','function':'1','time':'24','calorie':'240','like':'185'}"+
            "{'id':'5','imgUrl':'http://ww1.sinaimg.cn/mw690/b5c47ffbgw1ergtmulvpij20jq097jts.jpg','name':'牛油果鸡蛋三明治5','function':'2','time':'25','calorie':'250','like':'186'}"+
            "]";
}
