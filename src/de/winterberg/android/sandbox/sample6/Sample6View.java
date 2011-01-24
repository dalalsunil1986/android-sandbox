package de.winterberg.android.sandbox.sample6;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * @author Benjamin Winterberg
 */
public class Sample6View extends View {

    private static final String TEXT = "WINTERBE";

    public Sample6View(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint bg = new Paint();
        bg.setStyle(Paint.Style.FILL_AND_STROKE);
        bg.setColor(Color.WHITE);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(56f);
        p.setColor(Color.BLACK);
        p.setTypeface(Typeface.DEFAULT_BOLD);
        p.setSubpixelText(true);

        Paint p2 = new Paint(p);
        p2.setMaskFilter(new BlurMaskFilter(.75f, BlurMaskFilter.Blur.OUTER));

        int color1 = Color.rgb(0xCC, 0xCC, 0xCC);
        int color2 = Color.rgb(0x33, 0x33, 0x33);
        LinearGradient gradient = new LinearGradient(10, 200, 10, 240, color1, color2, Shader.TileMode.MIRROR);

        Paint p3 = new Paint(p);
        p3.setShader(gradient);
//        p3.setShadowLayer(1, 1, 1, Color.BLACK);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), bg);
        canvas.drawText(TEXT, 10, 200, p2);
        canvas.drawText(TEXT, 10, 200, p3);

    }
}