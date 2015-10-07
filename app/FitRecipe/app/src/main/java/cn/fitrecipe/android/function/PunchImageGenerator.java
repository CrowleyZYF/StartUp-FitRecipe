package cn.fitrecipe.android.function;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ScrollView;

/**
 * Created by wk on 2015/9/23.
 */
public class PunchImageGenerator {

    public static Bitmap convertViewToBitmap(ScrollView view){
        int h = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            h += view.getChildAt(i).getHeight();}
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), h, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
}
