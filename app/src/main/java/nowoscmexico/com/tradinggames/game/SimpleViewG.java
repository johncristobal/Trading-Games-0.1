package nowoscmexico.com.tradinggames.game;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nowoscmexico.com.tradinggames.DataBase.ArticuloDao;
import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.GalleryActivity;
import nowoscmexico.com.tradinggames.user.UserActivity;
import nowoscmexico.com.tradinggames.R;

public class SimpleViewG extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public LinearLayout contactar;
    public TextView categoriaTV,descripcionTV;

    private SliderLayout mDemoSlider;

    public String foto,usuario;
    public int finalI;

    public List<TextSliderView> listatext;
    public HashMap<String,String> url_maps = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view_g);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        Intent intent = getIntent();
        String tit = intent.getStringExtra("nombre");
        foto = intent.getStringExtra("foto");
        String descripcion = intent.getStringExtra("descripcion");
        String categoria = intent.getStringExtra("categoria");
        usuario = intent.getStringExtra("usuario");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(tit);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "fonts/Helvetica Neu Bold.ttf");

        contactar = (LinearLayout)findViewById(R.id.contactarDueno);

        categoriaTV = (TextView)findViewById(R.id.textViewcategoria);
        descripcionTV = (TextView)findViewById(R.id.textViewdescripcion);

        categoriaTV.setTypeface(typeface);
        descripcionTV.setTypeface(typeface);

        categoriaTV.setText(categoria);
        descripcionTV.setText(descripcion);

        TextView cate1 = (TextView)findViewById(R.id.textView2);
        TextView desc1 = (TextView)findViewById(R.id.textView10);
        cate1.setTypeface(typefacebold);
        desc1.setTypeface(typefacebold);

        listatext = new ArrayList<>();//List<TextSliderView>();

        /*
        HashMap<String,Integer> url_maps = new HashMap<String, Integer>();
        url_maps.put("Hannibal", R.drawable.papermario);
        url_maps.put("Big Bang Theory", R.drawable.tloz);
        url_maps.put("House of Cards", R.drawable.mario);
        url_maps.put("Game of Thrones", R.drawable.starfox);
        */

        /*HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);*/


        /*ListView l = (ListView)findViewById(R.id.transformers);
        l.setAdapter(new TransformerAdapter(this));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(SimpleViewG.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        Sendthelast last = new Sendthelast();
        last.execute();

        //cargarFotos();
    }

    public void cargarFotos(){
        //upload images to server
        final ProgressDialog progress = new ProgressDialog(SimpleViewG.this);
        progress.setTitle("Información");
        progress.setMessage("Actualizando noticias...");
        //progress.setIcon(R.drawable.miituo);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        try {
            final String folderuser = usuario;
            String[] fotos = foto.split(",");

            final int tamanio = fotos.length;
            //Evitr craah en caso que no traiga fotos
            if (fotos.length >= 1) {
                //Aqui solo recuperamos una foto para mostrar en mainview
                for (int i = 0; i < fotos.length; i++) {
                    final String name = fotos[i];
                    final File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ File.separator+folderuser+"/"+name);
                    //url_maps.put("tit"+i, getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ File.separator+folderuser+"/"+name);

                    final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com").child(folderuser + "/" + name);

                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            url_maps.put("tit"+ finalI, uri.toString());
                            finalI++;

                            TextSliderView textSliderView = new TextSliderView(SimpleViewG.this);
                            // initialize a SliderLayout
                            final String storageDir = url_maps.get(name);

                            textSliderView
                                    //.description(name)
                                    .image("https://firebasestorage.googleapis.com/v0/b/tradinggames-a6047.appspot.com/o/GVVIIFiLN1TN0rgxBsM8QhYQ7uD2%2FGVVIIFiLN1TN0rgxBsM8QhYQ7uD2_mario_0.png?alt=media&token=e60a18d1-4c6c-4c1f-a1fd-ffe9a00c4bb6")
                                    //.image(uri.toString())
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(SimpleViewG.this);

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra",name);

                            listatext.add(textSliderView);
                            mDemoSlider.addSlider(textSliderView);
                            if(tamanio == finalI)
                                progress.dismiss();
                                /*Glide.with(GalleryActivity.this)
                                        .load(uri.toString())
                                        .apply(new RequestOptions().override(240, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                                        //.load(storageRef)
                                        .into(imgSelected);*/
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.trend) {
            // Handle the camera action
        } else if (id == R.id.searchgame) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Contactaar dueño
    public void contacto(View v){

        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = preferences.getString("sesion","null");

        if(sesion.equals("1")){
            //launch activity to contaact dueno...???
            //This is goint to be wahts or an internal chat???
        }
        else{
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("activity","contacto");
            startActivity(intent);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

//=================================GEt data from firebase===========================================
    //public void saygoodbye(){
    public class Sendthelast extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress = new ProgressDialog(SimpleViewG.this);
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        int tamanio;

        @Override
        protected void onPreExecute() {

            //borro local database
            //get num elements into articulo
            /*myRef.child("articulo").addListenerForSingleValueEvent(new ValueEventListener() {
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
            });*/

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
                final String folderuser = usuario;
                final String[] fotos = foto.split(",");

                tamanio = fotos.length;
                finalI = 0;
                //Evitr craah en caso que no traiga fotos
                if (fotos.length >= 1) {
                    //Aqui solo recuperamos una foto para mostrar en mainview
                    for (int i = 0; i < fotos.length; i++) {
                        final String name = fotos[i];
                        //url_maps.put("tit"+i, getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ File.separator+folderuser+"/"+name);
                        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tradinggames-a6047.appspot.com").child(folderuser + "/" + name);

                        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                // Use the bytes to display the image
                                Log.w("Imagen","YES");
                                try {

                                    final File f = new File(getFilesDir().getAbsolutePath()+"/pictures/"+folderuser+"/tit"+ fotos[finalI]+".jpg");
                                    File path = new File(getFilesDir().getAbsolutePath()+"/pictures/"+folderuser);
                                    if(!path.exists())
                                        path.mkdirs();

                                    FileOutputStream out = new FileOutputStream(f);
                                    out.write(bytes);
                                    out.close();

                                    url_maps.put("tit"+ finalI, f.getAbsolutePath());
                                    finalI++;

                                    TextSliderView textSliderView = new TextSliderView(SimpleViewG.this);
                                    // initialize a SliderLayout
                                    //final String storageDir = url_maps.get(name);

                                    textSliderView
                                            //.description(name)
                                            .image(f)
                                            .setScaleType(BaseSliderView.ScaleType.Fit)
                                            .setOnSliderClickListener(SimpleViewG.this);

                                    //add your extra information
                                    textSliderView.bundle(new Bundle());
                                    textSliderView.getBundle()
                                            .putString("extra","tit"+ finalI);

                                    //listatext.add(textSliderView);
                                    mDemoSlider.addSlider(textSliderView);
                                    if(tamanio == finalI) {

                                        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                        //mDemoSlider.setDuration(4000);
                                        mDemoSlider.addOnPageChangeListener(SimpleViewG.this);
                                        mDemoSlider.stopAutoCycle();

                                        //when clic on list, change type transformer
                                        mDemoSlider.setPresetTransformer("RotateUp");
                                        progress.dismiss();
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                    progress.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                Log.w("Imagen","NO");

                            }
                        });

                        /*storageRef.getFile(f).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                url_maps.put("tit"+ finalI, f.getAbsolutePath());
                                finalI++;

                                TextSliderView textSliderView = new TextSliderView(SimpleViewG.this);
                                // initialize a SliderLayout
                                final String storageDir = url_maps.get(name);

                                textSliderView
                                        //.description(name)
                                        .image(f.getAbsolutePath())
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(SimpleViewG.this);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra",name);

                                listatext.add(textSliderView);
                                mDemoSlider.addSlider(textSliderView);
                                if(tamanio == finalI) {

                                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                    //mDemoSlider.setDuration(4000);
                                    mDemoSlider.addOnPageChangeListener(SimpleViewG.this);
                                    mDemoSlider.stopAutoCycle();

                                    //when clic on list, change type transformer

                                    mDemoSlider.setPresetTransformer("RotateUp");
                                    progress.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors

                            }
                        });*/

                       /*storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                url_maps.put("tit"+ finalI, uri.toString());
                                finalI++;

                                TextSliderView textSliderView = new TextSliderView(SimpleViewG.this);
                                // initialize a SliderLayout
                                final String storageDir = url_maps.get(name);

                                textSliderView
                                        //.description(name)
                                        .image(uri.toString())
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(SimpleViewG.this);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra",name);

                                listatext.add(textSliderView);
                                mDemoSlider.addSlider(textSliderView);
                                if(tamanio == finalI) {

                                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                    //mDemoSlider.setDuration(4000);
                                    mDemoSlider.addOnPageChangeListener(SimpleViewG.this);
                                    mDemoSlider.stopAutoCycle();

                                    //when clic on list, change type transformer

                                    mDemoSlider.setPresetTransformer("RotateUp");
                                    progress.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });*/

                        /*storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                try {
                                    // Data for "images/island.jpg" is returns, use this as needed
                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                                    bos.write(bytes);
                                    bos.flush();
                                    bos.close();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                Log.e("Error",exception.toString());
                            }
                        });*/
                    }
                }
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
                /*for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(SimpleViewG.this);
                    // initialize a SliderLayout
                    final String storageDir = url_maps.get(name);

                    textSliderView
                            //.description(name)
                            .image(storageDir)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(SimpleViewG.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);

                    mDemoSlider.addSlider(textSliderView);
                }*/
                //progress.dismiss();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private String saveImage(ImageView imagev, String folderuser, String imageFileName) {

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
}
