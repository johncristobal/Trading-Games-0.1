/*
* Activity to star session
* */
package nowoscmexico.com.tradinggames_1.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import nowoscmexico.com.tradinggames_1.R;
import nowoscmexico.com.tradinggames_1.TrendsGames;

public class UserActivity extends AppCompatActivity {

    public String lastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent i = getIntent();
        lastActivity = i.getStringExtra("activity");
    }

    public void sesionOpened(View v){
        //User registered--- launch new activity
        //Be CAREFUL!!! => it depends from the parent acvitiy: match, contactar dueño, etc.
        //oR could be a new user ... start registration...

        //uSE Sharedpereferences to save user sesion
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sesion","1");
        editor.commit();

        if(lastActivity.equals("trends")){
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);

        }else if(lastActivity.equals("match")){
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);

        }
    }
}

