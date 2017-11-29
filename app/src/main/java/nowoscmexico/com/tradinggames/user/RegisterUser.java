package nowoscmexico.com.tradinggames.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.LogInActivity;
import nowoscmexico.com.tradinggames.R;

public class RegisterUser extends AppCompatActivity {

    public EditText nombre;
    public EditText correo;
    public EditText pass;
    public EditText confirma;
    public EditText tlefono;
    public CheckBox condiciones;
    public TextView pathAvatar;
    public TextView chooseLocation;
    public AppCompatSpinner horarios;

    public String lastActivity;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent i = getIntent();
        lastActivity = i.getStringExtra("activity");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Vatiables
        nombre =(EditText)findViewById(R.id.editTextName);
        correo =(EditText)findViewById(R.id.editTextcorreoo);
        pass =(EditText)findViewById(R.id.editTextpassword);
        confirma =(EditText)findViewById(R.id.editTextconfirma);
        tlefono =(EditText)findViewById(R.id.editTextTelefono);
        condiciones = (CheckBox)findViewById(R.id.checkBox);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        nombre.setTypeface(custom_font);
        correo.setTypeface(custom_font);
        pass.setTypeface(custom_font);
        confirma.setTypeface(custom_font);
        tlefono.setTypeface(custom_font);
        condiciones.setTypeface(custom_font);

        Button b1 = (Button)findViewById(R.id.button2);
        b1.setTypeface(custom_font);

        TextView leer = (TextView)findViewById(R.id.textView7);
        leer.setTypeface(custom_font);
        //Textoview to show another antivities...
        //chooseLocation = (TextView)findViewById(R.id.textViewlocation);

        //Inicializas horariros
        //horarios = (AppCompatSpinner)findViewById(R.id.viewSpinnerHorarios);
        /*ArrayList<String> horas = new ArrayList<>();
        horas.add("Hora de comida");
        horas.add("Antes de las 12 pm");
        horas.add("Entre 12 y 1 pm");
        horas.add("Entre 1 y 2 pm");
        horas.add("Entre 2 y 3 pm");
        horas.add("Entre 3 y 4 pm");
        horas.add("Después de las 4 pm");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,horas);
        horarios.setAdapter(adapter);*/

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("In", "onAuthStateChanged:signed_in:" + user.getUid());
                    //guardo id en sharedpreferences
                    SharedPreferences shared = getSharedPreferences(getString(R.string.sharedName),MODE_PRIVATE);
                    shared.edit().putString("idusuario",user.getUid()).apply();
                    shared.edit().putString("sesion","1").apply();

                    //gellaeryactivity para ver juegos
                    Intent intent = new Intent(RegisterUser.this,LogInActivity.class);
                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.d("Out", "onAuthStateChanged:signed_out");
                    //Toast.makeText(RegisterUser.this,"Error al guardar información. \n Intente más tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        };
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

    //Opencondciones
    public void openCondicionesPage(View v){

    }

    //validate uinfo and show map
    public void validateInfoandInit(View v){

        //Validate info and launch activity
        //Save it in WS :>
        String name = nombre.getText().toString();
        String mail = correo.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = confirma.getText().toString();
        String telefono = tlefono.getText().toString();

        //get data from celular to send
        String modelo = "modelo";
        //location...ammmm...not at this time
        //String ubicacion = "0,0";
        //get phone number....

        if(pass1.equals("")){
            pass.setFocusable(true);
            Toast.makeText(this,"Coloque una constraseña.",Toast.LENGTH_LONG).show();
            return;
        }

        if(!pass1.equals(pass2)){
            pass.setFocusable(true);
            Toast.makeText(this,"Las contraseñas no coinciden. Favor de verificar.",Toast.LENGTH_LONG).show();
            return;
        }

        if(name.equals("")){
            nombre.setFocusable(true);
            Toast.makeText(this,"Coloque su nombre o algún alias",Toast.LENGTH_LONG).show();
            return;
        }

        if(mail.equals("")){
            correo.setFocusable(true);
            Toast.makeText(this,"Coloque un correo",Toast.LENGTH_LONG).show();
            return;
        }

        if(!condiciones.isChecked()){
            Toast.makeText(this,"Acepta los términos y condiciones",Toast.LENGTH_LONG).show();
            return;
        }

        //openDB to save Data
        try{
            DBaseMethods.ThreadDBInsert insert = new DBaseMethods.ThreadDBInsert();
            String res = insert.execute(modelBase.FeedEntryUsuario.TABLE_NAME, name, mail, telefono, pass1).get();
            //String res = "1";
            if(res.equals("-1")){
                Toast.makeText(this,"Error al guardar información. \n Intente más tarde.", Toast.LENGTH_SHORT).show();
            }else{
                //Si se guardo localmente,,,,intentammos guardar WS
                //Create task to send Data to DB --- WS
                //WSTask task = new WSTask();
                //String ws = task.execute(modelBase.FeedEntryUsuario.TABLE_NAME, name, mail, pass1,"1").get();

                //Create user
                mAuth.createUserWithEmailAndPassword(mail, pass1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Completo", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterUser.this, "Error al iniciar sesión",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }catch(Exception e){}
        //Create JSon with Data
        //Send json to WS
        //WS get json and save data to DB server
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
