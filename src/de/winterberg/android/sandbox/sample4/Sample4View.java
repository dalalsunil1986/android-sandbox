package de.winterberg.android.sandbox.sample4;

import android.content.Context;
import android.graphics.*;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample4View extends View {
    private static final String TAG = "Sample4";

    private static final int MODE_NONE = 0x00;
    private static final int MODE_DRAG = 0x01;
    private static final int MODE_ZOOM = 0x02;
    private static final int MODE_ROTATE = 0x03;

    private Paint defaultPaint;
    private Paint dragPaint;
    private Paint zoomPaint;
    private Paint rotatePaint;

    private Path path;
    private RectF bounds = new RectF();


    // gesture stuff

    private int mode = MODE_NONE;

    private Matrix defaultMatrix;
    private Matrix matrix = new Matrix();

    private PointF point = new PointF();
    private PointF pivot = new PointF();
    private float oldDistance = 1.0f;


    public Sample4View(Context context) {
        super(context);
        init();
        resetModel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (mode == MODE_NONE) {
                    float x = ev.getX();
                    float y = ev.getY();
                    RectF bounds = computedBounds();
                    // TODO
                    if (bounds.contains(x, y)) {
                        onRotateStart(ev);
//                        onDragStart(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (mode == MODE_NONE || mode == MODE_DRAG)
                    onZoomStart(ev);
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (mode == MODE_DRAG)
                    onDragEnd(ev);
                else if (mode == MODE_ZOOM)
                    onZoomEnd(ev);
                else if (mode == MODE_ROTATE)
                    onRotateEnd(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG)
                    onDrag(ev);
                else if (mode == MODE_ZOOM)
                    onZoom(ev);
                else if (mode == MODE_ROTATE)
                    onRotate(ev);
                break;
        }
        return true;
    }

    private void onRotate(MotionEvent ev) {
        float dx = ev.getX() - point.x;
        float dy = ev.getY() - point.y;
        float distance = Math.abs(dx) > Math.abs(dy) ? dx : dy;
        float degrees = 360 * (distance / 3600);
        matrix.setRotate(degrees, pivot.x, pivot.y);
        path.transform(matrix);
        invalidate();
    }

    private void onRotateStart(MotionEvent ev) {
        RectF bounds = computedBounds();
        pivot.set(bounds.centerX(), bounds.centerY());
        point.set(ev.getX(), ev.getY());
        Log.d(TAG, "mode=ROTATE");
        mode = MODE_ROTATE;
    }

    private void onRotateEnd(MotionEvent ev) {
        matrix.set(null);
        Log.d(TAG, "mode=NONE");
        mode = MODE_NONE;
        invalidate();
    }

    private float distance(MotionEvent ev) {
        float x = ev.getX(0) + ev.getX(1);
        float y = ev.getY(0) + ev.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private void onZoom(MotionEvent ev) {
        float newDistance = distance(ev);
        if (newDistance > 20f) {
            float scale = oldDistance / newDistance;
            matrix.setScale(scale, scale, pivot.x, pivot.y);
            path.transform(matrix);
            RectF bounds = computedBounds();
            invalidate((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
        }
        invalidate();
    }

    private void onZoomStart(MotionEvent ev) {
        Log.d(TAG, "mode=ZOOM");
        oldDistance = distance(ev);
        RectF bounds = computedBounds();
        pivot.set(bounds.centerX(), bounds.centerY());
        mode = MODE_ZOOM;
    }

    private void onZoomEnd(MotionEvent ev) {
        Log.d(TAG, "mode=NONE");
        mode = MODE_NONE;
        invalidate();
    }

    private void onDrag(MotionEvent ev) {
        float dx = ev.getX() - point.x;
        float dy = ev.getY() - point.y;
        matrix.setTranslate(dx, dy);
        point.set(ev.getX(), ev.getY());    // relevant for next drag
        path.transform(matrix);
        invalidate();
    }

    private void onDragStart(MotionEvent ev) {
        point.set(ev.getX(), ev.getY());
        Log.d(TAG, "mode=DRAG");
        mode = MODE_DRAG;
    }

    private void onDragEnd(MotionEvent ev) {
        matrix.set(null);
        mode = MODE_NONE;
        Log.d(TAG, "mode=NONE");
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, getPaintByMode());
    }

    private Paint getPaintByMode() {
        switch (mode) {
            case MODE_DRAG:
                return dragPaint;
            case MODE_ZOOM:
                return zoomPaint;
            case MODE_ROTATE:
                return rotatePaint;
            default:
                return defaultPaint;
        }
    }

    private RectF computedBounds() {
        path.computeBounds(bounds, true);
        return bounds;
    }

    private void resetModel() {
        PointF a = new PointF(-20f, -15f);
        PointF b = new PointF(20f, -15f);
        PointF c = new PointF(20f, 15f);
        PointF d = new PointF(-20f, 15f);
        PointF e = new PointF(0f, 35f);

        path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.lineTo(e.x, e.y);
        path.lineTo(c.x, c.y);
        path.moveTo(d.x, d.y);
        path.lineTo(a.x, a.y);

        path.transform(defaultMatrix);
    }

    private void init() {
        defaultMatrix = new Matrix();
        defaultMatrix.setScale(2f, 2f);
        defaultMatrix.preRotate(180f);
        defaultMatrix.postTranslate(150f, 200f);

        defaultPaint = new Paint();
        defaultPaint.setColor(Color.rgb(0xff, 0xff, 0xff));
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
        defaultPaint.setStrokeJoin(Paint.Join.ROUND);
        defaultPaint.setStrokeWidth(2.5f);

        dragPaint = new Paint(defaultPaint);
        dragPaint.setColor(Color.rgb(0xff, 0x00, 0x00));

        zoomPaint = new Paint(defaultPaint);
        zoomPaint.setColor(Color.rgb(0x00, 0xff, 0x00));

        rotatePaint = new Paint(defaultPaint);
        rotatePaint.setColor(Color.rgb(0x00, 0x00, 0xff));
    }
}