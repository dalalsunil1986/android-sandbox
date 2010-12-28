package de.winterberg.android.sandbox.sample1;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Benjamin Winterberg
 */
public class Sample1Activity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Sample1View(this));
    }

}