package cn.fitrecipe.android.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by wk on 2015/8/17.
 */
public class DietStructureView extends ImageView{

    private Report report = null;

    public DietStructureView(Context context) {
        super(context);
    }

    public DietStructureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DietStructureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValue(Report report) {
        this.report = report;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(report == null)
            return;
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        int square = Math.min(width, height);
        Bitmap output = Bitmap.createBitmap(square, square, Bitmap.Config.ARGB_8888);
        Canvas tmp = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(0xFFF9CD65);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        float radius_outter = Math.min(height, width) / 2.0f;
        float radius_inner = radius_outter / 2;
        float rx = width / 2.0f;
        float ry = height / 2.0f;
        RectF rectF_outter = new RectF(rx - radius_outter, ry - radius_outter, rx + radius_outter, ry + radius_outter);
        RectF rectF_inner = new RectF(rx - radius_inner, ry - radius_inner, rx + radius_inner, ry + radius_inner);

        float start = 270, sweep = 58, interval = 2;
        Bitmap icon;

        //1 水果
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;
//

        //2 奶
        paint.setColor(0xFFFB9128);
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;

        //3 肉类
        paint.setColor(0xFFE24856);
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;

        //4 油脂
        paint.setColor(0xFF065380);
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;

        //5 谷物
        paint.setColor(0xFF64CFD9);
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;

        //6 蔬菜
        paint.setColor(0xFF64D8BE);
        tmp.drawArc(rectF_outter, start, sweep, true, paint);
        start += sweep + interval;

        paint.setColor(Color.TRANSPARENT);
//        tmp.drawArc(rectF_inner, 0, 360, true, paint);
        float factor = (float) (radius_inner*Math.sqrt(3));
        Path path = new Path();
        float x1 = rx - factor / 2, y1 = ry - radius_inner / 2;
        path.moveTo(x1, y1);

        float x2 = rx, y2 = ry - radius_inner;
        path.lineTo(x2, y2);

        float x3 = rx + factor / 2, y3 = ry - radius_inner / 2;
        path.lineTo(x3, y3);

        float x4 = rx + factor / 2, y4 = ry + radius_inner / 2;
        path.lineTo(x4,  y4);

        float x5 = rx, y5 = ry + radius_inner;
        path.lineTo(x5, y5);

        float x6 = rx - factor / 2, y6 = ry + radius_inner / 2;
        path.lineTo(x6, y6);
        path.close();
        tmp.drawPath(path, paint);




        //奶
        float alignX = x3 + radius_outter/15, alignY = y3;
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(radius_outter / 8);
        String text = "奶";
        tmp.drawText(text, alignX, alignY + radius_inner / 4 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint);
        alignX += textPaint.measureText(text) + radius_outter / 15;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_milk);
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, y3, alignX + radius_outter / 8, y3 + radius_outter / 4), textPaint);
        alignY += radius_outter / 4 + radius_outter/15;
        textPaint.setTextSize(radius_outter / 12);
        tmp.drawText(Math.round(report.getDietStructureMilkMin()) +"~" + Math.round(report.getDietStructureMilkMax()) + "mL", x3 + radius_outter/15, alignY - textPaint.ascent(), textPaint);


        //肉
        alignX = x5 + radius_outter / 9;
        alignY = y5 - radius_outter / 30;
        textPaint.setTextSize(radius_outter / 8);
        text = "肉类";
        tmp.drawText(text, alignX, alignY - textPaint.ascent(), textPaint);
        alignX += textPaint.measureText(text) + radius_outter / 15;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_meat);
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, alignY, alignX + radius_outter / 6, alignY + radius_outter / 6), textPaint);
        alignY += radius_outter / 6 + radius_outter/15;
        textPaint.setTextSize(radius_outter / 12);
        tmp.drawText(Math.round(report.getDietStructureMeatMin()) + "~" + Math.round(report.getDietStructureMeatMax()) + "g", x5 + radius_outter / 9, alignY - textPaint.ascent(), textPaint);


        //油脂
        textPaint.setTextSize(radius_outter / 8);
        text = "油脂";
        String amount = Math.round(report.getDietStructureOilMin()) +"~" + Math.round(report.getDietStructureOilMax()) + "g";
        alignX = x6 - textPaint.measureText(text)/2;
        alignY = y6 + radius_outter / 9;
        tmp.drawText(text, alignX, alignY - textPaint.ascent(), textPaint);
        textPaint.setTextSize(radius_outter / 12);
        alignX = x6 - textPaint.measureText(amount)/2;
        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        tmp.drawText(amount, alignX, alignY - textPaint.ascent(), textPaint);

        alignX += textPaint.measureText(amount);
        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_oil);
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, alignY, alignX + radius_outter / 6, alignY + radius_outter / 6), textPaint);


        //谷物
        textPaint.setTextSize(radius_outter / 8);
        text = "谷物";
        amount = Math.round(report.getDietStructureGrainMin()) +"~" + Math.round(report.getDietStructureGrainMax()) + "g";
        alignX = x1 - textPaint.measureText(text) - radius_outter/6;
        alignY = y1;
        tmp.drawText(text, alignX, alignY - textPaint.ascent(), textPaint);
        textPaint.setTextSize(radius_outter / 12);
        alignX = x1 - textPaint.measureText(amount) - radius_outter/6;
        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        tmp.drawText(amount, alignX, alignY - textPaint.ascent(), textPaint);

        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_rice);
        alignX = x1 - radius_outter/3;
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, alignY, alignX + radius_outter / 6, alignY + radius_outter / 6), textPaint);

        //蔬菜
        alignX = x1 - radius_outter / 6 ;
        alignY = (float) (ry  - radius_outter * Math.sqrt(3) / 2) + radius_outter / 15;
        textPaint.setTextSize(radius_outter / 8);
        text = "蔬菜";
        tmp.drawText(text, alignX, alignY + radius_outter / 12 - (textPaint.descent() + textPaint.ascent())/2, textPaint);
        alignX += textPaint.measureText(text) + radius_outter / 16;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_vegetable);
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, alignY , alignX + radius_outter / 6, alignY + radius_outter / 6), textPaint);
        alignY += radius_outter / 6 + radius_outter / 15;
        textPaint.setTextSize(radius_outter / 12);
        tmp.drawText(Math.round(report.getDietStructureVegetableMin()) +"~" + Math.round(report.getDietStructureVegetableMax()) + "g", x1 - radius_outter / 6, alignY - textPaint.ascent(), textPaint);


        //水果
        textPaint.setTextSize(radius_outter / 8);
        text = "水果";
        amount = Math.round(report.getDietStructureFruitMin()) +"~" + Math.round(report.getDietStructureFruitMax()) + "g";
        alignX = rx + radius_outter / 15;
        alignY = ry - radius_outter + radius_outter / 12;
        tmp.drawText(text, alignX, alignY - textPaint.ascent(), textPaint);
        textPaint.setTextSize(radius_outter / 12);
        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        tmp.drawText(amount, alignX, alignY - textPaint.ascent(), textPaint);

        alignX += textPaint.measureText(amount);
        alignY += textPaint.descent() - textPaint.ascent() + radius_outter / 15;
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_fruit);
        tmp.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(alignX, alignY, alignX + radius_outter / 6, alignY + radius_outter / 6), textPaint);

        //推荐 膳食结构
        textPaint.setColor(0xFF888888);
        text = "推荐";
        textPaint.setTextSize(radius_outter / 8);
        tmp.drawText(text, x1 + (x3 - x1) / 2 - textPaint.measureText(text) / 2, y1 - textPaint.ascent() + radius_outter / 15, textPaint);
        text="膳食结构";
        tmp.drawText(text, x1 + (x3- x1)/2 - textPaint.measureText(text)/2, y4 - textPaint.descent() - radius_outter / 15, textPaint);
        canvas.drawBitmap(output, 0, 0, new Paint());
    }
}
