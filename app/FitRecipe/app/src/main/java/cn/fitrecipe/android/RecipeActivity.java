package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import pl.tajchert.sample.DotsTextView;

public class RecipeActivity extends Activity implements View.OnClickListener, PopupWindow.OnDismissListener {
    private ScrollView recipe_scrollView;
    private ScrollView recipeContent;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    //成品图
    private ImageView recipe_pic;
    //标签
    private TextView recipe_tags;
    //名称
    private TextView recipe_name;
    //特色
    private TextView recipe_feature;
    //烹饪时间
    private TextView recipe_time;
    //每百克热量
    private TextView recipe_calorie;
    //收藏数
    private TextView recipe_collect;
    //评论数
    private TextView recipe_comment;
    //作者头像
    private ImageView avatar_pic;
    //作者名称
    private TextView author_name;
    //食谱简介
    private TextView recipe_intro;
    //食材表默认重量
    private TextView recipe_weight;
    //营养表默认重量
    private TextView ingredient_title_weight;
    //总热量
    private TextView recipe_all_calorie;
    //用户每日所需热量
    private TextView user_need_calorie;
    //所占百分比
    private TextView calorie_radio;
    //食材表
    private LinearLayoutForListView ingredient_listView;
    private SimpleAdapter ingredient_adapter;
    private List<Map<String, Object>> ingredient_dataList;
    //营养表
    private LinearLayoutForListView nutrition_listView;
    private SimpleAdapter nutrition_adapter;
    private List<Map<String, Object>> nutrition_dataList;
    //查看步骤
    private TextView check_procedure_btn;
    //设置按钮
    private ImageView set_btn;
    //返回按钮
    private ImageView back_btn;
    //添加按钮
    private ImageView add_btn;
    //减少按钮
    private ImageView minus_btn;
    //菜单按钮
    private PopupWindow popupWindow;
    //收藏按钮
    private ImageView collect_btn;
    //评论按钮
    private ImageView comment_btn;
    //分享按钮
    private ImageView share_btn;
    //菜单是否打开
    private boolean open = false;
    //食谱是否已经收藏
    private boolean isCollected = false;
    //total weight
    private int total_weight = 0;
    private int count = -1;
    //calories per 100g
    double calories;
    //records
    List<List<Integer>> weight_records;

    private JSONArray procedure_set;

    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_container);
        //获取ID
        Intent intent =getIntent();
//        Map<String,Object> params=new HashMap<String, Object>();
//        params.put("id", );
        String id = intent.getStringExtra("id");
        //初始化
        initView();
        loadData(FrServerConfig.getRecipeDetails(id));
        initEvent();

        recipe_scrollView.smoothScrollTo(0, 0);
    }



    private void initView() {
        recipe_scrollView = (ScrollView) findViewById(R.id.recipe_scrollview);
        recipe_pic = (ImageView) findViewById(R.id.recipe_pic);
        recipe_tags = (TextView) findViewById(R.id.recipe_tags);
        recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_feature = (TextView) findViewById(R.id.recipe_feature);
        recipe_time = (TextView) findViewById(R.id.recipe_time);
        recipe_calorie = (TextView) findViewById(R.id.recipe_calorie);
        recipe_collect = (TextView) findViewById(R.id.recipe_collect);
        recipe_comment = (TextView) findViewById(R.id.recipe_comment);
        avatar_pic = (ImageView) findViewById(R.id.avatar_pic);
        author_name = (TextView) findViewById(R.id.author_name);
        recipe_intro = (TextView) findViewById(R.id.recipe_intro);
        recipe_weight = (TextView) findViewById(R.id.recipe_weight);
        ingredient_title_weight = (TextView) findViewById(R.id.ingredient_title_weight);
        recipe_all_calorie = (TextView) findViewById(R.id.recipe_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        calorie_radio = (TextView) findViewById(R.id.calorie_radio);
        ingredient_listView = (LinearLayoutForListView) findViewById(R.id.recipe_ingredient_list);
        nutrition_listView = (LinearLayoutForListView) findViewById(R.id.recipe_nutrition_list);

        check_procedure_btn = (TextView) findViewById(R.id.check_procedure);
        set_btn = (ImageView) findViewById(R.id.set_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        add_btn = (ImageView) findViewById(R.id.add_btn);
        minus_btn = (ImageView) findViewById(R.id.minus_btn);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        recipeContent = (ScrollView) findViewById(R.id.recipe_scrollview);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading(false, "");
            }
        }, 2000);*/


        View view = LayoutInflater.from(this).inflate(R.layout.activity_recipe_info_set, null);
        popupWindow = new PopupWindow(view, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 152, getResources().getDisplayMetrics()));
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
        //刷新状态
        popupWindow.update();

        collect_btn = (ImageView) view.findViewById(R.id.collect_btn);
        comment_btn = (ImageView) view.findViewById(R.id.comment_btn);
        share_btn = (ImageView) view.findViewById(R.id.share_btn);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            recipeContent.setVisibility(View.VISIBLE);
            recipe_scrollView.smoothScrollTo(0, 0);
        }
    }

    private void loadData(String url) {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Toast.makeText(this, "URL: "+ url, Toast.LENGTH_LONG).show();
        GetRequest request = new GetRequest(url, preferences.getString("token", ""), new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        getData(data);
                        initData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading(true, "网络连接出错!");
                if(volleyError != null && volleyError.networkResponse != null) {
                    int statusCode = volleyError.networkResponse.statusCode;
                    if(statusCode == 404) {
                       Toast.makeText(RecipeActivity.this, "食谱不存在！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void initData() {
        ingredient_adapter=new SimpleAdapter(this, ingredient_dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight","item_remark"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight,R.id.ingredient_remark});
        ingredient_listView.setAdapter(ingredient_adapter);
        nutrition_adapter=new SimpleAdapter(this, nutrition_dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight","item_remark"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight,R.id.ingredient_remark});
        nutrition_listView.setAdapter(nutrition_adapter);
        collect_recipe();

        //Sina
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        //QQ weibo
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        //QQ空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468", "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        //QQ好友
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468","c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        // 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this, R.drawable.welcome));
        // 设置分享图片，参数2为本地图片的资源引用
        //mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
        // 设置分享图片，参数2为本地图片的路径(绝对路径)
        //mController.setShareMedia(new UMImage(getActivity(),
        //                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));
    }

    private void getData(JSONObject data) throws JSONException {

        if(data.has("img"))
            FrApplication.getInstance().getMyImageLoader().displayImage(recipe_pic, FrServerConfig.getImageCompressed(data.getString("img")), new ILoadingListener() {
                @Override
                public void loadComplete() {
                    hideLoading(false, "");
                }

                @Override
                public void loadFailed() {
                    hideLoading(false, "图片未加载成功");
                }
            });
        StringBuilder tags = new StringBuilder();
        JSONArray time_labels = data.getJSONArray("time_labels");
        for(int i = 0; i < time_labels.length(); i++) {
            JSONObject label = time_labels.getJSONObject(i);
            tags.append(label.getString("name"));
            tags.append("、");
        }

        JSONArray meat_labels = data.getJSONArray("meat_labels");
        for(int i = 0; i < meat_labels.length(); i++) {
            JSONObject label = meat_labels.getJSONObject(i);
            tags.append(label.getString("name"));
            tags.append("、");
        }

        JSONArray other_labels = data.getJSONArray("other_labels");
        for(int i = 0; i < other_labels.length(); i++) {
            JSONObject label = other_labels.getJSONObject(i);
            tags.append(label.getString("name"));
            tags.append("、");
        }
        if(tags.length() > 0)
            tags.deleteCharAt(tags.length() - 1);

        recipe_tags.setText(tags.toString());
        recipe_name.setText(data.getString("title"));

        StringBuilder effects = new StringBuilder();
        JSONArray effect_labels = data.getJSONArray("effect_labels");
        for(int i = 0; i < effect_labels.length(); i++) {
            JSONObject label = effect_labels.getJSONObject(i);
            effects.append(label.getString("name"));
            effects.append(" ");
        }
        if(effects.length() > 0)
            effects.deleteCharAt(effects.length() - 1);

        recipe_feature.setText(effects);
        if(effects.toString().equals("完美")){
            recipe_feature.setBackground(getResources().getDrawable(R.drawable.perfect_background));
        }else if(effects.toString().equals("高蛋白")){
            recipe_feature.setBackground(getResources().getDrawable(R.drawable.hp_background));
        }else if(effects.toString().equals("低脂")){
            recipe_feature.setBackground(getResources().getDrawable(R.drawable.lf_background));
        }else if(effects.toString().equals("低卡")){
            recipe_feature.setBackground(getResources().getDrawable(R.drawable.lk_background));
        }
        recipe_time.setText("烹饪时间："+ data.getInt("duration") + "min");
        calories = data.getDouble("calories");
        recipe_calorie.setText("热量："+ calories + "kcal/100g");
        recipe_collect.setText("收藏 " + "100");
        recipe_comment.setText("评论 " + "100");
        //avatar_pic.setImageResource();
        JSONObject author = data.getJSONObject("author");
        FrApplication.getInstance().getMyImageLoader().displayImage(avatar_pic, author.getString("avatar"));
        author_name.setText(author.getString("nick_name"));
        recipe_intro.setText(data.getString("introduce"));

        String total = data.getString("total_amount");
        total_weight = Integer.parseInt(total.substring(0, total.length() - 1));
        recipe_weight.setText(total_weight + "克");
        ingredient_title_weight.setText("（" + total_weight + "克）");
        recipe_all_calorie.setText((int)(total_weight * calories / 100) +"kcal");
        user_need_calorie.setText("（"+"1730"+"kcal）");
        calorie_radio.setText((int)((total_weight * calories / 1730)) +"%");

        weight_records = new ArrayList<List<Integer>>();
        List<Integer> record = new ArrayList<Integer>();
        JSONArray component_set = data.getJSONArray("component_set");
        ingredient_dataList=new ArrayList<Map<String,Object>>();
        for(int i = 0;i < component_set.length();i++){
            JSONObject component = component_set.getJSONObject(i);
            Map<String, Object> map=new HashMap<String, Object>();
            JSONObject ingredient = component.getJSONObject("ingredient");
            map.put("item_name",  ingredient.getString("name"));//食材名称
            map.put("item_weight", component.getInt("amount"));//食材重量
            record.add(component.getInt("amount"));
            String item_remark = component.getString("remark");
            map.put("item_remark", item_remark.equals("")?"--":item_remark);//食材备注
            ingredient_dataList.add(map);
        }
        weight_records.add(++count, record);

        JSONObject nutrition_set = data.getJSONObject("nutrition_set");
        nutrition_dataList=new ArrayList<Map<String,Object>>();
        //String[] names = new String[]{"水", "蛋白质", "脂类", "碳水化合物", "纤维素", "钠", "维他命 C", "维他命 D", "不饱和脂肪酸", "胆固醇"};
        String[] names = new String[]{"Water", "Protein", "Total lipid (fat)", "Carbohydrate, by difference", "Fiber, total dietary", "Sodium, Na", "Vitamin C, total ascorbic acid", "Vitamin D", "Fatty acids", "Cholesterol"};
        for(int i = 0; i < names.length; i++) {
            JSONObject nutrition = nutrition_set.getJSONObject(names[i]);
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", names[i]);//营养元素名称，按照固定的顺序输入
            map.put("item_weight", nutrition.getDouble("amount") + nutrition.getString("unit"));//重量，要注意单位
            map.put("item_remark", "120g/12%");//百分比，如果用户没有经过评测，则显示“--”
            nutrition_dataList.add(map);
        }

        isCollected = false;

        procedure_set = data.getJSONArray("procedure_set");
    }

    private void initEvent() {
        check_procedure_btn.setOnClickListener(this);
        set_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);
        collect_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_procedure:{
                Intent intent = new Intent(this, RecipeProcedureActivity.class);
                intent.putExtra("procedure_set", procedure_set.toString());
                intent.putExtra("recipe_title", recipe_name.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.set_btn:{
                openSet();
                break;
            }
            case R.id.back_btn:{
                finish();
                break;
            }
            case R.id.add_btn:{
                adjustWeight(true);
                break;
            }
            case R.id.minus_btn:{
                adjustWeight(false);
                break;
            }
            case R.id.collect_btn:{
                collect_recipe();
                openSet();
                break;
            }
            case R.id.comment_btn:{
                startActivity(new Intent(this, cn.fitrecipe.android.CommentActivity.class));
                openSet();
                break;
            }
            case R.id.share_btn:{
                mController.openShare(this, false);
                openSet();
                break;
            }
        }
    }

    public void openSet(){
        if(open){
            popupWindow.dismiss();
            open=false;
        }else{
            set_btn.setImageResource(R.drawable.icon_close);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(set_btn,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()), 0);
            open=true;
        }
    }

    public void collect_recipe(){
        isCollected=!isCollected;
        //TODO
        //save the value
        if(isCollected){
            collect_btn.setImageResource(R.drawable.icon_like_noshadow);
        }else{
            collect_btn.setImageResource(R.drawable.icon_like_green);
        }
    }

    public void adjustWeight(boolean isAdd){
        List<Integer> now = null, last;
        if(isAdd){
            Toast.makeText(this, "加100克，并调整相应食材表和营养表", Toast.LENGTH_LONG).show();
            last = weight_records.get(count);
            now = new ArrayList<Integer>();
            int remain = 0;
            for(int i = 0; i < last.size(); i++) {
                double tmp = last.get(i) * 100.0 / total_weight;
                remain += (int) tmp + last.get(i);
                now.add((int) tmp + last.get(i));
            }
            remain = total_weight + 100 - remain;
            for(int i = 0; i < now.size(); i++) {
                int cnt = 0;
                for (int j = 0; j < now.size(); j++) {
                    if (i != j && now.get(j) < now.get(i)) {
                        cnt ++;
                    }
                }
                if(cnt == remain) {
                    for (int j = 0; j < now.size(); j++) {
                        if (i != j && now.get(j) < now.get(i)) {
                            now.set(j, now.get(j) + 1);
                        }
                    }
                    break;
                }
            }
            total_weight += 100;
            weight_records.add(++count, now);
        }else{
            if(count > 0) {
                Toast.makeText(this, "减100克，并调整相应食材表和营养表", Toast.LENGTH_LONG).show();
                total_weight -= 100;
                now = weight_records.get(--count);
            }else
                now = weight_records.get(0);

        }
        recipe_weight.setText(total_weight + "克");
        ingredient_title_weight.setText("（" + total_weight + "克）");
        recipe_all_calorie.setText((int)(total_weight * calories / 100) +"kcal");
        user_need_calorie.setText("（"+"1730"+"kcal）");
        calorie_radio.setText((int) ((total_weight * calories / 1730)) + "%");

        for(int i = 0; i < now.size(); i++) {
            Map<String, Object> map = ingredient_dataList.get(i);
            map.put("item_weight", now.get(i));
        }
        ingredient_adapter.notifyDataSetChanged();

//        for(int i = 0; i < nutrition_dataList.size(); i++) {
//            Map<String, Object> map = ingredient_dataList.get(i);
//            String str = (String) map.get("item_weight");
//            if(str.contains("ug")) {
//                double item_weight = Double.parseDouble(str.substring(0, str.indexOf("ug")));
//                map.put("item_remark",  * item_weight / 100);
//            }
//        }
    }

    @Override
    public void onDismiss() {
        open=!open;
        set_btn.setImageResource(R.drawable.icon_more);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        System.out.println("Recipe Activity destroy");
        super.onDestroy();
    }
}
