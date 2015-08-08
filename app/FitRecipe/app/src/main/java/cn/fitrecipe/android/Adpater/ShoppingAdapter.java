package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/8/5.
 */
public class ShoppingAdapter extends SimpleAdapter {

    private LayoutInflater inflater;
    private List<Map<String, Object>> listItems;
    private String[] from;
    private int[] to;
    private Context context;

    public ShoppingAdapter(Context context, List<Map<String, Object>> data,
                       int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
        this.listItems=data;
        this.from=from;
        this.to=to;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View localView = convertView;
        int temp=0;
        if(convertView==null){
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_shopping_food_item, null);
            localView = super.getView(position, convertView, parent);
        }else{
            localView = super.getView(position, convertView, parent);
        }
        String itemType=((TextView)localView.findViewById(R.id.ingredient_type)).getText().toString();
        if(itemType.equals("0")){
            (localView.findViewById(R.id.ingredient_margin)).setVisibility(View.GONE);
            (localView.findViewById(R.id.ingredient_icon)).setVisibility(View.GONE);
            (localView.findViewById(R.id.ingredient_weight)).setVisibility(View.VISIBLE);
        }else{
            (localView.findViewById(R.id.ingredient_margin)).setVisibility(View.VISIBLE);
            (localView.findViewById(R.id.ingredient_icon)).setVisibility(View.VISIBLE);
            (localView.findViewById(R.id.ingredient_weight)).setVisibility(View.GONE);
        }
        if(position==0){
            (localView.findViewById(R.id.ingredient_margin)).setVisibility(View.GONE);
        }
        String itemStatus=listItems.get(position).get("ingredient_status").toString();
        if(itemStatus.equals("0")){
            (localView.findViewById(R.id.ingredient_line)).setVisibility(View.GONE);
            ((TextView)localView.findViewById(R.id.ingredient_name)).setTextColor(this.context.getResources().getColor(R.color.login_input_text_color));
            ((TextView)localView.findViewById(R.id.ingredient_weight)).setTextColor(this.context.getResources().getColor(R.color.login_input_text_color));
        }else{
            (localView.findViewById(R.id.ingredient_line)).setVisibility(View.VISIBLE);
            ((TextView)localView.findViewById(R.id.ingredient_name)).setTextColor(this.context.getResources().getColor(R.color.gray));
            ((TextView)localView.findViewById(R.id.ingredient_weight)).setTextColor(this.context.getResources().getColor(R.color.gray));
        }

        /*
        String[] time=tempString.split(" ");
        String stateString=((TextView)convertView.findViewById(R.id.item_state)).getText().toString();
        if(Common.parseDay(time[1])<=Common.parseDay(Common.getTime())&&stateString.equals("0")){
            ((ImageView)convertView.findViewById(R.id.circle)).setImageResource(R.drawable.red_circle);
        }else{
            ((ImageView)convertView.findViewById(R.id.circle)).setImageResource(R.drawable.white_circle);
        }
        String nameString = ((TextView)convertView.findViewById(R.id.item_name)).getText().toString();
        String[] strarray=nameString.split("-");
        boolean findColor=false;
        for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length-1&&findColor==false;i++){
            if((strarray[0]).equals(this.context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
                ((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.COLOR[i]));
                temp=i;
                findColor=true;
                if (stateString.equals("1")) {
                    ((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
                }
            }
        }
        if (!findColor) {
            ((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.COLOR[DBOpenHelper.PLATFORM_NAMES.length-1]));
            findColor=true;
            if (stateString.equals("1")) {
                ((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            }
            temp=DBOpenHelper.PLATFORM_ICONS.length-1;
        }
        if(stateString.equals("1")){
            ((TextView)convertView.findViewById(R.id.timeBegin)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            ((TextView)convertView.findViewById(R.id.timeEnd)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            ((TextView)convertView.findViewById(R.id.money_icon)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            ((TextView)convertView.findViewById(R.id.item_money)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            ((TextView)convertView.findViewById(R.id.item_profit)).setTextColor(this.context.getResources().getColor(DBOpenHelper.gray));
            ((ImageView)convertView.findViewById(R.id.item_icon)).setImageResource(DBOpenHelper.PLATFORM_ICONS_GRAY[temp]);
        }else{
            ((TextView)convertView.findViewById(R.id.timeBegin)).setTextColor(this.context.getResources().getColor(R.color.light_black));
            ((TextView)convertView.findViewById(R.id.timeEnd)).setTextColor(this.context.getResources().getColor(R.color.light_black));
            ((TextView)convertView.findViewById(R.id.money_icon)).setTextColor(this.context.getResources().getColor(R.color.light_black));
            ((TextView)convertView.findViewById(R.id.item_money)).setTextColor(this.context.getResources().getColor(R.color.light_black));
            ((TextView)convertView.findViewById(R.id.item_profit)).setTextColor(this.context.getResources().getColor(R.color.light_black));
            ((ImageView)convertView.findViewById(R.id.item_icon)).setImageResource(DBOpenHelper.PLATFORM_ICONS[temp]);
        }*/
        return localView;
    }

}

