package de.winterberg.android.sandbox.sample4;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample4View extends View {

    private Paint defaultPaint;

    private Matrix defaultTransform;

    private Path path;


    public Sample4View(Context context) {
        super(context);
        init();
        resetModel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, defaultPaint);
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
    }
}