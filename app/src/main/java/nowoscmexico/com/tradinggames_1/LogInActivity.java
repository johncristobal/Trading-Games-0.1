package nowoscmexico.com.tradinggames_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import nowoscmexico.com.tradinggames_1.tuto.TutorialActivity;

public class LogInActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_log_in);

        TextView listo = (TextView)findViewById(R.id.textViewListo);
        TextView res = (TextView)findViewById(R.id.textViewres);
        TextView last = (TextView)findViewById(R.id.textView9);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        listo.setTypeface(custom_font);
        res.setTypeface(custom_font);
        last.setTypeface(custom_font);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent i = new Intent(LogInActivity.this, GalleryActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
