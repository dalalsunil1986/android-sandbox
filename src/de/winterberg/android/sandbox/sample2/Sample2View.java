package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample2View extends View {
    private static final int BLUE = Color.rgb(22, 245, 156);
    private static final int GREEN = Color.rgb(22, 167, 245);

    private static final float MARGIN = 5;
    private static final float STROKE_WIDTH = 5;
    private static final float CIRCLE_RADIUS = 30;

    public Sample2View(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);

        float x = canvas.getWidth() / 2f;
        float y = canvas.getHeight() - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS;

        Path circle = new Path();
        circle.addCircle(x, y, CIRCLE_RADIUS, Path.Direction.CW);
        canvas.drawPath(circle, paint);
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
