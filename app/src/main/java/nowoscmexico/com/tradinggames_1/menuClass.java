package nowoscmexico.com.tradinggames_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import nowoscmexico.com.tradinggames_1.game.GamesActivity;
import nowoscmexico.com.tradinggames_1.game.MatchActivity;
import nowoscmexico.com.tradinggames_1.user.UserActivity;

/**
 * Created by vera_john on 23/12/16.
 */
public class menuClass implements NavigationView.OnNavigationItemSelectedListener{

    public Context context;
    public NavigationView navigationView;
    public DrawerLayout drawer;

    public menuClass(Context c, NavigationView n,DrawerLayout drawe){
        context= c;
        navigationView = n;
        drawer = drawe;

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showMenu() {
        //Use sharedpreferences to save session
        SharedPreferences sharedPreferences =context.getSharedPreferences(context.getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = sharedPreferences.getString("sesion","null");
        if(sesion.equals("1")){
            showSesionOpenedMenu();
        }else if(sesion.equals("null")){
            closeMenu();
        }
    }

    //menu with open sesion
    private void showSesionOpenedMenu() {
        //hide / show menu items
        navigationView.getMenu().findItem(R.id.startsesion).setVisible(false);
        navigationView.getMenu().findItem(R.id.searchgame).setVisible(false);
        navigationView.getMenu().findItem(R.id.mismatch).setVisible(true);
        navigationView.getMenu().findItem(R.id.misgames).setVisible(true);
    }

    //menu with close session
    private void closeMenu(){
        navigationView.getMenu().findItem(R.id.searchgame).setVisible(false);
        navigationView.getMenu().findItem(R.id.mismatch).setVisible(false);
        navigationView.getMenu().findItem(R.id.misgames).setVisible(false);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.trend) {
            Intent intent = new Intent(context, TrendsGames.class);
            intent.putExtra("activity","trends");
            context.startActivity(intent);
        } else if (id == R.id.searchgame) {
            /*Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra("activity","trends");
            context.startActivity(intent);*/

        } else if(id == R.id.startsesion) {
            Intent intent = new Intent(context, UserActivity.class);
            intent.putExtra("activity","trends");
            context.startActivity(intent);
        } else if(id == R.id.mismatch){
            Intent intent = new Intent(context, MatchActivity.class);
            intent.putExtra("activity","match");
            context.startActivity(intent);
        } else if(id == R.id.misgames){
            Intent intent = new Intent(context, GamesActivity.class);
            intent.putExtra("activity","games");
            context.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
