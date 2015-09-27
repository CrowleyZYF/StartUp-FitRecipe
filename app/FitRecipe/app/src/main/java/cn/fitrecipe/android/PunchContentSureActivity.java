package cn.fitrecipe.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.fragment.PlanFragment;
import cn.fitrecipe.android.function.Common;
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
    private ProgressDialog pd;

    private DatePlanItem item;
    private Report report;
    private String pngName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_sure);

        String path = getIntent().getStringExtra("bitmap");
        bitmap = BitmapFactory.decodeFile(path);

        item = (DatePlanItem) getIntent().getSerializableExtra("item");
        report = FrApplication.getInstance().getReport();
        initView();
        initEvent();
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
            FrApplication.getInstance().getMyImageLoader().displayImage(punch_photo, item.getDefaultImageCover());

        left_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (TextView) findViewById(R.id.right_btn);

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
        item_calories.setText(String.format("%.2f", item.getCalories_take() / 4.0 ));
        caloire_ratio = (TextView) findViewById(R.id.caloire_ratio);
        caloire_ratio.setText(Math.round(item.getCalories_take() * 100.0 / report.getCaloriesIntake())  + "%");
        protein_amount = (TextView) findViewById(R.id.protein_amount);
        protein_amount.setText(String.format("%.1f", item.getProtein_take())+"g");
        carbohydrate_amount = (TextView) findViewById(R.id.carbohydrate_amount);
        carbohydrate_amount.setText(String.format("%.1f", item.getCarbohydrate_take())+"g");
        fat_amount = (TextView) findViewById(R.id.fat_amount);
        fat_amount.setText(String.format("%.1f", item.getFat_take()) + "g");

        double sum = item.getProtein_take() + item.getCarbohydrate_take() + item.getFat_take();
        int a = (int) Math.round(item.getCarbohydrate_take() * 100 / sum);
        int b = (int) Math.round(item.getProtein_take() * 100 / sum);
        int c = 100 - a - b;
        chartView2.setValue(new float[] {a, b, c}, true, false, false);

        nutrition_list = (LinearLayoutForListView) findViewById(R.id.nutrition_list);
        List<Nutrition> nutritions = item.getNutritions();
        NutritionAdapter nutritionAdapter = new NutritionAdapter(this, nutritions);
        nutrition_list.setAdapter(nutritionAdapter);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                publish();
                break;
        }
    }

    private void publish() {

        pd = ProgressDialog.show(this, "", "正在发布...", true);
        pd.setCanceledOnTouchOutside(false);

        UploadManager uploadManager = new UploadManager();
        Auth auth = Auth.create("LV1xTmPqkwCWd3yW4UNn90aaXyPZCGPG-MdaA3Ob", "mfMEtgpxmdRrgM7No-AwtaFCkCM6mOVr_FYbq6MR");        //get token from access key and secret key
        String token = auth.uploadToken("fitrecipe");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        pngName = FrApplication.getInstance().getAuthor().getNick_name() + Common.getTime();
        Toast.makeText(PunchContentSureActivity.this, pngName, Toast.LENGTH_SHORT).show();
        uploadManager.put(baos.toByteArray(), pngName, token, new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                Toast.makeText(PunchContentSureActivity.this, "上传完成！", Toast.LENGTH_SHORT).show();
                saveServerPunchState();
            }
        }, null);
    }

    private void saveServerPunchState() {
        //TODO @wk
        saveLocalPunchState();
    }


    private void saveLocalPunchState() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                PlanFragment.isFresh = true;
                pd.dismiss();
                Intent intent = new Intent(PunchContentSureActivity.this, RecordActivity.class);
                startActivity(intent);
            }

            @Override
            protected Void doInBackground(Void... params) {
                String now = item.getDate();
                Map<String, DatePlan> data = FrDbHelper.getInstance(PunchContentSureActivity.this).getDatePlan(now, now);
                if (data.size() > 0) {
                    DatePlan datePlan = data.get(now);
                    List<DatePlanItem> items = datePlan.getItems();
                    for (int j = 0; j < item.size(); j++) {
                        if (items.get(j).getType().equals(item.getType())) {
                            items.get(j).setImageCover(FrServerConfig.getQiNiuHost() + pngName);
                            items.get(j).setIsPunch(true);
                            break;
                        }
                    }
                    datePlan.setItems(items);
                    FrDbHelper.getInstance(PunchContentSureActivity.this).addDatePlan(datePlan);
                }
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
                holder.margin_space.setVisibility(View.GONE);
            }else {
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
