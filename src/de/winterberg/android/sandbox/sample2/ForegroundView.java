package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.TranslateAnimation;

import static de.winterberg.android.sandbox.sample2.Constants.*;

/**
 * @author Benjamin Winterberg
 */
public class ForegroundView extends View {

    private TranslateAnimation animation;

    private PointF start;
    private PointF end;


    public ForegroundView(Context context) {
        super(context);
    }

    private void setUpAnimation(Canvas canvas) {
        if (start == null)
            start = new PointF(canvas.getWidth() / 2f, canvas.getHeight() - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS);

        if (end == null)
            end = new PointF(canvas.getWidth() / 2f, MARGIN + STROKE_WIDTH + CIRCLE_RADIUS);

        animation = new TranslateAnimation(0, 0, 0, -250);
        animation.setDuration(5000);
        setAnimation(animation);
        startAnimation(animation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (animation == null)
            setUpAnimation(canvas);

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

}