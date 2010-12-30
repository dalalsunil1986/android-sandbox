package de.winterberg.android.sandbox.sample3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Benjamin Winterberg
 */
public class Sample3View extends SurfaceView implements SurfaceHolder.Callback {


    class SurfaceThread extends Thread {
        private Context context;
        private SurfaceHolder surfaceHolder;

        SurfaceThread(Context context, SurfaceHolder surfaceHolder) {
            super();
            this.context = context;
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            // TODO
        }
    }



    private Context context;

    private SurfaceThread thread;


    public Sample3View(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        this.context = context;
        this.thread = new SurfaceThread(context, holder);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}