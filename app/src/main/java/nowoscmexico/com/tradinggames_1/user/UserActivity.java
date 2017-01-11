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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nowoscmexico.com.tradinggames_1.R;
import nowoscmexico.com.tradinggames_1.TrendsGames;
import nowoscmexico.com.tradinggames_1.game.MatchActivity;

public class UserActivity extends AppCompatActivity {

    public String lastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Iniciar sesiòn");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        lastActivity = i.getStringExtra("activity");
    }

    public void sesionOpened(View v){
        //User registered--- launch new activity
        Intent i = new Intent(this,RegisterUser.class);
        startActivity(i);
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_view_g, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == 16908332){
            //Toast.makeText(this,"Bavk to the last page. This upon tha activity",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void entrarSesion(View v){
        //uSE Sharedpereferences to save user sesion
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sesion","1");
        editor.commit();

        //When session opened, you have to show a differentes menu
        if(lastActivity.equals("trends")){
            //jUST SHOW TRENDS
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);
        }else if(lastActivity.equals("match")){
            //TLaunch activty to save wish list
            Intent i = new Intent(this, MatchActivity.class);
            startActivity(i);
        }else if(lastActivity.equals("contacto")){
            //launch activity to contact dueno
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);
        }
    }
}

