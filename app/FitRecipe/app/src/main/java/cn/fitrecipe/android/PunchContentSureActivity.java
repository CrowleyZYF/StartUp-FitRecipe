package cn.fitrecipe.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.UI.SquareLayout;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.PunchImageGenerator;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PunchContentSureActivity extends Activity implements View.OnClickListener {

    private PieChartView chartView2;
    private Bitmap bitmap;
    private ImageView punch_photo, left_btn;
    private TextView right_btn, author_name, plan_date, plan_time, goal_type, punch_day, punch_type, punch_date, punch_calories, item_calories, caloire_ratio, protein_amount, carbohydrate_amount, fat_amount;
    private CircleImageView me_avatar;
    private LinearLayoutForListView component_list, nutrition_list;
    private ScrollView scrollView;
    private SquareLayout squareLayout;
    private ProgressDialog pd;

    private DatePlanItem item;
    private Report report;
    private String pngName;

    private int view_container_height;

    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_sure);

        if(getIntent().hasExtra("bitmap")) {
            String path = getIntent().getStringExtra("bitmap");
            bitmap = BitmapFactory.decodeFile(path);
        }

        action = getIntent().getStringExtra("action");
        item = (DatePlanItem) getIntent().getSerializableExtra("item");
        report = FrApplication.getInstance().getReport();
        initView();
        initData();
        initEvent();
    }

    private void initData() {
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
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initView() {
        chartView2 = (PieChartView) findViewById(R.id.piechartview);

        punch_photo = (ImageView) findViewById(R.id.punch_photo);
        if(bitmap != null)
            punch_photo.setImageBitmap(bitmap);
        else
            if(item.getImageCover() == null || item.getImageCover().length() == 0)
                FrApplication.getInstance().getMyImageLoader().displayImage(punch_photo, item.getDefaultImageCover());
            else
                FrApplication.getInstance().getMyImageLoader().displayImage(punch_photo, item.getImageCover());


        left_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (TextView) findViewById(R.id.right_btn);

        if(action.equals("share"))
            right_btn.setText("分享");
        else
            right_btn.setText("发布");

        author_name = (TextView) findViewById(R.id.author_name);
        author_name.setText(FrApplication.getInstance().getAuthor().getNick_name());

        plan_date = (TextView) findViewById(R.id.plan_date);
        plan_date.setText(Common.getDate());
        plan_time = (TextView) findViewById(R.id.plan_time);
        plan_time.setText(item.getTime());
        goal_type = (TextView) findViewById(R.id.goal_type);
        if(report.isGoalType())
            goal_type.setText("增肌ing");
        else
            goal_type.setText("减脂ing");
        punch_day = (TextView) findViewById(R.id.punch_day);
        punch_day.setText((Common.getDiff(Common.getDate(), report.getUpdatetime()) + 1) +"");
        punch_type = (TextView) findViewById(R.id.punch_type);
        switch (item.getType()) {
            case "breakfast" :
                punch_type.setText("早餐");    break;
            case "lunch":
                punch_type.setText("午餐");    break;
            case "supper":
                punch_type.setText("晚餐");    break;
            case "add_meal_01":
            case "add_meal_02":
                punch_type.setText("加餐");    break;
            case "add_meal_03":
                punch_type.setText("夜宵");    break;
        }
        punch_date = (TextView) findViewById(R.id.punch_date);
        punch_date.setText(Common.getDate());
        punch_calories = (TextView) findViewById(R.id.punch_calories);
        punch_calories.setText(Math.round(item.getCalories_take())+"kcal");
        me_avatar = (CircleImageView) findViewById(R.id.me_avatar);
        FrApplication.getInstance().getMyImageLoader().displayImage(me_avatar, FrApplication.getInstance().getAuthor().getAvatar());

        component_list = (LinearLayoutForListView) findViewById(R.id.component_list);
        List<PlanComponent> list = item.getComponents();
        List<PlanComponent> newList = new ArrayList<>();
        Collections.sort(list);
        for(int i = 0; i < list.size(); i++) {
            PlanComponent component = list.get(i);
            if(component.getType() == 1) {
                component.setType(0);
                newList.add(component);
                for(int j = 0; j < component.getComponents().size(); j++)  {
                    PlanComponent tmp = component.getComponents().get(j);
                    tmp.setType(1);
                    newList.add(tmp);
                }
            }else {
                component.setType(0);
                newList.add(component);
            }
        }
        ComponentAdapter componentAdapter = new ComponentAdapter(this, newList);
        component_list.setAdapter(componentAdapter);


        item_calories = (TextView) findViewById(R.id.item_calories);
        item_calories.setText(String.format("%.1f", item.getCalories_take()));
        caloire_ratio = (TextView) findViewById(R.id.caloire_ratio);
        caloire_ratio.setText(Math.round(item.getCalories_take() * 100.0 / report.getCaloriesIntake())  + "%");
        protein_amount = (TextView) findViewById(R.id.protein_amount);
        protein_amount.setText(String.format("%.1f", item.getProtein_take())+"g");
        carbohydrate_amount = (TextView) findViewById(R.id.carbohydrate_amount);
        carbohydrate_amount.setText(String.format("%.1f", item.getCarbohydrate_take())+"g");
        fat_amount = (TextView) findViewById(R.id.fat_amount);
        fat_amount.setText(String.format("%.1f", item.getFat_take()) + "g");

        double sum = item.getProtein_take() * 4+ item.getCarbohydrate_take() * 4 + item.getFat_take() * 9;
        int a = (int) Math.round(item.getCarbohydrate_take() * 4 * 100 / sum);
        int b = (int) Math.round(item.getProtein_take() * 4 * 100 / sum);
        int c = 100 - a - b;
        chartView2.setValue(new float[]{a, b, c}, true, false, false);

        nutrition_list = (LinearLayoutForListView) findViewById(R.id.nutrition_list);
        List<Nutrition> nutritions = item.getNutritions();
        NutritionAdapter nutritionAdapter = new NutritionAdapter(this, nutritions);
        nutrition_list.setAdapter(nutritionAdapter);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
        squareLayout = (SquareLayout) findViewById(R.id.punch_img_content);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                if(action.equals("share"))
                    share();
                else
                    publish();
                break;
        }
    }



    private void share() {
        ProgressDialog pd = ProgressDialog.show(PunchContentSureActivity.this, "", "正在生成图片...", true, false);
        pd.setCanceledOnTouchOutside(false);
        //Bitmap bitmap = PunchImageGenerator.convertViewToBitmap(scrollView);
        Bitmap bitmap = PunchImageGenerator.convertViewToPunchShareOne(squareLayout);
        File path  = new File(Environment.getExternalStorageDirectory() + "/fitrecipe/");
        if(!path.exists())
            path.mkdirs();
        File file = new File(path.getAbsolutePath() + "/abc.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        pd.dismiss();
        // 设置分享内容
        UMImage test = new UMImage(this, bitmap);
        mController.setShareImage(new UMImage(this, bitmap));
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        mController.openShare(this, false);
    }

    private void publish() {

        pd = ProgressDialog.show(this, "", "正在发布...", true);
        pd.setCanceledOnTouchOutside(false);

        if(bitmap != null) {
            UploadManager uploadManager = new UploadManager();
            Auth auth = Auth.create("LV1xTmPqkwCWd3yW4UNn90aaXyPZCGPG-MdaA3Ob", "mfMEtgpxmdRrgM7No-AwtaFCkCM6mOVr_FYbq6MR");        //get token from access key and secret key
            String token = auth.uploadToken("fitrecipe");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            pngName = FrApplication.getInstance().getAuthor().getNick_name() + Common.getTime();
            //Toast.makeText(PunchContentSureActivity.this, pngName, Toast.LENGTH_SHORT).show();
            uploadManager.put(baos.toByteArray(), pngName, token, new UpCompletionHandler() {
                @Override
                public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                    Toast.makeText(PunchContentSureActivity.this, "上传完成！", Toast.LENGTH_SHORT).show();
                    savePunchState(pngName);
                }
            }, null);
        }else {
            savePunchState(null);
        }
    }

    private void savePunchState(String pngName) {
        //TODO @wk
//        saveLocalPunchState();
        saveServerPunchState(pngName);
        pd.dismiss();
    }

    private void saveServerPunchState(String pngName) {
        JSONObject params = new JSONObject();
        try {

            switch (item.getType()) {
                case "breakfast":
                    params.put("type", 0);
                    break;
                case "add_meal_01":
                    params.put("type", 1);
                    break;
                case "lunch":
                    params.put("type", 2);
                    break;
                case "add_meal_02":
                    params.put("type", 3);
                    break;
                case "supper":
                    params.put("type", 4);
                    break;
                case "add_meal_03":
                    params.put("type", 5);
                    break;
            }
            if(pngName != null)
                params.put("img", FrServerConfig.getQiNiuHost() + pngName);
            else
                params.put("img", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostRequest request = new PostRequest(FrServerConfig.getPunchListUrl(Common.dateFormat(Common.getDate()), Common.dateFormat(Common.getDate())), FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    //Toast.makeText(PunchContentSureActivity.this, "打卡完成!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PunchContentSureActivity.this, RecordActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PunchContentSureActivity.this, "打卡出错!", Toast.LENGTH_SHORT).show();
        }
        });
        FrRequest.getInstance().request(request);
    }


    private void saveLocalPunchState() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
//                PlanFragment.isFresh = true;
//                pd.dismiss();
//                Intent intent = new Intent(PunchContentSureActivity.this, RecordActivity.class);
//                startActivity(intent);
            }

            @Override
            protected Void doInBackground(Void... params) {
//                String now = item.getDate();
//                Map<String, DatePlan> data = FrDbHelper.getInstance(PunchContentSureActivity.this).getDatePlan(now, now);
//                if (data.size() > 0) {
//                    DatePlan datePlan = data.get(now);
//                    List<DatePlanItem> items = datePlan.getItems();
//                    for (int j = 0; j < item.size(); j++) {
//                        if (items.get(j).getType().equals(item.getType())) {
//                            items.get(j).setImageCover(FrServerConfig.getQiNiuHost() + pngName);
//                            items.get(j).setIsPunch(true);
//                            datePlan.setIsPunch(true);
//                            break;
//                        }
//                    }
//                    datePlan.setItems(items);
//                    FrDbHelper.getInstance(PunchContentSureActivity.this).addDatePlan(datePlan);
//                }
                return null;
            }
        }.execute();
    }

    class NutritionAdapter extends BaseAdapter {

        private Context context;
        private List<Nutrition> data;

        public NutritionAdapter(Context context, List<Nutrition> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            if(data != null)
                return data.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView != null)
                holder = (ViewHolder) convertView.getTag();
            else {
                convertView = View.inflate(context, R.layout.punch_sure_nutrition_list_item, null);
                holder = new ViewHolder();
                holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
                convertView.setTag(holder);
            }
            if(position % 2 == 1)
                convertView.setBackgroundColor(0xfff8f8f8);
            else
                convertView.setBackgroundColor(0xffffffff);


            holder.textView1.setText(data.get(position).getName());
            holder.textView3.setText(String.format("%.1f", data.get(position).getAmount()) + data.get(position).getUnit());
            String str = "";
            switch (data.get(position).getName()) {
                case "水" :
                    str = Math.round(report.getWaterIntakeMin()*1000) + "~" + Math.round(report.getWaterIntakeMax()*1000) + data.get(position).getUnit();
                    break;
                case "蛋白质" :
                    str = Math.round(report.getProteinIntakeMin()) + "~" + Math.round(report.getProteinIntakeMax()) + data.get(position).getUnit();
                    break;
                case "脂类" :
                    str = Math.round(report.getFatIntakeMin()) + "~" + Math.round(report.getFatIntakeMax()) + data.get(position).getUnit();
                    break;
                case "碳水化合物" :
                    str = Math.round(report.getCarbohydrateIntakeMin()) + "~" + Math.round(report.getCarbohydrateIntakeMax()) + data.get(position).getUnit();
                    break;
                case "纤维素" :
                    str = Math.round(report.getFiberIntakeMin()) + "~" + Math.round(report.getFiberIntakeMax()) + data.get(position).getUnit();
                    break;
                case "钠" :
                    str = report.getSodiumIntakeMin() + "~" + report.getSodiumIntakeMax()+ data.get(position).getUnit();
                    break;
                case "维他命 C" :
                    str = Math.round(report.getVCIntakeMin()) + "~" + Math.round(report.getVCIntakeMax()) + data.get(position).getUnit();
                    break;
                case "维他命 D" :
                    str = Math.round(report.getVDIntakeMin()) + "~" + Math.round(report.getVDIntakeMax()) + data.get(position).getUnit();
                    break;
                case "不饱和脂肪酸" :
                    str = Math.round(report.getUnsaturatedFattyAcidsIntakeMin()) + "~" + Math.round(report.getUnsaturatedFattyAcidsIntakeMax()) + data.get(position).getUnit();
                    break;
                case "胆固醇" :
                    str =Math.round( report.getCholesterolIntakeMin()) + "~" + Math.round(report.getCholesterolIntakeMax()) + data.get(position).getUnit();
                    break;
            }
            holder.textView2.setText(str);
            return convertView;
        }

        class ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;
        }
    }

    class ComponentAdapter extends BaseAdapter {

        private Context context;
        private List<PlanComponent> data;

        public ComponentAdapter(Context context, List<PlanComponent> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            if(data != null)
                return data.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return data.get(position).getType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView != null)
                holder = (ViewHolder) convertView.getTag();
            else {
                convertView = View.inflate(context, R.layout.punch_sure_component_list_item, null);
                holder = new ViewHolder();
                holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
                holder.margin_space = (TextView) convertView.findViewById(R.id.margin_space);
                convertView.setTag(holder);
            }
            if(position % 2 == 0)
                convertView.setBackgroundColor(0xfff8f8f8);
            else
                convertView.setBackgroundColor(0xffffffff);

            holder.textView1.setText(data.get(position).getName());
            holder.textView3.setText(data.get(position).getAmount()+"g");
            if(getItemViewType(position) == 0) {
                holder.textView2.setText(Math.round(data.get(position).getCalories()) + "kcal");
                holder.textView1.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView3.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.margin_space.setVisibility(View.GONE);
            }else {
                holder.textView1.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView3.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.margin_space.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            TextView margin_space;
            TextView textView1;
            TextView textView2;
            TextView textView3;
        }
    }
}
