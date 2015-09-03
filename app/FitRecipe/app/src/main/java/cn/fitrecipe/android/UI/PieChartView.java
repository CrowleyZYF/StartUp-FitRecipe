package cn.fitrecipe.android.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 奕峰 on 2015/8/18.
 */
public class PieChartView extends ImageView{

    private float[] percents;

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
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int square = Math.min(width, height);
        float rx = width / 2.0f;
        float ry = height / 2.0f;
        drawPieChart(canvas, rx - square / 2.0f, ry - square / 2.0f, square / 2.0f);
    }

    public void setValue(float[] value) {
        this.percents = value;
        this.invalidate();
    }

    private void drawPieChart(Canvas canvas, float startX, float startY, float radius) {

        final float BIG_TEXT_SIZE = 60;
        final float SMALL_TEXT_SIZE = 30;

        int[] colors = new int[]{0xffbadc2d, 0xff4abdcc, 0xff9f9f9f};
        String[] kinds = new String[]{"碳水", "蛋白质", "脂类"};
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
            p.setStrokeWidth(5f);
            canvas.drawLine(rx, ry, x, y, p);
        }

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(SMALL_TEXT_SIZE);
        float w = textPaint.measureText("%");
        float h = textPaint.descent() - textPaint.ascent();

        for(int i = 1; i < start.length; i++) {
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
            textPaint.setTextSize(SMALL_TEXT_SIZE);
            canvas.drawText("%", x + ww, y, textPaint);
        }
    }
}
