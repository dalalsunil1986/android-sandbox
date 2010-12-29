package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import de.winterberg.android.sandbox.util.AnimationListenerAdapter;

import java.util.Random;

import static de.winterberg.android.sandbox.sample2.Constants.*;

/**
 * @author Benjamin Winterberg
 */
public class BallView extends View {
    private static final String TAG = "Sample2";

    private static final Random RANDOM = new Random();

    private boolean initialized = false;

    private RectF bounds;

    private PointF position;

    private PointF nextPosition;


    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initialize(Canvas canvas) {
        // calculate inner bounds
        float left = MARGIN + STROKE_WIDTH + CIRCLE_RADIUS;
        float top = MARGIN + STROKE_WIDTH + CIRCLE_RADIUS;
        float right = canvas.getWidth() - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS;
        float bottom = canvas.getHeight() - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS;
        bounds = new RectF(left, top, right, bottom);

        // setup starting position
        doAnimate(canvas);

        // done
        initialized = true;
    }

    private void doAnimate(Canvas canvas) {
        position = new PointF(canvas.getWidth() / 2f, bounds.bottom);
        nextPosition = new PointF(canvas.getWidth() / 2f, bounds.top);
        doAnimate(position, nextPosition);
    }

//    private PointF position(float x, float y) {
//        float xx = x;
//        if (x == bounds.left)
//            xx += CIRCLE_RADIUS;
//        if (x == bounds.right)
//            xx -= CIRCLE_RADIUS;
//
//        float yy = y;
//        if (y == bounds.top)
//            yy += CIRCLE_RADIUS;
//        if (y == bounds.bottom)
//            yy -= CIRCLE_RADIUS;
//
//        return new PointF(xx, yy);
//    }

    private void doAnimate(final PointF from, final PointF to) {
        position = from;
        nextPosition = to;

        float toXDelta = to.x - from.x;
        float toYDelta = to.y - from.y;
        float fromXDelta = 0;
        float fromYDelta = 0;

        Log.d(TAG, "fromYDelta=" + fromYDelta + "; toYDelta=" + toYDelta
                + "; fromXDelta=" + fromXDelta + "; toXDelta=" + toXDelta);

        final TranslateAnimation animation = createAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                position = to;
                nextPosition = null;
                doAnimate(position, nextRandomPosition(position));
            }
        });
        startAnimation(animation);
    }

    private PointF nextRandomPosition(PointF position) {
        int xBounds = (int) (bounds.right - bounds.left);
        float x = RANDOM.nextInt(xBounds) + bounds.left;

        float y;
        if (position.y <= bounds.top)
            y = bounds.bottom;
        else
            y = bounds.top;

        return new PointF(x, y);
    }

    private TranslateAnimation createAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
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