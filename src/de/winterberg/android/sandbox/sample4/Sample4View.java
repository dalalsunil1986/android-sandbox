package de.winterberg.android.sandbox.sample4;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample4View extends View {

    private Paint defaultPaint;

    private PointF a;
    private PointF b;
    private PointF c;
    private PointF d;
    private PointF e;

    private Path path;


    public Sample4View(Context context) {
        super(context);
        initPaints();
        resetModel();
        transformModel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, defaultPaint);
    }

    private void transformModel() {
        Matrix m;

        m = new Matrix();
        m.setRotate(180);
        path.transform(m);

        m = new Matrix();
        m.setScale(2, 2);
        path.transform(m);

        m = new Matrix();
        m.setTranslate(150, 200);
        path.transform(m);
    }

    private void resetModel() {
        a = new PointF(-20f, -15f);
        b = new PointF(20f, -15f);
        c = new PointF(20f, 15f);
        d = new PointF(-20f, 15f);
        e = new PointF(0f, 35f);

        path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.lineTo(e.x, e.y);
        path.lineTo(c.x, c.y);
        path.moveTo(d.x, d.y);
        path.lineTo(a.x, a.y);
    }

    private void initPaints() {
        defaultPaint = new Paint();
        defaultPaint.setColor(Color.rgb(0xff, 0xff, 0xff));
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
        defaultPaint.setStrokeJoin(Paint.Join.ROUND);
        defaultPaint.setStrokeWidth(2.5f);
    }
}