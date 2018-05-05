package nowoscmexico.com.tradinggames.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import nowoscmexico.com.tradinggames.DataBase.ArticuloDao;
import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.DataBase.modelBase;
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

    public String tag,_id;

    public File photoFile = null;
    public String mCurrentPhotoPath,idfirebase;
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

        //TextView titulo = (TextView)findViewById(R.id.textViewTitle);
        //titulo.setTypeface(typeface);
        Button b4 = (Button)findViewById(R.id.button4);
        b4.setTypeface(typeface);

        SharedPreferences shared = getSharedPreferences(getString(R.string.sharedName),MODE_PRIVATE);
        iduser = shared.getString("idusuario","null");

        Intent intent = getIntent();
        String tit = intent.getStringExtra("nombre");
        String foto = intent.getStringExtra("foto");
        String _descripcion = intent.getStringExtra("descripcion");
        String _categoria = intent.getStringExtra("categoria");
        _id = intent.getStringExtra("id");
        idfirebase = intent.getStringExtra("idfirebase");
        String _iduser = intent.getStringExtra("idusuario");

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
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + _iduser + "/" + fotos[i])
                                .apply(new RequestOptions().override(150, 200).centerCrop())//.override(150,200)
                                .into(imagenup1);

                        //imagenup1.setTag("1");
                        break;
                    case 1:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + _iduser + "/" + fotos[i])
                                .apply(new RequestOptions().override(150, 200).centerCrop())//.override(150,200)
                                .into(imagenup2);
                        //imagenup2.setTag("2");

                        break;
                    case 2:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + _iduser + "/" + fotos[i])
                                .apply(new RequestOptions().override(150, 200).centerCrop())//.override(150,200)
                                .into(imagenup3);
                        //imagenup3.setTag("3");
                        break;
                    case 3:
                        imagenes[i] = true;
                        Glide.with(UpdateGame.this)
                                .load(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + _iduser + "/" + fotos[i])
                                .apply(new RequestOptions().override(150, 200).centerCrop())//.override(150,200)
                                .into(imagenup4);
                        //imagenup4.setTag("4");

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

        /*
            El usuario podra modificar los datos que quiera
            En el caso de las fotos...al darle clic:
                Si tiene foto - preguntar si desea tomar otra foto y borrar actual
                No tiene foto, abrir camara normal para caputar imagen

            Clic en actualizar...local y en firebase
                - busco refrencia y actualio la info
                - busco database y actualizo imagenes...subo de nuevo
        */
    }

    //update picture
    public void pictureUpdate(View v){

        String tag = v.getTag().toString();

        if(imagenes[Integer.parseInt(tag)]){    //this means its true...has an image
            pictureSelectWithImage(v);
        }else{//open camera and get new image
            cameraIntent();
        }
    }

    //send datao to update
    public void updateGame(View v){
        name = nombre.getText().toString();
        desc = descripcion.getText().toString();
        catego = "categoria";
        if(name.equals("")){
            nombre.setFocusable(true);
            Toast.makeText(this,"Coloque nombre del juego",Toast.LENGTH_SHORT).show();
            return;
        }if(desc.equals("")){
            descripcion.setFocusable(true);
            Toast.makeText(this,"Coloque descripción del juego",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!sinimagenes()){
            Toast.makeText(this,"Suba al menos una imagen",Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            //starrt progress
            //progress.show();

            //Save the images names ftom the imageview to App folder
            saygoodbye();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saygoodbye(){
        AsyncTask<Void, Void, Void> sendthelast = new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(UpdateGame.this);
            String ErrorCode = "";

            @Override
            protected void onPreExecute() {
                progress.setTitle("Actualizando");
                progress.setMessage("Subiendo información...");
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
                    //res = api.ConfirmReportLast("ReportOdometer/Confirmreport");

                    String fotofull = saveImages(iduser);

                    //get date to save gmae
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String datestring = (dateFormat.format(date)); //2016/11/16 12:08:43

                    //Firstly update data into localabtabase
                    String res = insertarLocal(modelBase.FeedEntryArticle.TABLE_NAME, name, desc, catego, fotofull, datestring,iduser);
                    //String res = "1";
                    if(res.equals("-1")){
                        Toast.makeText(UpdateGame.this,"Error al guardar información. \n Intente más tarde.", Toast.LENGTH_SHORT).show();
                    }else{
                        //Si se actualizo localmente,,,,intentammos guardar WS
                        //Create task to send Data to DB --- WS
                        //WSTask task = new WSTask(AddGame.this);
                        ws = insertarFB(modelBase.FeedEntryArticle.TABLE_NAME, name, desc, catego,fotofull,datestring,iduser);

                        //Validar si ws ejecutado correctamente...
                        //String ws = "1";
                /*
                QUE REGRESARA firebase en caso de error???
                 */
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                /*if (res.equals("false") || res.equals("true")) {

                    new android.app.AlertDialog.Builder(LastOdometerActivity.this)
                            .setTitle("Odómetro Reportado")
                            .setMessage("El odómetro fue reportado con exito")
                            .setIcon(R.drawable.miituo)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //progresslast.dismiss();
                                    //finalizamos....
                                    //IinfoClient.getInfoClientObject().getPolicies().setReportState(13);

                                    Intent i = new Intent(LastOdometerActivity.this, SyncActivity.class);
                                    startActivity(i);
                                }
                            })
                            .show();
                }else{
                    new android.app.AlertDialog.Builder(LastOdometerActivity.this)
                            .setTitle("Odómetro no reportado")
                            .setMessage("Problema al reportar odómetro, intente más tarde.")
                            .setIcon(R.drawable.miituo)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //progresslast.dismiss();
                                    Intent i = new Intent(LastOdometerActivity.this, SyncActivity.class);
                                    startActivity(i);
                                }
                            })
                            .show();
                }*/

                progress.dismiss();

                if(!ws.equals("0") || !ws.equals("")){
                    new android.app.AlertDialog.Builder(UpdateGame.this)
                            .setTitle("Nivel completo!")
                            .setMessage("Tu juego está en línea ahora.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //progresslast.dismiss();
                                    //finalizamos....
                                    //IinfoClient.getInfoClientObject().getPolicies().setReportState(13);

                                    Intent i = new Intent(UpdateGame.this,GamesActivity.class);
                                    startActivity(i);
                                }
                            })
                            .show();

                }else{
                    new android.app.AlertDialog.Builder(UpdateGame.this)
                            .setTitle("Game Over!")
                            .setMessage("No pudimos completar la operación. Intenta más tarde.")
                            .setCancelable(false)
                            //.setIcon(R.drawable.)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //progresslast.dismiss();
                                    //finalizamos....
                                    //IinfoClient.getInfoClientObject().getPolicies().setReportState(13);

                                    Intent i = new Intent(UpdateGame.this,GamesActivity.class);
                                    startActivity(i);
                                }
                            })
                            .show();
                }
            }

            private String insertarFB(String tableName, String name, String desc, String catego, String fotofull, String datestring, String iduse) {

                try {
                    String table = tableName;
                    Log.w("Here",table);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    //Thread.sleep(3000);

                    switch (table){
                    /*Agrega articulo a DB*/
                        case modelBase.FeedEntryArticle.TABLE_NAME:

                            //String keyArticle = myRef.child("articulo").push().getKey();
                            //Log.w("key",keyArticle);
                            //myRef.child("articulo").child(idfirebase).child("categoria").setValue(catego);

                            //String keyArticle = myRef.child("articulo").child(idfirebase).push().getKey();
                            ArticuloDao article = new ArticuloDao(_id,name,idfirebase,desc,catego,fotofull,datestring,iduse);
                            Map<String, Object> postValuesArticle = article.toMap();
                            myRef.child("articulo").child(idfirebase).updateChildren(postValuesArticle);

                            //now...lets try upload an image to firebase...
                            // Create a storage reference from our app
                            //use the keyarticle to set the reference with the article....create the bucket

                            String fotos = fotofull;
                            String iduser = iduse;

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/");

                            String [] todasfotos = fotos.split(",");
                            for(int i=0; i<todasfotos.length;i++){
                                //here a have the filename from the pictures....
                                //gt each image from stotraga
                                File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+todasfotos[i]);
                                Uri uri = Uri.fromFile(image);

                                // Create file metadata including the content type
                                StorageMetadata metadata = new StorageMetadata.Builder()
                                        .setContentType("image/png")
                                        .build();

                                StorageReference riversRef = storageRef.child(iduser+"/"+image.getName());
                                //UploadTask uploadTask = riversRef.putBytes(image,metadata);
                                UploadTask uploadTask = riversRef.putFile(uri,metadata);

                                // Register observers to listen for when the download is done or if it fails
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Log.e("Error","Algo fallo");
                                        exception.printStackTrace();
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                                    }
                                });
                            }


                            // Create a reference to "mountains.jpg"
                            //StorageReference mountainsRef = storageRef.child("mountains.jpg");

                            //Uri uri = Uri.parse("android.resource://nowoscmexico.com.tradinggames_1/drawable/fifa.jpg");
                    /*String filename = "yy2.png";
                    String path = Environment.getExternalStorageDirectory()+"/Pictures/" + filename;
                    File f = new File(path);  //
                    Uri uri = Uri.fromFile(f);
                    */
                            // Create a reference to 'images/mountains.jpg'
                            //StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg").putFile(,metadata);

                            //UploadTask uploadTask = mountainsRef.putBytes(data);

                            return idfirebase;
                    }

                    //myRef.child("usuarios").child("2").setValue(user);

                    //String key = myRef.child("usuarios").push().getKey();
                    //UsuarioDao user2 = new UsuarioDao("2","nombre","telefono","correo","pass","0");

            /*ArticuloDao dao = new ArticuloDao("1","titulo","descripcion","cate","path","tiempo","11");
            myRef.child("articulo").child("1").setValue(dao);
            String keyart = myRef.child("articulo").push().getKey();

            Log.w("key",key);
            Log.w("keyart",keyart);*/


            /*myRef.child("usuarios").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UsuarioDao value = dataSnapshot.getValue(UsuarioDao.class);
                    Log.d("DB", "Value is: " + value.getId());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

                    // Read from the database
            /*myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try {
                        UsuarioDao value = dataSnapshot.getValue(UsuarioDao.class);
                        Log.d("DB", "Value is: " + value.getId());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("DB", "Failed to read value.", error.toException());
                }
            });*/

                    //return "1";
                }catch(Exception e){
                    e.printStackTrace();
                    return "0";
                }

                return "1";
            }
            @NonNull
            private String insertarLocal(String tableName, String name, String desc, String catego, String fotofull, String datestring, String iduser) {
                String val = tableName;
                Log.w("Here",val);

                switch (val){

                    case modelBase.FeedEntryArticle.TABLE_NAME:
                        // Gets the data repository in write mode
                        SQLiteDatabase db = DBaseMethods.base.getWritableDatabase();

                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        //values.put(modelBase.FeedEntryArticle.COLUMN_ID, strings[6]);
                        values.put(modelBase.FeedEntryArticle.COLUMN_TITULO, name);
                        values.put(modelBase.FeedEntryArticle.COLUMN_DESCRIPCION, desc);
                        values.put(modelBase.FeedEntryArticle.COLUMN_CATE, catego);
                        values.put(modelBase.FeedEntryArticle.COLUMN_FOTO, fotofull);
                        values.put(modelBase.FeedEntryArticle.COLUMN_TIME, datestring);
                        values.put(modelBase.FeedEntryArticle.COLUMN_FKUser, iduser);

                        // Insert the new row, returning the primary key value of the new row
                        //Just change name table and the values....
                        long newRowId = db.insert(val, null, values);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return "" + newRowId;

                    default:
                        break;
                }

                return "";
            }

        };

        sendthelast.execute();
    }

    public boolean sinimagenes(){

        int cont = 0;
        for(int i=0;i<imagenes.length;i++){
            if(!imagenes[i]){
                cont++;
            }
        }
        //no hay ninguna imagen...debe subir al menos una
        if(cont == 4){
            return false;
        }
        else{
            return true;
        }
    }

//***********************Recuperamos foto tomada de la camara y mostramos*********************************
    private void onCaptureImageResult(Intent data) {
        /*Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        mCurrentPhotoPath = preferences.getString("nombrefoto", "null");

        String filePath = mCurrentPhotoPath;//photoFile.getPath();
        //Bitmap bmp = BitmapFactory.decodeFile(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        Bitmap thumbnail = BitmapFactory.decodeFile(filePath,options);

        /*String filePath = mCurrentPhotoPath;//photoFile.getPath();
        Bitmap bmp = BitmapFactory.decodeFile(filePath);

        Bitmap thumbnail = Bitmap.createScaledBitmap(bmp, bmp.getWidth()/2, bmp.getHeight()/2, false);*/

        if(tag.equals("1")) {
            imagenes[Integer.parseInt(tag)] = true;
            imagenup1.setImageBitmap(thumbnail);
        }
        else if(tag.equals("2")) {
            imagenes[Integer.parseInt(tag)] = true;
            imagenup2.setImageBitmap(thumbnail);
        }
        else if(tag.equals("3")) {
            imagenes[Integer.parseInt(tag)] = true;
            imagenup3.setImageBitmap(thumbnail);
        }
        else if(tag.equals("4")) {
            imagenes[Integer.parseInt(tag)] = true;
            imagenup4.setImageBitmap(thumbnail);
        }
        /*(else if(tag.equals("5"))
            imagenup5.setImageBitmap(thumbnail);
        else if(tag.equals("6"))
            imagenup6.setImageBitmap(thumbnail);*/
    }

//***********************save iage to path folder app***********************************************
    private File createImageFile(String username, String tag,String gamaname) throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String imageFileName = username;

        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+username+"_"+gamaname+"_"+tag+".png");
        /*File image = File.createTempFile(
                username+tag,  // prefix
                ".png",         // suffix
                storageDir      // directory
        );*/

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        //save name jajaja
        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombrefoto",mCurrentPhotoPath);
        editor.commit();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AddGame.Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals(getString(R.string.tomafoto)))
                        cameraIntent();
                    else if(userChoosenTask.equals(getString(R.string.eliminarfoto))) {
                        //galleryIntent();
                        //lo mismo que borrar que esta mas abajo...
                        //borrarfoto();
                    }
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

//***********************Recuperamos foto seleccionadad y mostramos*********************************
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(tag.equals("1"))
            imagenup1.setImageBitmap(bm);
        else if(tag.equals("2"))
            imagenup2.setImageBitmap(bm);
        else if(tag.equals("3"))
            imagenup3.setImageBitmap(bm);
        else if(tag.equals("4"))
            imagenup4.setImageBitmap(bm);
        /*else if(tag.equals("5"))
            imagenup5.setImageBitmap(bm);
        else if(tag.equals("6"))
            imagenup6.setImageBitmap(bm);*/
    }

//***********************Abrir camara para tomar foto***********************************************
    private void cameraIntent()
    {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, REQUEST_CAMERA);

        Intent takepic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(i, FRONT_VEHICLE);
        if (takepic.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile(iduser,tag,name);
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = FileProvider.getUriForFile(VehiclePictures.this, "miituo.com.miituo", photoFile);
                Uri photoURI = Uri.fromFile(photoFile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takepic, REQUEST_CAMERA);
            }
        }
    }

    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

/*
* open galley or camera to add picture...
* */
    public void pictureSelectWithImage(View v){
        tag  = v.getTag().toString();

        Log.w("Tag",tag);

        name = nombre.getText().toString();

        if(name.equals("")){
            Toast.makeText(this,"Ponle un nombre para poder guardar la foto",Toast.LENGTH_SHORT).show();
        }else {
            final CharSequence[] items = {getString(R.string.nuevafoto), getString(R.string.eliminarfoto),
                    "Cancelar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Agregar imagen");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = AddGame.Utility.checkPermission(UpdateGame.this);
                    if (items[item].equals(getString(R.string.nuevafoto))) {
                        userChoosenTask = getString(R.string.nuevafoto);
                        if (result)
                            cameraIntent();
                    } else if (items[item].equals(getString(R.string.eliminarfoto))) {
                        userChoosenTask = getString(R.string.eliminarfoto);
                        if (result) {
                            borrarFoto(tag);

                            //borrar foto de la vista
                            //eliminarla de firebase
                            //eliminarla del path con comas
                        }
                    } else if (items[item].equals("Cancelar")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    public void borrarFoto(String t){
        imagenes[Integer.parseInt(t)] = false;

        int i = Integer.parseInt(t);

        switch (i){
            case 0:
                Glide.with(UpdateGame.this)
                        .load(R.drawable.camicon)
                        .into(imagenup1);
                break;
            case 1:
                Glide.with(UpdateGame.this)
                        .load(R.drawable.camicon)
                        .into(imagenup2);
                break;
            case 2:
                Glide.with(UpdateGame.this)
                        .load(R.drawable.camicon)
                        .into(imagenup3);
                break;
            case 3:
                Glide.with(UpdateGame.this)
                        .load(R.drawable.camicon)
                        .into(imagenup4);
                break;

            default:break;
        }
    }

    //--------------recupera nombre de fotos con ','----------------------------------------------------
    private String saveImages(String iduser) {

        String todaslasfotos = "";

        for(int i=1;i<=imagenes.length;i++){
            //get the image -- save as iduser+i.png into App folder

            int index = i-1;
            if(imagenes[index])
                todaslasfotos += iduser+"_"+name+"_"+i+".png,";
        }

        return todaslasfotos;
    }
}
