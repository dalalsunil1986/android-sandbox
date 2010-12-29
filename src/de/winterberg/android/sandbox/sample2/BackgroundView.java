package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import static de.winterberg.android.sandbox.sample2.Constants.*;

/**
 * @author Benjamin Winterberg
 */
public class BackgroundView extends View {

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(10000, 10000);     // maximize
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);

        Rect bounds = canvas.getClipBounds();
        RectF rectF = new RectF(MARGIN, MARGIN, bounds.right - MARGIN, bounds.bottom - MARGIN);
        canvas.drawRoundRect(rectF, 15, 15, paint);
    }
}
