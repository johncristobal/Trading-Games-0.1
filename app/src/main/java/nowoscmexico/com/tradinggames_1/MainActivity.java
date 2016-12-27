package nowoscmexico.com.tradinggames_1;

/*
* Splash activity....
* */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = sharedPreferences.getString("sesion","null");

        if(sesion.equals("1")){
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this, TutorialActivity.class);
            startActivity(i);
        }
    }
}
