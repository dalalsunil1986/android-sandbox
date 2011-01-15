package de.winterberg.android.sandbox.sample5;

import android.content.Context;
import android.graphics.*;
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


    public Sample5View(Context context) {
        super(context);
        initPaints();
        initPaths();
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


        float sx = 7, sy = 7;

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