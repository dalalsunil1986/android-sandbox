package de.winterberg.android.sandbox.sample1;

import android.content.Context;
import android.graphics.*;
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
        int circleHeight = 150;
        int circleWidth = 150;

        Paint glow = new Paint();
        glow.setShadowLayer(40, 0, 0, Color.argb(200, 255, 0, 0));

        Path glowCircle = new Path();
        glowCircle.addCircle(circleWidth, circleHeight, 100, Path.Direction.CW);
        canvas.drawPath(glowCircle, glow);



        Paint cPaint = new Paint();
        cPaint.setAntiAlias(true);
        cPaint.setDither(true);

        int color1 = Color.rgb(60, 60, 60);
        int color2 = Color.rgb(200, 200, 200);
        LinearGradient gradient = new LinearGradient(0, 0, 0, circleHeight*2, color1, color2, Shader.TileMode.REPEAT);
        cPaint.setShader(gradient);


        Paint tPaint = new Paint();
        tPaint.setTextSize(18f);
        tPaint.setTypeface(Typeface.DEFAULT_BOLD);
        tPaint.setColor(Color.BLACK);
        tPaint.setAntiAlias(true);

        Path circle = new Path();
        circle.addCircle(circleWidth, circleHeight, 100, Path.Direction.CW);

        canvas.drawPath(circle, cPaint);
        canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);
    }
}
