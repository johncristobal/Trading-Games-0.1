package nowoscmexico.com.tradinggames_1.DataBase;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by vera_john on 20/02/17.
 */
public class WSTask extends AsyncTask<String,Void,String>{
    @Override
    protected String doInBackground(String... params) {

        try {
            Log.w("Here",params[0]);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            //myRef.setValue("Hello, World!");

            UsuarioDao user = new UsuarioDao("2","nombre","telefono","correo","pass","0");
            myRef.child("usuarios").child("2").setValue(user);

            String key = myRef.child("usuarios").push().getKey();
            //UsuarioDao user2 = new UsuarioDao("2","nombre","telefono","correo","pass","0");

            ArticuloDao dao = new ArticuloDao("1","titulo","descripcion","cate","path","tiempo","11");
            myRef.child("articulo").child("1").setValue(dao);
            String keyart = myRef.child("articulo").push().getKey();

            Log.w("key",key);
            Log.w("keyart",keyart);


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
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    UsuarioDao value = dataSnapshot.getValue(UsuarioDao.class);
                    Log.d("DB", "Value is: " + value.getId());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("DB", "Failed to read value.", error.toException());
                }
            });

            return "1";
        }catch(Exception e){
            e.printStackTrace();
            return "0";

        }
    }
}
