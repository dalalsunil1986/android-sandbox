package de.winterberg.android.sandbox.sample1;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * @author Benjamin Winterberg
 */
public class Sample1View extends View {

    private static final String QUOTE = "Now is the time for all good men to come to the aid of their country.";

    private Animation animation;

    public Sample1View(Context context) {
        super(context);
    }

    private void initAnimation(Canvas canvas) {
        animation = new RotateAnimation(0, 360, 150, 150);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(7500L);
        animation.setInterpolator(new LinearInterpolator());
        startAnimation(animation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (animation == null)
            initAnimation(canvas);

        int x = 150;
        int y = 150;
        int r = 100;

        Paint glow = new Paint();
        glow.setAlpha(255);
        glow.setShadowLayer(40, 0, 0, Color.argb(200, 255, 0, 0));

        Path glowCircle = new Path();
        glowCircle.addCircle(x, y, r, Path.Direction.CW);
        canvas.drawPath(glowCircle, glow);



        Paint cPaint = new Paint();
        cPaint.setAntiAlias(true);
        cPaint.setDither(true);

        int color1 = Color.rgb(60, 60, 60);
        int color2 = Color.rgb(200, 200, 200);
        LinearGradient gradient = new LinearGradient(0, 0, 0, y*2, color2, color1, Shader.TileMode.REPEAT);
        cPaint.setShader(gradient);


        Paint tPaint = new Paint();
        tPaint.setTextSize(18f);

        tPaint.setTypeface(Typeface.DEFAULT_BOLD);
        tPaint.setColor(Color.BLACK);
        tPaint.setAntiAlias(true);

        Path circle = new Path();
        circle.addCircle(x, y, r, Path.Direction.CW);

        canvas.drawPath(circle, cPaint);
        canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);
    }
}
