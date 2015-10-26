package cn.fitrecipe.android.Adpater;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.IngredientNutritionActivity;
import cn.fitrecipe.android.ChoosePhotoActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.fragment.PlanFragment;
import cn.fitrecipe.android.function.RequestErrorHelper;

/**
 * Created by wk on 2015/8/26.
 */
public class PlanElementAdapter extends BaseAdapter implements View.OnClickListener{

    Fragment fragment;
    List<DatePlanItem> items;
    Report report;
    boolean isValid, isValid2; //isValid if can change , isValid2 is if can punch

    public PlanElementAdapter(Fragment fragment, List<DatePlanItem> items, Report report) {
        this.fragment = fragment;
        this.items = items;
        this.report = report;
    }

    public void setData(List<DatePlanItem> items, boolean isValid, boolean isValid2) {
        this.items = items;
        this.isValid = isValid;
        this.isValid2 = isValid2;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(items == null)   return 0;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(fragment.getActivity(), R.layout.plan_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        //如果是早餐，添加margin
        if (position!=0){
            holder.first_margin_01.setVisibility(View.GONE);
            holder.first_margin_02.setVisibility(View.GONE);
        }else{
            holder.first_margin_01.setVisibility(View.VISIBLE);
            holder.first_margin_02.setVisibility(View.VISIBLE);
        }

        //获取某一餐的信息
        final DatePlanItem item = items.get(position);

        //滑动删除
        final ContentAdapter adapter = new ContentAdapter(item);
        holder.plan_content.setAdapter(adapter);
        //添加食谱的按钮
        final RelativeLayout addBtn = holder.add_recipe;
        //添加到菜篮子
        holder.plan_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.size() == 0) {
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再加入菜篮子！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!item.isInBasket()) {
                    addBtn.setEnabled(false);
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_shopping_active);
                    item.setIsInBasket(true);
                    ((PlanFragment) fragment).addToBasket(item.getDate(), item.getType());
                    FrDbHelper.getInstance(fragment.getActivity()).addToBasket(item.getComponents(), item.getDate(), item.getType());
                    //Toast.makeText(fragment.getActivity(), "加入菜篮子", Toast.LENGTH_SHORT).show();
                }else {
                    addBtn.setEnabled(true);
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_shopping);
                    item.setIsInBasket(false);
                    ((PlanFragment) fragment).removeFromBasket(item.getDate(), item.getType());
                    FrDbHelper.getInstance(fragment.getActivity()).removeFromBasket(item.getComponents(), item.getDate(), item.getType());
                    //Toast.makeText(fragment.getActivity(), "从菜篮子取出", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
        //打卡
        holder.plan_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(!isValid2) {
                    Toast.makeText(fragment.getActivity(), "只能打当天的卡！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (item.size() == 0) {
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再打卡！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!item.isPunch()) {
//                    ((ImageView)v).setImageResource(R.drawable.icon_plan_punch_active);
//                    item.setIsPunch(true);
                    Intent intent = new Intent(fragment.getActivity(), ChoosePhotoActivity.class);
                    intent.putExtra("item", item);
                    fragment.startActivity(intent);
                    //Toast.makeText(fragment.getActivity(), "打卡", Toast.LENGTH_SHORT).show();
                }else {
                    item.setIsPunch(false);
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_punch);
                    item.setImageCover(null);
                    PostRequest request = new PostRequest(FrServerConfig.getPunchDeleteUrl(item.getPunchId()), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            //Toast.makeText(fragment.getActivity(), "取消打卡成功!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            RequestErrorHelper.toast(fragment.getActivity(), volleyError);
                        }
                    });
                    FrRequest.getInstance().request(request);
                    FrDbHelper.getInstance(fragment.getActivity()).unpunch(item.getDate(), item.getType());
                    ((PlanFragment)fragment).deletePunch(item.getDate(), item.getType());
                    notifyDataSetChanged();
                }
//                adapter.notifyDataSetChanged();
            }

        });

        holder.plan_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.size() != 0)
                    ((PlanFragment) fragment).toggle(item.getType());
                else
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再查看营养表！", Toast.LENGTH_SHORT).show();
            }
        });

        if(!isValid2) {
            holder.plan_punch.setImageResource(R.drawable.icon_plan_punch_disable);
        }else {
            if (item.isPunch()) {
                holder.plan_punch.setImageResource(R.drawable.icon_plan_punch_active);
            } else {
                holder.plan_punch.setImageResource(R.drawable.icon_plan_punch);
            }
        }
        if(item.isInBasket()) {
            holder.plan_shopping.setImageResource(R.drawable.icon_plan_shopping_active);

        }else {
            holder.plan_shopping.setImageResource(R.drawable.icon_plan_shopping);
        }

        if(!isValid) {
            holder.add_recipe.setVisibility(View.GONE);

        }
        else {
            holder.add_recipe.setVisibility(View.VISIBLE);
            holder.add_recipe.setEnabled((!item.isInBasket()) && (!(item.isPunch())));
        }
        holder.add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (item.getType()) {
                    case "breakfast":
                        Intent intent = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent, PlanFragment.BREAKFAST_CODE);
                        break;
                    case "add_meal_01":
                        Intent intent1 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent1, PlanFragment.ADDMEAL_01_CODE);
                        break;
                    case "lunch":
                        Intent intent2 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent2, PlanFragment.LUNCH_CODE);
                        break;
                    case "add_meal_02":
                        Intent intent4 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent4, PlanFragment.ADDMEAL_02_CODE);
                        break;
                    case "supper":
                        Intent intent3 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent3, PlanFragment.SUPPER_CODE);
                        break;
                    case "add_meal_03":
                        Intent intent5 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent5, PlanFragment.ADDMEAL_03_CODE);
                        break;
                }
            }
        });

        switch (item.getType()) {
            case "breakfast" :
                holder.plan_title.setText("早餐");
                holder.plan_time.setText("08:30 AM");
                holder.calorie_plan_need.setText(Math.round(report.getBreakfastRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/  report.getBreakfastRate())+"");
                break;
            case "lunch":
                holder.plan_title.setText("午餐");
                holder.plan_time.setText("12:30 PM");
                holder.calorie_plan_need.setText(Math.round( report.getLunchRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/ report.getLunchRate())+"");
                break;
            case "supper":
                holder.plan_title.setText("晚餐");
                holder.plan_time.setText("18:30 PM");
                holder.calorie_plan_need.setText(Math.round(report.getDinnerRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/ report.getDinnerRate())+"");
                break;
            case "add_meal_01":
                holder.plan_title.setText("加餐");
                holder.plan_time.setText("10:30 AM");
                holder.calorie_plan_need.setText(Math.round(report.getSnackMorningRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/ report.getSnackMorningRate())+"");
                break;
            case "add_meal_02":
                holder.plan_title.setText("加餐");
                holder.plan_time.setText("15:30 PM");
                holder.calorie_plan_need.setText(Math.round(report.getSnackAfternoonRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/ report.getSnackAfternoonRate())+"");
                break;
            case "add_meal_03":
                holder.plan_title.setText("夜宵");
                holder.plan_time.setText("21:30 PM");
                holder.calorie_plan_need.setText(Math.round(report.getSnackNightRate())+"");
                holder.calorie_plan_radio.setText(Math.round(item.getCalories_take() * 100/ report.getSnackNightRate())+"");
                break;
        }

        holder.calorie_plan_intake.setText(Math.round(item.getCalories_take())+"");
        holder.plan_carbohydrate_intake.setText(Math.round(item.getCarbohydrate_take())+"");
        holder.plan_protein_intake.setText(Math.round(item.getProtein_take())+"");
        holder.plan_fat_intake.setText(Math.round(item.getFat_take()) + "");
        if(item.getImageCover() == null || item.getImageCover().equals("")) {
            FrApplication.getInstance().getMyImageLoader().displayImage(holder.plan_image_cover, item.getDefaultImageCover());
        }else
            FrApplication.getInstance().getMyImageLoader().displayImage(holder.plan_image_cover, item.getImageCover());

        //
        holder.plan_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanComponent component = item.getComponents().get(position);
                if (component.getType() == 1) {
                    String recipe_id = component.getId() + "";
                    Intent intent = new Intent(fragment.getActivity(), RecipeActivity.class);
                    intent.putExtra("id", recipe_id);
                    fragment.startActivity(intent);
                }else {
                    Intent intent = new Intent(fragment.getActivity(), IngredientNutritionActivity.class);
                    intent.putExtra("component", item.getComponents().get(position));
                    fragment.getActivity().startActivity(intent);
                }
            }
        });/*
        if(position == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 20, 0, 0);
            convertView.setLayoutParams(params);
        }*/
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    class ViewHolder {
        ImageView plan_shopping, plan_punch, plan_nutrition;
        TextView plan_title, plan_time;
        TextView calorie_plan_intake, calorie_plan_need, calorie_plan_radio;
        TextView plan_carbohydrate_intake, plan_carbohydrate_need, plan_carbohydrate_rate;
        TextView plan_protein_intake, plan_protein_need, plan_protein_rate;
        TextView plan_fat_intake, plan_fat_need, plan_fat_rate;
        RelativeLayout add_recipe;
        ImageView plan_image_cover;
        LinearLayoutForListView plan_content;
        LinearLayout first_margin_01, first_margin_02;

        public ViewHolder(View v) {
            plan_shopping = (ImageView) v.findViewById(R.id.plan_shopping);
            plan_punch = (ImageView) v.findViewById(R.id.plan_punch);
            plan_nutrition = (ImageView) v.findViewById(R.id.plan_nutrition);
            plan_title = (TextView) v.findViewById(R.id.plan_title);
            calorie_plan_intake = (TextView) v.findViewById(R.id.calorie_plan_intake);
            calorie_plan_need = (TextView) v.findViewById(R.id.calorie_plan_need);
            calorie_plan_radio = (TextView) v.findViewById(R.id.calorie_plan_radio);
            plan_carbohydrate_intake = (TextView) v.findViewById(R.id.plan_carbohydrate_intake);
            plan_carbohydrate_need = (TextView) v.findViewById(R.id.plan_carbohydrate_need);
            plan_carbohydrate_rate = (TextView) v.findViewById(R.id.plan_carbohydrate_rate);
            plan_protein_intake = (TextView) v.findViewById(R.id.plan_protein_intake);
            plan_protein_need = (TextView) v.findViewById(R.id.plan_protein_need);
            plan_protein_rate = (TextView) v.findViewById(R.id.plan_protein_rate);
            plan_fat_intake = (TextView) v.findViewById(R.id.plan_fat_intake);
            plan_fat_need = (TextView) v.findViewById(R.id.plan_fat_need);
            plan_fat_rate = (TextView) v.findViewById(R.id.plan_fat_rate);
            add_recipe = (RelativeLayout) v.findViewById(R.id.add_recipe);
            plan_content = (LinearLayoutForListView) v.findViewById(R.id.plan_content);
            plan_image_cover = (ImageView) v.findViewById(R.id.plan_image_cover);
            first_margin_01 = (LinearLayout) v.findViewById(R.id.first_margin_01);
            first_margin_02 = (LinearLayout) v.findViewById(R.id.first_margin_02);
            plan_time = (TextView) v.findViewById(R.id.plan_time);
        }
    }


    class ContentAdapter extends BaseSwipeAdapter {

        DatePlanItem item;

        public ContentAdapter(DatePlanItem item) {
            this.item = item;
        }

        @Override
        public int getSwipeLayoutResourceId(int i) {
            return R.id.swipe;
        }


        @Override
        public View generateView(int position, ViewGroup viewGroup) {
            View v = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.plan_select_recipe_item, null);
            final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }

            });
            return v;
        }

        @Override
        public void fillValues(final int i, final View view) {
            Log.v(i + "", view.toString());
            TextView text1 = (TextView) view.findViewById(R.id.textview1);
            TextView text2 = (TextView) view.findViewById(R.id.textview2);
            TextView text3 = (TextView) view.findViewById(R.id.textview3);
            SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(getSwipeLayoutResourceId(i));
            if(swipeLayout.getOpenStatus() == SwipeLayout.Status.Open)
                swipeLayout.close();
            swipeLayout.setSwipeEnabled((!item.isInBasket()) && isValid &&(!(item.isPunch())));
            PlanComponent component = item.getComponents().get(i);
            text1.setText(component.getName());
            text2.setText(component.getAmount()+"g");
            text3.setText(Math.round(component.getCalories() * component.getAmount() / 100) + "kcal");
            view.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    FrDbHelper.getInstance(fragment.getActivity()).deletePlanItem(item, i);
                    item.deleteContent(i);
                    try {
                        ((PlanFragment) fragment).update();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    notifyDataSetChanged();
                    //Toast.makeText(fragment.getActivity(), "click delete", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getCount() {
            if(item.getComponents() == null)    return 0;
            return  item.getComponents().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
