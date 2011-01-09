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

    private Path path;
    private RectF bounds = new RectF();


    // gesture stuff

    private int mode;

    private PointF start = new PointF();

    private Matrix defaultMatrix;
    private Matrix startMatrix;
    private Matrix matrix;


    public Sample4View(Context context) {
        super(context);
        init();
        resetModel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (mode == MODE_NONE)
                    onDragStart(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (mode == MODE_DRAG)
                    onDragEnd(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG)
                    onDrag(ev);
                break;
        }
        return true;
    }

    private void onDrag(MotionEvent ev) {
        float dx = ev.getX() - start.x;
        float dy = ev.getY() - start.y;

        Log.d(TAG, "MOVING > dx=" + dx + "; dy=" + dy);

//        matrix.set(startMatrix);
        matrix.postTranslate(dx, dy);
        start.set(ev.getX(), ev.getY());

        path.transform(matrix);
        invalidate();
    }

    private void onDragStart(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        if (computedBounds().contains(x, y)) {
            Log.d(TAG, "mode=DRAG");
            start.set(x, y);
            startMatrix.set(matrix);
            mode = MODE_DRAG;
        }
    }

    private void onDragEnd(MotionEvent ev) {
        startMatrix.set(null);
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
        mode = MODE_NONE;

        defaultMatrix = new Matrix();
        defaultMatrix.setScale(2f, 2f);
        defaultMatrix.preRotate(180f);
        defaultMatrix.postTranslate(150f, 200f);

        matrix = new Matrix();
        startMatrix = new Matrix();

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