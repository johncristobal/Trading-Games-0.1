/*
* Activity to star session
* */
package nowoscmexico.com.tradinggames.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nowoscmexico.com.tradinggames.GalleryActivity;
import nowoscmexico.com.tradinggames.R;
import nowoscmexico.com.tradinggames.game.MatchActivity;

public class UserActivity extends AppCompatActivity {

    public String lastActivity;

    public EditText user,pass;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public TextView titulo,contrasena,registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Iniciar sesión");
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        lastActivity = i.getStringExtra("activity");

        user = (EditText)findViewById(R.id.editTextcorreoo);
        pass = (EditText)findViewById(R.id.editTextpass);

        titulo = (TextView)findViewById(R.id.textViewTitulo);
        contrasena = (TextView)findViewById(R.id.textViewContrasena);
        registrate = (TextView)findViewById(R.id.textView);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        user.setTypeface(custom_font);
        pass.setTypeface(custom_font);
        titulo.setTypeface(custom_font);
        contrasena.setTypeface(custom_font);
        registrate.setTypeface(custom_font);

        Button btemp = (Button)findViewById(R.id.buttonIn);
        btemp.setTypeface(custom_font);

        mAuth = FirebaseAuth.getInstance();

        //auth listener to lauch actvuty
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("in", "onAuthStateChanged:signed_in:" + user.getUid());
                    //uSE Sharedpereferences to save user sesion
                    if (user.getUid().equals("WII3oNBHEgcLJNcrB7GBy2GhmNF3")){
                        //Usuaario por defecto par aleer datos de forma asegura...
                        user = null;
                    }else {

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("sesion", "1");
                        editor.putString("idusuario", user.getUid());//.apply();

                        editor.commit();

                        //When session opened, you have to show a differentes menu
                        if (lastActivity.equals("trends")) {
                            //jUST SHOW TRENDS
                            Intent i = new Intent(UserActivity.this, GalleryActivity.class);
                            startActivity(i);
                        } else if (lastActivity.equals("match")) {
                            //TLaunch activty to save wish list
                            Intent i = new Intent(UserActivity.this, MatchActivity.class);
                            startActivity(i);
                        } else if (lastActivity.equals("contacto")) {
                            //launch activity to contact dueno
                            Intent i = new Intent(UserActivity.this, GalleryActivity.class);
                            startActivity(i);
                        }
                    }
                } else {
                    // User is signed out
                    Log.d("out", "onAuthStateChanged:signed_out");
                    //Toast.makeText(UserActivity.this,"Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void sesionOpened(View v){
        //User registered--- launch new activity
        Intent i = new Intent(this,RegisterUser.class);
        i.putExtra("activity",lastActivity);
        startActivity(i);
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

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

    public void entrarSesion(View v){

        //Validate information
        if(user.getText().toString().equals("")){
            Toast.makeText(this,"Favor de colocar correo",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.getText().toString().equals("")){
            Toast.makeText(this,"Favor de colocar contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

        //firebase to authenticate user
        mAuth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Completo", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Error", "signInWithEmail:failed", task.getException());
                            Toast.makeText(UserActivity.this, "Error al iniciar sesión",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
