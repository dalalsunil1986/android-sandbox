package de.winterberg.android.sandbox.sample1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample1View extends View {

    private static final String QUOTE = "Now is the time for all good men to come to the aid of their country.";

    public Sample1View(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.LTGRAY);

        Paint textPaint = new Paint();
        textPaint.setTextSize(20f);
        textPaint.setColor(Color.BLACK);

        Path circle = new Path();
        circle.addCircle(150, 150, 100, Path.Direction.CW);

        canvas.drawPath(circle, circlePaint);
        canvas.drawTextOnPath(QUOTE, circle, 0, 20, textPaint);
    }
}
