package cn.fitrecipe.android.Adpater;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.PlanTestActivity;
import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class TestViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private PlanTestActivity context;
    private List<Map<String, Object>> dataList;
    private List<View> questionLinearLayout = new ArrayList<View>();
    private int position;
    private int data;
    private int width;
    private int height;

    public TestViewPagerAdapter(PlanTestActivity context, List<Map<String, Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.position = 0;
        this.data = 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View questionContainer = null;
        switch (position) {
            case 0:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_00, null);
                break;
            case 1:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_01, null);
                RadioButton test_01_man = (RadioButton) questionContainer.findViewById(R.id.test_01_man);
                RadioButton test_01_woman = (RadioButton) questionContainer.findViewById(R.id.test_01_woman);
                RadioButton radios[] = {test_01_man, test_01_woman};
                this.position=position;
                if(Integer.parseInt(dataList.get(position).get("data").toString())==-1){
                    test_01_man.setChecked(false);
                    test_01_woman.setChecked(false);
                }else{
                    resetChoice(radios[Integer.parseInt(dataList.get(position).get("data").toString())]);
                }
                test_01_man.setOnClickListener(choiceHandler);
                test_01_woman.setOnClickListener(choiceHandler);
                break;
            case 2:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_02, null);
                TextView prev = (TextView) questionContainer.findViewById(R.id.plan_test_last_question);
                prev.setOnClickListener(this);
                break;
            case 3:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_03, null);
                break;
            case 4:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_04, null);
                break;
            case 5:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_05, null);
                break;
            case 6:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_06, null);
                break;
            case 7:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_07, null);
                break;
            case 8:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_08, null);
                break;
            case 9:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_09, null);
                break;
        }
        /*
        View recommendContainer = LayoutInflater.from(context).inflate(R.layout.fragment_index_recommend_item, null);
        //背景图片
        ImageView imageView = (ImageView) recommendContainer.findViewById(R.id.recommend_image);
//        imageView.setImageResource();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrApplication.getInstance().getMyImageLoader().displayImage(imageView, (String) dataList.get(position).get("imgUrl"));

        //ID
        TextView idTextView = (TextView) recommendContainer.findViewById(R.id.recommend_id);
        idTextView.setText(dataList.get(position).get("id").toString());
        //Type
        TextView typeTextView = (TextView) recommendContainer.findViewById(R.id.recommend_type);
        typeTextView.setText(dataList.get(position).get("type").toString());
        //添加点击事件
        recommendContainer.setOnClickListener(this);
        */
        container.addView(questionContainer);
        questionLinearLayout.add(questionContainer);
        return questionContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(questionLinearLayout.get(position));
    }


    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plan_test_last_question:
                context.goPrev();
                break;
            default:
                break;
        }

    }

    public void resetChoice(View v){
        View parentView = v.getRootView();
        LinearLayout radioGroup = (LinearLayout) parentView.findViewById(R.id.radioGroup);
        for(int i=0;i<radioGroup.getChildCount();i++){
            RadioButton resetBtn = (RadioButton) radioGroup.getChildAt(i);
            resetBtn.setChecked(false);
            i++;
        }
        ((RadioButton) v).setChecked(true);
    }

    View.OnClickListener choiceHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentView = v.getRootView();
            LinearLayout radioGroup = (LinearLayout) parentView.findViewById(R.id.radioGroup);
            for(int i=0;i<radioGroup.getChildCount();i++){
                RadioButton resetBtn = (RadioButton) radioGroup.getChildAt(i);
                resetBtn.setChecked(false);
                i++;
                if(v == resetBtn) {
                    resetBtn.setChecked(true);
                    dataList.get(position).put("data",i/2);
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.goNext();
                }
            }, 600);

        }
    };

}
