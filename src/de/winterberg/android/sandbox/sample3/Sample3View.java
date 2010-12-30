package de.winterberg.android.sandbox.sample3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Benjamin Winterberg
 */
public class Sample3View extends SurfaceView implements SurfaceHolder.Callback {

    public Sample3View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}