package de.winterberg.android.sandbox.sample4;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Benjamin Winterberg
 */
public class Sample4Activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Sample4View(this));
    }
}