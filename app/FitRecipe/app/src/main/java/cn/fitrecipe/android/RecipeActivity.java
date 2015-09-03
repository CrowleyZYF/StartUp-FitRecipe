package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.floatingactionbutton.FloatingActionButton;
import cn.fitrecipe.android.floatingactionbutton.FloatingActionsMenu;
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
    private MyComponentAdapter component_adapter;
    //营养表
    private LinearLayoutForListView nutrition_listView;
    private MyNutritionAdapter nutrition_adapter;
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
    //private PopupWindow popupWindow;
    //收藏按钮
    private FloatingActionButton collect_btn;
    //菜单
    private FloatingActionsMenu float_set;
    //查看步骤按钮
    //private FloatingActionButton check_btn;
    //评论按钮
    private FloatingActionButton comment_btn;
    //分享按钮
    private FloatingActionButton share_btn;
    //put recipe in basket
    private TextView put_in_basket;
    //菜单是否打开
    private boolean open = false;
    //食谱是否已经收藏
    private boolean isCollected = false;

    //the recipe displayed
    private Recipe recipe;
    //add weight every
    private List<Integer> increment_list;
    private int init_weight;

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
        put_in_basket = (TextView) findViewById(R.id.put_in_basket);

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


        /*View view = LayoutInflater.from(this).inflate(R.layout.activity_recipe_info_set, null);
        popupWindow = new PopupWindow(view, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 203, getResources().getDisplayMetrics()));
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
        //刷新状态
        popupWindow.update();

        collect_btn = (ImageView) view.findViewById(R.id.collect_btn);
        shopping_btn = (ImageView) view.findViewById(R.id.shopping_btn);
        comment_btn = (ImageView) view.findViewById(R.id.comment_btn);
        share_btn = (ImageView) view.findViewById(R.id.share_btn);
        */

        float_set = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        collect_btn = (FloatingActionButton) findViewById(R.id.action_collect);
        //check_btn = (FloatingActionButton) findViewById(R.id.action_check_procedure);
        comment_btn = (FloatingActionButton) findViewById(R.id.action_comment);
        share_btn = (FloatingActionButton) findViewById(R.id.action_share);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            recipeContent.setVisibility(View.VISIBLE);
            float_set.setVisibility(View.VISIBLE);
            recipe_scrollView.smoothScrollTo(0, 0);
        }
    }

    private void loadData(String url) {
        Toast.makeText(this, "URL: "+ url, Toast.LENGTH_LONG).show();
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        processData(data);
                        initData();
                        display();
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
        component_adapter=new MyComponentAdapter();
        ingredient_listView.setAdapter(component_adapter);
        nutrition_adapter=new MyNutritionAdapter();
        nutrition_listView.setAdapter(nutrition_adapter);
        if(isCollected){
            collect_btn.setIcon(R.drawable.icon_like_green);
        }else{
            collect_btn.setIcon(R.drawable.icon_like_noshadow);
        }

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

    private void processData(JSONObject data) throws JSONException {
        recipe = Recipe.fromJson(data.toString());
        //
        if(recipe != null) {
            recipe.setIncreWeight(recipe.getTotal_amount());
        }

        /**
         * 存储菜谱
         */
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                Toast.makeText(RecipeActivity.this, "添加到数据库！", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                FrDbHelper.getInstance(RecipeActivity.this).addRecipe(recipe);
                publishProgress();
                return null;
            }
        }.execute();
    }

    private void display(){
        //display recipe image
        FrApplication.getInstance().getMyImageLoader().displayImage(recipe_pic, recipe.getImg(), new ILoadingListener() {
            @Override
            public void loadComplete() {
                hideLoading(false, "");
            }

            @Override
            public void loadFailed() {
                hideLoading(false, getResources().getString(R.string.load_image_error));
            }
        });

        //set the tag
        recipe_tags.setText(recipe.getTags());
        //set the recipe name
        recipe_name.setText(recipe.getTitle());
        //set the function
        String function = recipe.getRecipe_function();
        recipe_feature.setText(function);
        switch (function) {
            case "完美":
                recipe_feature.setBackground(getResources().getDrawable(R.drawable.perfect_background));
                break;
            case "高蛋白":
                recipe_feature.setBackground(getResources().getDrawable(R.drawable.hp_background));
                break;
            case "低脂":
                recipe_feature.setBackground(getResources().getDrawable(R.drawable.lf_background));
                break;
            case "低卡":
                recipe_feature.setBackground(getResources().getDrawable(R.drawable.lk_background));
                break;
        }
        //set duration
        recipe_time.setText("烹饪时间："+ recipe.getDuration() + "min");
        recipe_calorie.setText("热量："+ recipe.getCalories() + "kcal/100g");
        recipe_collect.setText("收藏 " + "100");
        recipe_comment.setText("评论 " + "100");

        //set the recipe author
        FrApplication.getInstance().getMyImageLoader().displayImage(avatar_pic, recipe.getAuthor().getAvatar());
        author_name.setText(recipe.getAuthor().getNick_name());

        //set the introduction of the recipe
        recipe_intro.setText(recipe.getIntroduce());


        int total_weight = recipe.getIncreWeight();
        init_weight = recipe.getIncreWeight();
        recipe_weight.setText(total_weight + "克");
        ingredient_title_weight.setText("（" + total_weight + "克）");

        double calories = recipe.getCalories();
        recipe_all_calorie.setText((int)(total_weight * calories / 100) +"kcal");
        user_need_calorie.setText("（"+"1730"+"kcal）");
        calorie_radio.setText((int)((total_weight * calories / 1730)) +"%");

        List<Component> component_set = recipe.getComponent_set();
        increment_list = new ArrayList<Integer>();
        for(int i = 0;i < component_set.size();i++){
            Component component = component_set.get(i);
            increment_list.add(component.getMAmount()/2);
        }
        isCollected = false;
    }

    private void initEvent() {
        check_procedure_btn.setOnClickListener(this);
        set_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);
        collect_btn.setOnClickListener(this);
        //check_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        put_in_basket.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_procedure:{
                Intent intent = new Intent(this, RecipeProcedureActivity.class);
                //TODO
                intent.putExtra("procedure_set", recipe.getProcedure_set());
                intent.putExtra("recipe_title", recipe_name.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.set_btn:{
                //openSet();
                startActivity(new Intent(this, IngredientActivity.class));
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
            case R.id.action_collect:{
                collect_recipe();
                //openSet();
                break;
            }
            case R.id.shopping_btn:{
                startActivity(new Intent(this, IngredientActivity.class));
                //openSet();
                break;
            }
            case R.id.action_comment:{
                Intent intent = new Intent(this, cn.fitrecipe.android.CommentActivity.class);
                intent.putExtra("recipe_id", recipe.getId());
                intent.putExtra("author_id", recipe.getAuthor().getId());
                intent.putExtra("comment_set", recipe.getComment_set());
                startActivity(intent);
                //openSet();
                break;
            }
            case R.id.action_share:{
                mController.openShare(this, false);
                //openSet();
                break;
            }
            case R.id.put_in_basket:
               FrDbHelper.getInstance(this).addToBasket(recipe);
               Toast.makeText(this, recipe.getTitle() + "加入篮子", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void openSet(){
        if(open){
            popupWindow.dismiss();
            open=false;
        }else{
            set_btn.setImageResource(R.drawable.icon_close);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(set_btn,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()), 0);
            open=true;
        }
    }*/

    public void collect_recipe(){
        if(isCollected){
            collect_btn.setImageResource(R.drawable.icon_like_noshadow);
        }else{
            if(!FrApplication.getInstance().isLogin()) {
                Toast.makeText(RecipeActivity.this, "请登录!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(RecipeActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
                return;
            }
            String url = FrServerConfig.getCreateCollectionUrl();
            JSONObject params = new JSONObject();
            try {
                params.put("type", "recipe");
                params.put("id", recipe.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PostRequest request = new PostRequest(url, FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    Toast.makeText(RecipeActivity.this, "收藏成功!", Toast.LENGTH_SHORT).show();
                    collect_btn.setImageResource(R.drawable.icon_like_green);
//                    if(res.has("data")) {
//                        try {
//                            Collection collection = new Gson().fromJson(res.getJSONObject("data").toString(), Collection.class);
//                            collection.setType("recipe");
//                            List<Collection> collectionList = FrApplication.getInstance().getCollections();
//                            if(collectionList == null) collectionList = new ArrayList<>();
//                            collectionList.add(collection);
//                            FrApplication.getInstance().setCollections(collectionList);/
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            FrRequest.getInstance().request(request);
        }
        isCollected=!isCollected;
    }

    public void adjustWeight(boolean isAdd){
        if(isAdd){
            recipe.addWeight(increment_list);
        }else{
            if(recipe.getIncreWeight() >= init_weight)
                recipe.subWeight(increment_list);
        }
        recipe_weight.setText(recipe.getIncreWeight() + "克");
        ingredient_title_weight.setText("（" + recipe.getIncreWeight() + "克）");
        recipe_all_calorie.setText((int)(recipe.getIncreWeight() * recipe.getCalories() / 100) +"kcal");
        user_need_calorie.setText("（"+"1730"+"kcal）");
        calorie_radio.setText((int) ((recipe.getIncreWeight() * recipe.getCalories() / 1730)) + "%");

        component_adapter.notifyDataSetChanged();
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

    class MyComponentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recipe.getComponent_set().size();
        }

        @Override
        public Object getItem(int position) {
            return recipe.getComponent_set().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView != null)
                viewHolder = (ViewHolder) convertView.getTag();
            else {
                viewHolder = new ViewHolder();
                convertView = View.inflate(RecipeActivity.this, R.layout.activity_recipe_info_ingredient_item, null);
                viewHolder.textView1 = (TextView) convertView.findViewById(R.id.ingredient_name);
                viewHolder.textView2 = (TextView) convertView.findViewById(R.id.ingredient_weight);
                viewHolder.textView3 = (TextView) convertView.findViewById(R.id.ingredient_remark);
            }
            viewHolder.textView1.setText(recipe.getComponent_set().get(position).getIngredient().getName());
            viewHolder.textView2.setText(recipe.getComponent_set().get(position).getMAmount()+"");
            viewHolder.textView3.setText(recipe.getComponent_set().get(position).getRemark());
            convertView.setTag(viewHolder);
            return convertView;
        }
    }

    class MyNutritionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recipe.getNutrition_set().size();
        }

        @Override
        public Object getItem(int position) {
            return recipe.getNutrition_set().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView != null)
                viewHolder = (ViewHolder) convertView.getTag();
            else {
                viewHolder = new ViewHolder();
                convertView = View.inflate(RecipeActivity.this, R.layout.activity_recipe_info_ingredient_item, null);
                viewHolder.textView1 = (TextView) convertView.findViewById(R.id.ingredient_name);
                viewHolder.textView2 = (TextView) convertView.findViewById(R.id.ingredient_weight);
                viewHolder.textView3 = (TextView) convertView.findViewById(R.id.ingredient_remark);
            }
            viewHolder.textView1.setText(recipe.getNutrition_set().get(position).getName());
            viewHolder.textView2.setText(String.valueOf(recipe.getNutrition_set().get(position).getAmount()) + recipe.getNutrition_set().get(position).getUnit());
            viewHolder.textView3.setText("12%");
            convertView.setTag(viewHolder);
            return convertView;
        }
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }
}
