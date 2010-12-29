package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

import static de.winterberg.android.sandbox.sample2.Constants.*;

/**
 * @author Benjamin Winterberg
 */
public class BallView extends View {
    private static final String TAG = "Sample2";

//    private TranslateAnimation animation;
//
//    private PointF start;
//    private PointF end;

    private RectF bounds;

    private PointF position;

    private boolean initialized = false;

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initialize(Canvas canvas) {
        // calculate inner bounds
        float left = MARGIN + STROKE_WIDTH;
        float top = MARGIN + STROKE_WIDTH;
        float right = canvas.getWidth() - MARGIN - STROKE_WIDTH;
        float bottom = canvas.getHeight() - MARGIN - STROKE_WIDTH;
        bounds = new RectF(left, top, right, bottom);

        // setup starting position
        position = new PointF(canvas.getWidth() / 2f, bottom - CIRCLE_RADIUS);

        // ready for take off
        doAnimate(position, new PointF(canvas.getWidth() / 2f, top + CIRCLE_RADIUS));

        // done
        initialized = true;
    }

    private void doAnimate(PointF from, PointF to) {
        float toYDelta = to.y - from.y;
        float fromYDelta = 0;
        float toXDelta = to.x - from.x;
        float fromXDelta = 0;
        Log.d(TAG, "Starting animation: fromYDelta=" + fromYDelta + "; toYDelta=" + toYDelta + "; fromXDelta="
                + fromXDelta + "; toXDelta=" + toXDelta);
        TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(2500);
        animation.setFillAfter(true);
        startAnimation(animation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(10000, 10000);     // maximize
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!initialized)
            initialize(canvas);

        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);

        Path circle = new Path();
        circle.addCircle(position.x, position.y, CIRCLE_RADIUS, Path.Direction.CW);
        canvas.drawPath(circle, paint);
    }

}