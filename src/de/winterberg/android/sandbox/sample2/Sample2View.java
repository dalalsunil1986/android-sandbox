package de.winterberg.android.sandbox.sample2;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample2View extends View {

    private static final int BLUE = Color.rgb(22, 245, 156);

    public Sample2View(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Rect bounds = canvas.getClipBounds();
        Rect rect = new Rect(5, 5, bounds.right - 5, bounds.bottom - 5);
        canvas.drawRoundRect(new RectF(rect), 15, 15, paint);
    }
}
