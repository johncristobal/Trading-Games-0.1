package nowoscmexico.com.tradinggames.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import nowoscmexico.com.tradinggames.DataBase.ArticuloDao;
import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.R;

/**
 * Created by vera_john on 26/12/16.
 */

public class GamesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Object> games;
    private static LayoutInflater inflater = null;
    private Typeface tipo;

    public GamesAdapter(Context c, ArrayList<Object> p, Typeface t){
        context = c;
        games = p;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tipo = t;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;

        final int pos = i;

        if (vi == null)
            vi = inflater.inflate(R.layout.rowgame, null);


        TextView name = (TextView) vi.findViewById(R.id.textViewCustomName);
        TextView cate = (TextView) vi.findViewById(R.id.textViewCustomCategoria);

        name.setTypeface(tipo);
        cate.setTypeface(tipo);

        final ArticuloDao dao = (ArticuloDao) games.get(i);

        name.setText(dao.getTitulo());
        cate.setText(dao.getDescripcion());

        //get the imagesbutton to set methods
        ImageView delete = (ImageView)vi.findViewById(R.id.imageViewDeleteItem);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //first creat ealert to confirm deleting game
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Game Over!");
                    builder.setMessage("¿Deseas eliminar el juego de tu lista?");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //delete the game locally...
                            DBaseMethods.ThreadDBDelete delete1 = new DBaseMethods.ThreadDBDelete();
                            String cad = null;
                            try {
                                cad = delete1.execute(modelBase.FeedEntryArticle.TABLE_NAME,dao.getId()).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            if(cad.equals("-1") || cad == null){
                                Toast.makeText(context,"Error eliminando juego. Intenta más tarde.", Toast.LENGTH_LONG).show();
                                //error
                            }else{
                                Toast.makeText(context,"Eliminado...", Toast.LENGTH_LONG).show();
                                //now delete the game form firebase...
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();
                                //ArticuloDao article = new ArticuloDao(_id,name,idfirebase,desc,catego,fotofull,datestring,iduse);
                                //Map<String, Object> postValuesArticle = article.toMap();
                                myRef.child("articulo").child(dao.getIdfirebase()).removeValue();

                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReferenceFromUrl("gs://tradinggames-a6047.appspot.com/");

                                String [] todasfotos = dao.getFoto().split(",");
                                for(int j=0; j<todasfotos.length;j++){
                                    //here a have the filename from the pictures....
                                    //gt each image from stotraga
                                    //File image = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+todasfotos[i]);
                                    //Uri uri = Uri.fromFile(image);

                                    StorageReference riversRef = storageRef.child(dao.getIdusuario()+"/"+todasfotos[j]);
                                    //StorageReference riversRef = storageRef.child(dao.getIdusuario()+"/"+image.getName());
                                    //UploadTask uploadTask = riversRef.putBytes(image,metadata);
                                    riversRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }

                                Intent intent = new Intent(context, GamesActivity.class);
                                intent.putExtra("activity","games");
                                context.startActivity(intent);
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alerta = builder.create();
                    alerta.show();


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        ImageView update = (ImageView)vi.findViewById(R.id.imageView6);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context,UpdateGame.class);
                i.putExtra("nombre",dao.getTitulo());
                i.putExtra("descripcion",dao.getDescripcion());
                i.putExtra("categoria",dao.getCategoria());
                i.putExtra("foto",dao.getFoto());
                i.putExtra("id",dao.getId());
                i.putExtra("idfirebase",dao.getIdfirebase());
                i.putExtra("idusuario",dao.getIdusuario());

                context.startActivity(i);

                //abrir vista (single view to edit)
                //Toast.makeText(context,"updating game: "+pos, Toast.LENGTH_LONG).show();
            }
        });

        return vi;

    }
}
