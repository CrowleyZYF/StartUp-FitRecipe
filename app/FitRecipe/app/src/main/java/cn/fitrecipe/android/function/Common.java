package cn.fitrecipe.android.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

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
}
