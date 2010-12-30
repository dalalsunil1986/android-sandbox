package de.winterberg.android.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import de.winterberg.android.sandbox.sample1.Sample1Activity;
import de.winterberg.android.sandbox.sample2.Sample2Activity;
import de.winterberg.android.sandbox.sample3.Sample3Activity;

/**
 * @author Benjamin Winterberg
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startSample(3);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.sample1_button:
                startSample(1);
                break;
            case R.id.sample2_button:
                startSample(2);
                break;
            case R.id.sample3_button:
                startSample(3);
                break;
        }
    }

    private void startSample(int num) {
        switch (num) {
            case 1:
                startActivity(new Intent(this, Sample1Activity.class));
                break;
            case 2:
                startActivity(new Intent(this, Sample2Activity.class));
                break;
            case 3:
                startActivity(new Intent(this, Sample3Activity.class));
                break;
            default:
                Toast.makeText(this, "Sample not found " + num, Toast.LENGTH_LONG).show();
                break;
        }

    }
}