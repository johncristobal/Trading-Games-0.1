package nowoscmexico.com.tradinggames_1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import nowoscmexico.com.tradinggames_1.user.UserActivity;

public class TrendsGames extends AppCompatActivity implements SearchView.OnQueryTextListener{

    //These elements will come from WS
    public static String [] elements = {"uno","dos","tres","cuatro","cinco","seis","siete"};
    public GridView grid;
    public NavigationView navigationView;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(menu.onNavigationItemSelected());

        //Check sesion...if logged, then show elements
        menuClass menu = new menuClass(this,navigationView,drawer);
        menu.showMenu();

        //Use sharedpreferences to save session
        sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = sharedPreferences.getString("sesion","null");

        //Ya vio el tutorial al principio, lo vera de nuevo solo si lo busca en el menu de ocpioens
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tutohecho","1");
        editor.commit();

        //Launch grid with elements of trends....
        grid = (GridView)findViewById(R.id.gridView);
        grid.setAdapter(new CustomAdapterGrid(this,elements,sesion));
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
        getMenuInflater().inflate(R.menu.trends_games, menu);

        //Search configuration
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.searchopt);
        SearchView searchView = (SearchView)searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        //searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
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

    //Inicar sesion launch activity
    public void startUser(View v){
        Toast.makeText(this,"Start session",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("activity","trends");
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        //La idea es hacer un viaje al WS retornando las coincidencias del String
        //Mostrar los resultados en el view

        String sesion = sharedPreferences.getString("sesion","null");
        grid = (GridView)findViewById(R.id.gridView);
        String [] elements2 = {"a","b","c","d"};
        grid.setAdapter(new CustomAdapterGrid(this,elements2,sesion));

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
