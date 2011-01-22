package de.winterberg.android.sandbox.sample5;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

/**
 * Playing around with collision detection.
 *
 * @author Benjamin Winterberg
 */
public class Sample5View extends View {
    public static final String TAG = "Sample5";

    private Paint collisionPaint;
    private Paint normalPaint;

    private Path circle;
    private Path triangle;
    private Path square;

    private Path dragElement;
    private Matrix matrix = new Matrix();
    private PointF point = new PointF();
    private RectF rect = new RectF();
    private boolean dragging = false;


    public Sample5View(Context context) {
        super(context);
        initPaints();
        initPaths();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (!dragging)
                    onDragStart(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragging)
                    onDrag(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (dragging)
                    onDragEnd();
                break;
        }
        return true;
    }

    private void onDrag(MotionEvent ev) {
        float dx = ev.getX() - point.x;
        float dy = ev.getY() - point.y;
        matrix.setTranslate(dx, dy);
        point.set(ev.getX(), ev.getY());
        dragElement.transform(matrix);
        invalidate();
    }

    private void onDragStart(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        point.set(x, y);

        circle.computeBounds(rect, true);
        if (rect.contains(x, y)) {
            dragging = true;
            dragElement = circle;
            return;
        }

        triangle.computeBounds(rect, true);
        if (rect.contains(x, y)) {
            dragging = true;
            dragElement = triangle;
            return;
        }

        square.computeBounds(rect, true);
        if (rect.contains(x, y)) {
            dragging = true;
            dragElement = square;
        }
    }

    private void onDragEnd() {
        dragging = false;
        dragElement = null;
        invalidate();
    }

    private void initPaths() {
        circle = new Path();
        circle.addCircle(5, 5, 5, Path.Direction.CW);

        triangle = new Path();
        triangle.moveTo(0, 0);
        triangle.lineTo(10, 0);
        triangle.lineTo(5, 7);
        triangle.lineTo(0, 0);

        square = new Path();
        square.addRect(0, 0, 10, 10, Path.Direction.CW);


        float sx = 8, sy = 8;

        Matrix matrix = new Matrix();

        matrix.setScale(sx, sy);
        matrix.preRotate(180);
        matrix.postTranslate(100, 100);
        circle.transform(matrix);

        matrix.setScale(sx, sy);
        matrix.preRotate(180);
        matrix.postTranslate(200, 200);
        triangle.transform(matrix);

        matrix.setScale(sx, sy);
        matrix.preRotate(180);
        matrix.postTranslate(250, 350);
        square.transform(matrix);
    }

    private void initPaints() {
        normalPaint = new Paint();
        normalPaint.setAntiAlias(true);
        normalPaint.setColor(Color.WHITE);
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setStrokeCap(Paint.Cap.ROUND);
        normalPaint.setStrokeJoin(Paint.Join.ROUND);
        normalPaint.setStrokeWidth(4f);

        collisionPaint = new Paint(normalPaint);
        collisionPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(circle, normalPaint);
        canvas.drawPath(triangle, normalPaint);
        canvas.drawPath(square, normalPaint);
    }
}