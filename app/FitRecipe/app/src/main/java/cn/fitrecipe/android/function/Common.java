package cn.fitrecipe.android.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.fitrecipe.android.R;
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



    public static void toastNetworkError(Context context) {
        String error_info = context.getResources().getString(R.string.network_error);
        if(!isOpenNetwork(context))
            error_info = context.getResources().getString(R.string.network_close);
        Toast.makeText(context, error_info, Toast.LENGTH_SHORT).show();
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

    public static int CompareDate(String a, String b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(a);
            Date date2 = sdf.parse(b);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
