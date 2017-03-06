package nowoscmexico.com.tradinggames_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import nowoscmexico.com.tradinggames_1.game.SimpleViewG;
import nowoscmexico.com.tradinggames_1.user.UserActivity;

public class GalleryActivity extends AppCompatActivity {

    //Esto me parece tiene que entrar en un dao para que se pueda guardar nombre...blablabla
    //unicamente listas de datos...nada en tablas...diccionarios...
    // Ok => listas de objetos DAO

    Integer[] imageIDs = {
            R.drawable.fifa,
            R.drawable.mario,
            R.drawable.papermario,
            R.drawable.starfox,
            R.drawable.tloz
    };

    Context context;
    private ImageView imgSelected,imgmatch;
    private TextView videojuego;
    public NavigationView navigationView;

    private String gameSelected;
    private String sesion;
    private SharedPreferences sharedPreferences;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //Check sesion...if logged, then show elements
        menuClass menu = new menuClass(this,navigationView,drawer);
        menu.showMenu();

        //get if user logged
        sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        sesion = sharedPreferences.getString("sesion","null");

        videojuego = (TextView)findViewById(R.id.textViewGameView);
        //imgmatch = (ImageView) findViewById(R.id.imageViewmatch);

        // Note that Gallery view is deprecated in Android 4.1---
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        imgSelected = (ImageView) findViewById(R.id.imageViewgall);
        gallery.setAdapter(new ImageAdapter(this,imageIDs));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position,long id)
            {
                Toast.makeText(getBaseContext(),"pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();

                imgSelected.setImageResource(imageIDs[position]);
                videojuego.setText(imageIDs[position]);

                gameSelected = imageIDs[position]+"";
            }
        });

        videojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SimpleViewG.class);
                intent.putExtra("nombre","Juego activo from DAO");
                context.startActivity(intent);
            }
        });

    }

    //Inicar sesion launch activity
    public void startUser(View v){
        //Validar si sesion iniciada...
        Toast.makeText(this,"Start session",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("activity","trends");
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.gallery, menu);
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

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        private Activity activity;
        private Integer[] imageIDsbase;

        public ImageAdapter(Activity activity,Integer [] img)
        {
            this.activity = activity;
            imageIDsbase = img;
            //context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }
        // returns the number of images
        @Override
        public int getCount() {
            return imageIDsbase.length;
        }
        // returns the ID of an item
        @Override
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder
        {
            public ImageView imgViewFlag;
            public ImageView imgViewStar;
        }
        // returns an ImageView view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder view;
            final int intt = position;

            LayoutInflater inflator = activity.getLayoutInflater();

            if(convertView==null)
            {
                view = new ViewHolder();
                convertView = inflator.inflate(R.layout.swipe, null);

                view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView);
                view.imgViewStar = (ImageView) convertView.findViewById(R.id.imageViewstart);

                convertView.setTag(view);
            }
            else
            {
                view = (ViewHolder) convertView.getTag();
            }

            view.imgViewFlag.setImageResource(imageIDsbase[position]);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(450, 250);
            view.imgViewFlag.setLayoutParams(layoutParams);

            if(sesion.equals("1")){
                view.imgViewStar.setVisibility(View.VISIBLE);
            }else{
                view.imgViewStar.setVisibility(View.INVISIBLE);
            }

            view.imgViewStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sesion.equals("1")){
                        //Add and delete from the DB table match
                        //if not in list
                        if(!flag){
                            flag=true;
                            view.imgViewStar.setImageResource(android.R.drawable.btn_star_big_on);
                            Toast.makeText(activity,"Juego agregado a lista de Match.\n"+imageIDsbase[intt],Toast.LENGTH_SHORT).show();
                        }else{
                            flag=false;
                            view.imgViewStar.setImageResource(android.R.drawable.btn_star);
                            //Toast.makeText(context,"Juego eliminado de lista de Match.\n"+result[pos],Toast.LENGTH_SHORT).show();
                        }
                        //else
                        //Toast.makeText(context,"Juego elimiando de lista de Match.\n"+result[pos],Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = new Intent(context, UserActivity.class);
                        //intent.putExtra("nombre",gameSelected);
                        intent.putExtra("activity","match");
                        context.startActivity(intent);
                    }
                }
            });

            return convertView;

            /*ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
            imageView.setBackgroundResource(itemBackground);
            return imageView;*/
        }
    }
}
