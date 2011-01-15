package de.winterberg.android.sandbox.sample5;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Benjamin Winterberg
 */
public class Sample5Activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Sample5View(this));
    }
}