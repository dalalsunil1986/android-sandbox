package de.winterberg.android.sandbox.sample6;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Benjamin Winterberg
 */
public class Sample6Activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Sample6View(this));
    }
}