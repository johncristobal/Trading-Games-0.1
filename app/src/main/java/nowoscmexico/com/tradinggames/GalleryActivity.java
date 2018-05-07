package nowoscmexico.com.tradinggames;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.InputStream;

import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.user.UserActivity;
import nowoscmexico.com.tradinggames.DataBase.ArticuloDao;
import nowoscmexico.com.tradinggames.game.SimpleViewG;

public class GalleryActivity extends AppCompatActivity {

    //Esto me parece tiene que entrar en un dao para que se pueda guardar nombre...blablabla
    //unicamente listas de datos...nada en tablas...diccionarios...
    // Ok => listas de objetos DAO

    /*Integer[] imageIDs = {
            R.drawable.fifa,
            R.drawable.mario,
            R.drawable.papermario,
            R.drawable.starfox,
            R.drawable.tloz
    };*/

    //arraylist with data of games from firebase....
    public static ArrayList<ArticuloDao> juegosws;

    Context context;
    private ImageView imgSelected,imgmatch;
    private TextView videojuego;
    private Gallery gallery;
    private RelativeLayout contenedor;
    public NavigationView navigationView;

    private String gameSelected;
    private String sesion,idusuario;
    private SharedPreferences sharedPreferences;
    private boolean flag=false;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public int numArticulos;
    public int contadorArticulos;

    public ArrayList<ImageView> imagenes;
    public static ArrayList<ArticuloDao> lista;//lista con solo mis articulos idusuario
    public ArticuloDao daito;
    ImageAdapter adapter;
    public int flagcorrido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        context = this;

        imagenes = new ArrayList<>();

        flagcorrido = 0;
        //FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage.getReferenceFromUrl(getString(R.string.bucket));

        //FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage.getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*LocalDate date1 = new LocalDate(2015, 04, 29);
        LocalDate date2 = new LocalDate(2017, 04, 30);
        int days = Days.daysBetween(date1, date2).getDays();
        Toast.makeText(this,days+"",Toast.LENGTH_LONG).show();*/

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
        idusuario = sharedPreferences.getString("idusuario","null");

        videojuego = (TextView)findViewById(R.id.textViewGameView);
        // Note that Gallery view is deprecated in Android 4.1---
        gallery = (Gallery) findViewById(R.id.gallery1);
        imgSelected = (ImageView) findViewById(R.id.imageViewgall);
        contenedor = (RelativeLayout) findViewById(R.id.relativegame);
        /*
        * Probar glide sin hilos ni nada....puramente...
        * */

        /**************************
         * Go for the games WS
         */

        try {
            /*String namefo = "juegos/miituo.png";
            Log.w("Name", namefo);
            storageRef.child(namefo);

            final File localFile = File.createTempFile("images", "jpg");

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    String da = localFile.getAbsolutePath();
                    Log.w("Jajaja",da);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle anyors err
                    Log.w("Error","Waiting for an answer "+exception.toString());
                }
            });*/


            //new Thread(new Runnable() {
                  //@Override
                  //public void run() {

                      /*storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener() {
                          @Override
                          public void onSuccess(Object o) {

                              String cad = o.toString();
                              Log.w("oBJE",cad);
                              try {
                                  URL url = new URL(cad);
                                  Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                  //comment.setIamgentemp(image);
                                  //mImageView.setImageBitmap(bitmap);
                                  //lista.add(comment);
                                  String cads = image.toString();

                              } catch(Exception e) {
                                  //System.out.println(e.toString());
                                  e.printStackTrace();
                              }
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception exception) {
                              // Handle any errors
                              Log.w("Error",exception.toString());
                          }
                      });*/
                  //}
            //}).start();

            //loadImagen();


            Sendthelast last = new Sendthelast();
            last.execute();
            //GALLERY ADAPTER CODE DELETE
            //...

            //show first element from list
            /*final String folderuser = lista.get(0).getIdusuario();
            String[] fotos = lista.get(0).getFoto().split(",");
            //Evitr craah en caso que no traiga fotos
            if (fotos.length >= 1) {
                //Aqui solo recuperamos una foto para mostrar en mainview
                //for (int i = 0; i < 1; i++) {
                final String name = fotos[0];
                //Bitmap bmp = BitmapFactory.decodeFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser+"/"+name,options);
                //view.imgViewFlag.setImageBitmap(bmp);
                Glide.with(GalleryActivity.this)
                        .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser+"/"+name)
                        .into(imgSelected);
            }*/

            //set texto into background
            //videojuego.setText(lista.get(0).getTitulo());
            //gameSelected = lista.get(0).getFoto() + "";

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadImagen() {

        try {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/juegos/miituo.png");
            //StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/").child(folderuser+"/"+name);

            // Load the image using Glide
            imgSelected = (ImageView) findViewById(R.id.imageViewgall);
            imgSelected.setDrawingCacheEnabled(true);
            imgSelected.buildDrawingCache(true);
            //final ImageView imageView = new ImageView(GalleryActivity.this);

            Glide.with(GalleryActivity.this)
                    //.using(new FirebaseImageLoader())
                    .load("https://firebasestorage.googleapis.com/v0/b/tradinggames-a6047.appspot.com/o/s2YFT93wtFTXE75rjRkSvvdO6Y62%2Fs2YFT93wtFTXE75rjRkSvvdO6Y62_mario%20kart%2064_2.png?alt=media&token=ecb7fc6e-c4eb-4570-8a7b-560070656698")
                    //.asBitmap()
                    .into(imgSelected);

            Bitmap image = imgSelected.getDrawingCache();

            int a = 0;
            a++;
                /*imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache(true);
                Bitmap bitmapts = imageView.getDrawingCache();
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser+"/"+name);
                File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser);
                if(!dir.exists())
                    dir.mkdirs();
                try
                {
                    //file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmapts.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*/

            /*String savedImagePath = null;

            //String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
            File storageDir  = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+"s2YFT93wtFTXE75rjRkSvvdO6Y62");

            //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/YOUR_FOLDER_NAME");
            boolean success = true;
            if (!storageDir.exists()) {
                success = storageDir.mkdirs();
            }
            if (success) {
                File imageFile = new File(storageDir, "s2YFT93wtFTXE75rjRkSvvdO6Y62_mario kart 64_1.png");
                savedImagePath = imageFile.getAbsolutePath();
                try {
                    OutputStream fOut = new FileOutputStream(imageFile);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Add the image to the system gallery
                //galleryAddPic(savedImagePath);
                //Toast.makeText(mContext, "IMAGE SAVED"), Toast.LENGTH_LONG).show();
            }*/

            //saveImage(imgSelected,folderuser,name);
                                                /*.into(new SimpleTarget<GlideDrawable>(){
                                                    @Override
                                                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                        imageView.setImageDrawable(glideDrawable);
                                                        imageView.setDrawingCacheEnabled(true);
                                                        //saveImage();
                                                        saveImage(imageView,folderuser,name);
                                                    }});*/

                                        /*imageView.setDrawingCacheEnabled(true);
                                        imageView.buildDrawingCache(true);
                                        Bitmap bitmapts = imageView.getDrawingCache();
                                        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser+"/"+name);
                                        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser);
                                        if(!dir.exists())
                                            dir.mkdirs();
                                        try
                                        {
                                            //file.createNewFile();
                                            FileOutputStream ostream = new FileOutputStream(file);
                                            bitmapts.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                            ostream.close();
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }*/
            //imagenes.add(imageView);

        }
        catch(Exception e){
            e.printStackTrace();
        }

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


    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }

//=================================GEt data from firebase===========================================
    //public void saygoodbye(){
   public class Sendthelast extends AsyncTask<Void, Void, Void>{
        ProgressDialog progress = new ProgressDialog(GalleryActivity.this);
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        public ArrayList<ArticuloDao> listalocal = new ArrayList<>();
        public ArrayList<ArticuloDao> listalocalmios = new ArrayList<>();

        public void borrarDB(){

            SQLiteDatabase db = DBaseMethods.base.getWritableDatabase();

            db.execSQL("DELETE FROM " + modelBase.FeedEntryUsuario.TABLE_NAME);
            db.execSQL("DELETE FROM " + modelBase.FeedEntryArticle.TABLE_NAME);
            db.execSQL(String.format("DELETE FROM %s", modelBase.FeedEntryMatch.TABLE_NAME));
            db.close();
        }

        //cargar articulo a la base ddatos
        public long cargararticulo(ArticuloDao dao){

            try {
                SQLiteDatabase db = DBaseMethods.base.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                //values.put(modelBase.FeedEntryArticle.COLUMN_ID, strings[6]);
                values.put(modelBase.FeedEntryArticle.COLUMN_TITULO, dao.getTitulo());
                values.put(modelBase.FeedEntryArticle.COLUMN_IDFIREBASE, dao.getIdfirebase());
                values.put(modelBase.FeedEntryArticle.COLUMN_DESCRIPCION, dao.getDescripcion());
                values.put(modelBase.FeedEntryArticle.COLUMN_CATE, dao.getCategoria());
                values.put(modelBase.FeedEntryArticle.COLUMN_FOTO, dao.getFoto());
                values.put(modelBase.FeedEntryArticle.COLUMN_TIME, dao.getTimeup());
                values.put(modelBase.FeedEntryArticle.COLUMN_FKUser, dao.getIdusuario());

                // Insert the new row, returning the primary key value of the new row
                //Just change name table and the values....
                long newRowId = db.insert(modelBase.FeedEntryArticle.TABLE_NAME, null, values);
                return newRowId;
            }
            catch(Exception e){
                return -1;
            }
        }

        @Override
        protected void onPreExecute() {

            //borro local database
            borrarDB();

            //get num elements into articulo
            myRef.child("articulo").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    Log.e("Number", "contador:" + size);
                    numArticulos = size;

                    if(numArticulos == 0){
                        progress.dismiss();
                        cancel(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("mensaje","error al cargar datos");
                }
            });

            progress.setTitle("Actualizando");
            progress.setMessage("Recuperando información...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected void onCancelled() {
            progress.dismiss();
            Toast msg = Toast.makeText(getApplicationContext(), ErrorCode, Toast.LENGTH_LONG);
            msg.show();

            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //before launch alert, we have to send the confirmReport
            try {
                //get articulos from firebase
                Query recentPostsQuery = myRef.child("articulo").limitToFirst(100);

                recentPostsQuery.addChildEventListener(new ChildEventListener() {
                    public static final String TAG = "CHILD";

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s){

                        try {
                            Log.d(TAG, "onChildAdded:" + dataSnapshot.getChildrenCount());
                            //Recupero objeto articulo de firebase y cast a ArticuloDao
                            final ArticuloDao comment = dataSnapshot.getValue(ArticuloDao.class);
                            listalocal.add(comment);

                            //salvo en base local
                            if(comment.getIdusuario().equals(idusuario)) {
                                cargararticulo(comment);
                                listalocalmios.add(comment);
                            }

                            final String folderuser = comment.getIdusuario();
                            String[] fotos = comment.getFoto().split(",");
                            //Evitr craah en caso que no traiga fotos
                            if (fotos.length >= 1) {
                                //Aqui solo recuperamos una foto para mostrar en mainview
                                try {
                                    for (int i = 0; i < fotos.length; i++) {
                                        final String name = fotos[i];

                                        //COn el nombre de la imgaen...recuoero imagen de storage firebase

                                        //StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/").child("s2YFT93wtFTXE75rjRkSvvdO6Y62/s2YFT93wtFTXE75rjRkSvvdO6Y62_mario kart 64_1.png");
                                        //final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/").child(folderuser + "/" + name);
                                        final long ONE_MEGABYTE = 1024*1024;
                                        //imagenes.add(imageView);
                                        //final File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + folderuser);

                                        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/YOUR_FOLDER_NAME");
                                        boolean success = true;
                                        //if (!storageDir.exists()) {
                                        //    success = storageDir.mkdirs();
                                        //}
                                        if (success) {
                                            contadorArticulos++;

                                            if (contadorArticulos >= 5)
                                                progress.dismiss();

                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                                //}
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                //if(contadorArticulos == numArticulos) {
                gallery = (Gallery) findViewById(R.id.gallery1);

                lista = listalocalmios;

                adapter = new ImageAdapter(GalleryActivity.this, listalocal);
                //adapter.notifyDataSetChanged();
                gallery.setAdapter(adapter);
                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    final String folderuser = listalocal.get(position).getIdusuario();
                    String[] fotos = listalocal.get(position).getFoto().split(",");
                    //Evitr craah en caso que no traiga fotos
                    if (fotos.length >= 1) {
                        //Aqui solo recuperamos una foto para mostrar en mainview
                        final String name = fotos[0];
                        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com").child(folderuser + "/" + name);
                        final long ONE_MEGABYTE = 1024*1024;

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                Glide.with(GalleryActivity.this)
                                        .load(uri.toString())
                                        .apply(new RequestOptions().override(240, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                                        //.load(storageRef)
                                        .into(imgSelected);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }

                    //set texto into background
                    videojuego.setText(listalocal.get(position).getTitulo());
                    gameSelected = listalocal.get(position).getFoto() + "";

                    daito = listalocal.get(position);
                    }
                });

                //open view with description of game
                contenedor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SimpleViewG.class);
                        intent.putExtra("nombre", daito.getTitulo());
                        intent.putExtra("foto", daito.getFoto());
                        intent.putExtra("descripcion", daito.getDescripcion());
                        intent.putExtra("categoria", daito.getCategoria());
                        intent.putExtra("usuario", daito.getIdusuario());
                        context.startActivity(intent);
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private String saveImage(ImageView imagev,String folderuser,String imageFileName) {

            Bitmap image = imagev.getDrawingCache();
            /*imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache(true);
            Bitmap bitmapts = imageView.getDrawingCache();
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser+"/"+name);
            File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser);
            if(!dir.exists())
                dir.mkdirs();
            try
            {
                //file.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file);
                bitmapts.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }*/

            String savedImagePath = null;

            //String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
            File storageDir  = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+folderuser);

            //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/YOUR_FOLDER_NAME");
            boolean success = true;
            if (!storageDir.exists()) {
                success = storageDir.mkdirs();
            }
            if (success) {
                File imageFile = new File(storageDir, imageFileName);
                savedImagePath = imageFile.getAbsolutePath();
                try {
                    OutputStream fOut = new FileOutputStream(imageFile);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Add the image to the system gallery
                //galleryAddPic(savedImagePath);
                //Toast.makeText(mContext, "IMAGE SAVED"), Toast.LENGTH_LONG).show();
            }
            return savedImagePath;
        }
    };


/*
====================================================================================================
*   Clase imagAdapter para cargar imagenes en gallery
* */

   public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        private Activity activity;
        private ArrayList<ArticuloDao> imageIDsbase;

        public ImageAdapter(Activity activity,ArrayList<ArticuloDao> img)
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
            return imageIDsbase.size();
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
                //return convertView;
            }

            final String folderuser = imageIDsbase.get(position).getIdusuario();
            String[] fotos = imageIDsbase.get(position).getFoto().split(",");
            //Evitr craah en caso que no traiga fotos
            if (fotos.length >= 1) {
                //Aqui solo recuperamos una foto para mostrar en mainview
                //for (int i = 0; i < 1; i++) {
                final String name = fotos[0];
                final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com").child(folderuser + "/" + name);
                final long ONE_MEGABYTE = 1024*1024;

                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Glide.with(GalleryActivity.this)
                                .load(uri.toString())
                                .apply(new RequestOptions().override(240, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                                //.load(storageRef)
                                .into(view.imgViewFlag);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }

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
                            Toast.makeText(activity,"Juego agregado a lista de Match.\n"+imageIDsbase.get(intt).getFoto(),Toast.LENGTH_SHORT).show();
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
        }
    }
}
