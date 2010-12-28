package de.winterberg.android.sandbox.sample2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * @author Benjamin Winterberg
 */
public class Sample2Activity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(new BackgroundView(this));
        frameLayout.addView(new ForegroundView(this));
        setContentView(frameLayout);
    }

}