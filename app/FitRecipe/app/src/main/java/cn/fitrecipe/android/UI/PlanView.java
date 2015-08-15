package cn.fitrecipe.android.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.R;

/**
 * Created by wk on 2015/8/14.
 */
public class PlanView extends LinearLayout implements View.OnClickListener{


    ImageButton plan_add;
    TextView plan_add_text;
    ListView listView;
    List<Entity> dataList;
    ListAdapter listAdapter;
    Context context;
    List<String> names;

    public PlanView(Context context) {
        this(context, null);
    }

    public PlanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.custom_plan_view, null);

        plan_add = (ImageButton) view.findViewById(R.id.plan_add);
        plan_add.setOnClickListener(this);

        plan_add_text = (TextView) view.findViewById(R.id.plan_add_text);

        listView = (ListView) view.findViewById(R.id.listView);
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

        names = new ArrayList<>();
        names.add("西红柿炒牛腩");
        names.add("面包");
        names.add("牛奶");

        addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_add:
                if(plan_add_text.getVisibility() == View.VISIBLE) {
                    plan_add_text.setVisibility(View.INVISIBLE);
                    plan_add.setImageResource(R.drawable.plan_add_complete);
                    if(dataList == null)
                        dataList = new ArrayList<Entity>();
                    Entity entity = new Entity();
                    entity.type = 1;
                    dataList.add(entity);
                    listAdapter.notifyDataSetChanged();
                }else {
                    plan_add_text.setVisibility(View.VISIBLE);
                    plan_add.setImageResource(R.drawable.plan_add);
                    Entity entity = new Entity();
                    entity.type = 0;
                    View view = listView.getChildAt(dataList.size()-1);
                    ViewHolder holder = (ViewHolder) view.getTag();
                    entity.name = holder.autoCompleteTextView.getText().toString();
                    entity.amount = Integer.parseInt(holder.editText.getText().toString());
                    dataList.remove(dataList.size() - 1);
                    dataList.add(entity);
                    listAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(dataList == null)
                return 0;
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return dataList.get(position).type;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            ViewHolder holder;
            if(convertView != null)
                holder = (ViewHolder) convertView.getTag();
            else  {
                if(type == 0) {
                    convertView = View.inflate(context, R.layout.plan_list_item1, null);
                    holder = new ViewHolder();
                    holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                    holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                    convertView.setTag(holder);
                }else {
                    convertView = View.inflate(context, R.layout.plan_list_item2, null);
                    holder = new ViewHolder();
                    holder.autoCompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id.autoCompleteTextView);
                    holder.autoCompleteTextView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, names));
                    holder.autoCompleteTextView.setThreshold(1);
                    holder.editText = (EditText) convertView.findViewById(R.id.edittext);
                    convertView.setTag(holder);
                }
            }
            if(type == 0) {
                holder.textView1.setText(dataList.get(position).name);
                holder.textView2.setText(dataList.get(position).amount+"g");
            }else {
                holder.autoCompleteTextView.setText("");
                holder.editText.setText("");
            }
            return convertView;
        }
    }


    class ViewHolder {
        TextView textView1;
        TextView textView2;

        AutoCompleteTextView autoCompleteTextView;
        EditText editText;
    }

    class Entity {
        String name;
        int amount;
        int type;
    }

}
