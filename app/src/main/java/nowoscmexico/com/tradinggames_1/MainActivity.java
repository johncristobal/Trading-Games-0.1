package nowoscmexico.com.tradinggames_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nowoscmexico.com.tradinggames_1.tuto.TutorialActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTuto(View v){

        Intent i = new Intent(this, TrendsGames.class);
        startActivity(i);

    }
}
