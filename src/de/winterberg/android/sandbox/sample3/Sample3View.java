package de.winterberg.android.sandbox.sample3;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Benjamin Winterberg
 */
public class Sample3View extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Sample3";

    public static final int GREEN = Color.rgb(22, 245, 156);
    public static final int BLUE = Color.rgb(22, 167, 245);

    public static final float MARGIN = 5;
    public static final float STROKE_WIDTH = 5;
    public static final float CIRCLE_RADIUS = 20;


    class SurfaceThread extends Thread {
        private final Context context;
        private final SurfaceHolder surfaceHolder;

        private boolean running = false;

        private int surfaceWidth;
        private int surfaceHeight;

        private RectF bounds;

        private PointF position;

        


        SurfaceThread(Context context, SurfaceHolder surfaceHolder) {
            super();
            this.context = context;
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            Log.d(TAG, "surface thread running");
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        updatePhysics();
                        doDraw(canvas);
                    }
                } finally {
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            Log.d(TAG, "surface thread stopped");
        }

        private void updatePhysics() {
            // use center as starting position
            if (position == null) {
                float x = bounds.centerX();
                float y = bounds.centerY();
                position = new PointF(x, y);
            }


        }

        private void doDraw(Canvas canvas) {
            clearScreen(canvas);
            drawBackground(canvas);
            drawBall(canvas);
        }

        private void drawBall(Canvas canvas) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);      // TODO extract field
            paint.setColor(GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(STROKE_WIDTH);

            Path circle = new Path();
            circle.addCircle(position.x, position.y, CIRCLE_RADIUS, Path.Direction.CW);
            canvas.drawPath(circle, paint);
        }

        private void clearScreen(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
        }

        private void drawBackground(Canvas canvas) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);     // TODO extract field
            paint.setColor(BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(STROKE_WIDTH);

            Rect bounds = canvas.getClipBounds();
            RectF rectF = new RectF(MARGIN, MARGIN, bounds.right - MARGIN, bounds.bottom - MARGIN);
            canvas.drawRoundRect(rectF, 15, 15, paint);
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
                // size
                this.surfaceWidth = width;
                this.surfaceHeight = height;

                // bounds
                float left = MARGIN + STROKE_WIDTH + CIRCLE_RADIUS;
                float top = MARGIN + STROKE_WIDTH + CIRCLE_RADIUS;
                float right = surfaceWidth - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS;
                float bottom = surfaceHeight - MARGIN - STROKE_WIDTH - CIRCLE_RADIUS;
                bounds = new RectF(left, top, right, bottom);
            }
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

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated");
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed");
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // nothing
            }
        }
    }
}