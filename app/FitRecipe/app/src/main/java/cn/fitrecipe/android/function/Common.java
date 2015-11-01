package cn.fitrecipe.android.function;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 奕峰 on 2015/5/2.
 */
public class Common {
    public static SweetAlertDialog toBeContinuedDialog(Context context){
        return new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("功能还在开发中")
                .setContentText("敬请期待  ∩_∩")
                .setConfirmText("确定");
    }

    public static SweetAlertDialog infoDialog(Context context, String title, String content){
        return new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确定");
    }

    public static SweetAlertDialog errorDialog(Context context, String title, String content){
        return new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确定");
    }

    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public static void setPunchCount(Context context, int count){
        SharedPreferences sp = context.getSharedPreferences("user", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("punch_count", count);
        editor.commit();
    }

    public static int getPunchCount(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", context.MODE_PRIVATE);
        return sp.getInt("punch_count", 0);
    }


    public static String getSomeDay(String str, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            long after = date.getTime() + days * 24 * 3600 * 1000;
            Date afterDate = new Date(after);
            return sdf.format(afterDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long now = System.currentTimeMillis();
        Date afterDate = new Date(now);
        return sdf.format(afterDate);
    }

    public static String getAddDate(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        long now = System.currentTimeMillis() + days * 24 * 3600 * 1000;
        Date afterDate = new Date(now);
        return sdf.format(afterDate);
    }

    /**
     * translate 2015-09-26 to 20150926
     * @param date
     * @return
     */
    public static String dateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Date date1;
        try {
            date1 = sdf.parse(date);
            return sdf1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * translate 20150926 to 2015-09-26
     * @param date
     * @return String
     */
    public static String dateFormatReverse(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        try {
            date1 = sdf.parse(date);
            return sdf1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHmmss");
        long now = System.currentTimeMillis();
        Date afterDate = new Date(now);
        return sdf.format(afterDate);
    }

    /**
     * 比较两个时间的前后关系
     * @param date_1
     * @param date_2
     * @return 如果date_1小于date_2，返回-1；相同，返回0；比data_2大，返回1；
     */
    public static int CompareDate(String date_1, String date_2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date_1);
            Date date2 = sdf.parse(date_2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDiff(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try {
            date1 = sdf.parse(str1);
            date2 = sdf.parse(str2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (int) ((date1.getTime() - date2.getTime()) / (24 * 3600 *1000));
    }

    /**
     *  generate a custom dateplan
     * @return list of dateplanitem
     */
    public static ArrayList<DatePlanItem> generateDatePlan(String date, Context context) {
        ArrayList<DatePlanItem> items = new ArrayList<>();
        DatePlanItem item1 = new DatePlanItem();
        item1.setTime("07:30am");
        item1.setType("breakfast");
        item1.setDate(date);
        item1.setDefaultImageCover("drawable://" + R.drawable.breakfast);
        items.add(item1);

        DatePlanItem item2 = new DatePlanItem();
        item2.setTime("10:00am");
        item2.setType("add_meal_01");
        item2.setDate(date);
        item2.setDefaultImageCover("drawable://" + R.drawable.add_meal_01);
        items.add(item2);

        DatePlanItem item3 = new DatePlanItem();
        item3.setTime("12:00am");
        item3.setType("lunch");
        item3.setDate(date);
        item3.setDefaultImageCover("drawable://" + R.drawable.lunch);
        items.add(item3);


        DatePlanItem item4 = new DatePlanItem();
        item4.setTime("15:00am");
        item4.setType("add_meal_02");
        item4.setDate(date);
        item4.setDefaultImageCover("drawable://" + R.drawable.add_meal_02);
        items.add(item4);


        DatePlanItem item5 = new DatePlanItem();
        item5.setTime("17:30am");
        item5.setType("supper");
        item5.setDate(date);
        item5.setDefaultImageCover("drawable://" + R.drawable.dinner);
        items.add(item5);


        DatePlanItem item6 = new DatePlanItem();
        item6.setTime("22:00am");
        item6.setType("add_meal_03");
        item6.setDate(date);
        item6.setDefaultImageCover("drawable://" + R.drawable.add_meal_03);
        items.add(item6);

        return items;
    }


    public static SeriesPlan gerneratePersonalPlan(int id, Context context) {
        SeriesPlan plan = new SeriesPlan();
        plan.setId(id);
        plan.setTitle("personal plan");
        plan.setTotal_days(1);
        ArrayList<DatePlan> datePlans = new ArrayList<>();
        datePlans.add(gernerateEmptyPlan(Common.getDate(), context));
        plan.setDatePlans(datePlans);
        return plan;
    }

    public static DatePlan gernerateEmptyPlan(String date, Context context) {
        DatePlan datePlan = new DatePlan();
        datePlan.setPlan_name("personal plan");
        datePlan.setDate(date);
        datePlan.setPlan_id(-1);
        datePlan.setItems(Common.generateDatePlan(date, context));
        return datePlan;
    }

}
