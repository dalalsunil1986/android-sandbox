package de.winterberg.android.sandbox.sample4;

import android.content.Context;
import android.graphics.*;
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
    private static final int MODE_ROTATE = 0x02;

    private Paint defaultPaint;
    private Paint dragPaint;

    private Matrix defaultTransform;

    private Path path;

    private int mode;

    private PointF start = new PointF();


    public Sample4View(Context context) {
        super(context);
        init();
        resetModel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                onDragStart(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
                onDragEnd(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                onDrag(ev);
                break;
        }
        return true;
    }

    private void onDrag(MotionEvent ev) {
        // TODO
    }

    private void onDragStart(MotionEvent ev) {
        Log.d(TAG, "mode=DRAG");
        mode = MODE_DRAG;
        start.set(ev.getX(), ev.getY());
    }

    private void onDragEnd(MotionEvent ev) {
        Log.d(TAG, "mode=NONE");
        mode = MODE_NONE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, getPaintByMode());
    }

    private Paint getPaintByMode() {
        switch (mode) {
            case MODE_DRAG:
                return dragPaint;
            default:
                return defaultPaint;
        }
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

        path.transform(defaultTransform);
    }

    private void init() {
        mode = MODE_NONE;

        defaultTransform = new Matrix();
        defaultTransform.setScale(2f, 2f);
        defaultTransform.preRotate(180f);
        defaultTransform.postTranslate(150f, 200f);

        defaultPaint = new Paint();
        defaultPaint.setColor(Color.rgb(0xff, 0xff, 0xff));
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
        defaultPaint.setStrokeJoin(Paint.Join.ROUND);
        defaultPaint.setStrokeWidth(2.5f);

        dragPaint = new Paint(defaultPaint);
        dragPaint.setColor(Color.rgb(0xff, 0x00, 0x00));
    }
}