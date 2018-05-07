package nowoscmexico.com.tradinggames.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.GalleryActivity;
import nowoscmexico.com.tradinggames.menuClass;
import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.R;

public class GamesActivity extends AppCompatActivity {

    public ListView lista;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(context, AddGame.class);
                intent.putExtra("activity","games");
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //Check sesion...if logged, then show elements
        menuClass menu = new menuClass(this,navigationView,drawer);
        menu.showMenu();

        lista = (ListView)findViewById(R.id.listViewgames);

        /*
            Now let's read from te database
         */

        try {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");

            //leo articulos de la base de datos y los muestro aqui...
            DBaseMethods.ThreadDBRead read = new DBaseMethods.ThreadDBRead();
            ArrayList<Object> elements = new ArrayList<>();
            elements = read.execute(modelBase.FeedEntryArticle.TABLE_NAME).get();
            if(elements.size() == 0){
                Toast.makeText(this,"Agrega tu primer juego.",Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(context, AddGame.class);
                //intent.putExtra("activity","games");
                //startActivity(intent);
            }else{
                GamesAdapter adapter = new GamesAdapter(this, GalleryActivity.lista,typeface);
                lista.setAdapter(adapter);
            }

            TextView titulo = (TextView)findViewById(R.id.textViewTitle);
            titulo.setTypeface(typeface);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.games, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
