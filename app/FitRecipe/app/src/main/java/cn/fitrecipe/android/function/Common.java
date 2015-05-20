package cn.fitrecipe.android.function;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

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

    public static boolean isOpenNetwork(Activity activity) {
        ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}