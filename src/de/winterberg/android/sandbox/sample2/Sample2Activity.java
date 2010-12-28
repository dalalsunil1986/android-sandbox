package de.winterberg.android.sandbox.sample2;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Benjamin Winterberg
 */
public class Sample2Activity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Sample2View(this));
    }

}