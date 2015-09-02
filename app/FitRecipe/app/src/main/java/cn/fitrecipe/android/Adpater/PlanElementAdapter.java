package cn.fitrecipe.android.Adpater;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Comment;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.fragment.PlanFragment;

/**
 * Created by wk on 2015/8/26.
 */
public class PlanElementAdapter extends BaseAdapter{

    Fragment fragment;
    List<PlanItem> items;
    Report report;

    public PlanElementAdapter(Fragment fragment, List<PlanItem> items, Report report) {
        this.fragment = fragment;
        this.items = items;
        this.report = report;
    }

    public void setData(List<PlanItem> items) {
        this.items = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(fragment.getActivity(), R.layout.plan_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        final PlanItem item = items.get(position);
        //
        holder.plan_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.size() == 0) {
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再加入菜篮子！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!item.isInBasket()) {
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_shopping_active);
                    item.setInBasket(true);
                    FrDbHelper.getInstance(fragment.getActivity()).addPlanItem(item);
                    List<Component> components = new ArrayList<Component>();
                    for(int i = 0; i < item.getData().size(); i++) {
                        Object obj = item.getData().get(i);
                        if(obj instanceof Recipe) {
                            FrDbHelper.getInstance(fragment.getActivity()).addToBasket((Recipe) obj);
                        }else if(obj instanceof Component) {
                            Component component = (Component) obj;
                            component.setPlanItem(null);
                            components.add(component);
                        }
                    }
                    if(components.size() > 0) {
                        Recipe recipe = new Recipe();
                        recipe.setId(-1);
                        recipe.setComponent_set(components);
                        FrDbHelper.getInstance(fragment.getActivity()).addToBasket(recipe);
                    }
                    Toast.makeText(fragment.getActivity(), "加入菜篮子", Toast.LENGTH_SHORT).show();
                }else {
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_shopping);
                    item.setInBasket(false);
                    FrDbHelper.getInstance(fragment.getActivity()).addPlanItem(item);
                    List<Component> components = new ArrayList<Component>();
                    for(int i = 0; i < item.getData().size(); i++) {
                        Object obj = item.getData().get(i);
                        if(obj instanceof Recipe) {
                            FrDbHelper.getInstance(fragment.getActivity()).removeFromBasket((Recipe) obj);
                        }else if(obj instanceof Component) {
                            Component component = (Component) obj;
                            component.setPlanItem(null);
                            components.add(component);
                        }
                    }
                    if(components.size() > 0) {
                        Recipe recipe = new Recipe();
                        recipe.setId(-1);
                        recipe.setComponent_set(components);
                        FrDbHelper.getInstance(fragment.getActivity()).removeFromBasket(recipe);
                    }
                    Toast.makeText(fragment.getActivity(), "从菜篮子取出", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.plan_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO @wk
                if (item.size() == 0) {
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再打卡！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!item.isPunched()) {
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_punch_active);
                    item.setIsPunched(true);
                    item.getDayPlan().setIsPunched(true);
                    FrDbHelper.getInstance(fragment.getActivity()).punch(item);
                    Toast.makeText(fragment.getActivity(), "打卡", Toast.LENGTH_SHORT).show();
                }else {
                    item.setIsPunched(false);
                    ((ImageView)v).setImageResource(R.drawable.icon_plan_punch);
                    DayPlan dayPlan = item.getDayPlan();
                    List<PlanItem> planItems = dayPlan.getPlanItems();
                    boolean flag = false;
                    for(int i = 0; i < planItems.size(); i++) {
                        flag = flag || planItems.get(i).isPunched();
                    }
                    dayPlan.setIsPunched(flag);
                    FrDbHelper.getInstance(fragment.getActivity()).punch(item);
                    Toast.makeText(fragment.getActivity(), "取消打卡", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.plan_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.size() != 0)
                    ((PlanFragment) fragment).toggle(item.getItemType());
                else
                    Toast.makeText(fragment.getActivity(), "请添加食谱、食材后再查看营养表！", Toast.LENGTH_SHORT).show();
            }
        });

        if(item.isPunched()) {
            holder.plan_punch.setImageResource(R.drawable.icon_plan_punch_active);
        }else {
            holder.plan_punch.setImageResource(R.drawable.icon_plan_punch);
        }

        if(item.isInBasket()) {
            holder.plan_shopping.setImageResource(R.drawable.icon_plan_shopping_active);
        }else {
            holder.plan_shopping.setImageResource(R.drawable.icon_plan_shopping);
        }

        holder.add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (item.getItemType()) {
                    case BREAKFAST:
                        Intent intent = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent, PlanFragment.BREAKFAST_CODE);
                        break;
                    case ADDMEAL_01:
                        Intent intent1 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent1, PlanFragment.ADDMEAL_01_CODE);
                        break;
                    case LUNCH:
                        Intent intent2 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent2, PlanFragment.LUNCH_CODE);
                        break;
                    case ADDMEAL_02:
                        Intent intent4 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent4, PlanFragment.ADDMEAL_02_CODE);
                        break;
                    case SUPPER:
                        Intent intent3 = new Intent(fragment.getActivity(), SelectRecipeActivity.class);
                        fragment.startActivityForResult(intent3, PlanFragment.SUPPER_CODE);
                        break;
                }
            }
        });

        switch (item.getItemType()) {
            case BREAKFAST:
                holder.plan_title.setText("早餐");    break;
            case LUNCH:
                holder.plan_title.setText("午餐");    break;
            case SUPPER:
                holder.plan_title.setText("晚餐");    break;
            case ADDMEAL_01:
            case ADDMEAL_02:
                holder.plan_title.setText("加餐");    break;
        }

        holder.calorie_plan_intake.setText(Math.round(item.gettCalories())+"");
        holder.plan_carbohydrate_intake.setText(Math.round(item.getCarbohydrate())+"");
        holder.plan_protein_intake.setText(Math.round(item.getProtein())+"");
        holder.plan_fat_intake.setText(Math.round(item.getFat())+"");
        FrApplication.getInstance().getMyImageLoader().displayImage(holder.plan_image_cover, item.getImageCover());

        //
        holder.plan_content.setAdapter(new ContentAdapter(item));
        holder.plan_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = item.getData().get(position);
                if(obj instanceof Recipe) {
                    String recipe_id = ((Recipe)obj).getId() +"";
                    Intent intent=new Intent(fragment.getActivity(), RecipeActivity.class);
                    intent.putExtra("id", recipe_id);
                    fragment.startActivity(intent);
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView plan_shopping, plan_punch, plan_nutrition;
        TextView plan_title;
        TextView calorie_plan_intake, calorie_plan_need, calorie_plan_radio;
        TextView plan_carbohydrate_intake, plan_carbohydrate_need, plan_carbohydrate_rate;
        TextView plan_protein_intake, plan_protein_need, plan_protein_rate;
        TextView plan_fat_intake, plan_fat_need, plan_fat_rate;
        RelativeLayout add_recipe, plan_image_cover;
        LinearLayoutForListView plan_content;

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
            plan_image_cover = (RelativeLayout) v.findViewById(R.id.plan_image_cover);
        }
    }


    class ContentAdapter extends BaseSwipeAdapter {

        PlanItem item;

        public ContentAdapter(PlanItem item) {
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
            Object obj = item.getData().get(i);
            if(obj instanceof Recipe) {
                Recipe recipe = (Recipe) obj;
                text1.setText(recipe.getTitle());
                text2.setText(recipe.getIncreWeight() + "g");
                text3.setText(Math.round(recipe.getCalories() * recipe.getIncreWeight() / 100) + "kcal");
            }else{
                Component component = (Component)obj;
                text1.setText(component.getIngredient().getName());
                text2.setText(component.getMAmount() + "g");
                text3.setText(100 + "kcal");
            }
            view.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FrDbHelper.getInstance(fragment.getActivity()).deletePlanItem(item, i);
                    item.deleteContent(i);
                    notifyDataSetChanged();
                    Toast.makeText(fragment.getActivity(), "click delete", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getCount() {
            return  item.size();
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
