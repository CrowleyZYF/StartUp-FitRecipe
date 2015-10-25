package cn.fitrecipe.android.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 奕峰 on 2015/8/18.
 */
public class PieChartView extends View {

    private float[] percents;
    private boolean isHasText = true;
    private boolean isDinner = false;
    private boolean isHuge = false;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(percents == null || percents.length == 0)
            return;
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int square = Math.min(width, height);
        float rx = width / 2.0f;
        float ry = height / 2.0f;
        drawPieChart(canvas, rx - square / 2.0f, ry - square / 2.0f, square / 2.0f);
    }

    /**
     *碳水 ，蛋白质， 脂类
     * @param value
     * @param isHasText
     * @param isDinner
     * @param isHuge
     */
    public void setValue(float[] value, boolean isHasText, boolean isDinner, boolean isHuge) {
        this.percents = value;
        this.isHasText = isHasText;
        this.isDinner = isDinner;
        this.isHuge = isHuge;
        this.invalidate();
    }


    private void drawPieChart(Canvas canvas, float startX, float startY, float radius) {


//        TextPaint p = new TextPaint();
//        p.setColor(Color.BLACK);
//        p.setTextSize(50);
//        canvas.drawText("abc", 100, 100, p);

        final float BIG_TEXT_SIZE = 50;
        final float SMALL_TEXT_SIZE = 20;
        final float HUGE_BIG_TEXT_SIZE = 110;
        final float HUGE_SMALL_TEXT_SIZE = 50;
        int[] colors;
        String[] kinds;
        if(isDinner){
            colors = new int[]{0xffF6464A, 0xff46BEBC, 0xffFBB35C};
            kinds = new String[]{"早餐", "午餐", "晚餐"};
        }else{
            colors = new int[]{0xffbadc2d, 0xff4abdcc, 0xff9f9f9f};
            kinds = new String[]{"碳水", "蛋白质", "脂类"};
        }
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(startX, startY, startX + 2 * radius, startY + 2 * radius);
        float[] start = new float[percents.length+1];
        start[0] = 0;
        for(int i = 1; i <= percents.length; i++) {
            p.setColor(colors[i - 1]);
            canvas.drawArc(rectF, start[i-1], percents[i - 1] * 360 / 100, true, p);
            start[i] = start[i-1] + percents[i - 1] * 360 / 100;
        }
        float rx = startX + radius;
        float ry = startY + radius;

        for(int i = 0; i < start.length - 1; i++) {
            float x = (float) (rx + radius * Math.cos(start[i] * 2f * Math.PI / 360));
            float y = (float) (ry + radius * Math.sin(start[i] * 2f * Math.PI / 360));
            p.setColor(Color.WHITE);
            p.setStyle(Paint.Style.STROKE);
            if (this.isHasText){
                p.setStrokeWidth(5f);
            }else{
                p.setStrokeWidth(2f);
            }
            canvas.drawLine(rx, ry, x, y, p);
        }

        if (this.isHasText){
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.WHITE);
            if (isHuge)
                textPaint.setTextSize(HUGE_SMALL_TEXT_SIZE);
            else
                textPaint.setTextSize(SMALL_TEXT_SIZE);
            float w = textPaint.measureText("%");
            float h = textPaint.descent() - textPaint.ascent();

            for(int i = 1; i < start.length; i++) {
                if (isHuge)
                    textPaint.setTextSize(HUGE_BIG_TEXT_SIZE);
                else
                    textPaint.setTextSize(BIG_TEXT_SIZE);
                String data =  String.valueOf((int)percents[i - 1]);
                float angle = (start[i] - start[i - 1]) / 2 + start[i-1];
                float ww = textPaint.measureText(data);
                float R = radius;
                if(Float.valueOf(data) < 10)
                    R = 1.5f * radius;
                float x = (float) (rx + (R * Math.cos(angle * 2 * Math.PI / 360) / 2) - ww / 2);
                float y = (float) (ry + (R * Math.sin(angle * 2 * Math.PI / 360) / 2) - (textPaint.descent() + textPaint.ascent())/2);


                canvas.drawText(data, x, y, textPaint);
                if (isHuge)
                    textPaint.setTextSize(HUGE_SMALL_TEXT_SIZE);
                else
                    textPaint.setTextSize(SMALL_TEXT_SIZE);
                canvas.drawText("%", x + ww, y, textPaint);
            }
        }
    }
}
