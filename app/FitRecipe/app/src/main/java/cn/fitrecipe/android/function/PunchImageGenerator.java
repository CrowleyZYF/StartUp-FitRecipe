package cn.fitrecipe.android.function;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.widget.ScrollView;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.SquareLayout;

/**
 * Created by wk on 2015/9/23.
 */
public class PunchImageGenerator {

    public static Bitmap convertViewToPunchShareOne(SquareLayout view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap convertViewToBitmap(ScrollView view){
        int h = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            h += view.getChildAt(i).getHeight();}
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), h + 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, h, canvas.getWidth(), canvas.getHeight()), paint);


        int x1 = 20;
        int y1 = h;
        int bit_len = 150;
        int bit_len2 = 100;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(x1, h, view.getWidth() - x1, h, paint);

        y1 += 20;
        Bitmap bitmap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.code);
        canvas.drawBitmap(bitmap1, new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight()), new RectF(x1, y1, x1 + bit_len, y1 + bit_len), paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.BLACK);

        int x2 = bit_len + x1 + 20;
        String text1 = "扫码或者App Store";
        float y2 = y1 + bit_len/2 - textPaint.descent() - 5;
        canvas.drawText(text1, x2, y2, textPaint);
        String text2 = "搜索[健食记]下载App";
        float y21= y1 + bit_len/2 - textPaint.ascent() + 5;
        canvas.drawText(text2, x2, y21, textPaint);

        int x3 = view.getWidth() - 20 - bit_len2;
        int y3 = y1 + 20;
        Bitmap bitmap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.logo);
        canvas.drawBitmap(bitmap2, new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), new RectF(x3, y3, x3 + bit_len2, y3 + bit_len2), paint);
        return bitmap;
    }
}
