package nowoscmexico.com.tradinggames_1;

/*
* Splash activity....
* */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import nowoscmexico.com.tradinggames_1.DataBase.WSTask;
import nowoscmexico.com.tradinggames_1.tuto.TutorialActivity;

public class MainActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*try {
            //Thread.sleep(3000);
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
            String sesion = sharedPreferences.getString("sesion","null");
            //String tuto = sharedPreferences.getString("tutohecho","null");

            if(sesion.equals("1")){
                Intent i = new Intent(this, TrendsGames.class);
                startActivity(i);
            }else{
                Intent i = new Intent(this, TutorialActivity.class);
                startActivity(i);
            }
        }catch (Exception e){}*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
                String sesion = sharedPreferences.getString("sesion","null");
                //String tuto = sharedPreferences.getString("tutohecho","null");

                if(sesion.equals("1")){
                    Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        /*try{

            WSTask ws = new WSTask();
            String res = ws.execute("hola").get();

            Log.w("Res",res);

        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    public void showTuto(View v){

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = sharedPreferences.getString("sesion","null");
        //String tuto = sharedPreferences.getString("tutohecho","null");

        if(sesion.equals("1")){
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this, TutorialActivity.class);
            startActivity(i);
        }
    }
}
