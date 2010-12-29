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

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int TOP = 2;
    private static final int BOTTOM = 3;

    private static final Random RANDOM = new Random();

    private boolean initialized = false;

    private RectF bounds;

    private PointF position;

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

        doAnimate(canvas);
        initialized = true;
    }

    private void doAnimate(Canvas canvas) {
        PointF start = new PointF(canvas.getWidth() / 2f, bounds.bottom);
        doAnimate(start, nextRandomPosition(start));
    }

    private void doAnimate(final PointF from, final PointF to) {
        position = from;

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
                doAnimate(position, nextRandomPosition(position));
            }
        });
        startAnimation(animation);
    }

    private PointF nextRandomPosition(PointF position) {
        float x, y = .0f;

        int xBounds = (int) (bounds.right - bounds.left);
        int yBounds = (int) (bounds.bottom - bounds.top);

        int direction = nextRandomDirection(position);
        switch (direction) {
            case LEFT:
                x = bounds.left;
                y = RANDOM.nextInt(yBounds) + bounds.top;
                break;
            case RIGHT:
                x = bounds.right;
                y = RANDOM.nextInt(yBounds) + bounds.top;
                break;
            case TOP:
                x = RANDOM.nextInt(xBounds) + bounds.left;
                y = bounds.top;
                break;
            case BOTTOM:
                x = RANDOM.nextInt(xBounds) + bounds.left;
                y = bounds.bottom;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }

        return new PointF(x, y);
    }

    private int nextRandomDirection(PointF position) {
        int direction = RANDOM.nextInt(4);
        if (direction == getDirection(position))
            return (direction + 1) % 4;
        return direction;
    }

    private int getDirection(PointF position) {
        if (position.y == bounds.bottom)
            return BOTTOM;
        if (position.y == bounds.top)
            return TOP;
        if (position.x == bounds.left)
            return LEFT;
        if (position.x == bounds.right)
            return RIGHT;

        throw new IllegalStateException("Position does not intersect bounds: " + position);
    }

    private TranslateAnimation createAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {

        double distance = Math.sqrt((double) toXDelta * toXDelta + toYDelta * toYDelta);

        long duration = (long)((distance / DISTANCE_PER_SECOND) * 1000f);

        TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(duration);
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