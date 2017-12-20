package nowoscmexico.com.tradinggames.DataBase;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Map;

/**
 * Created by vera_john on 20/02/17.
 * Add to Firebase :)
 */
public class WSTask extends AsyncTask<String,Void,String>{

    byte[] dataimagen;
    Context context;

    String result = "";

    public WSTask(Context c){
        //dataimagen = dataimagen2;
        context = c;
    }

    public WSTask(){
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String table = params[0];
            Log.w("Here",table);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            //Thread.sleep(3000);

            switch (table){

                /*Agregar usuario a DB firebase*/
                case modelBase.FeedEntryUsuario.TABLE_NAME:
                    //myRef.setValue("Hello, World!");

                    String newPostKey = myRef.child("usuarios").push().getKey();
                    Log.w("key",newPostKey);
                    UsuarioDao user = new UsuarioDao(newPostKey,params[1],"telefono",params[2],params[3],"1");
                    Map<String, Object> postValues = user.toMap();
                    myRef.child("usuarios").child(newPostKey).updateChildren(postValues);

                    return newPostKey;

                /*Agrega articulo a DB*/
                case modelBase.FeedEntryArticle.TABLE_NAME:

                    //String keyArticle = myRef.child("articulo").push().getKey();
                    //Log.w("key",keyArticle);
                    String keyArticle = myRef.child("articulo").push().getKey();
                    ArticuloDao article = new ArticuloDao(keyArticle,"",params[1],params[2],params[3],params[4],params[5],params[6]);
                    Map<String, Object> postValuesArticle = article.toMap();
                    myRef.child("articulo").child(keyArticle).updateChildren(postValuesArticle);

                    //now...lets try upload an image to firebase...
                    // Create a storage reference from our app
                    //use the keyarticle to set the reference with the article....create the bucket

                    String fotos = params[4];
                    String iduser = params[6];

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/");

                    String [] todasfotos = fotos.split(",");
                    for(int i=0; i<todasfotos.length;i++){
                        //here a have the filename from the pictures....
                        //gt each image from stotraga
                        File image = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+todasfotos[i]);
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

                    return keyArticle;
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

    /*@Override
    protected void onPostExecute(String s) {
        progress.dismiss();

        if(!result.equals("")){
            Intent i = new Intent(context,GamesActivity.class);
            context.startActivity(i);
        }else{
            Log.w("Warning","Error al subir informacion");
        }
    }*/


}
