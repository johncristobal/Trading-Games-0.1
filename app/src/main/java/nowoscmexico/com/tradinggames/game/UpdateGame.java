package nowoscmexico.com.tradinggames.game;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import nowoscmexico.com.tradinggames.GalleryActivity;
import nowoscmexico.com.tradinggames.R;

public class UpdateGame extends AppCompatActivity {

    public String name,desc,catego,ws;

    public EditText nombre;
    public EditText descripcion;
    public Spinner categoria;

    public boolean[] imagenes = {false,false,false,false};

    public Drawable temp,temp2,temp3,temp4;//,temp5,temp6;

    private ImageView imagenup1,imagenup2,imagenup3,imagenup4;//,imagenup5,imagenup6;

    public byte[] dataimagen;
    private String userChoosenTask, iduser;
    private int REQUEST_CAMERA = 10;
    private int SELECT_FILE = 20;

    public String tag;

    public File photoFile = null;
    public String mCurrentPhotoPath;
    public ArrayList<ImageView> imagenesReales;
    private int FRONT_VEHICLE = 1;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        //get variables
        nombre = (EditText)findViewById(R.id.editTextnombre);
        descripcion = (EditText)findViewById(R.id.editTextdescripcion);
        categoria = (Spinner) findViewById(R.id.spinnercategoria);

        nombre.setTypeface(typeface);
        descripcion.setTypeface(typeface);

        //imagenup = (ImageView)findViewById(R.id.imagenup);
        imagenup1 = (ImageView)findViewById(R.id.imageViewfoto1);
        imagenup2 = (ImageView)findViewById(R.id.imageViewfoto2);
        imagenup3 = (ImageView)findViewById(R.id.imageViewfoto3);
        imagenup4 = (ImageView)findViewById(R.id.imageViewfoto4);

        TextView titulo = (TextView)findViewById(R.id.textViewTitle);
        titulo.setTypeface(typeface);
        Button b4 = (Button)findViewById(R.id.button4);
        b4.setTypeface(typeface);

        SharedPreferences shared = getSharedPreferences(getString(R.string.sharedName),MODE_PRIVATE);
        iduser = shared.getString("idusuario","null");

        Intent intent = getIntent();
        String tit = intent.getStringExtra("nombre");
        String foto = intent.getStringExtra("foto");
        String _descripcion = intent.getStringExtra("descripcion");
        String _categoria = intent.getStringExtra("categoria");
        String _id = intent.getStringExtra("id");

        //categoria......pendiente

        //precargamos la info para si el usuario quiere...la borre y
        nombre.setText(tit);
        descripcion.setText(_descripcion);
        String [] fotos = foto.split(",");
        if(fotos.length>0) {
            for (int i = 0; i < fotos.length; i++){
                switch (i){
                    case 0:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + iduser + "/" + fotos[i])
                                .into(imagenup1);
                        break;
                    case 1:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + iduser + "/" + fotos[i])
                                .into(imagenup2);
                        break;
                    case 2:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + iduser + "/" + fotos[i])
                                .into(imagenup3);
                        break;
                    case 3:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + iduser + "/" + fotos[i])
                                .into(imagenup4);
                        break;

                    default:break;
                }
            }
        }
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}
